package com.qiyuan.web.service;

import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.config.GomypayLogisticsProperties;
import com.qiyuan.web.dao.ShippingTrackingMapper;
import com.qiyuan.web.dto.C2CLogisticsCreateRawResponseDto;
import com.qiyuan.web.dto.ExpressCreateResult;
import com.qiyuan.web.dto.request.ExpressDeliveryRequest;
import com.qiyuan.web.dto.request.StorePickupRequest;
import com.qiyuan.web.entity.ShippingTracking;
import com.qiyuan.web.entity.example.ShippingTrackingExample;
import com.qiyuan.web.enums.OrderStatus;
import com.qiyuan.web.util.JsonUtil;
import com.qiyuan.web.util.Md5Util;
import com.qiyuan.web.util.RandomGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class GomypayLogisticsService {

	private final RestTemplate restTemplate;
	private final GomypayLogisticsProperties props;
	private final ShippingTrackingMapper shippingTrackingMapper;

	@Value("${ap.backend}")
	private String backendBaseUrl;

	/**
	 * 成立超商物流訂單
	 */
	public String createStorePickupOrder(StorePickupRequest req) {
		String storeCreateUri = props.getStoreCreateUri();
		String callbackUrl = backendBaseUrl + "/logistics/callback";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("Vendororder", req.getOrderId()); // 客戶訂單編號
		params.add("mode", "C2C"); // 物流方式
		params.add("EshopId", props.getCustomerId()); // 客戶代號
		params.add("StoreId", req.getStoreId()); // 門市代號
		params.add("Amount", req.getAmount().setScale(0, RoundingMode.DOWN).toPlainString()); // 交易金額
		params.add("ServiceType", "3"); // 服務型態代碼 //1- 取貨付款3- 取貨不付款退貨通5- 退貨付款4- 退貨不付款//通路代號 1:全家 2:萊爾富3: 統一超商 4.OK 超商
		params.add("OrderAmount", req.getOrderAmount().setScale(0, RoundingMode.DOWN).toPlainString()); // 商品價值
		params.add("SenderName", req.getSenderName()); // 寄件人姓名
		params.add("SendMobilePhone", req.getSendMobilePhone()); // 寄件人手機電話
		params.add("ReceiverName", req.getReceiverName()); // 取貨人姓名
		params.add("ReceiverMobilePhone", req.getReceiverMobilePhone()); // 取貨人手機電話
		params.add("OPMode", this.opModeMap(req.getStoreCode())); // 通路代號
		params.add("Internetsite", callbackUrl); // 接收狀態的網址
		params.add("ShipDate", DateFormatUtils.format(req.getShipDate(), "yyyy-MM-dd")); // 出貨日期
		// 檢查碼
		String check = this.props.getCustomerPassword() + req.getOrderId();
		String md5 = Md5Util.md5(check.toLowerCase());
		params.add("CHKMAC", md5.toUpperCase(Locale.ROOT));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		// 告訴對方你可以接純文字/HTML
		headers.setAccept(List.of(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML));

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		// 回應用 String！不要 Map
		ResponseEntity<String> response =
		        this.restTemplate.exchange(storeCreateUri, HttpMethod.POST, request, String.class);

		String body = response.getBody();
		log.info("[GomypayLogisticsService] raw response: {}", body);

		C2CLogisticsCreateRawResponseDto dto = parseStoreC2CResponse(body);

		if (!"000".equals(dto.getErrorCode())) {
			log.error("物流訂單創建失敗，錯誤代碼: {}, 錯誤說明: {}", StringUtils.defaultString(dto.getErrorCode()),
					StringUtils.defaultString(dto.getErrorMessage()));
			throw new ApiException("物流訂單創建失敗" + dto.getErrorMessage());
		}

		String id = RandomGenerator.getUUID();
		ShippingTracking t = ShippingTracking.builder().id(id).logisticsType("C2C").logisticsVendor(req.getStoreCode())
				.orderId(req.getOrderId()).waybillNo(dto.getOrderNo()).vendororder(dto.getVendororder())
				.createStatus(OrderStatus.CREATED.getValue()).requestPayload(params.toString())
				.responsePayload(dto.toString()).createTime(new Date()).updateTime(new Date()).build();

		shippingTrackingMapper.insertSelective(t);
		return id;
	}

	private String opModeMap(String code) {
		switch (code) {
		case "7_ELEVEN":
			return "3";
		case "FAMILY":
			return "1";
		}
		return null;
	}


	private C2CLogisticsCreateRawResponseDto parseStoreC2CResponse(String jsonResponse) {
	    if (jsonResponse == null) {
	        throw new ApiException("建立便利商店物流訂單失敗：物流平台未回應內容");
	    }

	    log.info("============== 建立物流訂單 開始 ===============");
	    log.info("raw response: {}", jsonResponse);

	    String trimmed = jsonResponse.trim();
	    if (trimmed.startsWith("{")) {
	        return JsonUtil.fromJson(trimmed, C2CLogisticsCreateRawResponseDto.class);
	    }

	    Map<String, String> kv = new LinkedHashMap<>();
	    Matcher m = Pattern.compile("([A-Za-z0-9_\\-]+)\\s*=\\s*([^,\\r\\n]*)")
	            .matcher(trimmed.replace('，', ',').replace(';', ','));
	    while (m.find()) {
	        kv.put(m.group(1).toLowerCase(Locale.ROOT), m.group(2).trim());
	    }
	    if (kv.isEmpty()) throw new ApiException("物流訂單解析失敗");

	    String vendorOrder = StringUtils.firstNonBlank(kv.get("vendororder"), kv.get("vendor_order"));
	    String orderNo = StringUtils.firstNonBlank(kv.get("orderno"), kv.get("order_no"));
	    String errorCode = StringUtils.firstNonBlank(kv.get("errorcode"), kv.get("error_code"), kv.get("code"));
	    String message = StringUtils.firstNonBlank(kv.get("errormessage"), kv.get("message"));

	    if (StringUtils.isAllBlank(vendorOrder, orderNo, errorCode)) {
	        throw new ApiException("物流訂單解析失敗：缺少必要欄位");
	    }

	    log.info("============== 建立物流訂單 結束 ===============");
	    return C2CLogisticsCreateRawResponseDto.builder()
	            .Vendororder(vendorOrder)
	            .OrderNo(orderNo)
	            .ErrorCode(errorCode)
	            .ErrorMessage(message)
	            .build();
	}

	/**
	 * 成立宅配物流訂單（順豐）
	 */
	public String createExpressDeliveryOrder(ExpressDeliveryRequest req) {
		String expressCreateUri = this.props.getExpressCreateUri();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("CustomerOrderNo", req.getOrderId()); // 客戶訂單編號
		params.add("EshopId", this.props.getCustomerId()); // 客戶代號
		params.add("DeclaredValue", req.getDeclaredValue().toPlainString()); // 商品價值
		params.add("TotalWeight", req.getTotalWeight().toPlainString()); // 商品總重量(KG)
		params.add("PickupType", String.valueOf(req.getPickupType())); // 0- 服務點自寄or自行連絡快遞員1- 上門收件
		if (req.getPickupType() == 1) {
			params.add("PickupAppointTime", DateFormatUtils.format(req.getPickupAppointTime(), "yyyy-MM-dd HH:mm")); // 若寄件方式
																														// 為”1”上門收件，此欄為”必填”格式:yyyy-MMdd
																														// HH:mm
		}
		params.add("Name", req.getProductName()); // 商品名稱
		params.add("Quantity", req.getQuantity().toString()); // 商品數量
		params.add("ReceiverContact", req.getRecipientName()); // 收件人名稱
		params.add("ReceiverPhone", req.getRecipientMobile()); // 收件人電話
		params.add("ReceiverRegion", req.getRecipientRegion()); // 收件人所在城市(只需輸入縣市)
		params.add("ReceiverAdress", req.getRecipientAddress()); // 收件人詳細地址(不需輸入縣市)
		params.add("ReceiverPostcode", req.getRecipientZipCode()); // 收件人郵遞區號
		params.add("SenderContact", req.getSenderName()); // 寄件人姓名
		params.add("SenderPhone", req.getSenderMobile()); // 寄件人電話
		params.add("SenderRegion", req.getSenderRegion()); // 寄件人所在城市(只需輸入縣市)
		params.add("SenderAdress", req.getSenderAddress()); // 寄件人地址(不需輸入縣市)
		params.add("SenderPodtcode", req.getSenderZipCode()); // 寄件人郵遞區號
		params.add("Remark", StringUtils.defaultString(req.getRemark())); // 訂單備註

		String check = this.props.getCustomerPassword() + req.getOrderId();
		String md5 = Md5Util.md5(check.toLowerCase());
		params.add("CHKMAC", md5.toUpperCase(Locale.ROOT)); // 檢查碼

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // 設定為表單格式
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity(expressCreateUri, requestEntity, String.class);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				String body = response.getBody();

				// 將 JSON 字串轉換成物件
				ExpressCreateResult result = JsonUtil.fromJson(body, ExpressCreateResult.class);

				if (StringUtils.isBlank(result.getErrorMessage()) && StringUtils.contains(result.getMsg(), "成功")) {
					log.info("順豐物流建立成功：{}", result.getSfWaybillNo());
					String id = RandomGenerator.getUUID();
					ShippingTracking t = ShippingTracking.builder().id(id).logisticsType("EXPRESS")
							.logisticsVendor("EXPRESS").orderId(req.getOrderId()).vendororder(result.getSfWaybillNo())
							.createStatus(OrderStatus.CREATED.getValue()).requestPayload(params.toString())
							.responsePayload(result.toString()).createTime(new Date()).updateTime(new Date()).build();

					shippingTrackingMapper.insertSelective(t);
					return id;
				} else {
					log.warn("順豐物流建立失敗：{}", result.getErrorMessage());
					throw new ApiException("訂單: " + req.getOrderId() + " ,物流建立失敗：" + result.getErrorMessage());
				}
			} else {
				throw new ApiException("物流 API 回傳非 200 或無內容");
			}
		} catch (Exception ex) {
			if (ex instanceof JsonProcessingException) {
				log.error("順豐物流回傳 JSON 解析失敗：{}", response.getBody(), ex);
				throw new ApiException("物流回傳資料格式異常");
			}
			log.error("呼叫順豐物流 API 發生錯誤", ex);
			throw new ApiException("呼叫物流 API 失敗：" + ex.getMessage());
		}
	}

	public boolean updateLogisticsStatus(String eshopId, String orderNo, String status) {
		ShippingTrackingExample e = new ShippingTrackingExample();
		e.createCriteria().andWaybillNoEqualTo(orderNo);
		List<ShippingTracking> trackings = shippingTrackingMapper.selectByExample(e);
		if (trackings == null || trackings.size() == 0) {
			throw new ApiException("查無物流訂單");
		}

		ShippingTracking target = new ShippingTracking();
		target.setId(trackings.get(0).getId());
		target.setStatus(status);
		target.setLastCallbackPayload(String.format("EshopId:%s,OrderNo:%s,status:%s", eshopId, orderNo, status));

		shippingTrackingMapper.updateByPrimaryKeySelective(target);
		return true;
	}
}

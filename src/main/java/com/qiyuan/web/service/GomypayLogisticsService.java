package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.config.GomypayLogisticsProperties;
import com.qiyuan.web.dto.C2CLogisticsCreateRawResponseDto;
import com.qiyuan.web.dto.request.ExpressDeliveryRequest;
import com.qiyuan.web.dto.request.StorePickupRequest;
import com.qiyuan.web.util.JsonUtil;
import com.qiyuan.web.util.Md5Util;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class GomypayLogisticsService {

    private final RestTemplate restTemplate;
    private final GomypayLogisticsProperties props;

    private static final Logger logger = LoggerFactory.getLogger(GomypayLogisticsService.class);

    /**
     * 成立超商物流訂單
     */
    public String createStorePickupOrder(StorePickupRequest req) {
        String storeCreateUri = props.getStoreCreateUri();
        String callbackUrl = "https://api.onemorelottery.tw:8081/logistics/callback";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("Vendororder", req.getOrderId()); // 客戶訂單編號
        params.add("mode", "C2C"); // 物流方式
        params.add("EshopId", props.getCustomerId()); // 客戶代號
        params.add("StoreId", req.getStoreId()); // 門市代號
        params.add("Amount", req.getAmount().toPlainString()); // 交易金額
        params.add("ServiceType", "3"); // 服務型態代碼 //1- 取貨付款3- 取貨不付款退貨通5- 退貨付款4- 退貨不付款//通路代號 1:全家 2:萊爾富3: 統一超商 4.OK 超商
        params.add("OrderAmount", req.getOrderAmount().toPlainString()); // 商品價值
        params.add("SenderName", req.getSenderName()); // 寄件人姓名
        params.add("SendMobilePhone", req.getSendMobilePhone()); // 寄件人手機電話
        params.add("ReceiverName", req.getReceiverName()); // 取貨人姓名
        params.add("ReceiverMobilePhone", req.getReceiverMobilePhone()); // 取貨人手機電話
        params.add("OPMode", req.getOpMode()); // 通路代號
        params.add("Internetsite", callbackUrl); // 接收狀態的網址
        params.add("ShipDate", DateFormatUtils.format(req.getShipDate(), "yyyy/MM/dd")); // 出貨日期
        // 檢查碼
        String check = this.props.getCustomerPassword() + req.getOrderId();
        String md5 = Md5Util.md5(check.toLowerCase());
        params.add("CHKMAC", md5.toUpperCase(Locale.ROOT));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // 表單格式
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(storeCreateUri, HttpMethod.POST, request, String.class);
        String jsonResponse = response.getBody();
        C2CLogisticsCreateRawResponseDto dto = parseStoreC2CResponse(jsonResponse);


        if (!"000".equals(dto.getErrorCode())) {
            logger.error("物流訂單創建失敗，錯誤代碼: {}, 錯誤說明: {}",
                    StringUtils.defaultString(dto.getErrorCode()),
                    StringUtils.defaultString(dto.getErrorMessage()));
            throw new ApiException("物流訂單創建失敗" + dto.getErrorMessage());
        }

        // TODO: 補上DB操作

        // TODO: 字串作法
//        if ("000".equals(errorCode)) {
//            VendorOrderEntity vendorOrderEntity = new VendorOrderEntity();
//            vendorOrderEntity.setVendorOrder(vendorOrder);
//            vendorOrderEntity.setOrderNo(orderNo);
//            vendorOrderEntity.setErrorCode(errorCode);
//            vendorOrderEntity.setErrorMessage(errorMessage);
//            vendorOrderEntity.setExpress("1".equals(logisticsRequest.getOpMode()) ? "全家" : "711");
//            vendorOrderEntity.setStatus("未寄出");
//
//            // 插入資料庫
//            vendorOrderRepository.insert(vendorOrderEntity);
//            System.out.println("已插入資料庫");
//        } else {
//            System.out.println("訂單失敗，錯誤代碼：" + errorCode);
//        }
        // TODO: JSON作法
        //vendorOrderEntity.setExpress("1".equals(logisticsRequest.getOpMode()) ? "全家" : "711");
        //            vendorOrderRepository.insert(vendorOrderEntity);

        return null;
    }

    private C2CLogisticsCreateRawResponseDto parseStoreC2CResponse(String jsonResponse) {
        if (jsonResponse == null) throw new ApiException("建立便利商店物流訂單失敗：物流平台未回應內容");

        logger.info("============== 建立物流訂單 開始 ===============");
        logger.info(jsonResponse);
        if (jsonResponse.trim().startsWith("{")) {
            C2CLogisticsCreateRawResponseDto dto = JsonUtil.fromJson(jsonResponse, C2CLogisticsCreateRawResponseDto.class);
            logger.info("============== 建立物流訂單 結束 ===============");
            return dto;
        } else {
            String regex = "Vendororder=(.*?),OrderNo=(.*?),ErrorCode=(.*?),ErrorMessage=(.*)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(jsonResponse);
            if (matcher.find()) {
                // 提取相應的值
                String vendorOrder = matcher.group(1);
                String orderNo = matcher.group(2);
                String errorCode = matcher.group(3);
                String errorMessage = matcher.group(4);
                logger.info("============== 建立物流訂單 結束 ===============");
                return C2CLogisticsCreateRawResponseDto.builder()
                        .Vendororder(vendorOrder)
                        .OrderNo(orderNo)
                        .ErrorMessage(errorMessage)
                        .ErrorCode(errorCode)
                        .build();
            } else {
                // TODO
                // 寫入物流回應(DB)
                logger.error("物流訂單解析失敗");
                throw new ApiException("物流訂單解析失敗");
            }
        }
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
            params.add("PickupAppointTime", DateFormatUtils.format(req.getPickupAppointTime(), "yyyy-MM-dd HH:mm")); // 若寄件方式 為”1”上門收件，此欄為”必填”格式:yyyy-MMdd HH:mm
        }
        params.add("Name", req.getProductName()); // 商品名稱
        params.add("Quantity", req.getQuantity().toString()); // 商品數量
        params.add("ReceiverContact", req.getRecipientName()); //收件人名稱
        params.add("ReceiverPhone", req.getRecipientMobile()); // 收件人電話
        params.add("ReceiverRegion", req.getRecipientRegion()); //收件人所在城市(只需輸入縣市)
        params.add("ReceiverAdress", req.getRecipientAddress()); // 收件人詳細地址(不需輸入縣市)
        params.add("ReceiverPostcode", req.getRecipientZipCode()); //收件人郵遞區號
        params.add("SenderContact", req.getSenderName()); // 寄件人姓名
        params.add("SenderPhone", req.getSenderMobile()); // 寄件人電話
        params.add("SenderRegion", req.getSenderRegion()); // 寄件人所在城市(只需輸入縣市)
        params.add("SenderAdress", req.getSenderAddress()); // 寄件人地址(不需輸入縣市)
        params.add("SenderPodtcode", req.getSenderZipCode()); //寄件人郵遞區號
        params.add("Remark", StringUtils.defaultString(req.getRemark())); //訂單備註


        String check = this.props.getCustomerPassword() + req.getOrderId();
        String md5 = Md5Util.md5(check.toLowerCase());
        params.add("CHKMAC", md5.toUpperCase(Locale.ROOT)); //檢查碼



        // references
//        params.add("Spec", String.valueOf(homeReq.getSpec())); // 規格(代碼)
//        params.add("ServiceType", "3"); // 服務型態代碼
//        params.add("InternetSite", ""); // 接收狀態的網址
//        params.add("Amount", String.valueOf(homeReq.getAmount())); // 交易金額
//        params.add("OrderAmount", homeReq.getOrderAmount()); // 商品價值
//        params.add("RecipientName", homeReq.getRecipientName()); // 取貨人姓名
//        params.add("RecipientMobile", homeReq.getRecipientMobile()); // 取貨人手機電話
//        params.add("RecipientAddress", homeReq.getRecipientAddress()); // 取貨人地址
//        params.add("SenderName", homeReq.getSenderName()); // 寄件人姓名
//        params.add("SenderMobile", homeReq.getSenderMobile()); // 寄件人手機電話
//        params.add("SenderZipCode", homeReq.getSenderZipCode()); // 寄件人郵碼
//        params.add("SenderAddress", homeReq.getSenderAddress()); // 寄件人地址
//        params.add("ShipmentDate", homeReq.getShipmentDate()); // 出貨日期
//        params.add("DeliveryDate", homeReq.getDeliveryDate()); // 希望配達日期
//        params.add("DeliveryTime" , homeReq.getDeliveryTime());
//        params.add("ProductTypeId", "0015"); // 商品類別(代碼)
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        params.add("PrintDateTime", sdf.format(new Date()));
//        params.add("ProductName", "景品"); // 商品名稱
//        params.add("CHKMAC", s); // 檢查碼


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // 設定為表單格式

        // 處理回傳
        // 塞入DB
        // 封裝請求
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
//            String key = entry.getKey();
//            List<String> values = entry.getValue();
//            System.out.println(key + ": " + values);
//        }
        return null;
    }
}

ALTER TABLE qiyuan.dbo.shipping_method
    ADD min_size int NULL;

ALTER TABLE qiyuan.dbo.shipping_method
    ADD max_size int NULL;

ALTER TABLE qiyuan.dbo.users
    ADD zip_code varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

ALTER TABLE qiyuan.dbo.orders
    ADD zip_code varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    recipient_city nvarchar(30) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

DROP TABLE IF EXISTS qiyuan.dbo.shipping_tracking;

CREATE TABLE qiyuan.dbo.shipping_tracking (
                                              id char(32) NOT NULL PRIMARY KEY,                     -- UUID 作為主鍵
                                              order_id varchar(32) NOT NULL,                        -- 你系統的訂單編號
                                              logistics_type varchar(20) NOT NULL,                  -- 物流類型（如 C2C / EXPRESS）
                                              logistics_vendor varchar(50) NOT NULL,                -- 物流廠商（如 7-11 / 全家 / 順豐）
                                              waybill_no varchar(32) NULL,                          -- 萬事達物流回傳的 OrderNo
                                              vendororder varchar(32) NULL,                         -- 超商回傳的 Vendororder
                                              status varchar(20) NULL,                              -- 建立狀態（如 SUCCESS / FAIL）
                                              request_payload nvarchar(max) NULL,                   -- 發送時的完整參數 JSON（可選）
                                              response_payload nvarchar(max) NULL,                  -- 回傳原始 JSON 字串（可選）
                                              create_time datetime DEFAULT GETDATE() NOT NULL,
                                              update_time datetime DEFAULT GETDATE() NOT NULL
);

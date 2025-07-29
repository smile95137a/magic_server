-- 新增第三方訂單編號
ALTER TABLE qiyuan.dbo.payment_transaction
    ADD provider_order_no VARCHAR(19) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

-- 建表
CREATE TABLE qiyuan.dbo.god_purchase (
     id CHAR(32) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL, -- UUID
     user_id CHAR(32) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
     god_id CHAR(32) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
     duration_days TINYINT NOT NULL,               -- 7 or 30
     status VARCHAR(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL, -- pending, paid, failed
     external_order_no VARCHAR(25) COLLATE SQL_Latin1_General_CP1_CI_AS NULL, -- FK to payment_transaction.external_order_no
     create_time DATETIME2 NOT NULL DEFAULT GETDATE(),
     update_time DATETIME2 NOT NULL DEFAULT GETDATE(),
     CONSTRAINT PK_god_purchase PRIMARY KEY (id)
);


-- 調整訂單表欄位長度 external_order_no
UPDATE qiyuan.dbo.orders
SET external_order_no = LEFT(external_order_no, 25)
WHERE LEN(external_order_no) > 25;

UPDATE qiyuan.dbo.orders
SET external_order_no = LEFT(external_order_no, 25)
WHERE LEN(external_order_no) > 25;

ALTER TABLE qiyuan.dbo.orders
DROP CONSTRAINT UQ__orders__83AD2A3DFD0A32E6;

ALTER TABLE qiyuan.dbo.orders
ALTER COLUMN external_order_no VARCHAR(25) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL;

ALTER TABLE qiyuan.dbo.orders
    ADD CONSTRAINT UQ_orders_external_order_no UNIQUE (external_order_no);

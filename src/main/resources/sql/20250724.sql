UPDATE qiyuan.dbo.lantern_purchase
SET external_order_no = LEFT(external_order_no, 25);

ALTER TABLE qiyuan.dbo.lantern_purchase
ALTER COLUMN external_order_no varchar(25) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL;

UPDATE qiyuan.dbo.master_service_request
SET external_order_no = LEFT(external_order_no, 25)
WHERE LEN(external_order_no) > 25;

ALTER TABLE qiyuan.dbo.master_service_request
ALTER COLUMN external_order_no varchar(25) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

UPDATE qiyuan.dbo.offering_purchase
SET external_order_no = LEFT(external_order_no, 25)
WHERE LEN(external_order_no) > 25;

ALTER TABLE qiyuan.dbo.offering_purchase
ALTER COLUMN external_order_no varchar(25) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL;

UPDATE qiyuan.dbo.payment_transaction
SET external_order_no = LEFT(external_order_no, 25)
WHERE LEN(external_order_no) > 25;

ALTER TABLE qiyuan.dbo.payment_transaction
ALTER COLUMN external_order_no varchar(25) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL;



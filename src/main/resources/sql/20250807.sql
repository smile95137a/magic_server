ALTER TABLE qiyuan.dbo.shipping_method
    ADD min_size int NULL;

ALTER TABLE qiyuan.dbo.shipping_method
    ADD max_size int NULL;

ALTER TABLE qiyuan.dbo.users
    ADD zip_code varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

ALTER TABLE qiyuan.dbo.orders
    ADD zip_code varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    recipient_city nvarchar(30) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

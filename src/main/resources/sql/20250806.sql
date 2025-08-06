ALTER TABLE qiyuan.dbo.orders
    ADD store_id varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

ALTER TABLE qiyuan.dbo.orders
    ADD store_name nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

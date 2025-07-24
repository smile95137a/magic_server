
-- 發票
ALTER TABLE qiyuan.dbo.users
    ADD receipt varchar(200) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

ALTER TABLE qiyuan.dbo.users
DROP COLUMN receipt_type;

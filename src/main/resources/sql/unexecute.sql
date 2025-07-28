
-- 變更點燈代號
ALTER TABLE qiyuan.dbo.lantern
    ADD code varchar(32) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL;

UPDATE qiyuan.dbo.lantern SET code = 'lan-yinyuan' WHERE id = '230db11cd7d04bce9dfd98a55fd78d37';
UPDATE qiyuan.dbo.lantern SET code = 'lan-shiye' WHERE id = '2635b14c3a384ab1aab13f9254065c29';
UPDATE qiyuan.dbo.lantern SET code = 'lan-zhuiyi' WHERE id = '377c167c6e2f47b1bb6236c51daa7898';
UPDATE qiyuan.dbo.lantern SET code = 'lan-qiuzi' WHERE id = '5484cc83ce0d4446ba59239b8c178181';
UPDATE qiyuan.dbo.lantern SET code = 'lan-pingan' WHERE id = '5d49520025cd4974ba84766e3e249e1e';
UPDATE qiyuan.dbo.lantern SET code = 'lan-zaoming' WHERE id = '5faefcd1c86c4b168090edf68c5174c7';
UPDATE qiyuan.dbo.lantern SET code = 'lan-zhaocai' WHERE id = '6486e1c6ab61404886d5e640e4505c01';
UPDATE qiyuan.dbo.lantern SET code = 'lan-taisui' WHERE id = '772ef6d020304156a0f597c2ebc67321';
UPDATE qiyuan.dbo.lantern SET code = 'lan-jiankang' WHERE id = '7c8c1d96e16a461aa62c62ce3f102f8b';
UPDATE qiyuan.dbo.lantern SET code = 'lan-chanhui' WHERE id = '7f8838ba1ad6401c91b550c73ebb01f0';
UPDATE qiyuan.dbo.lantern SET code = 'lan-tianshou' WHERE id = '85236560442349fd9b875f786eac6d30';
UPDATE qiyuan.dbo.lantern SET code = 'lan-guangming' WHERE id = '8723385a401742f891bab060ded30a37';
UPDATE qiyuan.dbo.lantern SET code = 'lan-chuhui' WHERE id = 'a0d70186ab614f55abb2819ecb8df7c8';
UPDATE qiyuan.dbo.lantern SET code = 'lan-shengchen' WHERE id = 'a872161b5091488db89bdebcdce29425';
UPDATE qiyuan.dbo.lantern SET code = 'lan-taohua' WHERE id = 'ac8068dcf2614864a7fb72a0fafaaca1';
UPDATE qiyuan.dbo.lantern SET code = 'lan-shunchan' WHERE id = 'd77822e95a4442139adde9fb154c5f18';
UPDATE qiyuan.dbo.lantern SET code = 'lan-wenchang' WHERE id = 'f59330736248461d8bd4fd33b1b05074';

ALTER TABLE qiyuan.dbo.lantern
    ADD CONSTRAINT UQ_lantern_code UNIQUE (code);



-- 變更 payment_transaction
DROP TABLE qiyuan.dbo.payment_transaction;
CREATE TABLE qiyuan.dbo.payment_transaction (
    id VARCHAR(25) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,  -- 外部金流代碼
    user_id CHAR(32) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    source_type CHAR(1) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL, -- L 點燈, O 供品, M 老師
    amount DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL, -- OrderStatus enum
    pay_method VARCHAR(20) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    raw_data NVARCHAR(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
    create_time DATETIME DEFAULT GETDATE() NOT NULL,
    update_time DATETIME DEFAULT GETDATE() NOT NULL,
    CONSTRAINT PK_payment_transaction PRIMARY KEY (id)
);


-- 發票
ALTER TABLE qiyuan.dbo.users
    ADD receipt varchar(200) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

ALTER TABLE qiyuan.dbo.users
DROP COLUMN receipt_type;

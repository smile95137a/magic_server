ALTER TABLE qiyuan.dbo.payment_transaction
ALTER COLUMN pay_method varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL;

CREATE TABLE qiyuan.dbo.poe_throw (
      user_id char(32) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
      times int NULL,
      create_time datetime2 NOT NULL,
      update_time datetime2 NULL,
      CONSTRAINT PK__poe_throw__user PRIMARY KEY (user_id)
);

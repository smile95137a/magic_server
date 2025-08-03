CREATE TABLE qiyuan.dbo.virtual_orders (
    id CHAR(32) NOT NULL, -- UUID
    external_order_no VARCHAR(25) NOT NULL, -- 對外使用單號，供品/點燈等主鍵映射
    user_id CHAR(32) NOT NULL,
    total_amount DECIMAL(12, 2) NOT NULL,
    status VARCHAR(20) NOT NULL, -- created, paid, cancelled, etc.

    invoice_type VARCHAR(10) NULL,            -- 發票類型
    invoice_target NVARCHAR(50) NULL,         -- 統編或其他發票對象
    invoice_no VARCHAR(10) NULL,              -- 開立後對應的電子發票號碼

    source_type CHAR(1) NOT NULL,             -- O:供品, L:點燈, M:老師服務...
    remark NVARCHAR(255) NULL,

    create_time DATETIME NOT NULL DEFAULT GETDATE(),
    update_time DATETIME NOT NULL DEFAULT GETDATE(),

    CONSTRAINT PK_virtual_orders PRIMARY KEY (id),
    CONSTRAINT UQ_virtual_orders_external_order_no UNIQUE (external_order_no)
);

CREATE TABLE qiyuan.dbo.virtual_order_item (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id CHAR(32) NOT NULL,

    description NVARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    amount DECIMAL(12, 2) NOT NULL,

    create_time DATETIME DEFAULT GETDATE() NOT NULL,

    CONSTRAINT FK_virtual_order_item_order_id FOREIGN KEY (order_id)
REFERENCES qiyuan.dbo.virtual_orders(id)
);


ALTER TABLE qiyuan.dbo.invoice
    ADD external_order_no VARCHAR(25) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL;

CREATE UNIQUE NONCLUSTERED INDEX uq_invoice_external_order_no
ON qiyuan.dbo.invoice (external_order_no);



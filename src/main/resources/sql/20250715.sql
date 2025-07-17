ALTER TABLE [qiyuan].[dbo].[lantern]
    ADD [price_list_json] NVARCHAR(MAX) NULL;

CREATE TABLE [qiyuan].[dbo].[product_stock] (
    [id] INT IDENTITY(1,1) PRIMARY KEY,       -- 自動遞增主鍵
    [product_id] INT NOT NULL,                -- 產品ID (對應 product.id)
    [total_stock] INT NOT NULL DEFAULT 0,     -- 總庫存
    [reserved_stock] INT NOT NULL DEFAULT 0,  -- 待出貨庫存
    [update_time] DATETIME NOT NULL DEFAULT GETDATE(),
    [remark] NVARCHAR(128) NULL,
    CONSTRAINT FK_product_stock_product
    FOREIGN KEY (product_id) REFERENCES [product](id)
    );


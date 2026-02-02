create schema if not exists product;

create table if not exists product.product_sourcing
(
    id                       varchar(64) primary key,
    created_at               timestamptz not null,
    updated_at               timestamptz not null,

    user_id                  varchar(64) not null,
    name                     varchar(255) not null,
    image_url                text,

    cost_cny                 bigint,
    cost_krw                 bigint,

    coupang_category         varchar(255),
    wing_logistics_category  varchar(255),

    sale_price_krw           bigint,
    fee_rate_percent         numeric(10,4),
    fee_amount_krw           bigint,
    vat_krw                  bigint,
    gross_margin_krw         bigint,
    gross_margin_rate_percent numeric(10,4),
    min_ad_roi_percent       numeric(10,4),

    note                     text
    );

create index if not exists idx_product_sourcing_user_created_at
    on product.product_sourcing (user_id, created_at desc);

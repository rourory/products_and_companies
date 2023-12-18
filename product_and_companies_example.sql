CREATE TABLE "categories" (
  "id" bigserial,
  "category_name" varchar(255) NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp,
  PRIMARY KEY ("id")
);

CREATE TABLE "companies" (
  "id" bigserial,
  "company_name" varchar(255) NOT NULL,
  "country_id" int4 NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp,
  PRIMARY KEY ("id")
);

CREATE TABLE "countries" (
  "id" bigserial,
  "country_name" varchar(255) NOT NULL,
  "created_at" timestamp NOT NULL,
  PRIMARY KEY ("id")
);

CREATE TABLE "products" (
  "id" bigserial,
  "product_name" varchar(255) NOT NULL,
  "category_id" int8 NOT NULL,
  "company_id" int8 NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp,
  PRIMARY KEY ("id")
);

ALTER TABLE "companies" ADD CONSTRAINT "fk_companies_countries_1" FOREIGN KEY ("country_id") REFERENCES "countries" ("id");
ALTER TABLE "products" ADD CONSTRAINT "fk_products_companies_1" FOREIGN KEY ("company_id") REFERENCES "companies" ("id");
ALTER TABLE "products" ADD CONSTRAINT "fk_products_categories_1" FOREIGN KEY ("category_id") REFERENCES "categories" ("id");

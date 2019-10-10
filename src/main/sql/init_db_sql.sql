DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS orders;


CREATE TABLE product_category (
                          id serial PRIMARY KEY,
                          name VARCHAR,
                          department VARCHAR,
                          description VARCHAR
);

CREATE TABLE supplier (
                          id serial PRIMARY KEY,
                          name VARCHAR,
                          description VARCHAR
);

CREATE TABLE product (
                         id serial PRIMARY KEY,
                         name VARCHAR,
                         description VARCHAR,
                         default_price FLOAT,
                         default_currency VARCHAR(3),
                         product_category INTEGER REFERENCES product_category(id),
                         supplier INTEGER REFERENCES supplier(id),
                         image VARCHAR
);

CREATE TABLE cart (
                         id SERIAL,
                         cart_id INTEGER,
                         product_id INTEGER REFERENCES product(id)
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    buyer_name VARCHAR NOT NULL,
    buyer_phone VARCHAR(20) NOT NULL,
    buyer_email VARCHAR NOT NULL,
    buyer_shipping_address VARCHAR NOT NULL,
    buyer_billing_address VARCHAR NOT NULL,
    cart_id INTEGER
);

INSERT INTO supplier (name, description) VALUES
    ('Amazon', 'Digital content and services'),                                         /* 1 */
    ('Lenovo', 'Computers'),                                                            /* 2 */
    ('Apple', 'All kinds of useless overpriced hardware'),                              /* 3 */
    ('Samsung', 'All kinds of actually useful hardware. Still overpriced'),             /* 4 */
    ('Tesla', 'The stuff Elon Musk makes when he''s bored'),                            /* 5 */
    ('Stofi', 'A phenomenon that nobody will ever really understand')                   /* 6 */
;

INSERT INTO product_category (name, department, description) VALUES
    ('Tablet', 'Hardware', 'Thin, flat mobile computer with a touchscreen display'),    /* 1 */
    ('Laptop', 'Hardware', 'Weird flat boxes with black magic happening inside'),       /* 2 */
    ('Smartphone', 'Hardware', 'That thing you use all day'),                           /* 3 */
    ('Car', 'Vehicles', 'vroom vroom'),                                                 /* 4 */
    ('Accessory', 'Whatever', 'Literally just accessories')                             /* 5 */
;

INSERT INTO product (name, default_price, default_currency, description, product_category, supplier) VALUES
    ('Amazon Fire', 49.99, 'USD', 'Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.', 1, 1),
    ('Lenovo IdeaPad Miix 700', 479, 'USD', 'Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.', 1, 2),
    ('Amazon Fire HD 8', 89, 'USD', 'Amazon''s latest Fire HD 8 tablet is a great value for media consumption.', 1, 1),
    ('Apple Macbook', 1300, 'USD', 'It''s a desktop machine.', 2, 3),
    ('Apfel Macbuch Luft', 1899, 'USD', 'It''s a desktop machine but newer. 50% more power efficiency, also uses 50% more power. Only 46% more expensive', 2, 3),
    ('Samsung Galaxy S nagyonsok Pro X 2019.v42', 1000, 'USD', 'I''m running out of ideas.', 3, 4),
    ('Self-driving car', 9999999, 'USD', 'If you''re already living in 3019.', 4, 5),
    ('Wireless phone charger', 49, 'USD', 'More oomph for your phone, anywhere!', 5, 5),
    ('Tesla Shirt', 39, 'USD', 'Wearing this will double your IQ.', 5, 5),
    ('I don''t even know what this is', 19, 'USD', 'some screen cleaner i guess', 5, 3),
    ('Death Ray', 0, 'USD', 'Or the Peace Ray, as Tesla called it.' ||
                            'Tesla believed that by accelerating mercury isotopes to 48 times the speed of sound,' ||
                            'the resulting beam would produce enough energy to destroy entire armies at a distance' ||
                            'limited only by the curvature of the Earth.', 5, 5),
    ('Zsiguli', 195, 'USD', 'Some things just never get old.', 4,  1),
    ('Bambuszfogpiszkáló', 0.79, 'USD', 'Bamboozled.', 5, 6),
    ('Tompahegyű nyíl', 19.99, 'USD', 'Used by the Sami to hunt squirrels.', 5, 6),
    ('"Mr. Bean''s Mini', 60000, 'USD', 'Tiny, cozy, fit for purpose.', 4, 1),
    ('"iPhone 11', 1099, 'USD', 'Now with revolutionary fidget spinner camera.', 3, 3),
    ('iLight 7s', 85, 'USD', 'For those who want to fit the world in their pockets.', 3, 1),
    ('Dreamcom 10', 1800, 'USD', 'Fully adjustable. Looks ugly.', 2, 1)
;

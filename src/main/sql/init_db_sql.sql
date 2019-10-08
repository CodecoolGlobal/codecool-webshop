DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS supplier;

CREATE TABLE product_category (
                                  id serial PRIMARY KEY,
                                  name VARCHAR(30),
                                  description VARCHAR(150),
                                  department VARCHAR(30)
);

CREATE TABLE supplier (
                          id serial PRIMARY KEY,
                          name VARCHAR(30),
                          description VARCHAR(150)
);

CREATE TABLE product (
                         id serial PRIMARY KEY,
                         name VARCHAR(50),
                         description VARCHAR(150),
                         default_price FLOAT,
                         default_currency VARCHAR(3),
                         product_category INTEGER REFERENCES product_category(id),
                         supplier INTEGER REFERENCES supplier(id)
);

CREATE TABLE cart (
                      cart_id INTEGER,
                      product_id INTEGER REFERENCES product(id)
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    buyer_name VARCHAR(30) NOT NULL,
    buyer_phone VARCHAR(20) NOT NULL,
    buyer_email VARCHAR(35) NOT NULL,
    buyer_shipping_address VARCHAR(50) NOT NULL,
    buyer_billing_address VARCHAR(50) NOT NULL,
    cart_id INTEGER REFERENCES cart(cart_id)
);

INSERT INTO supplier (name, description) VALUES
    ('Apple', 'All kind of useless overpriced hardware'),
    ('Samsung', 'All kinds od askomfkaaaaa'),
    ('Stofi', 'A guyasdkongjadokngkoasngoka')
;

INSERT INTO product_category (name, description, department) VALUES
    ('Tablet', 'All kind of useless overpadkognjpkas riced hardware', 'hardware'),
    ('Laptop', 'All kinds od askomfkaaaaa', 'hardware'),
    ('Smartphone', 'A guyasdkongjadokngkoasngoka', 'hardware')
;

INSERT INTO product (name, description, default_price, default_currency, product_category, supplier) VALUES
    ('Iphone 100 XS MAX PLUS extra', 'All kind of useless overpadkognjpkas riced hardware', 1000, 'USD', 3, 1),
    ('Mekbúk', 'All kinds od askomfkaaaaa', 100, 'USD', 2, 1)
;

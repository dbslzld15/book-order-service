drop table if exists user;

CREATE TABLE user
(
    user_id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL ,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    address VARCHAR(200) NOT NULL,
    role ENUM('USER', 'ADMIN') default 'USER',
    is_deleted ENUM('Y', 'N') DEFAULT 'N',
    created_at datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT unq_user_email UNIQUE (email)
);

drop table if exists orders;

CREATE TABLE orders
(
    order_id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    address VARCHAR(200) NOT NULL,
    order_status ENUM('ACCEPTED', 'SHIPPED', 'CANCELED'),
    order_date datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_id BIGINT NOT NULL,
    is_deleted ENUM('Y', 'N') DEFAULT 'N',
    created_at datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

drop table if exists order_item;

CREATE TABLE order_item
(
    order_item_id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    total_price bigint NOT NULL,
    order_quantity INT NOT NULL,
    order_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    is_deleted ENUM('Y', 'N') DEFAULT 'N',
    created_at datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_order_id ON order_item(order_id);
CREATE INDEX idx_item_id ON order_item(item_id);

drop table if exists item;

CREATE TABLE item
(
    item_id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    price bigint NOT NULL,
    stock_quantity INT NOT NULL,
    is_deleted ENUM('Y', 'N') DEFAULT 'N',
    created_at datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

drop table if exists book;

CREATE TABLE book
(
    book_id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    author_name VARCHAR(20) NOT NULL,
    item_id bigint NOT NULL,
    is_deleted ENUM('Y', 'N') DEFAULT 'N',
    created_at datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)
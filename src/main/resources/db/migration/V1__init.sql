CREATE TABLE users
(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);
CREATE TABLE "public".catalog
(
    id          int8         NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    description varchar(2048) DEFAULT NULL,
    name        varchar(512) NOT NULL,
    CONSTRAINT catalog_pkey PRIMARY KEY (id)
);

CREATE TABLE "public".role
(
    id   int8         NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    name varchar(512) NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id)
);

CREATE TABLE "public".user
(
    id          INT8         NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    enabled     bool         NOT NULL DEFAULT TRUE,
    description VARCHAR(2048)         DEFAULT NULL,
    name        VARCHAR(512) NOT NULL,
    username    VARCHAR(256) NOT NULL,
    password    VARCHAR(256) NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT user_username_key UNIQUE (username)
);

CREATE TABLE "public".product
(
    id          INT8          NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    description VARCHAR(2048) DEFAULT NULL,
    name        VARCHAR(512)  NOT NULL,
    quantity    INT           DEFAULT 0,
    price       DECIMAL       DEFAULT 0,
    discount    INT           DEFAULT 0,
    images      VARCHAR(2048) NULL,
    created     DATE          NULL,
    catalog_id  INT8          NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    FOREIGN KEY (catalog_id) REFERENCES catalog (id)
);

CREATE TABLE "public".order
(
    id          INT8         NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    description VARCHAR(2048) DEFAULT NULL,
    status      VARCHAR(100) NOT NULL,
    name        VARCHAR(512) NOT NULL,
    address     VARCHAR(256) NOT NULL,
    phone       VARCHAR(256) NULL,
    created     TIMESTAMP    NOT NULL,
    payment     VARCHAR(100) NOT NULL,
    user_id     INT8         NULL,
    CONSTRAINT order_pkey PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES "public".user (id)
);

CREATE TABLE "public".review
(
    id         INT8          NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    comment    VARCHAR(256)           DEFAULT NULL,
    rate       INT           NOT NULL DEFAULT 1,
    images     VARCHAR(2048) NULL,
    created    TIMESTAMP     NOT NULL,
    user_id    INT8          NOT NULL,
    product_id INT8          NOT NULL,
    CONSTRAINT review_pkey PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES "public".user (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE "public".order_detail
(
    id         INT8    NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    quantity   INT DEFAULT 1,
    total      DECIMAL NULL,
    order_id   INT8    NOT NULL,
    product_id INT8    NOT NULL,
    CONSTRAINT order_detail_pkey PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES "public".order (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE "public".role_assignment
(
    id      INT8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    user_id INT8 NOT NULL,
    role_id INT8 NOT NULL,
    CONSTRAINT role_assignment_pkey PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES "public".user (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE "public".otp_message
(
    id          INT8         NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    random_code VARCHAR(100) NULL,
    created     TIMESTAMP    NULL,
    expired     TIMESTAMP    NULL,
    user_id     INT8         NOT NULL,
    CONSTRAINT otp_message_pkey PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES "public".user (id)
);
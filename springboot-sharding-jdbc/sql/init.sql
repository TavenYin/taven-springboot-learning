CREATE TABLE t_order_0 (
order_id BIGINT NOT NULL,
user_id BIGINT NULL,
create_time DATETIME NULL,
CONSTRAINT t_order_0_pk PRIMARY KEY (order_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE t_order_1 (
order_id BIGINT NOT NULL,
user_id BIGINT NULL,
create_time DATETIME NULL,
CONSTRAINT t_order_1_pk PRIMARY KEY (order_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
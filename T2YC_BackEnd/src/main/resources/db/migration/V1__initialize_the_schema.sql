CREATE SCHEMA IF NOT EXISTS t2yc_test;

CREATE TABLE IF NOT EXISTS support_agent (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               first_name VARCHAR(45),
                               last_name VARCHAR(45),
                               availability VARCHAR(10) CHECK (availability IN ('AVAILABLE', 'BUSY', 'AWAY')),
                               years_of_experience INT,
                               role VARCHAR(20) CHECK (role IN ('CUSTOMER', 'SUPPORT_AGENT')),
                               email VARCHAR(45),
                               is_active TINYINT,
                               password VARCHAR(255) NOT NULL

    );

CREATE TABLE IF NOT EXISTS customer (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          first_name VARCHAR(45),
                          last_name VARCHAR(45),
                          email VARCHAR(45),
                          role VARCHAR(20) CHECK (role IN ('SUPPORT_AGENT', 'CUSTOMER')),
                          is_active TINYINT,
                          password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(45),
                         is_active TINYINT
    );

CREATE TABLE IF NOT EXISTS chat_session (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              customer_id INT,
                              support_agent_id INT,
                              start_time DATE,
                              chat_status VARCHAR(45) CHECK (chat_status IN ('NEVER_STARTED', 'ON_GOING', 'FINISHED')),
                              end_time DATETIME,
                              product_id INT,
                              FOREIGN KEY (customer_id) REFERENCES customer(id),
                              FOREIGN KEY (support_agent_id) REFERENCES support_agent(id),
                              FOREIGN KEY (product_id) REFERENCES product(id)
);


CREATE TABLE IF NOT EXISTS message (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         time_stamp DATETIME,
                         content VARCHAR(255),
                         sender_id INT,
                         chat_session_id INT,
                         sender_role VARCHAR(20) CHECK (sender_role IN ('SUPPORT_AGENT', 'CUSTOMER')),
                         FOREIGN KEY (chat_session_id) REFERENCES chat_session(id)
);

CREATE TABLE IF NOT EXISTS ratings (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         rate INT,
                         comment VARCHAR(255),
                         rated_agent INT,
                         FOREIGN KEY (rated_agent) REFERENCES support_agent(id)
);

CREATE TABLE IF NOT EXISTS agent_product (
                               agent_id INT NOT NULL,
                               product_id INT NOT NULL,
                               PRIMARY KEY (agent_id, product_id),
                               FOREIGN KEY (agent_id) REFERENCES support_agent(id),
                               FOREIGN KEY (product_id) REFERENCES product(id)
);
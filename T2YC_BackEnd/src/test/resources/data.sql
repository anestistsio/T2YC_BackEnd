INSERT INTO support_agent (first_name, last_name, availability, years_of_experience, role, email, is_active, password) VALUES
                                                                                                      ('John', 'Doe', 'AVAILABLE', 5, 'SUPPORT_AGENT', 'john.doe@example.com',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                                                                      ('Jane', 'Smith', 'BUSY', 3, 'SUPPORT_AGENT', 'jane.smith@example.com',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                                                                      ('Michael', 'Johnson', 'AWAY', 7, 'SUPPORT_AGENT', 'michael.johnson@example.com',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                                                                      ('Emily', 'Williams', 'AVAILABLE', 4, 'SUPPORT_AGENT', 'emily.williams@example.com',0,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                                                                      ('Robert', 'Brown', 'AVAILABLE', 6, 'SUPPORT_AGENT', 'robert.brown@example.com',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                                                                      ('Jessica', 'Miller', 'BUSY', 2, 'SUPPORT_AGENT', 'jessica.miller@example.com',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                                                                      ('Daniel', 'Davis', 'AVAILABLE', 8, 'SUPPORT_AGENT', 'daniel.davis@example.com',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                                                                      ('Sarah', 'Garcia', 'AWAY', 5, 'SUPPORT_AGENT', 'sarah.garcia@example.com',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                                                                      ('David', 'Rodriguez', 'AVAILABLE', 3, 'SUPPORT_AGENT', 'david.rodriguez@example.com',0,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                                                                      ('Lisa', 'Martinez', 'AVAILABLE', 4, 'SUPPORT_AGENT', 'lisa.martinez@example.com',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm');

INSERT INTO customer (first_name, last_name, email, role, is_active, password) VALUES
                                                              ('Alice', 'Johnson', 'alice.johnson@example.com', 'CUSTOMER',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                              ('Bob', 'Wilson', 'bob.wilson@example.com', 'CUSTOMER',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                              ('Carol', 'Davis', 'carol.davis@example.com', 'CUSTOMER',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                              ('David', 'Thomas', 'david.thomas@example.com', 'CUSTOMER',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                              ('Eve', 'Martinez', 'eve.martinez@example.com', 'CUSTOMER',0,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                              ('Frank', 'Garcia', 'frank.garcia@example.com', 'CUSTOMER',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                              ('Grace', 'Miller', 'grace.miller@example.com', 'CUSTOMER',0,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                              ('Henry', 'Brown', 'henry.brown@example.com', 'CUSTOMER',0,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                              ('Isabel', 'Rodriguez', 'isabel.rodriguez@example.com', 'CUSTOMER',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm'),
                                                              ('Jack', 'Gonzalez', 'jack.gonzalez@example.com', 'CUSTOMER',1,'$2a$10$xPqIGmMW0Uj0Mw9.v5BBkup8L7G1O3O94kR79AD5cUIQKgGONY/fm');

INSERT INTO product (name, is_active) VALUES
                               ('Product A',1),
                               ('Product B',1);

INSERT INTO chat_session (customer_id, support_agent_id, start_time, chat_status, end_time, product_id) VALUES
                                                                                                            (1, 1, NOW(), 'NEVER_STARTED', NOW(), 1),
                                                                                                            (2, 3, NOW(), 'ON_GOING', NOW(), 2),
                                                                                                            (3, 5, NOW(), 'FINISHED', NOW(), 1),
                                                                                                            (4, 2, NOW(), 'NEVER_STARTED', NOW(), 2),
                                                                                                            (5, 4, NOW(), 'ON_GOING', NOW(), 1),
                                                                                                            (6, 6, NOW(), 'FINISHED', NOW(), 2),
                                                                                                            (7, 8, NOW(), 'NEVER_STARTED', NOW(), 1),
                                                                                                            (8, 10, NOW(), 'ON_GOING', NOW(), 2),
                                                                                                            (9, 9, NOW(), 'FINISHED', NOW(), 1),
                                                                                                            (10, 7, NOW(), 'NEVER_STARTED', NOW(), 2);

INSERT INTO message (time_stamp, content, sender_id, chat_session_id, sender_role) VALUES
                                                                                       (NOW(), 'Hello, how can I help you?', 2, 1, 'SUPPORT_AGENT'),
                                                                                       (NOW(), 'I have a question about my order.', 1, 1, 'CUSTOMER'),
                                                                                       (NOW(), 'Please wait a moment, I will check.', 4, 2, 'SUPPORT_AGENT'),
                                                                                       (NOW(), 'Sure, take your time.', 2, 2, 'CUSTOMER'),
                                                                                       (NOW(), 'Thank you for your assistance!', 6, 3, 'SUPPORT_AGENT'),
                                                                                       (NOW(), 'You are welcome!', 3, 3, 'CUSTOMER'),
                                                                                       (NOW(), 'How can I assist you today?', 8, 4, 'SUPPORT_AGENT'),
                                                                                       (NOW(), 'I would like to inquire about your products.', 5, 4, 'CUSTOMER'),
                                                                                       (NOW(), 'Let me know which products you are interested in.', 10, 5, 'SUPPORT_AGENT'),
                                                                                       (NOW(), 'I am interested in Product D.', 7, 5, 'CUSTOMER');

INSERT INTO ratings (rate, comment, rated_agent) VALUES
                                                     (5, 'Excellent service!', 1),
                                                     (4, 'Very helpful and knowledgeable.', 3),
                                                     (5, 'I received great support.', 5),
                                                     (3, 'Could have been quicker.', 2),
                                                     (4, 'Helped me solve my issue.', 4),
                                                     (5, 'Highly recommended!', 6),
                                                     (4, 'Good response time.', 8),
                                                     (3, 'Satisfactory.', 7),
                                                     (5, 'Extremely satisfied!', 9),
                                                     (4, 'Resolved my problem effectively.', 10);

INSERT IGNORE INTO agent_product (agent_id, product_id) VALUES
                                                            (1, 1),
                                                            (2, 2),
                                                            (3, 1),
                                                            (4, 2),
                                                            (5, 1),
                                                            (6, 2),
                                                            (7, 1),
                                                            (8, 2),
                                                            (9, 1),
                                                            (10, 2);
INSERT INTO TEACHERS (first_name, last_name)
VALUES ('Margot', 'DELAHAYE'),
       ('Hélène', 'THIERCELIN');


INSERT INTO USERS (first_name, last_name, admin, email, password)
VALUES ('Admin', 'Admin', true, 'yoga@studio.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq'),
       ('toto', 'toto', false, 'toto@toto.com', '$2a$10$WUg9L4ldWGFVojicToiL.ubU4kCgz5Lh5gFqlfw.I38/Ap4F2zkmS'),
       ('Yoyo', 'Yoyo', false, 'yoyo@yoyo.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq');

INSERT INTO SESSIONS (name, description, teacher_id, date)
VALUES ('Session', 'Description', 1, '2023-11-15'),
        ('Another Session', 'Another Description', 2, '2023-11-20');

INSERT INTO PARTICIPATE (user_id, session_id)
VALUES (2, 1);
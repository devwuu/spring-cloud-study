INSERT INTO member (id, email, encrypted_pwd, user_id)
VALUES (1, 'test@gmail.com', '$2a$10$VdOUpwi7oVyp8vAz2b0Le.KoOqeRPaa.Mg/nmp8E4tONw5KNn25uK', 'a1b98c14-08d7-4e4f-a233-dc095297bee5')
    ON DUPLICATE KEY UPDATE email = VALUES(email), encrypted_pwd = VALUES(encrypted_pwd), user_id = VALUES(user_id);

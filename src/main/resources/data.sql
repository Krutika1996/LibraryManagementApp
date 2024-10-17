INSERT INTO LIBBOOK  (title, copies_available) VALUES ('The Great Gatsby', 5);
INSERT INTO LIBBOOK  (title, copies_available) VALUES ('11 Seconds', 3);
INSERT INTO LIBBOOK  (title, copies_available) VALUES ('Kite Runner', 1);
INSERT INTO LIBBOOK  (title, copies_available) VALUES ('2 states', 2);


-- Insert users into the user table
INSERT INTO "user" (id, username, password, roles) VALUES (1, 'riya', 'password123', ARRAY['USER']);
INSERT INTO "user" (id, username, password, roles) VALUES (2, 'shreya', 'password456', ARRAY['USER']);
INSERT INTO "user" (id, username, password, roles) VALUES (3, 'admin', 'adminpass', ARRAY['ADMIN', 'USER']);

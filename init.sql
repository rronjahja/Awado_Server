CREATE database awado_db;

\connect awado_db;

CREATE TABLE bikes (bike_id CHARACTER VARYING CONSTRAINT bike_pkey PRIMARY KEY,
                   availability BOOLEAN,
                   bike_name CHARACTER VARYING);

-- CREATE TABLE users (id INTEGER CONSTRAINT user_pkey PRIMARY KEY,
--                     username CHARACTER VARYING UNIQUE NOT NULL,
--                     password CHARACTER VARYING NOT NULL,
--                     is_admin BOOLEAN NOT NULL);

INSERT INTO bikes (bike_id, availability, bike_name)
            VALUES ('eBike20131126003c', TRUE, 'name_tbdefined'),
                   ('eBike201209280029', TRUE, 'name_tbdefined'),
                   ('eBike20131107003d', TRUE, 'name_tbdefined');

-- INSERT INTO users (id, username, password, is_admin)
--             VALUES (1, 'Bob', 'BobPassword', FALSE),
--                    (2, 'theAdmin', 'theAdminPassword', TRUE);

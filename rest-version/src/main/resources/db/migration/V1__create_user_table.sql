CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY NOT NULL UNIQUE,
    nick     VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    token    VARCHAR(36)
);

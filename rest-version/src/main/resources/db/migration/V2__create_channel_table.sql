CREATE TABLE IF NOT EXISTS channels
(
    id   SERIAL PRIMARY KEY NOT NULL UNIQUE,
    name VARCHAR(20)        NOT NULL UNIQUE
);

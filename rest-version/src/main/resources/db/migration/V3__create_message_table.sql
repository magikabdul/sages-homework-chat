CREATE TABLE IF NOT EXISTS messages
(
    id              SERIAL PRIMARY KEY NOT NULL UNIQUE,
    created_at_date DATE               NOT NULL,
    created_at_time TIME               NOT NULL,
    user_id         SERIAL             NOT NULL REFERENCES users (id),
    body            VARCHAR(500)       NOT NULL
);

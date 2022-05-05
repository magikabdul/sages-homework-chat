CREATE TABLE IF NOT EXISTS messages
(
    id         SERIAL PRIMARY KEY NOT NULL UNIQUE,
    created_at TIMESTAMP          NOT NULL,
    user_id    SERIAL             NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

ALTER TABLE messages
    ADD COLUMN channel_id BIGINT NOT NULL REFERENCES messages (id);

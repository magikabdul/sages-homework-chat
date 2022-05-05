ALTER TABLE messages
    ADD COLUMN channel_id BIGINT NOT NULL REFERENCES channels(id);

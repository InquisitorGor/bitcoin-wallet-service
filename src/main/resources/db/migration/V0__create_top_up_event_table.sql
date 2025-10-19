CREATE TABLE top_up_event
(
    event_id   UUID PRIMARY KEY,
    wallet_id  VARCHAR(255)   NOT NULL,
    event_ts   timestamptz    NOT NULL,
    amount     numeric(30, 8) NOT NULL,
    payload    jsonb,
    created_at timestamptz DEFAULT now()
);
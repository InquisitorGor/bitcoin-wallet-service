CREATE TABLE wallet
(
    id            BIGSERIAL PRIMARY KEY,
    balance       numeric(30, 8) NOT NULL DEFAULT 0,
    wallet_id     varchar,
    last_event_ts timestamptz             DEFAULT now()
);

INSERT INTO wallet(id, balance, wallet_id)
VALUES (1, 0, 'walletId')
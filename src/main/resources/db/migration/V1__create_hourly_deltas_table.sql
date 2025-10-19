CREATE TABLE hourly_deltas
(
    hour_start timestamptz PRIMARY KEY,
    delta      numeric(30, 8) NOT NULL DEFAULT 0
);
CREATE TABLE asset (
                       id SERIAL PRIMARY KEY,
                       exchange_id INT REFERENCES exchange(id),
                       currency_id INT REFERENCES currency(id),
                       isin VARCHAR(20) NOT NULL,
                       bloomberg_ticker VARCHAR(20) NOT NULL,
                       name VARCHAR(255) NOT NULL
);

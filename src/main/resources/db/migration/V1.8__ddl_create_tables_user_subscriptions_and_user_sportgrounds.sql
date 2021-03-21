DROP TABLE IF EXISTS user_subscriptions;

CREATE TABLE user_subscriptions
(
    user_id             BIGINT NOT NULL,
    subscription_id     BIGINT NOT NULL,
    PRIMARY KEY (user_id, subscription_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (subscription_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS user_sportgrounds;

CREATE TABLE user_sportgrounds
(
    user_id             BIGINT NOT NULL,
    sportground_id      BIGINT NOT NULL,
    PRIMARY KEY (user_id, sportground_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (sportground_id) REFERENCES sportgrounds (id)
);
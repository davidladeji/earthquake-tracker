CREATE Table earthquakes (
    id          BIGINT AUTO_INCREMENT NOT NULL,
    mag         DOUBLE NOT NULL,
    type        VARCHAR(255) NOT NULL,
    time        DATETIME NOT NULL,
    city        VARCHAR(255) NOT NULL,
    country     VARCHAR(255) NOT NULL,
    place       VARCHAR(255) NOT NULL,
    title       VARCHAR(255) NOT NULL,
    quake_key   VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
)
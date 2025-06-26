CREATE Table earthquakes (
    id          BIGINT AUTO_INCREMENT NOT NULL,
    mag         DOUBLE NOT NULL,
    type        VARCHAR(255) NOT NULL,
    time        DATETIME NOT NULL,
    city        VARCHAR(255) NOT NULL,
    country     VARCHAR(255) NOT NULL,
    place       VARCHAR(255) NOT NULL,

    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
)
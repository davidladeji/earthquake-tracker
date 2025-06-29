alter table earthquakes
    ADD COLUMN `longtitude` DECIMAL(10,4) NOT NULL,
    ADD COLUMN `latitude` DECIMAL(10,4) NOT NULL,
    ADD COLUMN `depth` DOUBLE NOT NULL;
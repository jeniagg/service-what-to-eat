-- liquibase formatted sql

-- changeset admin:create-table-recipe
CREATE TABLE recipe (
    id int NOT NULL AUTO_INCREMENT,
    recipe_name varchar(255) NOT NULL,
    recipe_author varchar(255),
    ingredients varchar(255),
    baking varchar(255),
    PRIMARY KEY (id)
);
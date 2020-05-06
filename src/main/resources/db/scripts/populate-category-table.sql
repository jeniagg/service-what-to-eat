-- liquibase formatted sql

-- changeset admin:populate-category-table
INSERT INTO category(name)
VALUES ("APPETIZER"), ("SALAD"), ("ENTREE"), ("MAIN"), ("DESSERT"), ("BEVERAGE");
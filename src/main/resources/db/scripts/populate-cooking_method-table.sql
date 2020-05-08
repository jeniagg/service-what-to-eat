-- liquibase formatted sql

-- changeset admin:populate-cooking_method-table
INSERT INTO cooking_method(name)
VALUES ("BAKING"), ("FRYING"), ("ROASTING"), ("GRILLING"), ("STEAMING"), ("POACHING"),
("SIMMERING"), ("BROILING"), ("BLANCHING"), ("BRAISING"), ("STEWING");
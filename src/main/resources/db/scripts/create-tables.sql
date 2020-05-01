-- liquibase formatted sql

-- changeset admin:create-table-users
CREATE TABLE users (
	id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- changeset admin:create-table-category
CREATE TABLE category (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- changeset admin:create-table-cooking_method
CREATE TABLE cooking_method (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- changeset admin:create-table-recipe
CREATE TABLE recipe (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    user_id INT,
    steps text,
    cooking_method_id INT,
    category_id INT,
    comment text,
    PRIMARY KEY (id),
    CONSTRAINT fk_recipe_users FOREIGN KEY (user_id) REFERENCES users(id),
	CONSTRAINT fk_recipe_category FOREIGN KEY (category_id) REFERENCES category(id),
	CONSTRAINT fk_recipe_cooked_method FOREIGN KEY (cooking_method_id) REFERENCES cooking_method(id)
);

-- changeset admin:create-table-quantity_unit
CREATE TABLE quantity_unit (
	id INT NOT NULL AUTO_INCREMENT,
    description VARCHAR (255),
	PRIMARY KEY (id)
);

-- changeset admin:create-table-ingredients
CREATE TABLE ingredients (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR (255),
	PRIMARY KEY (id)
);

-- changeset admin:create-table-quantity
CREATE TABLE quantity (
	id INT NOT NULL AUTO_INCREMENT,
    amount VARCHAR (50),
	PRIMARY KEY (id)
);

-- changeset admin:create-table-recipe_ingredients
CREATE TABLE recipe_ingredients (
	id INT NOT NULL AUTO_INCREMENT,
    recipe_id INT NOT NULL,
    quantity_id INT,
    quantity_unit_id INT,
    ingredients_id INT,
    PRIMARY KEY (id),
	CONSTRAINT fk_recipe_ingredients_recipe FOREIGN KEY (recipe_id) REFERENCES recipe(id),
	CONSTRAINT fk_recipe_ingredients_quantity FOREIGN KEY (quantity_id) REFERENCES quantity(id),
	CONSTRAINT fk_recipe_ingredients_quantity_unit FOREIGN KEY ( quantity_unit_id) REFERENCES  quantity_unit(id),
    CONSTRAINT fk_recipe_ingredients_ingredients FOREIGN KEY (ingredients_id) REFERENCES ingredients(id)
);

-- changeset admin:create-table-rating
CREATE TABLE rating (
	id INT NOT NULL AUTO_INCREMENT,
    recipe_id INT,
    rating INT,
    user_id INT,
	PRIMARY KEY (id),
    CONSTRAINT fk_rating_recipe FOREIGN KEY (recipe_id) REFERENCES recipe(id),
    CONSTRAINT fk_rating_users FOREIGN KEY (user_id) REFERENCES users(id),
	CONSTRAINT uniques_user_recipe UNIQUE (recipe_id,user_id)
);

-- changeset admin:create-table-user_favorite
CREATE TABLE user_favorite (
	id INT NOT NULL AUTO_INCREMENT,
	recipe_id INT,
    user_id INT,
	PRIMARY KEY (id),
	CONSTRAINT fk_user_favorite_recipe FOREIGN KEY (recipe_id) REFERENCES recipe(id),
    CONSTRAINT fk_user_favorite_users FOREIGN KEY (user_id) REFERENCES users(id)
);

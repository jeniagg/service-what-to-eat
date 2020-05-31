-- liquibase formatted sql

-- changeset admin:users-table-add-column
ALTER TABLE users ADD COLUMN salt VARBINARY(500);
CREATE TABLE users_roles
(
    user_id bigserial,
    role_id bigserial,
    primary key (user_id, role_id),
    FOREIGN key (user_id) REFERENCES users (id),
    FOREIGN key (role_id) REFERENCES roles (id)
);
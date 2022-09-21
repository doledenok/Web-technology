DROP TABLE IF EXISTS position CASCADE;
CREATE TABLE position (
    pos_id   serial  PRIMARY KEY,
    name     text,
    description text
);

DROP TABLE IF EXISTS employee CASCADE;
CREATE TABLE employee (
    emp_id          serial      PRIMARY KEY,
    pos_id          integer     REFERENCES position (pos_id) ON DELETE CASCADE,
    name            text,
    education       text,
    address         text,
    work_experience text,
    status          text,
    date_of_birth   date
);

DROP TABLE IF EXISTS positions_history CASCADE;
CREATE TABLE positions_history (
    pos_history_id  serial      PRIMARY KEY,
    pos_id          integer     NOT NULL REFERENCES position (pos_id) ON DELETE CASCADE,
    emp_id          integer     NOT NULL REFERENCES employee (emp_id) ON DELETE CASCADE,
    start_date      date        NOT NULL,
    end_date        date
);

DROP TABLE IF EXISTS project CASCADE;
CREATE TABLE project (
    proj_id         serial      PRIMARY KEY,
    name            text,
    start_date      date,
    end_date        date,
    description     text
);

DROP TABLE IF EXISTS project_roles CASCADE;
CREATE TABLE project_roles (
    proj_role_id    serial      PRIMARY KEY,
    proj_id         integer     NOT NULL REFERENCES project (proj_id) ON DELETE CASCADE,
    role_name       text,
    required_count  integer,
    real_count      integer,
    status          text,
    description     text
);

DROP TABLE IF EXISTS employee_role CASCADE;
CREATE TABLE employee_role (
    emp_role_id         serial      PRIMARY KEY,
    emp_id              integer     NOT NULL REFERENCES employee (emp_id) ON DELETE CASCADE,
    proj_role_id        integer     NOT NULL REFERENCES project_roles (proj_role_id) ON DELETE CASCADE,
    start_date          date        NOT NULL,
    end_date            date
);

DROP TABLE IF EXISTS payment_policy CASCADE;
CREATE TABLE payment_policy (
    policy_id       serial      PRIMARY KEY,
    pos_id          integer     REFERENCES position (pos_id) ON DELETE CASCADE,
    proj_role_id    integer     REFERENCES project_roles (proj_role_id) ON DELETE CASCADE,
    sum             integer     NOT NULL,
    regularity      text,
    type            text,
    description     text
);

DROP TABLE IF EXISTS payment CASCADE;
CREATE TABLE payment (
    pay_id      serial      PRIMARY KEY,
    emp_id      integer     NOT NULL REFERENCES employee (emp_id) ON DELETE CASCADE,
    policy_id   integer     NOT NULL REFERENCES payment_policy (policy_id) ON DELETE CASCADE,
    date        date        NOT NULL
);

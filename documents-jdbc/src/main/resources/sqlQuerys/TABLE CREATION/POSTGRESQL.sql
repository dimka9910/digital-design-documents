------------------------------------------------------------------------------------
-- DOCUMENT_TYPE
CREATE TABLE IF NOT EXISTS DOCUMENT_TYPE
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);


------------------------------------------------------------------------------------
-- USER
CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    login    TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role     INTEGER default 1
);

select * from users;

insert into users (login, password) values ('1', '2');
-- drop table USER;

------------------------------------------------------------------------------------
-- FILE_PATH
CREATE TABLE IF NOT EXISTS file_path
(
    id        SERIAL PRIMARY KEY,
    filepath  TEXT    NOT NULL,
    parent_id INTEGER NOT NULL,
    FOREIGN KEY (parent_id)
        REFERENCES CONCRETE_DOCUMENT (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
);

------------------------------------------------------------------------------------
-- CONCRETE_DOCUMENT
CREATE TABLE IF NOT EXISTS CONCRETE_DOCUMENT
(
    id            SERIAL PRIMARY KEY,
    name          TEXT     NOT NULL,
    description   TEXT     NOT NULL,
    version       INTEGER  NOT NULL,
    modified_time TIMESTAMP NOT NULL,
    parent_id     INTEGER  NOT NULL,
    FOREIGN KEY (parent_id)
        REFERENCES DOCUMENT (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    UNIQUE (parent_id, version)
);

------------------------------------------------------------------------------------
-- CATALOGUE
CREATE TABLE IF NOT EXISTS CATALOGUE
(
    id           SERIAL PRIMARY KEY,
    name         TEXT     NOT NULL,
    type_of_file INTEGER CHECK ( type_of_file = 0) default 0,
    created_time TIMESTAMP not null,
    parent_id    INTEGER                           default (1),
    FOREIGN KEY (parent_id)
        REFERENCES CATALOGUE (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    UNIQUE (name, parent_id)
);

-- -- DROP TABLE CATALOGUE;
--  INSERT INTO CATALOGUE (NAME)
--  VALUES ('ROOT');
-- select *
-- from CATALOGUE
--          inner join CATALOGUE C on CATALOGUE.parent_id = C.id;


------------------------------------------------------------------------------------
-- DOCUMENT
CREATE TABLE IF NOT EXISTS DOCUMENT
(
    id               SERIAL PRIMARY KEY,
    name             TEXT     NOT NULL,
    type_of_file     INTEGER CHECK ( type_of_file = 1 ) default 1,
    priority         INTEGER  NOT NULL,
    document_type_id INTEGER  not null,
    created_time     TIMESTAMP not null,
    parent_id        INTEGER                            default (1),

    FOREIGN KEY (document_type_id)
        REFERENCES DOCUMENT_TYPE (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    FOREIGN KEY (parent_id)
        REFERENCES CATALOGUE (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,

    UNIQUE (name, parent_id)
);


INSERT INTO CATALOGUE (name, created_time, parent_id) VALUES ('name', '2000-12-16 12:21:13', 11);

select * from CATALOGUE WHERE parent_id is null;



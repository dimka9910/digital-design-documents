------------------------------------------------------------------------------------
-- DOCUMENT_TYPE
CREATE TABLE IF NOT EXISTS DOCUMENT_TYPE
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

-- DROP TABLE DOCUMENT_TYPE

------------------------------------------------------------------------------------
-- USER
CREATE TABLE IF NOT EXISTS USER
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    login    TEXT NOT NULL UNIQUE ,
    password TEXT NOT NULL,
    role     INTEGER default 1
);

-- drop table USER;

------------------------------------------------------------------------------------
-- FILE_PATH
CREATE TABLE IF NOT EXISTS FILE_PATH
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    filepath TEXT NOT NULL
);

------------------------------------------------------------------------------------
-- CONCRETE_DOCUMENT
CREATE TABLE IF NOT EXISTS CONCRETE_DOCUMENT
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    name        TEXT    NOT NULL,
    description TEXT    NOT NULL,
    version INTEGER NOT NULL,
    modified_time DATETIME NOT NULL,
    parent_id   INTEGER NOT NULL,
    FOREIGN KEY (parent_id)
        REFERENCES DOCUMENT (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
);

------------------------------------------------------------------------------------
-- CATALOGUE
CREATE TABLE IF NOT EXISTS CATALOGUE
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    name         TEXT NOT NULL,
    type_of_file INTEGER CHECK ( type_of_file = 0) default 0,
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
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    name             TEXT    NOT NULL,
    type_of_file     INTEGER CHECK ( type_of_file = 1 ) default 1,
    priority         INTEGER NOT NULL,
    document_type_id INTEGER not null,
    created_time DATETIME not null,
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



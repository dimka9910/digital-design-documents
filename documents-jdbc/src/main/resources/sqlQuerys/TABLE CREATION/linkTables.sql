------------------------------------------------------------------------------------
-- FILE_PATH and CONCRETE_DOCUMENT

CREATE TABLE IF NOT EXISTS FILE_PATH_AND_CONCRETE_DOCUMENT
(
    CONCRETE_DOCUMENT_id INTEGER NOT NULL,
    FILE_PATH_id         INTEGER NOT NULL,
    FOREIGN KEY (CONCRETE_DOCUMENT_id)
        REFERENCES CONCRETE_DOCUMENT (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    FOREIGN KEY (FILE_PATH_id)
        REFERENCES FILE_PATH (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    UNIQUE (CONCRETE_DOCUMENT_id, FILE_PATH_id)
);

------------------------------------------------------------------------------------
-- DOCUMENT and CONCRETE_DOCUMENT

CREATE TABLE IF NOT EXISTS DOCUMENT_AND_CONCRETE_DOCUMENT
(
    CONCRETE_DOCUMENT_id INTEGER NOT NULL,
    DOCUMENT_id          INTEGER NOT NULL,
    FOREIGN KEY (CONCRETE_DOCUMENT_id)
        REFERENCES CONCRETE_DOCUMENT (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    FOREIGN KEY (DOCUMENT_id)
        REFERENCES DOCUMENT (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    UNIQUE (CONCRETE_DOCUMENT_id, DOCUMENT_id)
);


------------------------------------------------------------------------------------
-- CATALOGUE AND DOCUMENT
CREATE TABLE IF NOT EXISTS CATALOGUE_AND_DOCUMENT
(
    CATALOGUE_id INTEGER NOT NULL,
    DOCUMENT_id  INTEGER NOT NULL,
    FOREIGN KEY (CATALOGUE_id)
        REFERENCES CATALOGUE (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    FOREIGN KEY (DOCUMENT_id)
        REFERENCES DOCUMENT (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    UNIQUE (CATALOGUE_id, DOCUMENT_id)
);

------------------------------------------------------------------------------------
-- CATALOGUE AND CATALOGUE
CREATE TABLE IF NOT EXISTS CATALOGUE_AND_CATALOGUE
(
    CATALOGUE_id          INTEGER NOT NULL,
    CATALOGUE_children_id INTEGER NOT NULL,
    FOREIGN KEY (CATALOGUE_id)
        REFERENCES CATALOGUE (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    FOREIGN KEY (CATALOGUE_children_id)
        REFERENCES CATALOGUE (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    UNIQUE (CATALOGUE_children_id, CATALOGUE_id)
);
-- SELECT * FROM catalogue_entity;

INSERT INTO USER (login, password, role)
VALUES ('log2', 'pas', 1);


SELECT D.id           as id,
       D.name         as name,
       d.type_of_file as type_of_file,
       D.priority     as priority,
       document_type_id,
       created_time,
       D.parent_id
FROM CATALOGUE
         JOIN CATALOGUE_AND_DOCUMENT CAD on CATALOGUE.id = CAD.CATALOGUE_id
         JOIN DOCUMENT D on CAD.DOCUMENT_id = D.id
WHERE CATALOGUE.id = 1;

SELECT C.id as id, C.name as name, c.type_of_file as type_of_file, c.parent_id as parent_id
FROM CATALOGUE
         JOIN CATALOGUE_AND_CATALOGUE CAC on CATALOGUE.id = CAC.CATALOGUE_id
         JOIN CATALOGUE C on C.id = CAC.CATALOGUE_children_id
WHERE CATALOGUE.id = 3;

SELECT *
FROM CATALOGUE_AND_CATALOGUE;

DELETE
FROM CATALOGUE
WHERE id = 3;

SELECT *
FROM CATALOGUE
         JOIN CATALOGUE_AND_CATALOGUE CAC on CATALOGUE.id = CAC.CATALOGUE_id
         JOIN CATALOGUE C on C.id = CAC.CATALOGUE_children_id;

SELECT *
FROM CATALOGUE;

-- UPDATE CATALOGUE SET name = 'cc1' WHERE id = 2;

DELETE
FROM CATALOGUE
WHERE id = 8;


INSERT INTO CATALOGUE (name, parent_id)
VALUES ('c3', 1);
INSERT INTO DOCUMENT (name, priority, document_type_id, created_time)
VALUES ('d3', 0, 1, DATETIME('now'));

select *
from DOCUMENT;

INSERT INTO CATALOGUE_AND_DOCUMENT (CATALOGUE_id, DOCUMENT_id)
VALUES (1, 1),
       (1, 2),
       (1, 3);
INSERT INTO CATALOGUE_AND_CATALOGUE (CATALOGUE_id, CATALOGUE_children_id)
VALUES (1, 2),
       (1, 3),
       (1, 4);

SELECT *
FROM CATALOGUE_AND_CATALOGUE


SELECT id, filepath, parent_id
FROM FILE_PATH_AND_CONCRETE_DOCUMENT
         JOIN FILE_PATH FP on FP.id = FILE_PATH_AND_CONCRETE_DOCUMENT.FILE_PATH_id WHERE CONCRETE_DOCUMENT_id =
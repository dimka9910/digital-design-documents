select *
from DOCUMENT;


SELECT id, filepath, parent_id
FROM FILE_PATH_AND_CONCRETE_DOCUMENT
         JOIN FILE_PATH FP on FP.id = FILE_PATH_AND_CONCRETE_DOCUMENT.FILE_PATH_id
WHERE CONCRETE_DOCUMENT_id = 1;


SELECT * FROM DOCUMENT JOIN CONCRETE_DOCUMENT C on DOCUMENT.id = C.parent_id;



INSERT INTO CONCRETE_DOCUMENT (name, description, version, modified_time, parent_id)
VALUES ('name1', '(mod)dd1', 2, DATETIME('now'), 1);

SELECT C.id as id, C.name as name, description, version, modified_time, c.parent_id as parent_id
FROM DOCUMENT JOIN CONCRETE_DOCUMENT C on DOCUMENT.id = C.parent_id
WHERE DOCUMENT.parent_id = 1
ORDER BY version DESC;


SELECT * FROM CONCRETE_DOCUMENT
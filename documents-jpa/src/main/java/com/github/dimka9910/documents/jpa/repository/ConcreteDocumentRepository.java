package com.github.dimka9910.documents.jpa.repository;

import com.github.dimka9910.documents.jpa.entity.files.documents.ConcreteDocument;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcreteDocumentRepository extends JpaRepository<ConcreteDocument, Long> {

    @Query(value = "SELECT cc FROM ConcreteDocument c INNER JOIN c.parent_id cc WHERE cc.id = ?1")
    List<ConcreteDocument> getAllVersions(@Param("idd") Long idd);

}

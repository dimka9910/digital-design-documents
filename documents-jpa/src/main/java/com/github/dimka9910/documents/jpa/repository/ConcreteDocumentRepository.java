package com.github.dimka9910.documents.jpa.repository;

import com.github.dimka9910.documents.jpa.entity.files.documents.ConcreteDocument;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ConcreteDocumentRepository extends JpaRepository<ConcreteDocument, Long> {

    @Query(value = "SELECT c FROM ConcreteDocument c INNER JOIN c.parent_id cc WHERE cc.id = ?1")
    List<ConcreteDocument> getAllVersions(@Param("idd") Long idd);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ConcreteDocument c WHERE c.parent_id in (SELECT d from Document d where d.id = ?1)")
    void deleteByParent_id(@Param("idd") Long idd);

}

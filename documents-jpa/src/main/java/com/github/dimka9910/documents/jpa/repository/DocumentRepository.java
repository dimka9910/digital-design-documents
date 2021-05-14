package com.github.dimka9910.documents.jpa.repository;

import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {



    @Query(value = "SELECT * FROM Document c WHERE c.fk_parent = ?1",
            nativeQuery = true)
    List<Document> getChildrens(Long idd);

}

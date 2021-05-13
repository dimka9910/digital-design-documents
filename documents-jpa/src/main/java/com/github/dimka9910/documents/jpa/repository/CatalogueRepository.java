package com.github.dimka9910.documents.jpa.repository;

import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {

    @Query(value = "SELECT c FROM Catalogue c WHERE c.parentCatalogue is null ")
    Optional<Catalogue> getRoot();


    @Query(value = "SELECT * FROM Catalogue c WHERE c.fk_parent = ?1",
    nativeQuery = true)
    List<Catalogue> getChildrens(Long idd);

    List<Catalogue> findAll();
}

package com.github.dimka9910.documents.jpa.repository;

import com.github.dimka9910.documents.dao.CatalogueDao;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {

    @Query(value = "SELECT c FROM Catalogue c WHERE c.parent_id is null ")
    Optional<Catalogue> getRoot();

//    @Query(value = "SELECT c FROM Catalogue c INNER JOIN c.parent_id cc WHERE cc.id = ?1")
//    List<Catalogue> getChildrens(@Param("idd") Long idd);

    @Query(value = "SELECT * FROM Catalogue c WHERE c.parent_id = ?1",
    nativeQuery = true)
    List<Catalogue> getChildrens(Long idd);




    Optional<Catalogue> getById(Long id);

    List<Catalogue> findAll();
}

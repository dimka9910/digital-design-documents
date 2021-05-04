package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.CatalogueDao;
import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entityParser.files.CatalogueParser;
import com.github.dimka9910.documents.jpa.entityParser.files.DocumentParser;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.CatalogueRepository;
import com.github.dimka9910.documents.jpa.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.nio.channels.NotYetConnectedException;
import java.util.LinkedList;
import java.util.List;

@Component("catalogueDaoJpa")
public class CatalogueDaoJpa implements CatalogueDao {


    @Autowired
    private CatalogueRepository catalogueRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private CatalogueParser catalogueParser;
    @Autowired
    private DocumentParser documentParser;

    @PersistenceContext
    private EntityManager em;

    @Override
    public CatalogueDto getRootCatalogue() {
        return catalogueParser.EtoDTO(
                catalogueRepository.getRoot()
                        .orElseThrow(IdNotFoundException::new));
    }

    @Override
    public CatalogueDto getCatalogueById(Long id) {
        return catalogueParser.EtoDTO(catalogueRepository.findById(id).orElseThrow(IdNotFoundException::new));
    }

    @Override
    public List<CatalogueDto> getAllCatalogues() {
        List<CatalogueDto> catalogueDto = new LinkedList<>();
        catalogueRepository.findAll().forEach(v ->{
            catalogueDto.add(catalogueParser.EtoDTO(v));
        });
        return catalogueDto;
    }

    @Override
    public List<FileAbstractDto> getAllChildren(CatalogueDto catalogueDto) {
        List<FileAbstractDto> list = new LinkedList<>();
        list.addAll(catalogueParser.fromList(catalogueRepository.getChildrens(catalogueDto.getId())));
        list.addAll(documentParser.fromList(documentRepository.getChildrens(catalogueDto.getId())));
        return list;
    }

    @Transactional
    @Override
    public CatalogueDto addCatalogue(CatalogueDto catalogueDto, CatalogueDto parent) {
        catalogueDto.setParent_id(parent.getId());
        Catalogue catalogue = catalogueParser.DTOtoE(catalogueDto);

        em.persist(catalogue);
        return catalogueParser.EtoDTO(catalogue);
    }

    @Override
    @Transactional
    public CatalogueDto modifyCatalogue(CatalogueDto catalogueDto) {
        Catalogue catalogue = catalogueRepository.findById(catalogueDto.getId()).orElseThrow(IdNotFoundException::new);
        catalogue.setName(catalogueDto.getName());
        return catalogueParser.EtoDTO(catalogue);
    }

    @Override
    public Long deleteCatalogue(CatalogueDto catalogueDto) {
        catalogueRepository.deleteById(catalogueDto.getId());
        return 0L;
    }
}

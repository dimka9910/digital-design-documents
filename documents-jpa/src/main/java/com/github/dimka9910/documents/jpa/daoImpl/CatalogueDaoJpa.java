package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.CatalogueDao;
import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entityParser.files.CatalogueParser;
import com.github.dimka9910.documents.jpa.entityParser.files.DocumentParser;
import com.github.dimka9910.documents.jpa.exceprions.ConstraintsException;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.CatalogueRepository;
import com.github.dimka9910.documents.jpa.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component("catalogueDaoJpa")
@Slf4j
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
    public Page<CatalogueDto> getAllCatalogues(Pageable pageable, String name) {
        name = "%" + Optional.ofNullable(name).orElse("") + "%";
        return catalogueRepository.findAllCataloguesByName(name, pageable).map(catalogueParser::EtoDTO);
    }

    @Override
    @Transactional
    public List<FileAbstractDto> getAllChildren(Long id) {
        List<FileAbstractDto> list = new LinkedList<>();
        list.addAll(catalogueParser.fromList(catalogueRepository.getChildrens(id)));
        list.addAll(documentParser.fromList(documentRepository.getChildrens(id)));
        return list;
    }

    @Transactional
    @Override
    public CatalogueDto addCatalogue(CatalogueDto catalogueDto) {
        Catalogue catalogue = catalogueParser.DTOtoE(catalogueDto);
        try {
            em.persist(catalogue);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ConstraintsException();
        }
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
    public Long deleteCatalogue(Long id) {
        catalogueRepository.deleteById(id);
        return 0L;
    }
}

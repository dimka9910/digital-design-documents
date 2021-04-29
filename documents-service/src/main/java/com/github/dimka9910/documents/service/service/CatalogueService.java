package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.CatalogueDao;
import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dao.DocumentTypeDao;
import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.service.Exceprions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("catalogueService")
public class CatalogueService {

    private CatalogueDao catalogueDao;
    private ConcreteDocumentDao concreteDocumentDao;
    private DaoFactory daoFactory;

    public CatalogueService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        concreteDocumentDao = daoFactory.getConcreteDocumentDao();
        catalogueDao = daoFactory.getCatalogueDao();
    }

    public CatalogueDto getCatalogueById(Long id){
        CatalogueDto catalogueDto;
        if (id == null)
            return catalogueDao.getRootCatalogue();
        else
            return catalogueDao.getCatalogueById(id);
    }

    public List<FileAbstractDto> getInnerCataloguesAndDocuments(Long id){
        List<FileAbstractDto> list = List.of();
        if (id == null)
            list = catalogueDao.getAllChildren(catalogueDao.getRootCatalogue());
        else{
            list = catalogueDao.getAllChildren(catalogueDao.getCatalogueById(id));
        }
        return list;
    }

    public void deleteCatalogueById(Long id){
        if (id != catalogueDao.getRootCatalogue().getId())
            catalogueDao.deleteCatalogue(catalogueDao.getCatalogueById(id));
    }

    public CatalogueDto createCatalogue(CatalogueDto children, Long parent_id){
        return catalogueDao.addCatalogue(children, getCatalogueById(parent_id));
    }

    public CatalogueDto modifyCatalogue(Long id, String name){
        if (id != catalogueDao.getRootCatalogue().getId())
            catalogueDao.modifyCatalogue(CatalogueDto.builder().name(name).id(id).build());
        return catalogueDao.getCatalogueById(id);
    }

}

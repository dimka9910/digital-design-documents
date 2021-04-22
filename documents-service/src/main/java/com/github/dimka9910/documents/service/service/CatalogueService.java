package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.CatalogueDao;
import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dao.DocumentDao;
import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;

import java.util.List;

public class CatalogueService {
    private CatalogueDao catalogueDao = DaoFactory.getInstance("").getCatalogueDao();
    private DocumentDao documentDao = DaoFactory.getInstance("").getDocumentDao();
    private DocumentService documentService = new DocumentService();
    private ConcreteDocumentDao concreteDocumentDao = DaoFactory.daoFactory.getConcreteDocumentDao();

    public CatalogueDto getCatalogueById(Long id){
        if (id == null)
            return catalogueDao.getRootCatalogue();
        else
            return catalogueDao.getCatalogueById(id);
    }

    public List<FileAbstractDto> getInnerCataloguesAndDocuments(Long id){
        List<FileAbstractDto> list;
        if (id == null)
            list = catalogueDao.getAllChildren(catalogueDao.getRootCatalogue());
        else{
            list = catalogueDao.getAllChildren(catalogueDao.getCatalogueById(id));
        }
        list.forEach(v -> {
            if (v.getTypeOfFile() == TypeOfFileEnum.DOCUMENT){
                v.setName(concreteDocumentDao.getLastVersion((DocumentDto) v).getName());
            }
        });
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

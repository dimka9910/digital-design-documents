package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.CatalogueDao;
import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CatalogueService {

    @Autowired
    UserService userService;

    private CatalogueDao catalogueDao;

    public CatalogueService(DaoFactory daoFactory) {
        catalogueDao = daoFactory.getCatalogueDao();
    }

    public CatalogueDto getCatalogueById(Long id) {
        CatalogueDto catalogueDto;
        if (id == null)
            return catalogueDao.getRootCatalogue();
        else
            return catalogueDao.getCatalogueById(id);
    }

    public List<FileAbstractDto> getInnerCataloguesAndDocuments(Long id) {
        List<FileAbstractDto> list = List.of();
        list = catalogueDao.getAllChildren(id);
        return list;
    }

    public void deleteCatalogueById(Long id) {
        if (id != catalogueDao.getRootCatalogue().getId())
            catalogueDao.deleteCatalogue(id);
    }

    public CatalogueDto createCatalogue(CatalogueDto children) {
        children.setUserCreatedById(userService.getCurrentUser().getId());
        return catalogueDao.addCatalogue(children);
    }

    public CatalogueDto modifyCatalogue(Long id, String name) {
        if (id != catalogueDao.getRootCatalogue().getId())
            catalogueDao.modifyCatalogue(CatalogueDto.builder().name(name).id(id).build());
        return catalogueDao.getCatalogueById(id);
    }

}

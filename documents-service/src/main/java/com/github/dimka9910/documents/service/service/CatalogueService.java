package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.CatalogueDao;
import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class CatalogueService {

    @Autowired
    UserService userService;
    @Autowired
    AccessService accessService;

    @Autowired
    private CatalogueDao catalogueDao;


    public CatalogueDto getCatalogueById(Long id) {
        CatalogueDto catalogueDto;
        if (id == null)
            return catalogueDao.getRootCatalogue();
        else {
            if (!accessService.chekRAccess(id))
                throw new AccessDeniedException("Access error");
            return catalogueDao.getCatalogueById(id);
        }
    }

    public List<FileAbstractDto> getInnerCataloguesAndDocuments(Long id, String type, String name) {
        if (!accessService.chekRAccess(id))
            throw new AccessDeniedException("Access error");

        List<FileAbstractDto> list = catalogueDao.getAllChildren(id);
        if (type != null)
            list = list.stream().filter(v -> v.getTypeOfFile().equals(type.toUpperCase())).collect(Collectors.toList());
        if (name != null) {
            Pattern pattern = Pattern.compile(".*" + name + ".*", Pattern.CASE_INSENSITIVE);
            list = list.stream().filter(v -> pattern.matcher(v.getName()).matches()).collect(Collectors.toList());
        }
        return list;
    }

    public void deleteCatalogueById(Long id) {
        if (!accessService.chekRWAccess(id))
            throw new AccessDeniedException("You cant delete this file");

        if (id != catalogueDao.getRootCatalogue().getId())
            catalogueDao.deleteCatalogue(id);
    }

    public void deleteCatalogueByNameAndParentId(CatalogueDto catalogueDto) {
        CatalogueDto catalogueDto1 = getInnerCataloguesAndDocuments(catalogueDto.getParentId(), null, null)
                .stream()
                .filter(v -> v.getTypeOfFile().equals("CATALOGUE")).map(v -> (CatalogueDto) v)
                .filter(v -> v.getName().equals(catalogueDto.getName())).
                        findAny().orElse(null);

        assert catalogueDto1 != null;
        deleteCatalogueById(catalogueDto1.getId());
    }

    public CatalogueDto createCatalogue(CatalogueDto children) {
        if (!accessService.chekRWAccess(children.getParentId()))
            throw new AccessDeniedException("Access error");

        children.setUserCreatedById(userService.getCurrentUser().getId());
        return catalogueDao.addCatalogue(children);
    }

    public CatalogueDto modifyCatalogue(Long id, String name) {
        if (id != catalogueDao.getRootCatalogue().getId()) {
            catalogueDao.modifyCatalogue(CatalogueDto.builder().name(name).id(id).build());
        }
        if (!accessService.chekRWAccess(id))
            throw new AccessDeniedException("You cant modify this file");
        return catalogueDao.getCatalogueById(id);
    }

}

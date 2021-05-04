package com.github.dimka9910.documents.jpa.entityParser.files;

import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.dto.user.UserRolesEnum;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.user.User;
import com.github.dimka9910.documents.jpa.repository.CatalogueRepository;
import com.github.dimka9910.documents.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class CatalogueParser {

    @Autowired
    CatalogueRepository catalogueRepository;
    @Autowired
    UserRepository userRepository;


    //@Transactional
    public CatalogueDto EtoDTO(Catalogue catalogue) {
        Long id = null;
        //System.out.println(catalogue.getParent_id());
        if(catalogue.getParent_id() != null)
            id = catalogue.getParent_id().getId();

        return CatalogueDto.builder()
                .id(catalogue.getId())
                .created_time(new Timestamp(catalogue.getCreated_time().getTime()))
                .parent_id(id)
                .name(catalogue.getName()).typeOfFile(TypeOfFileEnum.CATALOGUE).build();
    }

    public Catalogue DTOtoE(CatalogueDto catalogueDto){

        Date date;
        if (catalogueDto.getCreated_time() == null)
            date = new Date();
        else
            date = new Date(catalogueDto.getCreated_time().getTime());

        System.out.println("----" + catalogueRepository.findById(catalogueDto.getParent_id()).orElse(null));

        return new Catalogue(catalogueDto.getId(),
                catalogueRepository.findById(catalogueDto.getParent_id()).orElse(null),
                date,
                catalogueDto.getCreated_by() == null ? null : userRepository.findById(catalogueDto.getCreated_by()).orElse(null),
                catalogueDto.getName(),
                catalogueDto.getReadWritePermissionedUsers(),
                catalogueDto.getReadPermissionedUsers());
    }

    public List<CatalogueDto> fromList(List<Catalogue> list){
        List<CatalogueDto> catalogueDto = new LinkedList<>();
        list.forEach(v ->{
            catalogueDto.add(EtoDTO(v));
        });
        return catalogueDto;
    }
}
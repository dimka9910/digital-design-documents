package com.github.dimka9910.documents.jpa.entityParser.files;

import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dao.FilePathDao;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.dto.files.documents.FilePathDto;
import com.github.dimka9910.documents.dto.files.documents.PriorityEnum;
import com.github.dimka9910.documents.jpa.entity.files.documents.ConcreteDocument;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import com.github.dimka9910.documents.jpa.entity.files.documents.DocumentType;
import com.github.dimka9910.documents.jpa.entity.user.User;
import com.github.dimka9910.documents.jpa.repository.DocumentRepository;
import com.github.dimka9910.documents.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConcreteDocumentParser {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DocumentRepository documentRepository;

    public ConcreteDocumentDto EtoDTO(ConcreteDocument document) {
        return ConcreteDocumentDto.builder()
                .id(document.getId())
                .parent_id(document.getParent_id().getId())
                .version(document.getVersion())
                .data(document.getData().stream().map(v -> FilePathDto.builder().path(v).build()).collect(Collectors.toList()))
                .modified_time(new Timestamp(document.getModified_time().getTime()))
                .description(document.getDescription())
                .modified_by(document.getModified_by().getId())
                .name(document.getName())
                .build();
    }

    public ConcreteDocument DTOtoE(ConcreteDocumentDto concreteDocumentDto) {

        User user = userRepository.getById(concreteDocumentDto.getModified_by()).orElse(null);
        Document document = documentRepository.findById(concreteDocumentDto.getParent_id()).orElse(null);

        return new ConcreteDocument(concreteDocumentDto.getId(),
                concreteDocumentDto.getName(),
                concreteDocumentDto.getDescription(),
                concreteDocumentDto.getVersion(),
                new Date(concreteDocumentDto.getModified_time().getTime()),
                user,
                document,
                concreteDocumentDto.getData().stream().map(v -> v.getPath()).collect(Collectors.toList())
        );
    }

        public List<ConcreteDocumentDto> fromList(List<ConcreteDocument> list) {
        List<ConcreteDocumentDto> concreteDocumentDto = new LinkedList<>();
        list.forEach(v -> {
            concreteDocumentDto.add(EtoDTO(v));
        });
        return concreteDocumentDto;
    }
}

package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.files.documents.ConcreteDocument;
import com.github.dimka9910.documents.jpa.entityParser.files.ConcreteDocumentParser;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.ConcreteDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class ConcreteDocumentDaoJpa implements ConcreteDocumentDao {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    ConcreteDocumentParser concreteDocumentParser;
    @Autowired
    ConcreteDocumentRepository concreteDocumentRepository;

    @Override
    public ConcreteDocumentDto addNewVersion(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto) {
        ConcreteDocumentDto concreteDocumentDto1 = getLastVersion(documentDto);
        if (concreteDocumentDto1 == null)
            concreteDocumentDto.setVersion(1L);
        else
            concreteDocumentDto.setVersion(concreteDocumentDto1.getVersion() + 1);
        concreteDocumentDto.setParent_id(documentDto.getId());

        return concreteDocumentParser.EtoDTO(
                concreteDocumentRepository.save(concreteDocumentParser.DTOtoE(concreteDocumentDto))
        );
    }

    @Override
    public ConcreteDocumentDto getLastVersion(DocumentDto documentDto) {
        ConcreteDocument concreteDocument = (ConcreteDocument) em.createQuery("select max(c.version) from ConcreteDocument c INNER JOIN c.parent_id cc where cc.id = :idd")
                .setParameter("idd", documentDto.getId())
                .getSingleResult();
        if (concreteDocument == null)
            return null;
        return concreteDocumentParser.EtoDTO(concreteDocument);
    }

    @Override
    public List<ConcreteDocumentDto> getAllVersions(DocumentDto documentDto) {
        return concreteDocumentParser.fromList(
                concreteDocumentRepository.getAllVersions(documentDto.getId())
        );
    }

    @Override
    public ConcreteDocumentDto getById(Long id) {
        return concreteDocumentParser.EtoDTO(concreteDocumentRepository.findById(id).orElseThrow(IdNotFoundException::new));
    }

    @Override
    public Long deleteConcreteDocument(ConcreteDocumentDto concreteDocumentDto) {
        concreteDocumentRepository.deleteById(concreteDocumentDto.getId());
        return 0L;
    }
}

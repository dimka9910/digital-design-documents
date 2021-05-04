package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import com.github.dimka9910.documents.jpa.entity.files.documents.ConcreteDocument;
import com.github.dimka9910.documents.jpa.entity.files.documents.Document;
import com.github.dimka9910.documents.jpa.entityParser.files.ConcreteDocumentParser;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.ConcreteDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
    @Transactional
    public ConcreteDocumentDto addNewVersion(DocumentDto documentDto, ConcreteDocumentDto concreteDocumentDto) {
        ConcreteDocumentDto concreteDocumentDto1 = getLastVersion(documentDto);
        if (concreteDocumentDto1 == null)
            concreteDocumentDto.setVersion(1L);
        else
            concreteDocumentDto.setVersion(concreteDocumentDto1.getVersion() + 1);
        concreteDocumentDto.setParent_id(documentDto.getId());

        ConcreteDocument concreteDocument = concreteDocumentParser.DTOtoE(concreteDocumentDto);
        concreteDocument.setId(null);

        em.persist(concreteDocument);
        return concreteDocumentParser.EtoDTO(concreteDocument);
    }

    @Override
    public ConcreteDocumentDto getLastVersion(DocumentDto documentDto) {
        try {
            ConcreteDocument concreteDocument = (ConcreteDocument) em.createQuery("select c from ConcreteDocument c INNER JOIN c.parent_id cc where cc.id = :idd ORDER BY c.version desc")
                    .setParameter("idd", documentDto.getId())
                    .setMaxResults(1)
                    .getSingleResult();
            if (concreteDocument == null)
                return null;
            return concreteDocumentParser.EtoDTO(concreteDocument);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
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

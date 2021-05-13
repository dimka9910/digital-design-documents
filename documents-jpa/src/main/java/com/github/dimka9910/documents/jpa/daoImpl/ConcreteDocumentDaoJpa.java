package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.ConcreteDocumentDao;
import com.github.dimka9910.documents.dto.files.documents.ConcreteDocumentDto;
import com.github.dimka9910.documents.dto.files.documents.DocumentDto;
import com.github.dimka9910.documents.jpa.entity.files.documents.ConcreteDocument;
import com.github.dimka9910.documents.jpa.entityParser.files.ConcreteDocumentParser;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.ConcreteDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ConcreteDocumentDto addNewVersion(ConcreteDocumentDto concreteDocumentDto) {
        return null;
    }

    @Override
    public ConcreteDocumentDto getLastVersion(Long id) {
        try {
            ConcreteDocument concreteDocument = (ConcreteDocument) em.createQuery("select c from ConcreteDocument c INNER JOIN c.parent_id cc where cc.id = :idd ORDER BY c.version desc")
                    .setParameter("idd", id)
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
    public List<ConcreteDocumentDto> getAllVersions(Long id) {
        return concreteDocumentParser.fromList(
                concreteDocumentRepository.getAllVersions(id)
        );
    }

    @Override
    public ConcreteDocumentDto getById(Long id) {
        return concreteDocumentParser.EtoDTO(concreteDocumentRepository.findById(id).orElseThrow(IdNotFoundException::new));
    }

    @Override
    public Long deleteConcreteDocument(Long id) {
        concreteDocumentRepository.deleteById(id);
        return 0L;
    }

}

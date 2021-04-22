package com.github.dimka9910.documents.dto.files.documents;

import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.files.TypeOfFileEnum;
import com.github.dimka9910.documents.dto.files.catalogues.CatalogueDto;
import com.github.dimka9910.documents.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class DocumentDto extends FileAbstractDto {
    private Long documentType;
    private PriorityEnum priority;
    private ConcreteDocumentDto topVersionDocument;
    private Timestamp created_time;
    private List<Long> versionsOfDocuments;

    @Builder
    public DocumentDto(Long id, Long parent_id, Set<UserDto> readWritePermissionedUsers, Set<UserDto> readPermissionedUsers, String name, TypeOfFileEnum typeOfFile, Long documentType, PriorityEnum priority, ConcreteDocumentDto topVersionDocument, Timestamp created_time, List<Long> versionsOfDocuments) {
        super(id, parent_id, readWritePermissionedUsers, readPermissionedUsers, name, typeOfFile);
        this.documentType = documentType;
        this.priority = priority;
        this.topVersionDocument = topVersionDocument;
        this.created_time = created_time;
        this.versionsOfDocuments = versionsOfDocuments;
    }

    @Override
    public String toString() {
        return "DocumentDto{" +
                "documentType=" + documentType +
                ", priority=" + priority +
                ", topVersionDocument=" + topVersionDocument +
                ", created_time=" + created_time +
                ", versionsOfDocuments=" + versionsOfDocuments +
                ", id=" + id +
                ", parent_id=" + parent_id +
                ", readWritePermissionedUsers=" + readWritePermissionedUsers +
                ", readPermissionedUsers=" + readPermissionedUsers +
                ", name='" + name + '\'' +
                ", typeOfFile=" + typeOfFile +
                '}';
    }
}

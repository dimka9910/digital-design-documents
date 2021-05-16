package com.github.dimka9910.documents.dto.files.documents;

import com.github.dimka9910.documents.dto.AbstractDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcreteDocumentDto implements AbstractDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Long version;
    private Timestamp modifiedTime;
    private Long userModifiedBy;
    private Long parentDocumentId;
    private List<FilePathDto> data;

}

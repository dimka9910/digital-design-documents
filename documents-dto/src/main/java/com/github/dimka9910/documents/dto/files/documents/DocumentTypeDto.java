package com.github.dimka9910.documents.dto.files.documents;

import com.github.dimka9910.documents.dto.AbstractDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class DocumentTypeDto implements AbstractDto {
    private Long id;
    @NotBlank
    private String name;
}

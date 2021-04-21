package com.github.dimka9910.documents.dto.files.catalogues;

import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CatalogueDto {
    private Set<FileAbstractDto> innerFiles;
}

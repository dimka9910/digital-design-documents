package com.github.dimka9910.documents.dto.files.documents;

import com.github.dimka9910.documents.dto.AbstractDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilePathDto implements AbstractDto {
    private Long id;
    private String path;
    private Long parent_id;
}

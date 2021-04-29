package com.github.dimka9910.documents.dto.files.documents;

import com.github.dimka9910.documents.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class DocumentVisualizationDto {

    private Long id;
    private String name;
    private String description;
    private Long documentType;
    private PriorityEnum priority;
    private Long version;
    private Timestamp modified_time;
    private Long parent_id;
    private List<FilePathDto> data;
}

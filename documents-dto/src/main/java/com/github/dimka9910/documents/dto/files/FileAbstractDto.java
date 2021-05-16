package com.github.dimka9910.documents.dto.files;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dimka9910.documents.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FileAbstractDto implements AbstractDto {
    protected Long id;
    @NotNull
    protected Long parentId;
    protected Timestamp createdTime;
    protected Long userCreatedById;
    protected String name;
    protected String typeOfFile;
}

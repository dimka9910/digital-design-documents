package com.github.dimka9910.documents.dao;

import com.github.dimka9910.documents.dto.restdtos.ManageAccessDto;
import com.github.dimka9910.documents.dto.user.UserDto;

import java.util.List;

public interface FileAbstractDao extends AbstractDao{

    boolean checkRWAccess(Long id);
    boolean checkRAccess(Long id);

    List<UserDto> manageAccess(ManageAccessDto manageAccessDto);

}

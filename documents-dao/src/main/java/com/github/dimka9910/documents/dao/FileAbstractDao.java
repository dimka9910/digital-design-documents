package com.github.dimka9910.documents.dao;

import com.github.dimka9910.documents.dto.files.FileAbstractDto;
import com.github.dimka9910.documents.dto.user.UserDto;

import java.util.List;

public interface FileAbstractDao extends AbstractDao{

    boolean checkRWAccess(Long id);
    boolean checkRAccess(Long id);

    List<UserDto> grantRAccess(Long fileId, Long userId);
    List<UserDto> grantRWAccess(Long fileId, Long userId);
    List<UserDto> declineRAccess(Long fileId, Long userId);
    List<UserDto> declineRWAccess(Long fileId, Long userId);

}

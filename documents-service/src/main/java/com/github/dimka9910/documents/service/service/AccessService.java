package com.github.dimka9910.documents.service.service;

import com.github.dimka9910.documents.dao.FileAbstractDao;
import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.jpa.entity.files.FileAbstract;
import com.github.dimka9910.documents.jpa.entity.user.User;
import com.github.dimka9910.documents.jpa.entity.user.UserRolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessService {

    @Autowired
    UserService userService;
    @Autowired
    FileAbstractDao fileAbstractDaoJpa;

    public boolean chekRWAccess(Long id) {
        if (userService.getCurrentUser().getRole().equals("ADMIN"))
            return true;
        return fileAbstractDaoJpa.checkRWAccess(id);
    }

    public boolean chekRAccess(Long id) {
        if (userService.getCurrentUser().getRole().equals("ADMIN"))
            return true;
        return (fileAbstractDaoJpa.checkRAccess(id) || fileAbstractDaoJpa.checkRWAccess(id));
    }

    public List<UserDto> modifyFileAccess(Long fileId, Long userId, boolean rw, boolean grant) {
        if (!chekRWAccess(fileId))
            throw new AccessDeniedException("You cant modify this file");

        if (rw)
            if (grant)
                return fileAbstractDaoJpa.grantRWAccess(fileId, userId);
            else
                return fileAbstractDaoJpa.declineRWAccess(fileId, userId);
        else
            if (grant)
                return fileAbstractDaoJpa.grantRAccess(fileId, userId);
            else
                return fileAbstractDaoJpa.declineRAccess(fileId, userId);
    }


    public UserDto grantAccess(Long id, UserRolesEnum role) {
        if (userService.getCurrentUser().getRole().equals("ADMIN"))
            return userService.modifyUser(
                    UserDto.builder().id(id).role(role.toString()).build()
            );
        else
            throw new AccessDeniedException("403 returned");
    }


}

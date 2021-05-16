package com.github.dimka9910.documents.jpa.daoImpl;

import com.github.dimka9910.documents.dao.UserDao;
import com.github.dimka9910.documents.dto.user.UserDto;
import com.github.dimka9910.documents.jpa.entity.user.User;
import com.github.dimka9910.documents.jpa.entity.user.UserRolesEnum;
import com.github.dimka9910.documents.jpa.entityParser.user.UserParser;
import com.github.dimka9910.documents.jpa.exceprions.ConstraintsException;
import com.github.dimka9910.documents.jpa.exceprions.IdNotFoundException;
import com.github.dimka9910.documents.jpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Slf4j
public class UserDaoJpa implements UserDao {

    @Autowired
    UserParser userParser;
    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDto getCurrentUser() {
        return userParser.EtoDTO(
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );
    }

    @Override
    @Transactional
    public UserDto addNewUser(UserDto userDto) {
        User user = userParser.DTOtoE(userDto);
        user.setRole(UserRolesEnum.USER); // EVERYONE "USER" by default

        try {
            em.persist(user);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ConstraintsException();
        }
        return userParser.EtoDTO(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public Long deleteUser(Long id) {
        return null;
    }

    @Override
    public UserDto getUserByLogin(String login) {
        return null;
    }


    @Override
    @Transactional
    public UserDto modifyUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(IdNotFoundException::new);

        if (userDto.getRole() != null)
            user.setRole(UserRolesEnum.valueOf(userDto.getRole()));
        if (userDto.getLogin() != null)
            user.setLogin(userDto.getLogin());
        if (userDto.getPassword() != null)
            user.setPassword(userDto.getPassword());

        try {
            em.merge(user);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ConstraintsException();
        }
        return userParser.EtoDTO(user);
    }
}

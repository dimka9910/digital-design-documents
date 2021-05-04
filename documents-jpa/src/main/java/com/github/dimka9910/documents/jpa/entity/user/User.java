package com.github.dimka9910.documents.jpa.entity.user;

import com.github.dimka9910.documents.jpa.entity.files.FileAbstract;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users", uniqueConstraints =
        {
                @UniqueConstraint(columnNames = {"login"})
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private UserRolesEnum role;


    public User(Long id, String login, String password, UserRolesEnum role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}

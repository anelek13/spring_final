package com.example.schoolapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_users",
        initialValue = 1,
        allocationSize = 1
)
public class User extends AuditModel {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(unique = true, name = "login")
    private String login;

    private String password;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Role role;

    @Transient
    public boolean isAdmin() {
        return Objects.nonNull(this.role) && Objects.equals(this.role.getId(), Role.ROLE_ADMIN_ID);
    }


    @Transient
    public boolean isStudent() {
        return Objects.nonNull(this.role) && Objects.equals(this.role.getId(), Role.ROLE_STUDENT_ID);
    }

    @Transient
    public boolean isTeacher() {
        return Objects.nonNull(this.role) && Objects.equals(this.role.getId(), Role.ROLE_TEACHER_ID);
    }

    @Transient
    public boolean isManager() {
        return Objects.nonNull(this.role) && Objects.equals(this.role.getId(), Role.ROLE_MANAGER_ID);
    }
}

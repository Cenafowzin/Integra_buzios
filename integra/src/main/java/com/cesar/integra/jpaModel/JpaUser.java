package com.cesar.integra.jpaModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "User")
public class JpaUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String management;

    @Column
    private String phone;

    @Column
    private boolean pwd;

    @Column
    private String gender;

    public JpaUser() {

    }

}

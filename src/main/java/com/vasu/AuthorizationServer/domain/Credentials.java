package com.vasu.AuthorizationServer.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "credentials")
@Data
public class Credentials implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;
    private boolean enabled;
}

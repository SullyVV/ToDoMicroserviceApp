package com.Ryan.ToDoMicroservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "EMAIL")
    @Getter @Setter
    @NotNull @NotEmpty @NotBlank
    private String email;
    @Column(name = "NAME")
    @Getter @Setter
    @NotNull @NotEmpty @NotBlank
    private String name;
    @Column(name = "PASSWD")
    @Getter @Setter
    @NotNull @NotEmpty @NotBlank
    private String passwd;

}

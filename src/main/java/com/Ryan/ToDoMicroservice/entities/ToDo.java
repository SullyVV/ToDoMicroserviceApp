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
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todos")
@AllArgsConstructor
@NoArgsConstructor
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @Setter @Getter
    private Integer id;

    @Column(name = "DESCRIPTION")
    @Setter @Getter
    @NotNull @NotBlank @NotEmpty
    private String description;

    @Column(name = "PRIORITY")
    @Setter @Getter
    @NotNull @NotBlank @NotEmpty
    private String priority;

    @Column(name = "FKUSER")
    @Setter @Getter
    @NotNull @NotBlank @NotEmpty
    private String fkUser;

    public String getPriority() {
        return priority;
    }
}

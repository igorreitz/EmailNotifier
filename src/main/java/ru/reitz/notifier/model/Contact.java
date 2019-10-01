package ru.reitz.notifier.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @Setter @ToString
public class Contact {
    private Integer id;
    private String name;
    private LocalDate birthDate;

    public Contact(String name, LocalDate birthDate) {
        this.id = null;
        this.name = name;
        this.birthDate = birthDate;
    }

    public boolean isNew() {
        return getId() == null;
    }
}

package ru.reitz.notifier.repository;

import ru.reitz.notifier.model.Contact;

import java.time.LocalDate;
import java.util.Collection;

public interface ContactRepository {
    Contact save(Contact contact);
    boolean delete(int id);
    Contact get(int id);
    Collection<Contact> getAll();
    Collection<Contact> getByDate(LocalDate date);
}

package ru.reitz.notifier.repository;

import org.springframework.stereotype.Repository;
import ru.reitz.notifier.model.Contact;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryContactRepositoryImpl implements ContactRepository {
    private static AtomicInteger counter = new AtomicInteger(0);

    private Map<Integer, Contact> entryMap = new ConcurrentHashMap<>();

    @Override
    public Contact save(Contact contact) {
        Objects.requireNonNull(contact, "Contact must not be null");
        if (contact.isNew()) {
            contact.setId(counter.incrementAndGet());
            entryMap.put(contact.getId(), contact);
            return contact;
        }
        return entryMap.computeIfPresent(contact.getId(), (id, oldT) -> contact);
    }

    @Override
    public boolean delete(int id) {
        return entryMap.remove(id) != null;
    }

    @Override
    public Contact get(int id) {
        return entryMap.get(id);
    }

    @Override
    public Collection<Contact> getAll() {
        return entryMap.values();
    }

    @Override
    public Collection<Contact> getByDate(LocalDate date) {
        return entryMap.values().stream()
                .filter(s -> s.getBirthDate().getMonth().equals(date.getMonth())
                        && s.getBirthDate().getDayOfMonth() == date.getDayOfMonth())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

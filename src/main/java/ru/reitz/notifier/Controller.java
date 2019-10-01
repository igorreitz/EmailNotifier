package ru.reitz.notifier;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.reitz.notifier.model.Contact;
import ru.reitz.notifier.repository.ContactRepository;
import ru.reitz.notifier.util.EmailSender;
import ru.reitz.notifier.util.GmailContactsParser;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@EnableScheduling
@Component
@RequiredArgsConstructor
class Controller {
    private static final long DELAY_MS = 86400000; //24 часа
    private static final long RETRY_MS = 86400000; //24 часа

    private final ContactRepository contactRepository;

    @Scheduled(fixedRate = RETRY_MS, initialDelay = DELAY_MS)
    void sendMail(){
        GmailContactsParser.getContacts().forEach(contactRepository::save);

        contactRepository.save(new Contact("Иванов Иван Иванович", LocalDate.of(1987,10,1)));

        Collection<Contact> contacts = contactRepository.getByDate(LocalDate.now());
        if (contacts != null && !contacts.isEmpty()) {
            StringBuilder body = new StringBuilder("Сегодня день рождения празднуют: \n");
            contacts.forEach(contact -> body
                    .append(contact.getName())
                    .append(" - ")
                    .append(contact.getBirthDate().until(LocalDate.now(), ChronoUnit.YEARS))
                    .append("\n"));

            EmailSender emailSender = new EmailSender("my-notifier@mail.ru","pochtAMPT");
            emailSender.send("Уведомление о днях рождения", body.toString(),"raits@ngs.ru");
        }
    }

    @PostConstruct
    void start(){
        sendMail();
    }
}

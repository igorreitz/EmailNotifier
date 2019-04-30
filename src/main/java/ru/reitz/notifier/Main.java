package ru.reitz.notifier;

import ru.reitz.notifier.util.EmailSender;

public class Main {
    public static void main(String[] args) {
        EmailSender emailSender = new EmailSender("my-notifier@mail.ru","pochtAMPT");
        emailSender.send("test", "body","raits@ngs.ru");
    }
}

package icu.jnet.mccreate.entities;

public class MailMessage {

    private int id;
    private String date, subject, fromAddress, textHtml;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getTextHtml() {
        return textHtml;
    }
}

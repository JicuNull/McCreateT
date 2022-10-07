package icu.jnet.mccreate.response;

import icu.jnet.mccreate.entities.MailMessage;

import java.util.ArrayList;
import java.util.List;

public class MailResponse implements Response {

    private List<MailMessage> mails;
    private String username, address;

    public List<MailMessage> getMails() {
        return mails != null ? mails : new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }
}

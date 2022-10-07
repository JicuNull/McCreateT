package icu.jnet.mccreate.request;

public class MailRequest implements Request {

    private final String id;

    public MailRequest(String id) {
        this.id = id;
    }

    @Override
    public String getUrl() {
        return "https://trashmailgenerator.de/backend.php?username=" + id;
    }
}

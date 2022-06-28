import icu.jnet.mccreate.McCreate;

public class Main {

    public static void main(String[] args) {
        McCreate create = new McCreate("host", 993, "user@example.org", "pass");
        create.register("123", "75408e58622a88c6");
    }
}

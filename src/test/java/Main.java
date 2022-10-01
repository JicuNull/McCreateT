import icu.jnet.mccreate.McCreate;
import icu.jnet.mcd.api.McClient;
import icu.jnet.mcd.utils.listener.ClientStateListener;


public class Main {

    public static void main(String[] args) {
        ClientStateListener clientListener = createClientListener();
        McCreate create = new McCreate("host", 993, "user@example.org", "pass", clientListener);
        create.register("123", McClient.DEFAULT_DEVICE_ID);
    }

    private static ClientStateListener createClientListener() {
        return new ClientStateListener() {
            @Override
            public String tokenRequired() {
                return ClientStateListener.super.tokenRequired();
            }
        };
    }
}

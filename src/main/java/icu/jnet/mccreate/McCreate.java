package icu.jnet.mccreate;

import icu.jnet.mcd.api.McClient;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.utils.listener.ClientStateListener;

import java.util.Random;

public class McCreate extends EmailHandler {

    private final String domain;
    private ClientStateListener listener;

    public McCreate(String host, int port, String user, String password) {
        super(host, port, user, password);
        this.domain = user.contains("@") ? user.split("@")[1] : "";
    }

    public void setClientStateListener(ClientStateListener listener) {
        this.listener = listener;
    }

    public RegAccount register(String password, String deviceId) {
        String email = rdmID() + "@" + domain;
        McClient client = new McClient();
        client.addStateListener(listener);
        if(client.register(email, password, "25524", deviceId).success()) {
            String code = searchActivationCode(email, 240);
            if(code != null && client.activateAccount(email, code, deviceId).success()) {
                if(client.login(email, password, deviceId).success() && success(client.useMyMcDonalds(true))) {
                    return new RegAccount(email, password, deviceId);
                }
            }
        }
        return null;
    }

    private String rdmID() {
        return new Random().ints(97, 123).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    private boolean success(Response response) {
        return response.getStatus().getMessage().contains("successfully");
    }
}

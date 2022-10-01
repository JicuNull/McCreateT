package icu.jnet.mccreate;

import icu.jnet.mcd.api.McClient;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.utils.listener.ClientStateListener;

import java.util.Random;

public class McCreate extends EmailHandler {

    private final Random rand = new Random();
    private final String domain;
    private final ClientStateListener clientListener;

    public McCreate(String host, int port, String user, String password, ClientStateListener clientListener) {
        super(host, port, user, password);
        this.domain = user.contains("@") ? user.split("@")[1] : "";
        this.clientListener = clientListener;
    }

    public RegAccount register(String password) {
        return register(password, rdmID(16));
    }

    public RegAccount register(String password, String deviceId) {
        String email = rdmID(10) + "@" + domain;
        McClient client = new McClient();
        client.addStateListener(clientListener);
        if(client.register(email, password, "25524", deviceId).success()) {
            String code = searchActivationCode(email, 240);
            if(code != null && client.activateAccount(email, code, deviceId).success()) {
                if(client.login(email, password, deviceId).success() && client.useMyMcDonalds(true).success()) {
                    return new RegAccount(email, password, deviceId);
                }
            }
        }
        return null;
    }

    private String rdmID(int length) {
        return rand.ints(97, 123).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}

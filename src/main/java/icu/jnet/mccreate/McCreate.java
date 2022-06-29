package icu.jnet.mccreate;

import icu.jnet.mcd.api.McClient;
import icu.jnet.mcd.api.response.Response;

import java.util.Random;

public class McCreate extends EmailHandler {

    private final String domain;

    public McCreate(String host, int port, String user, String password) {
        super(host, port, user, password);
        this.domain = user.contains("@") ? user.split("@")[1] : "";
    }

    public RegAccount register(String password, String deviceId) {
        String email = rdmID() + "@" + domain;
        if(new McClient().register(email, password, "25524", deviceId)) {
            String code = searchActivationCode(email, 240);
            if(code != null && new McClient().activateAccount(email, code, deviceId)) {
                McClient client = new McClient();
                if(client.login(email, password, deviceId) && success(client.useMyMcDonalds(true))) {
                    return new RegAccount(email, password, deviceId);
                }
            }
        }
        return null;
    }

    private String rdmID() {
        return new Random().ints(65, 123).filter(i -> !(i >= 91 && i <= 96)).limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    private boolean success(Response response) {
        return response.getStatus().getMessage().equals("Success");
    }
}

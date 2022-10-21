package icu.jnet.mccreate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import icu.jnet.mcd.api.McClient;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.utils.listener.ClientStateListener;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Random;

public class McCreate extends EmailHandler {

    private final Random rand = new Random();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ClientStateListener clientListener;
    private final String[] plz = {"22083", "22145", "22399", "23554", "28205", "28197", "30625", "30419"};

    public McCreate(String host, int port, String user, String password, ClientStateListener clientListener) {
        super(host, port, user, password);
        this.clientListener = clientListener;
    }

    public RegAccount register(String email) {
        return register(email, rdmID(8) + "1K#", rdmID(16));
    }

    public RegAccount register(String email, String password, String deviceId) {
        McClient client = new McClient();
        client.addStateListener(clientListener);
        Response response = client.register(email, password, plz[rand.nextInt(plz.length)], deviceId);
        if(response.success()) {
            String code = searchActivationCode(email, 240);
            if (code != null && client.activateAccount(email, code, deviceId).success()) {
                if (client.login(email, password, deviceId).success() && client.useMyMcDonalds(true).success()) {
                    return new RegAccount(email, password, deviceId);
                }
            }
        } else {
            System.out.println(gson.toJson(response));
        }
        return null;
    }

    private String rdmID(int length) {
        return rand.ints(48, 123).filter(i -> !(i >= 58 && i <= 96)).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}

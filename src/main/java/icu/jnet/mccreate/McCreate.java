package icu.jnet.mccreate;

import icu.jnet.mccreate.entities.MailMessage;
import icu.jnet.mccreate.request.MailRequest;
import icu.jnet.mccreate.response.MailResponse;
import icu.jnet.mcd.api.McClient;
import icu.jnet.mcd.api.response.Response;
import icu.jnet.mcd.utils.Utils;
import icu.jnet.mcd.utils.listener.ClientStateListener;

import java.util.List;
import java.util.Random;

public class McCreate extends McCreateBase {

    private final Random rand = new Random();
    private final ClientStateListener clientListener;
    private final String[] plz = {"22083", "22145", "22399", "23554", "28205", "28197", "30625", "30419"};

    public McCreate(ClientStateListener clientListener) {
        this.clientListener = clientListener;
    }

    public RegAccount register(String password) {
        return register(password, rdmID(16));
    }

    public RegAccount register(String password, String deviceId) {
        String address = genRandomMailbox();
        System.out.println("Email: " + address);
        if(address != null) {
            String username = address.split("@")[0];
            McClient client = new McClient();
            client.addStateListener(clientListener);
            if(client.register(address, password, plz[rand.nextInt(plz.length)], deviceId).success()) {
                System.out.println("Register successful");
                String code = getActivationCode(username);
                System.out.println("AC: " + code);
                if(code != null && client.activateAccount(address, code, deviceId).success()) {
                    if(client.login(address, password, deviceId).success() && client.useMyMcDonalds(true).success()) {
                        System.out.println("Logged in");
                        return new RegAccount(address, password, deviceId);
                    }
                }
            }
        }
        return null;
    }

    private String getActivationCode(String username) {
        for(int i = 0; i < 240; i += 5) {
            for(MailMessage message : getMessages(username)) {
                if(message.getSubject().contains("McDonald")){
                    return message.getTextHtml().split("ac=")[1].split("/")[0];
                }
            }
            Utils.waitMill(5000);
        }
        return null;
    }

    private List<MailMessage> getMessages(String username) {
        return query(new MailRequest(username), MailResponse.class).getMails();
    }

    private String genRandomMailbox() {
        return query(new MailRequest(rdmID(10)), MailResponse.class).getAddress();
    }

    private String rdmID(int length) {
        return rand.ints(97, 123).limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}

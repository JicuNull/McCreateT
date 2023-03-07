package org.jannsen.mccreate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jannsen.mcreverse.api.McClient;
import org.jannsen.mcreverse.api.entity.akamai.SensorToken;
import org.jannsen.mcreverse.api.response.RegisterResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public class McCreate extends EmailHandler {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final AddressGenerator generator;
    private final Random rand = new Random();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Supplier<SensorToken> tokenSupplier;

    public McCreate(String host, int port, String user, String password, Supplier<SensorToken> tokenSupplier) {
        super(host, port, user, password);
        this.generator = new AddressGenerator(user);
        this.tokenSupplier = tokenSupplier;
    }

    public RegAccount register() {
        return register(generator.createEmail());
    }

    public RegAccount register(String email) {
        return register(email, rdmPass() + "1K#");
    }

    public RegAccount register(String email, String password) {
        email = email.toLowerCase();
        McClient client = new McClient();
        client.setTokenSupplier(tokenSupplier);
        RegisterResponse response = client.register(email, password);
        if(response.success()) {
            String deviceId = response.getDeviceId();
            Optional<String> code = searchActivationCode(email, 240);
            if(code.isPresent() && client.activateAccount(email, code.get(), deviceId).success()) {
                if(client.login(email, password, deviceId).success() && client.useMyMcDonalds(true).success()) {
                    return new RegAccount(email, password, deviceId);
                }
            }
        } else {
            LOG.info(gson.toJson(response));
        }
        return null;
    }

    private String rdmPass() {
        return rand.ints(48, 123).filter(i -> !(i >= 58 && i <= 96)).limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}

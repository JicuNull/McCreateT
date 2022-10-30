package icu.jnet.mccreate;

import org.jannsen.mcreverse.api.entity.offer.Offer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegAccount {

    private final String email, password, deviceId;
    private List<Offer> offers = new ArrayList<>();
    private List<Integer> supportedTypes = Arrays.asList(3, 6, 12);
    private int points;
    private boolean cataloged, myMcDActive;

    public RegAccount(String email, String password, String deviceId) {
        this.email = email;
        this.password = password;
        this.deviceId = deviceId;
    }
}

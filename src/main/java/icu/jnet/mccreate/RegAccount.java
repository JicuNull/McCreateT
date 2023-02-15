package icu.jnet.mccreate;

import org.jannsen.mcreverse.api.entity.offer.Offer;

import java.util.ArrayList;
import java.util.List;

public class RegAccount {

    private final String _id, password, deviceId;
    private List<Offer> offers = new ArrayList<>();
    private int points;
    private boolean cataloged, myMcDActive;

    public RegAccount(String email, String password, String deviceId) {
        this._id = email;
        this.password = password;
        this.deviceId = deviceId;
    }
}

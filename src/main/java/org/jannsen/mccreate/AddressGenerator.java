package org.jannsen.mccreate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AddressGenerator {

    private final List<String> names = getNames();
    private final char[] trimChar = {'.', '-', '+', '_'};
    private final Random rand = new Random();
    private final String host;

    public AddressGenerator(String host) {
        this.host = "@" + host.split("@")[1];
    }

    public String createEmail() {
        int end = rand.nextInt(3);
        return names.get(rand.nextInt(names.size())) +
                trimChar[rand.nextInt(trimChar.length)] +
                names.get(rand.nextInt(names.size())) +
                ((char) (rand.nextInt(26) + 97)) +
                (end == 0 ? rand.nextInt(30) + 1990 : end == 1 ? rand.nextInt(8) + 1 : "") +
                host;
    }

    private List<String> getNames() {
        List<String> names = new ArrayList<>();
        try(InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("names")))) {
            BufferedReader br = new BufferedReader(reader);

            String s;
            while((s = br.readLine()) != null) {
                names.add(s.toLowerCase());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return names;
    }
}

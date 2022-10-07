package icu.jnet.mccreate;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import icu.jnet.mccreate.request.Request;
import icu.jnet.mccreate.response.Response;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class McCreateBase {

    private static final HttpRequestFactory factory = new NetHttpTransport().createRequestFactory();
    private static final Gson gson = new Gson();

    public <T extends Response> T query(Request request, Class<T> clazz) {
        try {
            HttpRequest httpRequest = factory.buildGetRequest(new GenericUrl(request.getUrl()));
            HttpResponse response = httpRequest.execute();
            if(response.isSuccessStatusCode()) {
                return gson.fromJson(response.parseAsString(), clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createInstance(clazz);
    }

    private <T extends Response> T createInstance(Class<T> clazz) {
        try {
            clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}

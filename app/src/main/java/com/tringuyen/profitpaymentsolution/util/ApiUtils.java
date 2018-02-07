package com.tringuyen.profitpaymentsolution.util;

public class ApiUtils {

    private static final String BASE_URL = "http://192.168.1.146:3000/";

    public static ServerAPI getServerAPI() {
        return RetrofitClient.getClient(BASE_URL).create(ServerAPI.class);
    }

}

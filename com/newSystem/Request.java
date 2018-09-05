package com.newSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Request {
    public static void send(String method, ArrayList<String> keys, ArrayList<String> vals) {
        String url = "http://166.104.126.21:9999/?method=" + method;
        for (int cnt = 0; cnt < keys.size(); cnt++) {
            String key = keys.get(cnt);
            String val = vals.get(cnt);
            url += "&" + key + "=" + val;
        }
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            //add request header 헤더를 만들어주는것.
            con.setRequestProperty("User-Agent", "Chrome/version");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

package com.newSystem.Bitcoin;

import com.newSystem.Main;
import com.newSystem.Settings;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class BitcoinServer extends Thread {
    public void run() {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(9999), 0);
            httpServer.createContext("/", new Handler());
            httpServer.setExecutor(null);
            httpServer.start();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.exit(1);
        }
    }
    public class  Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            StringBuilder response = new StringBuilder();
            Map <String,String> params = queryToMap(httpExchange.getRequestURI().getQuery());
            String method = params.get("method");
            String pid;
            String companyAddress;
            String companyName;
            if (Integer.valueOf(method) == 4) {
                // method:4 --> send_to_address request (redirected from gener's server).
                companyName = params.get("companyName");
                if (companyName.equals(Settings.companyName)) {
                    companyAddress = params.get("address");
                    pid = params.get("pid");
                    response.append(Main.bitcoinJSONRPCClient.send_to_address(companyAddress, pid));
                    writeResponse(httpExchange, response.toString());
                }
            }
        }
    }
    // url 뒤 parameter들을 파싱 하기 위한 함수들. (ex. ip주소:port/?method=1&address=~~~&pid=~~)
    static public Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1)
                result.put(pair[0], pair[1]);
            else
                result.put(pair[0], "");
        }
        return result;
    }
    static public void writeResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

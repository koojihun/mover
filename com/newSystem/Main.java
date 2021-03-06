package com.newSystem;

import com.alee.laf.WebLookAndFeel;
import com.bitcoinClient.javabitcoindrpcclient.BitcoinJSONRPCClient;
import com.newSystem.Bitcoin.BitcoinServer;
import com.newSystem.Bitcoin.Bitcoind;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Main {
    static public BitcoinJSONRPCClient bitcoinJSONRPCClient;
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new WebLookAndFeel());
        new Settings();
        sendCompanyNameAndIp();

        MainFrame mainFrame = new MainFrame();

        // bincoind 실행 쓰레드
        new Bitcoind(MidPanel.getBitcoindArea()).start();

        // bincoind로 rpc 명령을 전달하는 서버를 돌리는 쓰레드.
        new BitcoinServer().start();

        try {
            bitcoinJSONRPCClient = new BitcoinJSONRPCClient(Settings.getRpcUser(), Settings.getRpcPassword());
        } catch (MalformedURLException e) {
            System.err.println("BitcoinJSONRPCClient Constructor Error");
        }

        if (Settings.companyAddress == null) {
            while (Settings.companyAddress == null) {
                try {
                    Settings.companyAddress = bitcoinJSONRPCClient.get_new_address(Settings.companyName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String filePath = "C:\\Users\\" + Settings.getUserNmae() + "\\AppData\\Roaming\\Bitcoin\\bitcoin.conf";
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
            bw.newLine();
            bw.write("companyAddress=" + Settings.companyAddress);
            bw.close();
            /////////////////////////////////////////////////////
            ArrayList<String> keys = new ArrayList<>();
            ArrayList<String> vals = new ArrayList<>();
            keys.add("companyName"); keys.add("companyAddress");
            vals.add(Settings.companyName); vals.add(Settings.companyAddress);
            Request.send("0", keys, vals);
            /////////////////////////////////////////////////////
        }
    }

    private static void sendCompanyNameAndIp() {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();
        keys.add("companyName");
        vals.add(Settings.companyName);
        Request.send("2", keys, vals);
    }
}

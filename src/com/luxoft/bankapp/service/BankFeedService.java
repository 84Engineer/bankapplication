/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.luxoft.bankapp.model.FeedException;
import java.io.BufferedReader;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
//import java.util.Iterator;

/**
 *
 * @author LocalUser
 */
public class BankFeedService {

    public static List<Map<String, String>> loadFeed(String folder) throws Exception {
        List<Map<String, String>> feedData = new ArrayList();
        //Map<String, String> clientData = new HashMap();
        File dir = new File(folder);
        if (!dir.isDirectory()) {
            throw new FeedException("Wrong directory");
        }

        File[] feedFiles = dir.listFiles();
        for (File file : feedFiles) {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = br.readLine()) != null) {
                Map<String, String> clientData = new HashMap();
                String[] values = line.split(";");
                for (String value : values) {
                    String[] data = value.split("=");
                    clientData.put(data[0], data[1]);
                }
                feedData.add(clientData);
            }
        }

        /*for (Map<String, String> clientData : feedData) {

            Iterator it = clientData.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
            }
            System.out.println("");

        }*/
        return feedData;
    }

    /*public static void main(String[] args) throws Exception {
        BankFeedService.loadFeed("D:\\IDEfiles\\BankApplication\\src\\feedfiles\\");
    }*/
}

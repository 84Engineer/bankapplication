/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.clientserver;

import com.luxoft.bankapp.command.CommandHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author LocalUser
 */
public class CreateRequest {

    public static RequestData generateRequest() {
        String clientName;
        RequestData.Operation operation;
        float operationSum;

        Scanner sc = new Scanner(System.in);
        //System.out.println("To quit type \"quit\".");
        System.out.println("Input client's name:");
        while (true) {
            clientName = sc.nextLine();
            //checkForQuit(clientName);
            if (clientName == null) {
                continue;
            }
            clientName = clientName.trim();
            //System.out.println("Client name is: " + clientName);
            break;
        }

        System.out.println("Select operation: ");
        while (true) {
            Integer i = 0;
            Map<Integer, RequestData.Operation> selectMap = new HashMap();
            for (RequestData.Operation operationToSelect : RequestData.Operation.values()) {
                selectMap.put(i, operationToSelect);
                System.out.println(i + ") " + operationToSelect);
                i++;
            }
            String option = sc.nextLine();
            if (option == null) {
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(option);
            } catch (NumberFormatException e) {
                System.out.println("Select valid option");
                continue;
            }

            if (choice < 0 || choice > selectMap.size() - 1) {
                System.out.println("Select option from 0 to " + (selectMap.size() - 1));
                continue;
            }

            operation = selectMap.get(choice);
            System.out.println("Operation selected: " + operation);
            break;
        }

        //System.out.println("Imput operation sum:");
        while (true) {
            operationSum = CommandHelper.getOperationSum();
            if (operationSum < 0) {
                System.out.println("Operation sum cannot be negative.");
                continue;
            }
            break;
        }

        return new RequestData(clientName, operation, operationSum);
    }

}

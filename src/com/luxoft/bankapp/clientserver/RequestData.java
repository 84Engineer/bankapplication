/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.clientserver;

import java.io.Serializable;

/**
 *
 * @author LocalUser
 */
public class RequestData implements Serializable {

    private String clientName;
    private Operation operation;
    private float operationSum;

    public enum Operation {
        WITHDRAW("Withrdaw"), DEPOSIT("Deposit");

        private String operationDesc;

        private Operation(String operationDesc) {
            this.operationDesc = operationDesc;
        }

        public String getOperationDesc() {
            return operationDesc;
        }
    }

    public RequestData(String clientName, Operation operation, float operationSum) {
        this.clientName = clientName;
        this.operation = operation;
        this.operationSum = operationSum;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public float getOperationSum() {
        return operationSum;
    }

    public void setOperationSum(float operationSum) {
        this.operationSum = operationSum;
    }

}

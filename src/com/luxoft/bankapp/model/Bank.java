package com.luxoft.bankapp.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bank implements Report {

    private Set<Client> clients = new HashSet<Client>();
    private Map<String, Client> clientMap = new HashMap<String, Client>();
    private List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
    private final String bankName;

    public Bank(String bankName) {

        this.bankName = bankName;

        class PrintClientListener implements ClientRegistrationListener {

            @Override
            public void onClientAdded(Client c) {
                System.out.println("Listener start.");
                c.printReport();
            }
        }

        class EmailNotificationListener implements ClientRegistrationListener {

            @Override
            public void onClientAdded(Client c) {
                System.out.println("Notification email for client " + c.getName() + " to be sent");
            }
        }

        addListener(new PrintClientListener());
        addListener(new EmailNotificationListener());

        addListener(new ClientRegistrationListener() {

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            @Override
            public void onClientAdded(Client c) {
                System.out.println(c.getClientSalutation() + " " + df.format(new Date()));
            }
        });

        addListener((c) -> clientMap.put(c.getName(), c));

    }

    public void parseFeed(Map<String, String> feed) throws ClientExistsException, IllegalArgumentException {
        Client client;
        String clientName = feed.get("name");
        if (clientMap.containsKey(clientName)) {
            client = clientMap.get(clientName);
        } else {
            Client.Gender gender;
            if (feed.get("gender").equals("m")) {
                gender = Client.Gender.MALE;
            } else {
                gender = Client.Gender.FEMALE;
            }
            client = new Client(clientName, gender);
            addClient(client);
        }
        client.parseFeed(feed);
    }

    @Override
    public void printReport() {
        for (Client client : clients) {
            client.printReport();
            System.out.println("");
        }
    }

    public void addClient(Client client) throws ClientExistsException {
        for (Client clientToCheck : clients) {
            if (clientToCheck.equals(client)) {
                throw new ClientExistsException("Client with name " + client.getName() + " already exists.");
            }
        }
        clients.add(client);

        for (ClientRegistrationListener listener : listeners) {
            listener.onClientAdded(client);
        }
    }

    public Set<Client> getClients() {
        return Collections.unmodifiableSet(clients);
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public List<ClientRegistrationListener> getListeners() {
        return listeners;
    }

    public void addListener(ClientRegistrationListener listener) {
        listeners.add(listener);
    }

    public String getBankName() {
        return bankName;
    }

    public Client getClientByName(String name) {
        return clientMap.get(name);
    }

}

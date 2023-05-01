package com.example.quizapp.hubs;

import com.microsoft.signalr.Action;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.function.Consumer;

public class HubConnectivity {

    private String ipConenction;
    private HubConnection hubConnection;

    private String url;
    private static HubConnectivity instance;

    public String getIpConenction() {
        return ipConenction;
    }

    private HubConnectivity(String url)
    {
        this.url = url;
    }

    public static HubConnectivity getInstance(String url)
    {
        if (instance == null)
            instance = new HubConnectivity(url);
        return instance;
    }

    public void onDisconnect(Consumer<String> onDisconnectingExpression) {
        hubConnection.on("OnDisconectedSession",message-> {
            onDisconnectingExpression.accept(message);
            dispose();
        }, String.class);
    }

    public void onCounting(Consumer<String> onDisconnectingExpression) {
        hubConnection.on("INIT_GAME_SEQUENCER_P2P",message-> {
            onDisconnectingExpression.accept(message);
        }, String.class);
    }

    public void onGame(Consumer<String> onDisconnectingExpression) {
        hubConnection.on("QUESTION_P2P",message-> {
            onDisconnectingExpression.accept(message);
        }, String.class);
    }

    public void onQuestionTimer(Consumer<String> onDisconnectingExpression) {
        hubConnection.on("QUESTION_TIMER_P2P",message-> {
            onDisconnectingExpression.accept(message);
        }, String.class);
    }

    public void connect()
    {
        hubConnection = HubConnectionBuilder.create(url)
                .build();
    }
    public void start()
    {
        hubConnection.start().blockingAwait();
        ipConenction = hubConnection.getConnectionId();
    }
    public void dispose()
    {
        hubConnection.close();
    }
}

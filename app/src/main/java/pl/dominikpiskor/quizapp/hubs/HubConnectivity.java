package pl.dominikpiskor.quizapp.hubs;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.function.Consumer;

/**
 * The class responsible for connecting to the server thanks by gameplay hubs
 */
public class HubConnectivity {

    /**
     * Variable declaration
     */
    private String ipConenction;
    private HubConnection hubConnection;
    private String url;
    private static HubConnectivity instance;

    public String getIpConenction() {
        return ipConenction;
    }

    //==============================================================================================

    private HubConnectivity(String url) {
        this.url = url;
    }

    //==============================================================================================

    public static HubConnectivity getInstance(String url) {
        if (instance == null)
            instance = new HubConnectivity(url);
        return instance;
    }

    //==============================================================================================

    /**
     * The method responsible for disconnect with hub
     * @param onDisconnectingExpression
     */
    public void onDisconnect(Consumer<String> onDisconnectingExpression) {
        hubConnection.on("OnDisconnectedSession",message-> {
            onDisconnectingExpression.accept(message);
            dispose();
        }, String.class);
    }

    //==============================================================================================

    /**
     * The method responsible for downloading synchronizes lobby time with the server
     * @param onDisconnectingExpression
     */
    public void onCounting(Consumer<String> onDisconnectingExpression) {
        hubConnection.on("INIT_GAME_SEQUENCER_P2P",message-> {
            onDisconnectingExpression.accept(message);
        }, String.class);
    }

    //==============================================================================================

    /**
     * The helper method responsible for get data about given question in quiz
     * @param onDisconnectingExpression
     */
    public void onGame(Consumer<String> onDisconnectingExpression) {
        hubConnection.on("QUESTION_MOBILE_P2P",message-> {
            onDisconnectingExpression.accept(message);
        }, String.class);
    }

    //==============================================================================================

    /**
     * The method responsible for downloading synchronizes game time with the server
     * @param onDisconnectingExpression
     */
    public void onQuestionTimer(Consumer<String> onDisconnectingExpression) {
        hubConnection.on("QUESTION_TIMER_P2P",message-> {
            onDisconnectingExpression.accept(message);
        }, String.class);
    }

    //==============================================================================================

    /**
     * The method responsible for get result data about best players in game
     * @param onDisconnectingExpression
     */
    public void onQuestionResult(Consumer<String> onDisconnectingExpression) {
        hubConnection.on("QUESTION_RESULT_P2P",message-> {
            onDisconnectingExpression.accept(message);
        }, String.class);
    }

    //==============================================================================================

    /**
     * The method responsible for get correct answers from server
     * @param onDisconnectingExpression
     */
    public void onCorrectAnswer(Consumer<String> onDisconnectingExpression) {
        hubConnection.on("CORRECT_ANSWERS_SCREEN",message-> {
            onDisconnectingExpression.accept(message);
        }, String.class);
    }

    //==============================================================================================

    /**
     * The method responsible for connect with the hub
     */
    public void connect() {
        hubConnection = HubConnectionBuilder.create(url)
                .build();
    }

    //==============================================================================================

    /**
     * The method responsible for get id connection
     */
    public void start() {
        hubConnection.start().blockingAwait();
        ipConenction = hubConnection.getConnectionId();
    }

    //==============================================================================================

    /**
     * The method responsible for disconnected with the hub
     */
    public void dispose() {
        hubConnection.close();
    }
}

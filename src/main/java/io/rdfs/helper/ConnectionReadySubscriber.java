package io.rdfs.helper;

public class ConnectionReadySubscriber {
    private ConnectionReadyListener listener;

    public void subscribe(ConnectionReadyListener listener){
        this.listener = listener;
    }

    public void ready(){
        if(listener != null)
            listener.onReady();
    }
}


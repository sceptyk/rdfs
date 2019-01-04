package io.rdfs.model;

public class WSMessage {
    public WSMessageType type;
    public Object content;

    public WSMessage(WSMessageType type, Object content){
        this.type = type;
        this.content = content;
    }
}
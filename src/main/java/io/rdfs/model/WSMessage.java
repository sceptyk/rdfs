package io.rdfs.model;

import com.google.gson.Gson;

public class WSMessage {
    public static Gson gson = new Gson();

    public WSMessageType type;
    public String content;

    public WSMessage(WSMessageType type, Object content){
        this.type = type;
        this.content = gson.toJson(content);
    }

    public Object getContent(Class type){
        return gson.fromJson(content, type);
    }
}
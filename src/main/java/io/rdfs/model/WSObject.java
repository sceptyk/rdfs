package io.rdfs.model;

import com.google.gson.Gson;

public class WSObject {
    public String setID;
    public String passwd;
    public String to;
    public String FROM;
    public String sID;
    public WSMessage message;

    private WSObject(){}

    public static String createInitMessage(){
        WSObject wsObject = new WSObject();
        wsObject.setID = "ds2018";
        wsObject.passwd = "distributedsystems";

        Gson gson = new Gson();

        return gson.toJson(wsObject);
    }

    public static String createMessage(WSMessage message){
        WSObject wsObject = new WSObject();
        wsObject.to = "ds2018";
        wsObject.message = message;

        Gson gson = new Gson();

        return gson.toJson(wsObject);
    }
}

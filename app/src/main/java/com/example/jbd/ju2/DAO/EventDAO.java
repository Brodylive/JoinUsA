package com.example.jbd.ju2.DAO;

import com.example.jbd.ju2.models.Categorie;
import com.example.jbd.ju2.models.Event;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by JBD on 13/02/17.
 */

public class EventDAO {
    public ArrayList<Event> getAllEvents() throws Exception {
        URL url = new URL("http://api.androidhive.info/volley/person_array.json");
        URLConnection connection = url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String stringJSON = "", line;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        stringJSON = sb.toString();

        return jsonToEvent(stringJSON);
    }

    public ArrayList<Event> jsonToEvent(String stringJSON) throws Exception{
        ArrayList<Event> events = new ArrayList<>();
        Event event;

        JSONArray jsonArray = new JSONArray(stringJSON);
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonEvent = jsonArray.getJSONObject(i);
            //event = new Event(jsonEvent.getInt("id"), jsonEvent.getString("name"), jsonEvent.getString("description"), jsonEvent.getString("address"), jsonEvent.getString("urlFacebook"), jsonEvent.get("Date"), jsonEvent.getString("name"), jsonEvent.getString("name"));
            //events.add(event);
        }

        return events;
    }

}

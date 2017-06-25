package com.example.jbd.ju2.DAO;

import com.example.jbd.ju2.models.Event;
import com.example.jbd.ju2.models.User;

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

public class UserDAO {
    public ArrayList<User> getAllUsers() throws Exception {
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

        return jsonToUser(stringJSON);
    }

    public ArrayList<User> jsonToUser(String stringJSON) throws Exception{
        ArrayList<User> users = new ArrayList<>();
        User user;

        JSONArray jsonArray = new JSONArray(stringJSON);
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonUser = jsonArray.getJSONObject(i);
            //user = new User(jsonUser.getString("id"), jsonUser.getString("username"), jsonUser.getString("firstName"), jsonUser.getString("lastName"), jsonUser.get("date"), jsonUser.getString("lastLoc"));
            //users.add(user);
        }

        return users;
    }
}

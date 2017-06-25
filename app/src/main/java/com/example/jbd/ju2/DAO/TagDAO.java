package com.example.jbd.ju2.DAO;

import com.example.jbd.ju2.models.Event;
import com.example.jbd.ju2.models.Tag;

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

public class TagDAO {
    public ArrayList<Tag> getAllTags() throws Exception {
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

        return jsonToTag(stringJSON);
    }

    public ArrayList<Tag> jsonToTag(String stringJSON) throws Exception{
        ArrayList<Tag> tags = new ArrayList<>();
        Tag tag;

        JSONArray jsonArray = new JSONArray(stringJSON);
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonTag = jsonArray.getJSONObject(i);
            tag = new Tag(jsonTag.getInt("id"), jsonTag.getString("name"));
            tags.add(tag);
        }

        return tags;
    }
}

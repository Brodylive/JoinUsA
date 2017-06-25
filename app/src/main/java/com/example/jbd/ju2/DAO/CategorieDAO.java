package com.example.jbd.ju2.DAO;

import com.example.jbd.ju2.models.Categorie;

import java.io.*;
import java.net.*;
import org.json.*;
import java.util.ArrayList;

/**
 * Created by JBD on 13/02/17.
 */

public class CategorieDAO {

    public ArrayList<Categorie> getAllCategories() throws Exception {
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

        return jsonToCategorie(stringJSON);
    }

    public ArrayList<Categorie> jsonToCategorie(String stringJSON) throws Exception{
        ArrayList<Categorie> categories = new ArrayList<>();
        Categorie categorie;

        JSONArray jsonArray = new JSONArray(stringJSON);
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonCategorie = jsonArray.getJSONObject(i);
            categorie = new Categorie(jsonCategorie.getInt("id"), jsonCategorie.getString("name"));
            categories.add(categorie);
        }

        return categories;
    }

}


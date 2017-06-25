package com.example.jbd.ju2;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.henallux.smartcity.visiteludique.models.IdParcours;
import com.henallux.smartcity.visiteludique.models.LoginBindingModel;
import com.henallux.smartcity.visiteludique.models.Parcours;
import com.henallux.smartcity.visiteludique.models.RegisterBindingModel;
import com.henallux.smartcity.visiteludique.models.UserReturnModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jenniferbrody on 14/03/17.
 */

public class AccessService<T> {
    private static String _url = "http://visiteludique.azurewebsites.net/api/";
    private static String _cookie = null;
    private static UserReturnModel _user;
    private static CookieManager _cookieManager;
    private String _ctl;

    AccessService(String controller){
        _ctl = controller + "/";
    }

    private static HttpURLConnection getConnection(URL url, String method) throws IOException{
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        if(_cookie != null){
            connection.setRequestProperty("Cookie", _cookie);
        }
        connection.setRequestProperty("Accept", "*/*");

        if(method.equals("POST") || method.equals("PUT"))
            connection.setDoOutput(true);

        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        return connection;
    }

    private static void writeConnection(HttpURLConnection connection, String Json) throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);

        connection.connect();
        writer.write(Json);
        writer.flush();
        writer.close();
        outputStream.close();
    }

    private static String getResult(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder result = new StringBuilder();
        String line;

        while((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();

        return result.toString();
    }

    private static String objectToJson(Object objet)
    {
        Gson gson = new Gson();
        return gson.toJson(objet);
    }

    private static <T> T jsonToObject(String jsonString, Class<T> clas)
    {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, clas);
    }

    private static void parseStatusCode(HttpURLConnection connection) throws Exception {
        switch (connection.getResponseCode()){
            case 400:
                // Parse model state errors
                String err = getResult(connection.getErrorStream());
                JSONObject jobj = new JSONObject(err);
                if(jobj.has("ModelState")) {
                    String msg = "";
                    JSONObject state = jobj.getJSONObject("ModelState");
                    Iterator it = state.keys();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        JSONArray arr = state.getJSONArray(key);
                        for (int i = 0; i < arr.length(); i++) {
                            msg += arr.get(i) + " ";
                        }
                    }
                    throw new BadRequestException(msg);
                } else if (jobj.has("Message")){
                    throw new BadRequestException(jobj.getString("Message"));
                } else {
                    throw new BadRequestException(err);
                }
            case 401:
                throw new AuthorizationException();
            case 404:
                throw new NotFoundException();
            case 200:
                break;
            default:
                throw new ServerErrorException();
        }
    }

    public static boolean IsLogged(){
        return _cookie != null;
    }

    public static void Login(String login, String password) throws Exception {
        LoginBindingModel model = new LoginBindingModel(login,password);
        String stringJSON = objectToJson(model);

        URL url = new URL(_url + "Account/Login");
        HttpURLConnection connection = getConnection(url, "POST");

        writeConnection(connection, stringJSON);

        parseStatusCode(connection);

        String res = getResult(connection.getInputStream());

        Map<String, List<String>> headerFields = connection.getHeaderFields();
        List<String> cookiesHeader = headerFields.get("Set-Cookie");
        _cookie = "";

        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                _cookie += cookie + ";";
            }
        }

        _user = (UserReturnModel)jsonToObject(res, UserReturnModel.class);

        connection.disconnect();
    }

    public static void Register(String login, String password) throws Exception {
        RegisterBindingModel model = new RegisterBindingModel(login, password, password);
        String stringJSON = objectToJson(model);

        URL url = new URL(_url + "Account/Register");
        HttpURLConnection connection = getConnection(url, "POST");

        writeConnection(connection, stringJSON);

        parseStatusCode(connection);

        connection.disconnect();
    }

    public static void Logout() throws Exception {
        URL url = new URL(_url + "Account/Logout");
        HttpURLConnection connection = getConnection(url, "POST");
        connection.connect();

        parseStatusCode(connection);

        _cookie = null;

        connection.disconnect();
    }

    public static List<Parcours> GetParcoursInscrits() throws Exception {
        URL url = new URL(_url + "Parcours/Inscrits");
        HttpURLConnection connection = getConnection(url, "GET");
        connection.connect();

        parseStatusCode(connection);

        String result = getResult(connection.getInputStream());
        connection.disconnect();

        Type listType = new TypeToken<ArrayList<Parcours>>(){}.getType();
        List<Parcours> list = new Gson().fromJson(result, listType);

        return list;
    }

    public static Parcours GetParcoursDetail(Long id) throws Exception {
        URL url = new URL(_url + "Parcours/Detail");
        HttpURLConnection connection = getConnection(url, "POST");

        String stringJSON = objectToJson(new IdParcours(id));

        writeConnection(connection, stringJSON);

        parseStatusCode(connection);

        String result = getResult(connection.getInputStream());
        connection.disconnect();

        return new Gson().fromJson(result, Parcours.class);
    }

    public static void InscriptionParcours(long id) throws Exception{
        URL url = new URL(_url + "Parcours/Inscription");
        HttpURLConnection connection = getConnection(url, "POST");

        String stringJSON = objectToJson(new IdParcours(id));

        writeConnection(connection, stringJSON);

        parseStatusCode(connection);

        connection.disconnect();
    }

    public T Get(int id) throws Exception {
        URL url = new URL(_url + _ctl + id);
        HttpURLConnection connection = getConnection(url, "GET");
        connection.connect();

        parseStatusCode(connection);

        String result = getResult(connection.getInputStream());
        connection.disconnect();


        Type listType = new TypeToken<T>(){}.getType();
        T obj = new Gson().fromJson(result, listType);

        return obj;
    }

    public List<T> Get() throws Exception {
        URL url = new URL(_url + _ctl);
        HttpURLConnection connection = getConnection(url, "GET");
        connection.connect();

        parseStatusCode(connection);

        String result = getResult(connection.getInputStream());
        connection.disconnect();

        Type listType = new TypeToken<ArrayList<T>>(){}.getType();
        List<T> list = new Gson().fromJson(result, listType);

        return list;
    }

    public void Set(int id, T o) throws Exception {
        String stringJSON = objectToJson(o);

        URL url = new URL(_url + _ctl + id);
        HttpURLConnection connection = getConnection(url, "PUT");

        writeConnection(connection, stringJSON);

        parseStatusCode(connection);

        connection.disconnect();
    }

    public  void Delete(int id) throws Exception {
        URL url = new URL(_url + _ctl + id);
        HttpURLConnection connection = getConnection(url, "DELETE");

        connection.connect();

        parseStatusCode(connection);

        connection.disconnect();
    }

    public void Create(T o) throws Exception
    {
        String stringJSON = objectToJson(o);

        URL url = new URL(_url + _ctl);
        HttpURLConnection connection = getConnection(url, "POST");

        writeConnection(connection, stringJSON);

        parseStatusCode(connection);

        connection.disconnect();
    }
    public void Create(int id, T o) throws Exception
    {
        String stringJSON = objectToJson(o);

        URL url = new URL(_url + _ctl + id);
        HttpURLConnection connection = getConnection(url, "POST");

        writeConnection(connection, stringJSON);

        parseStatusCode(connection);

        connection.disconnect();
    }
}

package com.example.jbd.ju2;

/**
 * Created by jenniferbrody on 2/03/17.
 */

        import android.util.Log;

        import com.google.gson.Gson;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.ProtocolException;
        import java.net.URL;
        import java.util.Iterator;

public class HttpHandler
{

    private HttpURLConnection connection;

    public HttpHandler()
    {

    }

    public void Post(String urlRequest, String stringJSON)
    {

        try {
            URL url = new URL(urlRequest);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            connection.connect();
            writer.write(stringJSON);
            writer.flush();
            System.out.println("code: "+connection.getResponseCode());
            parseStatusCode(connection);

            writer.close();
            outputStream.close();
            connection.disconnect();
        }

        catch (MalformedURLException e) {
            Log.e("tag", "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e("tag", "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e("tag", "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e("tag", "Exception: " + e.getMessage());
        }
    }

    public String login(String urlRequest, String stringJSON){


        try {
            URL url = new URL(urlRequest);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "x-wwww-form-urlencoded");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            connection.connect();
            writer.write(stringJSON);
            writer.flush();
            System.out.println("code: "+connection.getResponseCode());

            // si erreur je recupère l'info ici
            parseStatusCode(connection);

            // récupérer la réponse de ma requete content token
            String response = getResult(connection.getInputStream());
            JSONObject responseObject = new JSONObject(response);

            writer.close();
            outputStream.close();
            connection.disconnect();

            return (String)responseObject.get("access_token");
        }

        catch (MalformedURLException e) {
            Log.e("tag", "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e("tag", "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e("tag", "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e("tag", "Exception: " + e.getMessage());
        }

        return null;
    }

    // Méthodes utile pour testing
    private String getResult(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder result = new StringBuilder();
        String line;

        while((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();

        return result.toString();
    }

    private void parseStatusCode(HttpURLConnection connection) throws Exception {
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
                    System.out.println(msg);

                }
            default:
                break;
        }
    }



    public <T> String objectToJson(T objet)
    {
        Gson gson = new Gson();
        String jsonString = gson.toJson(objet);
        return jsonString;
    }

    public <T> Object jsonToObject(String jsonString)
    {
        Gson gson = new Gson();
        Object object = gson.fromJson(jsonString,Object.class);
        return object;
    }



}

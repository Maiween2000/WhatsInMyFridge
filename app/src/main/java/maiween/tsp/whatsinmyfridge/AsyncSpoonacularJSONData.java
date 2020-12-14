package maiween.tsp.whatsinmyfridge;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncSpoonacularJSONData extends AsyncTask<String, Void, JSONObject> {

    private AppCompatActivity myActivity;

    public AsyncSpoonacularJSONData(AppCompatActivity mainActivity) {

        myActivity = mainActivity;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        URL url = null;
        HttpsURLConnection urlConnection = null;
        String result = null;
        try {
            url = new URL("https://api.spoonacular.com/food/ingredients/search");
            urlConnection = (HttpsURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream

            result = readStream(in); // Read stream
        }
        catch (MalformedURLException e) { e.printStackTrace(); }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        JSONObject json = null;
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return json; // returns the result
    }

    private String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        in.close();
        // Extracting the JSON object from the String
        String jsonextracted = sb.substring("jsonSpoon".length(), sb.length() - 1);
        //Log.i("CIO", jsonextracted);
        return jsonextracted;
    }

    protected void onPostExecute(JSONObject s) {

        Spinner list = myActivity.findViewById(R.id.spinner);
       // BitmapAdapter tableau = new BitmapAdapter(list.getContext());
        //list.setAdapter(tableau);
        JSONArray items = null;

        try {
            items = s.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i<items.length(); i++)
            {
                JSONObject spoonacular_entry = null;
                try {
                    spoonacular_entry = items.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String urlmedia = null;
                try {
                    urlmedia = spoonacular_entry.getJSONObject("media").getString("m");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("CIO", "URL media: " + urlmedia);
            }

    }
}


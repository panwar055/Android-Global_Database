package com.example.viratpanwar.gdb;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button b1, b2;
    String path;
    HashMap<String, String> postDataParams;
    static String response = "";
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b2 = (Button) findViewById(R.id.btn2);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetData().execute();
            }
        });
    }



    class GetData extends AsyncTask<Void, Void, Void> {

        int success = 0;
        ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            path = "https://panwar05.000webhostapp.com/";

            postDataParams = new HashMap<String, String>();
            postDataParams.put("name", "");

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading... Please wait !!!");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                HTTPURLConnection service = new HTTPURLConnection();

                response = service.ServerData(path, postDataParams);

                Log.d("locality", " result :  :  " + response);

                JSONObject obj = new JSONObject(response);

                JSONArray data = obj.getJSONArray("data");

                success = obj.getInt("success");

                if (success == 1) {


                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        name = jo.getString("name");

                    }


                } else {
                    Log.d("success  ::", "no");
                }

            } catch (Exception e) {
                Log.d("Exception ", "" + e);

            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            pDialog.dismiss();

            if (success == 1) {

                Toast.makeText(MainActivity.this, "" + name, Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
            }
        }


    }

}

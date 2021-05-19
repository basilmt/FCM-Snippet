package com.revolt.fcmtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.revolt.fcmtest.constants.AllConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.sent).setOnClickListener(view -> {
           getTokenForNotification();
        });

    }

    private void getTokenForNotification() {
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(token -> prepNotification(token));
    }

    private void prepNotification(String token) {
        JSONObject message = new JSONObject();
        JSONObject to = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("title", "Notification");
            data.put("body", "you have 1 notification");

            to.put("token", token);
            to.put("data", data);

            message.put("message", to);
            if (token != null) {
                sentNotification(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sentNotification(JSONObject to) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AllConstants.NOTIFICATION_URL,to, response -> {

        },error -> {
            error.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() {

                Map<String,String> map = new HashMap<>();
                try {
                    String tkn =  getAccessToken();
                    map.put("Authorization", "Bearer " + tkn);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                map.put("Content-Type", "application/json");

                return map;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    private String getAccessToken() throws IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.service_account);

        GoogleCredentials googleCredential = GoogleCredentials
                .fromStream(inputStream)
                .createScoped(Arrays.asList(AllConstants.SCOPES));
        googleCredential.refresh();

        Log.i("TAGggg", "getAccessToken: " + googleCredential.toString());
        return googleCredential.getAccessToken().getTokenValue();
    }

}
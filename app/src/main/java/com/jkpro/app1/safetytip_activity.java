package com.jkpro.app1;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class safetytip_activity extends AppCompatActivity {
    private TextView safetyTipsTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safetytip_activity);
        safetyTipsTextView = findViewById(R.id.safetyTipTextView);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jatinprojectapi.000webhostapp.com/honeyapi/safetytips.php"; // Replace with your actual API URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            StringBuilder formattedText = new StringBuilder();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject tipObject = response.getJSONObject(i);
                                StringBuilder formattedText1 = new StringBuilder();
                                // Extract id and tip
                                int id = tipObject.getInt("id");
                                String engtip = tipObject.getJSONObject("english").getString("tip");
                                String engDes = tipObject.getJSONObject("english").getString("description");
                                String hitip = tipObject.getJSONObject("hindi").getString("tip");
                                String hiDes = tipObject.getJSONObject("hindi").getString("description");
                                // Format id and tip together with bold styling
                                String formattedTip = "<b>Tip:- "+id+"</b>";
                                formattedText1.append(HtmlCompat.fromHtml(formattedTip, HtmlCompat.FROM_HTML_MODE_LEGACY)).append("<br>");
                                formattedTip = formattedTip+ engtip;
                                formattedText1.append(HtmlCompat.fromHtml(formattedTip, HtmlCompat.FROM_HTML_MODE_LEGACY)).append("<br>");
                                formattedTip=formattedTip+engDes;
                                formattedText1.append(HtmlCompat.fromHtml(formattedTip, HtmlCompat.FROM_HTML_MODE_LEGACY)).append("<br><br>");
                                formattedTip = formattedTip+hitip;
                                formattedText1.append(HtmlCompat.fromHtml(formattedTip, HtmlCompat.FROM_HTML_MODE_LEGACY)).append("<br>");
                                formattedTip=formattedTip+hiDes;

                                // Append to the final formatted text
                                formattedText.append(HtmlCompat.fromHtml(formattedTip, HtmlCompat.FROM_HTML_MODE_LEGACY)).append("<br><br><br>");
                            }

                            // Set the final formatted text to the TextView
                            safetyTipsTextView.setText(HtmlCompat.fromHtml(formattedText.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });

        queue.add(jsonArrayRequest);
    }
}

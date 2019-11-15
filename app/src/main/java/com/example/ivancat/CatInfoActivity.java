package com.example.ivancat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;


public class CatInfoActivity extends AppCompatActivity {

    private Cat cat;
    private ImageView catimage;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private TextView textviewName;
    private TextView textviewWeight;
    private TextView textviewTemp;
    private TextView textviewDes;
    private TextView textviewOrigin;
    private TextView textviewlifeSpan;
    private TextView textviewwikipedialink;
    private TextView textviewdogfriendlinesslv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed);
         requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);
        cat = (Cat) getIntent().getSerializableExtra("cat");
        catimage = findViewById(R.id.catimage);
        textviewName = findViewById(R.id.textview_name);
        textviewWeight = findViewById(R.id.textview_Weight);
        textviewTemp = findViewById(R.id.textview_temp);
        textviewDes = findViewById(R.id.textview_des);
        textviewOrigin = findViewById(R.id.textview_origin);
        textviewlifeSpan = findViewById(R.id.textview_lifespan);
        textviewwikipedialink = findViewById(R.id.textview_wikipedialink);
        textviewdogfriendlinesslv = findViewById(R.id.textview_dogfriendlinesslv);




    }

    @Override
    protected void onResume() {
        super.onResume();
        textviewName.setText(cat.name);
        textviewWeight.setText(cat.weight.imperial+"(imperial)"+cat.weight.metric+"(metric)");
        textviewTemp.setText(cat.temperament);
        textviewDes.setText(cat.description);
        textviewOrigin.setText(cat.origin);
        textviewlifeSpan.setText(cat.life_span);
        textviewwikipedialink.setText(cat.wikipedia_url);
        textviewdogfriendlinesslv.setText(cat.dog_friendly);


        getCatImage();
    }

    private void getCatImage(){
        String url = "https://api.thecatapi.com/v1/images/search?breed_ids="+ cat.id;

        Response.Listener<String> success = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    JSONObject obj = arr.getJSONObject(0);
                    String url =  obj.getString("url");
                    Glide.with(CatInfoActivity.this).load(url).into(catimage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };
        StringRequest request = new StringRequest(Request.Method.GET, url, success,error );
        request.setTag("ids");
        requestQueue.add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();
       requestQueue.cancelAll("ids");
    }

    public void addtofavourite(View view) {
        String data =  new Gson().toJson(cat);
        sharedPreferences.edit().putString(cat.id,data).commit();
        Toast.makeText(this,"ADDED SUCESSFULLY",Toast.LENGTH_SHORT).show();

    }
}

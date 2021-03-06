package com.example.parakhgarg.adminbirts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_page);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
////        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    ArrayList<ComplaintObject> items;
    Context context = this;
    Bundle b;
    String accessToken,secretKey;
    Boolean verified;
    String id,subject,description,image,latitude,longitude,city,state,pincode,created_at,updated_at,userid,status,priority,name,email;
    String URL_FOR_TRACKING = Constants.SERVER+"/complaints/pending_complaint_list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent i = getIntent();
        b = i.getExtras();
        accessToken = b.getString("accessToken");
        secretKey = b.getString("secretKey");
        verified = b.getBoolean("verified");
        //phone_no_verified = b.getBoolean("phone_no_verified");
        name = b.getString("name");
        email = b.getString("email");
        items = new ArrayList<ComplaintObject>();
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setCancelable(false);
        recyclerView = (RecyclerView) findViewById(R.id.rvComplaints);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String cancel_req_tag = "register";

        progressDialog.setMessage("Tracking your Complaints");
        showDialog();
       /* if(test == 0) {
            URL_FOR_REGISTRATION+="?name="+name+"&contact="+contact+"&email="+email+"&password="+password;
            test = 1;
        }*/
        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_FOR_TRACKING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Form", "Register Response: " + response.toString());
                JSONArray jsonarray = null;

                //   Toast.makeText(getApplicationContext(),"Register Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                hideDialog();

                try {
                    jsonarray = new JSONArray(response.toString());
                    Toast.makeText(getApplicationContext(),
                            "Complaints Successfully Shown!", Toast.LENGTH_LONG).show();
                    ComplaintObject co[] = new ComplaintObject[100];
                    JSONObject jsonobject[] = new JSONObject[100];
                    if(jsonarray!=null) {
                        for (int i = 0; i < jsonarray.length(); i++) {

                            co[i] = new ComplaintObject();
                            jsonobject[i] = jsonarray.getJSONObject(i);
                            co[i].setId(jsonobject[i].getString("id"));
                            co[i].setSubject(jsonobject[i].getString("subject"));
                            co[i].setDescription(jsonobject[i].getString("description"));
                            co[i].setImage(jsonobject[i].getString("image"));

                            co[i].setCreated_at(jsonobject[i].getString("created_at"));
                            co[i].setUpdated_at(jsonobject[i].getString("updated_at"));
                            co[i].setUserid(jsonobject[i].getString("user_id"));
                            co[i].setStatus(jsonobject[i].getString("status"));

                            co[i].setAccessToken(accessToken);
                            co[i].setSecretKey(secretKey);
                            // co[i].setAddress(jsonobject[i].getString("address"));
                            items.add(co[i]);
                        }
                    }
                    Log.e("Form", "items: " + items);
                    adapter = new CardViewAdapter(items,context,name,email,verified);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Form", "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Error:"+
                        "Please Check your Internet Connection!", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            /*  @Override
              protected Map<String, String> getParams() {
                  // Posting params to register url
                  Map<String, String> params = new HashMap<String, String>();
                  params.put("subject", subject);
                  params.put("description",description);
                  params.put("image", image);
                  params.put("city", city);
                  params.put("state", state);
                  params.put("pincode", pincode);
                  params.put("latitude", latitude);
                  params.put("longitude", longitude);
                  params.put("access_token",accessToken);
                  params.put("secret_key",secretKey);
                  params.put("created_at",created_at);
                  params.put("updated_at",updated_at);
                  params.put("user_id",userid);
                  params.put("status",status);
                  params.put("priority",priority);
                  return params;
              }*/
            @Override
            public Map<String, String> getHeaders() {
                Map <String,String> params  = new HashMap<String, String>();
                params.put("access_token",accessToken);
                params.put("secret_key",secretKey);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);



    }
    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


}

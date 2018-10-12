package com.honeywelltest;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView itemsListRecyclerView;

    private ItemsAdapter itemsAdapter;

    ProgressDialog dialog;

    private TextView centerMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsListRecyclerView = findViewById(R.id.recycler_view);

        centerMessage = findViewById(R.id.centerMessage);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        itemsListRecyclerView.setLayoutManager(linearLayoutManager);

        dialog = new ProgressDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (HoneywellUtils.getInstance().getItemsList().size() != 0) {
//            itemsAdapter = new ItemsAdapter(MainActivity.this, MainActivity.this, HoneywellUtils.getInstance().getItemsList());
            itemsListRecyclerView.setAdapter(itemsAdapter);
        }
    }

    private List<Item> itemList = new ArrayList<Item>();

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            dialog.dismiss();
            if (error instanceof NetworkError) {
                centerMessage.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "error: " + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void getData() {
        if (dialog != null) {
            if (!dialog.isShowing()) {
                dialog.setTitle("Please wait");
                // dialog.show();
            }
        }

        itemList.clear();

        RequestQueue requestQueue = SingletonRequestQueue.getInstance(this).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, HoneywellUtils.BASE_URL + "/api/users?page=2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                Log.d("suresh", "response is: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);

                        String id = dataObject.getString(HoneywellUtils.ID);
                        String fName = dataObject.getString(HoneywellUtils.FNAME);
                        String lName = dataObject.getString(HoneywellUtils.LNAME);
                        String avatarImage = dataObject.getString(HoneywellUtils.AVATAR);

                        Item item = new Item();
                        item.setId(Integer.parseInt(id));
                        item.setfName(fName);
                        item.setlName(lName);
                        item.setAvatar(avatarImage);

                        itemList.add(item);
                    }

                } catch (JSONException e) {

                }
            }
        }, errorListener) {
        };

        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                try {
                    if (request != null && request.getCacheEntry() != null) {
                        centerMessage.setVisibility(View.GONE);
                        String cacheValue = new String(request.getCacheEntry().data, "UTF-8");
                        Log.d("suresh", "request finished: " + cacheValue);

                        HoneywellUtils mHoneywellUtils = new HoneywellUtils();


                        HoneywellUtils.getInstance().updateListItems(itemList);

//                        itemsAdapter = new ItemsAdapter(MainActivity.this, MainActivity.this, itemList);
                        itemsListRecyclerView.setAdapter(itemsAdapter);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

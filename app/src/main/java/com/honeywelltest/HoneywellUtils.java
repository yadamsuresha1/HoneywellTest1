package com.honeywelltest;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class HoneywellUtils {

    public static String BASE_URL = "https://reqres.in";

    public static final String ID = "id";
    public static final String FNAME = "first_name";
    public static final String LNAME = "last_name";
    public static final String AVATAR = "avatar";

    private static HoneywellUtils honeywellUtils;

    public static HoneywellUtils getInstance() {
        if (honeywellUtils == null) {
            honeywellUtils = new HoneywellUtils();
        }
        return honeywellUtils;
    }


    private List<Item> itemList;

    public List<Item> getItemsList() {
        if (itemList == null) {
            itemList = new ArrayList<Item>();
        }
        return itemList;
    }

    public void updateListItems(List<Item> itemList) {
        if (itemList != null) {
            this.itemList = itemList;
        }
    }


    private ListemItemsListener listemItemsListener;

    public void setListUpdateListner(ListemItemsListener listUpdateListner) {
        this.listemItemsListener = listUpdateListner;
    }

    private ProgressDialog progressDialog;

    private void showProgress(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait!");
        progressDialog.show();
    }

    private void dismissProgress(Context context) {
        if (progressDialog != null)
            progressDialog.dismiss();
    }


    public List<Item> getData(final Context context) {

        getItemsList().clear();
        showProgress(context);

        RequestQueue requestQueue = SingletonRequestQueue.getInstance(context).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, HoneywellUtils.BASE_URL + "/api/users?page=2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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

                    dismissProgress(context);
                    listemItemsListener.onListUpdated(itemList);


                } catch (JSONException e) {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dismissProgress(context);

                if (error instanceof NetworkError) {
                    listemItemsListener.onErrorOccured(context.getString(R.string.network_error));
                    Toast.makeText(context, context.getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else {
                    listemItemsListener.onErrorOccured(error.getMessage().toString());
                    Toast.makeText(context, "error: " + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
        };

        requestQueue.add(stringRequest);
        return itemList;
    }
}

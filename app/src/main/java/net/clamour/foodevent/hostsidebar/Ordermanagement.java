package net.clamour.foodevent.hostsidebar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



import net.clamour.foodevent.R;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ordermanagement extends AppCompatActivity {

    ProgressDialog pDialog;
    ListView listView;
     ArrayList<OrderStorage>order_array;
    OrderAdapter orderAdapter;

    SharedPreferences login_prefrence;
    SharedPreferences Registration_preferences;
    String host_id;
    Button ViewMoreOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermanagement);

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("My Orders");


        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        login_prefrence = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);

        host_id=Registration_preferences.getString("outh_id","");
        Log.i("auth_iduseeeerrrrrr",host_id);


        host_id=login_prefrence.getString("outh_id","");
        Log.i("auth_idlogin",host_id);

        ViewMoreOrders=(Button)findViewById(R.id.ViewMoreOrders);

        ViewMoreOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                            browserIntent.setData(Uri.parse("http://myhealthyhost.com/login.php"));
                            startActivity(browserIntent);

            }
        });



        listView=(ListView)findViewById(R.id.list);

        order_array=new ArrayList<>();
        orderAdapter = new
                OrderAdapter(this,order_array);

        listView.setAdapter(orderAdapter);

        showOrders();
    }


    public  void showOrders(){

        pDialog = new ProgressDialog(Ordermanagement.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        // pDialog.show();

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://www.myhealthyhost.com/api/orderlist.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("response", response);



                        //   arrayList=new ArrayList<>();

                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);


                        try {



                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                OrderStorage data=new OrderStorage();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String firstname=jsonObject.getString("fname");
                                String lastname=jsonObject.getString("lname");
                                data.setOrderno(jsonObject.getString("OrderNumber"));
                                data.setOrderdate(jsonObject.getString("bookingdate"));
                                data.setOrderTime(jsonObject.getString("bookingtime"));
                                data.setPrice(jsonObject.getString("price"));
                                data.setName(firstname+" "+lastname);
                                data.setService(jsonObject.getString("servicename"));

                                order_array.add(data);




                            }


                        }
                        catch (Exception e){
                        }
              orderAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(JobDetails.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.i("errorr",error.toString());
                    }
                })

        {




            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("hostid",host_id);

                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(Ordermanagement.this);
        requestQueue1.add(stringRequest1);


    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case android.R.id.home:
                // Respond to the action bar's Up/Home button
                // adapter.notifyDataSetChanged();

                finish();
                // NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }





    }
}
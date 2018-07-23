package net.clamour.foodevent.privateevent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.clamour.foodevent.EventDescriptionGuest;
import net.clamour.foodevent.GuestProfile.GuestProfileScreen;
import net.clamour.foodevent.R;
import net.clamour.foodevent.booking.GuestBooking;
import net.clamour.foodevent.eventlist.EventListAdapter;
import net.clamour.foodevent.eventlist.EventListStorage;
import net.clamour.foodevent.eventlist.ItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Privateevent_list extends AppCompatActivity implements ItemClickListener {

    private RecyclerView recyclerView;
    private EventListAdapter eventListAdapter;
    private ArrayList<EventListStorage>event_list_array;
    ProgressDialog pDialog;
    String rating_count,price_per_guest,event_address,host_event_name,host_authid,service_id;
    String host_event_image,host_image,host_auth_id_get;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privateevent_list);

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("Service List");


        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent=getIntent();
        host_authid=intent.getStringExtra("outh_id_host");
        Log.i("host_authid_private",host_authid);
        service_id=intent.getStringExtra("service_id");
        Log.i("service_id",service_id);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        event_list_array=new ArrayList<>();
        eventListAdapter=new EventListAdapter (Privateevent_list.this,event_list_array);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eventListAdapter);
        eventListAdapter.setClickListener(this);


        eventListData();
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


    @Override
    public void onClick(View view, int position) {



        host_auth_id_get=event_list_array.get(position).getOauth_uid();

        Intent intent=new Intent(Privateevent_list.this, Privateevent_Description.class);
        intent.putExtra("host_auth_id",host_authid);
        intent.putExtra("service_id",service_id);


        intent.putExtra("firstname",event_list_array.get(position).getFirstName());
        intent.putExtra("title",event_list_array.get(position).getCat_title());
        intent.putExtra("lastname",event_list_array.get(position).getLastName());
        intent.putExtra("eventlocation",event_list_array.get(position).getAddress());
        Log.i("eventlocation",event_list_array.get(position).getAddress());
        intent.putExtra("price",event_list_array.get(position).getCat_price());
        intent.putExtra("minimum_guest",event_list_array.get(position).getMinimum_guest());
        intent.putExtra("maximum_guest",event_list_array.get(position).getMaximum_guest());
       // intent.putExtra("cusine",event_list_array.get(position).getCuisine());
       // intent.putExtra("specialdiet",event_list_array.get(position).getSpecial_diets());
        intent.putExtra("experience",event_list_array.get(position).getExperience());
      //  intent.putExtra("availability",event_list_array.get(position).getAvailability_timing());
        intent.putExtra("paydate",event_list_array.get(position).getPay_date());
        intent.putExtra("recievingtime",event_list_array.get(position).getRecieving_timing());
       // intent.putExtra("menu",event_list_array.get(position).getMenu());
        intent.putExtra("instruction",event_list_array.get(position).getInstruction());
        intent.putExtra("description",event_list_array.get(position).getDescription());
        intent.putExtra("cover_image",event_list_array.get(position).getCat_coverimg());
        intent.putExtra("host_image",event_list_array.get(position).getPicture());
        intent.putExtra("facilities",event_list_array.get(position).getPayfacilities());
        intent.putExtra("palace",event_list_array.get(position).getPay_place());
        intent.putExtra("product_id",event_list_array.get(position).getProduct_id());
        intent.putExtra("cateogry_name","pay");
        startActivity(intent);


    }

    public void eventListData(){

        pDialog = new ProgressDialog(Privateevent_list.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/paying_details.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("eventlist_private", response);





                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);


                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                EventListStorage eventListStorage=new EventListStorage();
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                host_auth_id_get=jsonObject.getString("oauth_uid");
                                host_event_name=jsonObject.getString("pay_title");

                                Log.i("event_image",host_event_name);
                                String back_image=jsonObject.getString("pay_fetureimg");
                                String host_icon=jsonObject.getString("picture");
                                event_address=jsonObject.getString("pay_fulladdress");
                                price_per_guest=jsonObject.getString("pay_price");

                                host_event_image="http://myhealthyhost.com/"+back_image;
                                host_image="http://myhealthyhost.com/"+host_icon;

                                eventListStorage.setAddress(event_address);
                                eventListStorage.setCat_coverimg(host_event_image);
                                eventListStorage.setPicture(host_image);
                                eventListStorage.setCat_price(price_per_guest);
                                eventListStorage.setCat_title(host_event_name);
                                eventListStorage.setFirstName(jsonObject.getString("first_name"));
                                eventListStorage.setLastName(jsonObject.getString("last_name"));
                                eventListStorage.setExperience(jsonObject.getString("pay_exp"));
                                eventListStorage.setDescription(jsonObject.getString("pay_description"));
                                eventListStorage.setPay_date(jsonObject.getString("pay_date"));
                                eventListStorage.setMinimum_guest(jsonObject.getString("pay_min_guests"));
                                eventListStorage.setMaximum_guest(jsonObject.getString("pay_max_guests"));
                                eventListStorage.setRecieving_timing(jsonObject.getString("pay_last_time_booking"));
                                eventListStorage.setInstruction(jsonObject.getString("pay_additional_info"));
                                eventListStorage.setPay_place(jsonObject.getString("place_name"));
                                eventListStorage.setPayfacilities(jsonObject.getString("pay_facilities"));
                                eventListStorage.setProduct_id(jsonObject.getString("id"));

                                event_list_array.add(eventListStorage);





                            }


                        }
                        catch (Exception e){
                        }
                        eventListAdapter.notifyDataSetChanged();

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
                params.put("oauth_uid",host_authid);



                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);


    }
}
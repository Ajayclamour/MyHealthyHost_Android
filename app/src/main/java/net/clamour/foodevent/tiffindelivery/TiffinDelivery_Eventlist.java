package net.clamour.foodevent.tiffindelivery;

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

public class TiffinDelivery_Eventlist extends AppCompatActivity implements ItemClickListener {

    private RecyclerView recyclerView;
    private EventListAdapter eventListAdapter;
    private ArrayList<EventListStorage>event_list_array;
    ProgressDialog pDialog;
    String rating_count,price_per_guest,event_address,host_event_name,host_authid,service_id;
    String host_event_image,host_image,host_auth_id_get;
    String back_image,host_icon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiffin_delivery__eventlist);

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
        Log.i("host_authid",host_authid);

        service_id=intent.getStringExtra("service_id");
        Log.i("service_id",service_id);


        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        event_list_array=new ArrayList<>();
        eventListAdapter=new EventListAdapter (TiffinDelivery_Eventlist.this,event_list_array);

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

        Intent intent=new Intent(TiffinDelivery_Eventlist.this, TiffinDeliveryDescription.class);
        intent.putExtra("host_auth_id",host_authid);
        intent.putExtra("service_id",service_id);
        intent.putExtra("firstname",event_list_array.get(position).getFirstName());
        intent.putExtra("title",event_list_array.get(position).getCat_title());
        intent.putExtra("lastname",event_list_array.get(position).getLastName());
        intent.putExtra("eventlocation",event_list_array.get(position).getAddress());
        intent.putExtra("price",event_list_array.get(position).getCat_price());
        intent.putExtra("minimum_guest",event_list_array.get(position).getMinimum_guest());
        intent.putExtra("maximum_guest",event_list_array.get(position).getMaximum_guest());
        intent.putExtra("cusine",event_list_array.get(position).getCuisine());
        intent.putExtra("specialdiet",event_list_array.get(position).getSpecial_diets());
        intent.putExtra("experience",event_list_array.get(position).getExperience());
        intent.putExtra("availability",event_list_array.get(position).getAvailability_timing());
       intent.putExtra("duration",event_list_array.get(position).getDuration());
        intent.putExtra("recievingtime",event_list_array.get(position).getRecieving_timing());
        intent.putExtra("menu",event_list_array.get(position).getMenu());
        intent.putExtra("instruction",event_list_array.get(position).getInstruction());
        intent.putExtra("description",event_list_array.get(position).getDescription());
        intent.putExtra("cover_image",event_list_array.get(position).getCat_coverimg());
        intent.putExtra("host_image",event_list_array.get(position).getPicture());
        intent.putExtra("product_id",event_list_array.get(position).getProduct_id());
        intent.putExtra("cateogry_name","tiff");
        startActivity(intent);


    }

    public void eventListData(){

        pDialog = new ProgressDialog(TiffinDelivery_Eventlist.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/tiffin_details.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("eventlist", response);





                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);


                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                EventListStorage eventListStorage=new EventListStorage();
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                host_auth_id_get=jsonObject.getString("oauth_uid");
                                host_event_name=jsonObject.getString("tif_title");

                                Log.i("event_image",host_event_name);
                                 back_image=jsonObject.getString("tif_coverimg");
                                Log.i("host_eventimage",back_image);
                                 host_icon=jsonObject.getString("picture");
                                Log.i("host_eventimage",host_icon);
                                event_address=jsonObject.getString("tif_fulladdress");
                                price_per_guest=jsonObject.getString("tif_price");

                                host_event_image="http://myhealthyhost.com/"+back_image;
                                Log.i("host_eventimage",host_event_image);
                                host_image="http://myhealthyhost.com/"+host_icon;
                                Log.i("host_image",host_image);

                                String menu=jsonObject.getString("tif_menu");
                                Log.i("menuuuutiffin",menu);

                                eventListStorage.setAddress(event_address);
                                eventListStorage.setCat_coverimg(host_event_image);
                                eventListStorage.setPicture(host_image);
                                eventListStorage.setCat_price(price_per_guest);
                                eventListStorage.setCat_title(host_event_name);

                                eventListStorage.setFirstName(jsonObject.getString("first_name"));
                                eventListStorage.setLastName(jsonObject.getString("last_name"));
                                eventListStorage.setExperience(jsonObject.getString("tif_exp"));
                                eventListStorage.setSpecial_diets(jsonObject.getString("tif_specialdiets"));
                                eventListStorage.setCuisine(jsonObject.getString("cuisinesname"));
                                eventListStorage.setDescription(jsonObject.getString("tif_description"));
                                eventListStorage.setDuration(jsonObject.getString("tif_event_time_duration"));
                                eventListStorage.setMinimum_guest(jsonObject.getString("tif_min_guests"));
                                eventListStorage.setMaximum_guest(jsonObject.getString("tif_max_guests"));
                                eventListStorage.setRecieving_timing(jsonObject.getString("tif_last_time_booking"));
                                eventListStorage.setAvailability_timing(jsonObject.getString("tif_tiffintime"));
                                eventListStorage.setMenu(jsonObject.getString("tif_menu"));
                                eventListStorage.setInstruction(jsonObject.getString("tif_additional_info"));
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
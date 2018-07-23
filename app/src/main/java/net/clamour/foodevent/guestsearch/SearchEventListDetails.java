package net.clamour.foodevent.guestsearch;

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
import net.clamour.foodevent.GuestHomeScreen.RecentActivityStorage;
import net.clamour.foodevent.GuestProfile.GuestProfileScreen;
import net.clamour.foodevent.R;
import net.clamour.foodevent.booking.GuestBooking;
import net.clamour.foodevent.catering.Catering_Evet_Details;
import net.clamour.foodevent.eventlist.EventListAdapter;
import net.clamour.foodevent.eventlist.EventListStorage;
import net.clamour.foodevent.eventlist.ItemClickListener;
import net.clamour.foodevent.privateevent.Privateevent_Description;
import net.clamour.foodevent.tiffindelivery.TiffinDeliveryDescription;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchEventListDetails extends AppCompatActivity implements ItemClickListener {

    private RecyclerView recyclerView;
    private SearchEventListAdapter eventListAdapter;
    private ArrayList<EventListStorage>event_list_array;
    ProgressDialog pDialog;
    String rating_count,price_per_guest,event_address,host_event_name,host_authid,service_id;
    String host_event_image,host_image,host_auth_id_get;
    String cateogery_name,service_id_search,host_auth_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event_list_details);

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("HOST SERVICES");

        setSupportActionBar(toolbar1);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent=getIntent();
        host_authid=intent.getStringExtra("outh_id_host");
        Log.i("host_authid",host_authid);



        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        event_list_array=new ArrayList<>();
        eventListAdapter=new SearchEventListAdapter (net.clamour.foodevent.guestsearch.SearchEventListDetails.this,event_list_array);

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



       // host_auth_id_get=event_list_array.get(position).getOauth_uid();
        service_id_search=event_list_array.get(position).getServiceid_serach();
        cateogery_name=event_list_array.get(position).getSearch_cateogry();
        Log.i("cateogry_name",cateogery_name);
       // host_auth_id=event_list_array.get(position).getSearch_hostId();

//        Intent intent=new Intent(net.clamour.foodevent.guestsearch.SearchEventListDetails.this, Catering_Evet_Details.class);
//        intent.putExtra("host_auth_id",host_authid);
//        intent.putExtra("service_id",service_id);
//        startActivity(intent);
if(cateogery_name.equals("paying")){

    Intent intent=new Intent(SearchEventListDetails.this, Privateevent_Description.class);
    intent.putExtra("host_auth_id",host_authid);
    intent.putExtra("service_id",service_id_search);
    intent.putExtra("cateogry_name",cateogery_name);
    intent.putExtra("host_auth_id",host_authid);

    startActivity(intent);
}
        else if(cateogery_name.equals("social")){

            Intent intent=new Intent(SearchEventListDetails.this, EventDescriptionGuest.class);
            intent.putExtra("host_auth_id",host_authid);
            intent.putExtra("service_id",service_id_search);
            intent.putExtra("cateogry_name",cateogery_name);
            intent.putExtra("host_auth_id",host_authid);
            startActivity(intent);
        }
        else if(cateogery_name.equals("tiffin")){

            Intent intent=new Intent(SearchEventListDetails.this, TiffinDeliveryDescription.class);
            intent.putExtra("host_auth_id",host_authid);
            intent.putExtra("service_id",service_id_search);
            intent.putExtra("cateogry_name",cateogery_name);
            intent.putExtra("host_auth_id",host_authid);
            startActivity(intent);
        }
        else if(cateogery_name.equals("catering")){

            Intent intent=new Intent(SearchEventListDetails.this, Catering_Evet_Details.class);
            intent.putExtra("host_auth_id",host_authid);
            intent.putExtra("service_id",service_id_search);
            intent.putExtra("cateogry_name",cateogery_name);
            intent.putExtra("host_auth_id",host_authid);
            startActivity(intent);
        }

    }

    public void eventListData(){

        pDialog = new ProgressDialog(net.clamour.foodevent.guestsearch.SearchEventListDetails.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST," http://myhealthyhost.com/api/host_detail.php?",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("eventlist_search", response);





                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);


                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            String response_data=jsonObject.getString("result");
                            Log.i("responsedata",response_data);

                          //  eventListStorage.setSearch_picture(jsonObject.getString("picture"));
                            //eventListStorage.setSearch_address(jsonObject.getString("address"));

                            JSONObject jsonObject1=new JSONObject(response_data);
                            String first_name=jsonObject1.getString("first_name");
                            Log.i("first_name",first_name);
                            String host_image=jsonObject1.getString("picture");
                            String complete_host_image="http://myhealthyhost.com/"+host_image;
                            String response_data_service=jsonObject1.getString("service");
                            Log.i("responseservice",response_data_service);


                            JSONArray jsonArray = new JSONArray(response_data_service);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                EventListStorage eventListStorage=new EventListStorage();
                                JSONObject jsonObject2=jsonArray.getJSONObject(i);
                                String cover_image=jsonObject2.getString("coverimg");
                                String cover_image_complete="http://myhealthyhost.com/"+cover_image;
                               cateogery_name=jsonObject2.getString("cat_name");
                               Log.i("cateogryname",cateogery_name);
                              // host_auth_id=jsonObject2.getString("outh_id");

                             //  Log.i("host_authid",host_auth_id);

                                eventListStorage.setSearch_cat_price(jsonObject2.getString("price"));

                                eventListStorage.setSearch_cat_title(jsonObject2.getString("tittle"));
                                eventListStorage.setSearch_picture(complete_host_image);
                                eventListStorage.setSearch_catcoverimage(cover_image_complete);
                                eventListStorage.setServiceid_serach(jsonObject2.getString("serviceid"));
                                eventListStorage.setSearch_cateogry(jsonObject2.getString("cat_name"));
                                eventListStorage.setSearchevent_address(jsonObject2.getString("fulladdress"));



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

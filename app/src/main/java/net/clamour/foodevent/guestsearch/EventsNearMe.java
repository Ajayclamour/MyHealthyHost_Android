package net.clamour.foodevent.guestsearch;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.clamour.foodevent.GuestHomeScreen.RecentActivityAdapter;
import net.clamour.foodevent.GuestHomeScreen.RecentActivityStorage;
import net.clamour.foodevent.GuestProfile.GuestRegistration;
import net.clamour.foodevent.R;
import net.clamour.foodevent.catering.Catering_eventlist;
import net.clamour.foodevent.eventlist.EventListAdapter;
import net.clamour.foodevent.eventlist.EventListStorage;
import net.clamour.foodevent.webservice.ApiClient;
import net.clamour.foodevent.webservice.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsNearMe extends AppCompatActivity {

    EventsNearMeAdapter recentActivityAdapter;
    ArrayList<RecentActivityStorage>searched_array;
    ListView listView;
    ApiInterface apiInterface;
    String outh_id_host,service_id;
    ProgressDialog pDialog;
    String latitude,longitude,search_host_outhid;
    Button switch_map;
    Boolean isSucess;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_near_me);

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("HOST NEAR ME");


        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Intent intent=getIntent();
        latitude=intent.getStringExtra("searchedlattitude");
        Log.i("latitude",latitude);
        longitude=intent.getStringExtra("searchlongitude");
        Log.i("longitude",longitude);

        switch_map=(Button)findViewById(R.id.switch_map);

        switch_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventsNearMe.this,MapActivity.class);
                intent.putExtra("lattitude",latitude);
                intent.putExtra("longitude",longitude);
                startActivity(intent);
            }
        });
        listView=(ListView)findViewById(R.id.list);
        searched_array=new ArrayList<>();

        recentActivityAdapter=new EventsNearMeAdapter(this,searched_array);
        listView.setAdapter(recentActivityAdapter);

        getSearchedHost();

        listClick();


    }

    public void getSearchedHost(){
        pDialog = new ProgressDialog(EventsNearMe.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/search.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("searchedhost", response);


                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);


                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            isSucess=jsonObject.getBoolean("status");
                            String response_data=jsonObject.getString("result");
                            Log.i("responsedata",response_data);



                            JSONArray jsonArray = new JSONArray(response_data);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                RecentActivityStorage recentActivityStorage=new RecentActivityStorage();
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);

                                String city=jsonObject1.getString("city");
                                String address=jsonObject1.getString("address");
                                String country=jsonObject1.getString("country");
//                                host_cusine_st=recent_activity_array.get(position).getMy_offered_cuisine_host();
//                                host_proffession_st=recent_activity_array.get(position).getProfession();
                                String service_host=jsonObject1.getString("service_host");
                                Log.i("service_host",service_host);
                                String picture=jsonObject1.getString("picture");
                                String Complete_url="http://myhealthyhost.com/"+picture;
                                Log.i("completeurl",Complete_url);
                               search_host_outhid=jsonObject1.getString("oauth_uid");
                                Log.i("searchhostid",search_host_outhid);

                                recentActivityStorage.setFirst_name(jsonObject1.getString("first_name"));
                                recentActivityStorage.setLast_name(jsonObject1.getString("last_name"));
                                recentActivityStorage.setAddress(address+""+city);
                                recentActivityStorage.setCity(jsonObject1.getString("city"));
                                recentActivityStorage.setCountry(jsonObject1.getString("country"));
                                recentActivityStorage.setMy_offered_cuisine_host(jsonObject1.getString("my_offered_cuisine_host"));
                                recentActivityStorage.setProfession(jsonObject1.getString("profession"));
                                recentActivityStorage.setService_host(jsonObject1.getString("service_host"));
                                recentActivityStorage.setOauth_uid(jsonObject1.getString("oauth_uid"));
                                recentActivityStorage.setPicture(Complete_url);
                                recentActivityStorage.setUsername_host(jsonObject1.getString("username_host"));
                                recentActivityStorage.setCurrency(jsonObject1.getString("currency"));
                                recentActivityStorage.setGender(jsonObject1.getString("gender"));
                                recentActivityStorage.setProfession(jsonObject1.getString("profession"));
                                recentActivityStorage.setLanguage(jsonObject1.getString("language"));
                                recentActivityStorage.setRatting(jsonObject1.getInt("ratting"));
                                recentActivityStorage.setDescription(jsonObject1.getString("description"));

                                searched_array.add(recentActivityStorage);


                            }


                        }

                        catch (Exception e){
                        }
                        recentActivityAdapter.notifyDataSetChanged();

                        if(isSucess==false){

                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    EventsNearMe.this).create();
                            // saveData();
                            // Setting Dialog Title
                            alertDialog.setTitle("                    Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("              No Host Available");

                            // Setting Icon to Dialog


                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                                    emailIntent.setType("text/plain");
//                                    startActivity(emailIntent);

                                    // Write your code here to execute after dialog closed
                                    alertDialog.dismiss();
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();

                    }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
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
                params.put("lat",latitude);
                params.put("lng",longitude);



                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
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
    public void listClick(){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                outh_id_host=searched_array.get(position).getOauth_uid();
                Intent intent=new Intent(EventsNearMe.this,SearchEventListDetails.class);
                intent.putExtra("outh_id_host",outh_id_host);
               // intent.putExtra("service_id",service_id);
                startActivity(intent);

            }
        });
    }
}
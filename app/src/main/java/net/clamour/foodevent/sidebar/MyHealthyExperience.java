package net.clamour.foodevent.sidebar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.clamour.foodevent.GuestHomeScreen.GuestRiview;
import net.clamour.foodevent.R;
import net.clamour.foodevent.eventlist.EventListAdapter;
import net.clamour.foodevent.eventlist.EventListStorage;
import net.clamour.foodevent.guestsearch.EventFilterScreen;
import net.clamour.foodevent.guestsearch.EventsNearMeAdapter;
import net.clamour.foodevent.guestsearch.EventsNearMeStorage;
import net.clamour.foodevent.guestsearch.MapActivity;
import net.clamour.foodevent.privateevent.Privateevent_list;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyHealthyExperience extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HealthyExperienceAdapter eventListAdapter;
    private ArrayList<EventListStorage>user_history_array=new ArrayList<>();
    Context context;
    ProgressDialog pDialog;
    SharedPreferences Registration_preferences;
    SharedPreferences login_prefrence;
    String user_outh_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_healthy_experience);



        context=this;

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

        user_outh_id=Registration_preferences.getString("outh_id","");
        user_outh_id=login_prefrence.getString("outh_id","");
        Log.i("user_outhid",user_outh_id);



        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        eventListAdapter=new HealthyExperienceAdapter(net.clamour.foodevent.sidebar.MyHealthyExperience.this,user_history_array);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eventListAdapter);


        guesteventHistory();
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
//    public void addData(){
//
//        EventListStorage eventListStorage = new EventListStorage(R.drawable.icon1,R.drawable.user,"Indian Food Evening","$67","Aalia");
//        event_list_nearme_array.add(eventListStorage);
//
//        EventListStorage eventListStorage1 = new EventsNearMeStorage(R.drawable.icon1,R.drawable.user,"Indian Food Evening","$67","Aalia");
//        event_list_nearme_array.add(eventListStorage1);
//
//    }
    public void addRiview(View view){
        Button bt = (Button) view;
        Toast.makeText(this, "Button " + bt.getText().toString(), Toast.LENGTH_LONG).show();
        Log.i("clicked","meeeeeeeeee");

        Intent intent =new Intent(MyHealthyExperience.this, GuestRiview.class);
       // intent.putExtra("hostid",)
        startActivity(intent);

    }

    public void guesteventHistory(){

        pDialog = new ProgressDialog(MyHealthyExperience.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/user_booking.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("eventHistory", response);


                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);


                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                EventListStorage eventListStorage=new EventListStorage();
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                eventListStorage.setEventNameHistory(jsonObject.getString("eventtittle"));
                                eventListStorage.setEventPriceHistory(jsonObject.getString("eventprice"));
                                eventListStorage.setHostNameHistory(jsonObject.getString("hostname"));
                                String event_image= jsonObject.getString("eventimage");
                                String eventimage_url="http://myhealthyhost.com/"+event_image;
                                eventListStorage.setEventImageHistory(eventimage_url);
                                String host_image=jsonObject.getString("hostimage");
                                String host_image_url="http://myhealthyhost.com/"+host_image;
                                eventListStorage.setHostImageHistory(host_image_url);
                                eventListStorage.setHostIdHistory(jsonObject.getString("hostid"));



                                user_history_array.add(eventListStorage);


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
                params.put("user_id",user_outh_id);



                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);


    }
}

package net.clamour.foodevent.privateevent;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.clamour.foodevent.EventDescriptionGuest;
import net.clamour.foodevent.GuestHomeScreen.GuestRiviewAdapter;
import net.clamour.foodevent.GuestHomeScreen.RecentActivityStorage;
import net.clamour.foodevent.R;
import net.clamour.foodevent.booking.GuestBooking;
import net.clamour.foodevent.catering.Catering_Evet_Details;
import net.clamour.foodevent.catering.ViewMoreDescription;
import net.clamour.foodevent.eventlist.EventListStorage;
import net.clamour.foodevent.eventlist.GuestEventList;
import net.clamour.foodevent.guestsearch.EventsNearMe;
import net.clamour.foodevent.tiffindelivery.TiffinDeliveryDescription;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Privateevent_Description extends AppCompatActivity {


    Button req_book;
    String host_outh_id,service_id;

    ProgressDialog pDialog;
    SharedPreferences login_prefrence;
    SharedPreferences Registration_preferences;

    @BindView(R.id.event_background_image)ImageView event_background;
    @BindView(R.id.host_image)ImageView event_host_image;
    @BindView(R.id.host_name_des)TextView host_name_des;
    @BindView(R.id.event_address_desc)TextView event_address_desc;
   // @BindView(R.id.cusineeeee)TextView cusine_desc;
    @BindView(R.id.price_perguest_desc)TextView price_perguest_desc;
    @BindView(R.id.no_guest_desc)TextView no_of_guest_desc;
    @BindView(R.id.event_description)TextView event_description_desc;
    @BindView(R.id.event_name_des)TextView event_name_desc;

    //  @BindView(R.id.event_timing_desc)TextView event_time_des;
    @BindView(R.id.Experience)TextView Experience_desc;
    @BindView(R.id.add_wishlist)Button add_to_wishlist;
   // @BindView(R.id.availav_desc)TextView avalability;
  //  @BindView(R.id.duration_desc)TextView duration;
    @BindView(R.id.recivingtime_desc)TextView recievingTime;
   // @BindView(R.id.menu_desc)TextView menu_desc;
   // @BindView(R.id.instructin_desc)TextView Instruction;
 //   @BindView(R.id.specialdiet_desc)TextView specialdiet;

    @BindView(R.id.instructin_desc)TextView Instruction;
    @BindView(R.id.typeofplace)TextView typeofpalace;
    @BindView(R.id.facilities)TextView facilities;
    @BindView(R.id.paydate)TextView paydate;
    @BindView(R.id.viewmore_description)
    TextView viewmore_description;



    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<EventListStorage> guest_riview_array;

    String firstname_st,lastname_st,hostimage_st,coverimage_st,service_title_st,service_address_st,price_st,
            minimumguest_st,maximumguest_st,cusine_st,specialdiet_st,experience_st,availability_st,duration_st,recievingtime_st,menu_st,instruction_st,
            description_st,palace_st,paydate_st,facilities_st,product_id_st,cateogry_name_main;


    String event_background_image,host_image,host_name,event_name,event_address,event_price,no_of_guest,cusine_name,event_timing,event_description,experience;
String user_outh_id,product_id;
Boolean isSucess;
String replaced_facilities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privateevent__description);

        ButterKnife.bind(this);
        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("Service Description");


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


        Intent intent=getIntent();
        host_outh_id=intent.getStringExtra("host_auth_id");
        Log.i("host_authid",host_outh_id);
        service_id=intent.getStringExtra("service_id");
        Log.i("service_id",service_id);


        cateogry_name_main = intent.getStringExtra("cateogry_name");
        Log.i("cateogry_name", cateogry_name_main);

        req_book=(Button)findViewById(R.id.request_book) ;
        sendBookingDetails();


        add_to_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWishList();
            }
        });

        if (cateogry_name_main.contains("paying")) {

            eventDescription();

          //  Toast.makeText(getApplicationContext(),"calllllll",Toast.LENGTH_LONG).show();
            Log.i("gafdytafuysuiof","hafudyyayfu");

        }
else {


        firstname_st=intent.getStringExtra("firstname");
        lastname_st=intent.getStringExtra("lastname");
        service_title_st=intent.getStringExtra("title");
        Log.i("servicetitle",service_title_st);
        service_address_st=intent.getStringExtra("eventlocation");
        price_st=intent.getStringExtra("price");
        minimumguest_st=intent.getStringExtra("minimum_guest");
        maximumguest_st=intent.getStringExtra("maximum_guest");
        cusine_st=intent.getStringExtra("cusine");
        specialdiet_st=intent.getStringExtra("specialdiet");
        experience_st=intent.getStringExtra("experience");
        availability_st=intent.getStringExtra("availability");
        duration_st=intent.getStringExtra("duration");
        recievingtime_st=intent.getStringExtra("recievingtime");
        menu_st=intent.getStringExtra("menu");
        instruction_st=intent.getStringExtra("instruction");
        description_st=intent.getStringExtra("description");
        hostimage_st=intent.getStringExtra("host_image");
        coverimage_st=intent.getStringExtra("cover_image");
        palace_st=intent.getStringExtra("palace");
        facilities_st=intent.getStringExtra("facilities");
        paydate_st=intent.getStringExtra("paydate");
        product_id_st=intent.getStringExtra("product_id");

        replaced_facilities=facilities_st.replaceAll("=>",",");
        Log.i("replacedd",replaced_facilities);



        host_name_des.setText(firstname_st+" "+lastname_st);
        event_description_desc.setText(description_st);
        //duration.setText(duration_st);
       // avalability.setText(availability_st);
        no_of_guest_desc.setText(minimumguest_st+" to "+maximumguest_st+""+" Nights");
       // menu_desc.setText(menu_st);
        recievingTime.setText(recievingtime_st);
      //  avalability.setText(availability_st);
        Experience_desc.setText(experience_st);
       // specialdiet.setText(specialdiet_st);
        price_perguest_desc.setText("CAD "+price_st);
        event_address_desc.setText(description_st);
       // cusine_desc.setText(cusine_st);
        Instruction.setText(instruction_st);
        event_name_desc.setText(service_title_st);
        typeofpalace.setText(palace_st);
        facilities.setText(replaced_facilities);
        paydate.setText(paydate_st);


        Glide.with(Privateevent_Description.this).load(coverimage_st)
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(event_background);

        Glide.with(Privateevent_Description.this).load(hostimage_st)
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(event_host_image);



        //  mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

       // guest_riview_array=new ArrayList<>();
        //mRecyclerView.setHasFixedSize(true);


        // The number of Columns
//        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        mAdapter = new GuestRiviewAdapter(Privateevent_Description.this,guest_riview_array);
//        mRecyclerView.setAdapter(mAdapter);

       // eventDescription();
       // guestRiview();


    }

        viewmore_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Privateevent_Description.this, ViewMoreDescription.class);
                intent1.putExtra("description_st",description_st);
                startActivity(intent1);

            }
        });

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


    public void sendBookingDetails(){

        req_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Privateevent_Description.this, GuestBooking.class);
                intent.putExtra("host_image_url",hostimage_st);
                intent.putExtra("event_price",price_st);
                intent.putExtra("event_name",service_title_st);
                intent.putExtra("host_auth_id",host_outh_id);
                intent.putExtra("service_id",service_id);
               intent.putExtra("cateogry","pay");


                startActivity(intent);
            }
        });}


    public void addWishList(){

        pDialog = new ProgressDialog(Privateevent_Description.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/add_wishlist.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("responsewishlist", response);





                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);


                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                EventListStorage eventListStorage=new EventListStorage();
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                isSucess=jsonObject.getBoolean("success");


                            }


                        }
                        catch (Exception e){
                        }
                        if((isSucess==true)) {




                            // Intent i = new Intent(GuestRegistration.this,GuestRegistration.class);
                            //  i.putExtra("send",item);
                            //startActivity(i);

                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    Privateevent_Description.this).create();


                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("Added Sucessfully in Wishlist");

                            // Setting Icon to Dialog


                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                                    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
//                                    startActivity(intent);
                                    // Write your code here to execute after dialog closed
                                    // alertDialog.dismiss();
                                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_LONG).show();

                                    // verifyEmail();
                                    // saveData();
//                                        Intent i = new Intent(GuestDe.this,GuestHomeScreen.class);
//
//                                        startActivity(i);
                                  //  finish();
                                    alertDialog.dismiss();
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();


                        }

                        else if(isSucess==false){

                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    Privateevent_Description.this).create();
                            // saveData();
                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("         Already in Wishlist ");

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
                            alertDialog.show();}


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
                params.put("product_id",product_id_st);
                params.put("product_type","Pay");



                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);

    }

    public void eventDescription(){

        pDialog = new ProgressDialog(Privateevent_Description.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/service_details.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("eventlist", response);





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
                            // event_name=jsonObject.getString("cat_title");
                            //   Log.i("event_image",event_name);


                            String last_name=jsonObject1.getString("last_name");
                            host_name=first_name+" "+last_name;


                            String host_image_url="http://myhealthyhost.com/";
                            String host_image1=jsonObject1.getString("picture");
                            hostimage_st=host_image_url+host_image1;



                            String response_data_service=jsonObject1.getString("service");
                            Log.i("responseservice",response_data_service);

                            JSONObject jsonObject2=new JSONObject(response_data_service);

                            String position=jsonObject2.getString("0");

                            JSONObject jsonObject3=new JSONObject(position);


                            price_st=jsonObject3.getString("price");
                            Log.i("event_price",price_st);
                            event_address=jsonObject3.getString("fulladdress");
                            String back_image=jsonObject3.getString("coverimg");
                            event_background_image="http://myhealthyhost.com/"+back_image;
                            Log.i("backkkimage",event_background_image);
                            String minimum_no_guest=jsonObject3.getString("min_guests");
                            String maximum_no_guest=jsonObject3.getString("max_guests");
                            no_of_guest=minimum_no_guest+" to "+maximum_no_guest+" "+"Guests";
                            cusine_name=jsonObject3.getString("cuisines");
                            specialdiet_st=jsonObject3.getString("specialdiets");
                            experience=jsonObject3.getString("exp");
                            availability_st=jsonObject3.getString("eventdate");
                            duration_st=jsonObject3.getString("time_duration");
                            recievingtime_st=jsonObject3.getString("last_time_booking");
                            menu_st=jsonObject3.getString("menu");
                            instruction_st=jsonObject3.getString("additional_info");
                            description_st=jsonObject3.getString("description");
                            service_title_st=jsonObject3.getString("tittle");
                            product_id_st=jsonObject3.getString("serviceid");




                        }
                        catch (Exception e){
                        }
                        setDEscriptionData();

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
                params.put("cat_name",cateogry_name_main);
                params.put("serviceid",service_id);
                params.put("host_id",host_outh_id);



                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);


    }
    public void setDEscriptionData(){


        host_name_des.setText(host_name);
        event_description_desc.setText(description_st);
       // duration.setText(duration_st);
      //  avalability.setText(availability_st);
        no_of_guest_desc.setText(no_of_guest);
       // menu_desc.setText(menu_st);
        recievingTime.setText(recievingtime_st);
      //  avalability.setText(availability_st);
        Experience_desc.setText(experience);
      //  specialdiet.setText(specialdiet_st);
        price_perguest_desc.setText("CAD " + price_st);
        event_address_desc.setText(event_address);
      //  cusine_desc.setText(cusine_name);
        Instruction.setText(instruction_st);
        event_name_desc.setText(service_title_st);
//
//
        Glide.with(Privateevent_Description.this).load(event_background_image)
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(event_background);

        Glide.with(Privateevent_Description.this).load(hostimage_st)
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(event_host_image);





    }

}


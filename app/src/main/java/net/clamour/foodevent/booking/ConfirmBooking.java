package net.clamour.foodevent.booking;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.clamour.foodevent.GuestHomeScreen.GuestHomeScreen;
import net.clamour.foodevent.GuestProfile.GuestLogin;
import net.clamour.foodevent.GuestProfile.GuestProfileScreen;
import net.clamour.foodevent.R;
import net.clamour.foodevent.catering.Catering_eventlist;
import net.clamour.foodevent.eventlist.EventListStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmBooking extends AppCompatActivity {

    @BindView(R.id.TotalpaidAmount)TextView totalpaid_amount;
    @BindView(R.id.event_name_booking)TextView Event_name_booking;
    @BindView(R.id.eventdate_booking)TextView eventdate_booking;
    @BindView(R.id.numberofguest_booking)TextView numberofguest_booking;

    @BindView(R.id.request_book)Button request_book;
    @BindView(R.id.booking_time)TextView booking_time;

    SharedPreferences profile_prefrence;
    ProgressDialog pDialog;
    SharedPreferences Registration_preferences;
    SharedPreferences login_prefrence;
    String user_outh_id;
    Boolean isSucess;
    Intent intent;

    String EventName,NoOfGuest,HostAuthId,ServiceId,CateogryName,event_price,EventDate,TotalFare,BookingTime,priceperguest,bookingtime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        ButterKnife.bind(this);

        profile_prefrence= this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestProfileScreen", MODE_PRIVATE);

        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        login_prefrence = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);

        user_outh_id=Registration_preferences.getString("outh_id","");
        user_outh_id=login_prefrence.getString("outh_id","");
        Log.i("outhidlogin",user_outh_id);


        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("Booking Confirmation");

        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

          intent=getIntent();
        HostAuthId=intent.getStringExtra("host_auth_id");
        Log.i("hostid",HostAuthId);
        ServiceId=intent.getStringExtra("service_id");
        Log.i("service_id",ServiceId);
        CateogryName=intent.getStringExtra("cateogry");
        Log.i("cateogryid",CateogryName);
        event_price=intent.getStringExtra("event_price");
        Log.i("event_price",event_price);
        EventDate=intent.getStringExtra("eventdate");
      //  Log.i("eventdate",EventDate);
        TotalFare=intent.getStringExtra("totalpayableamount");
        Log.i("totalfare",TotalFare);
        NoOfGuest=intent.getStringExtra("total_no_guest");
//        Log.i("noofguest",NoOfGuest);
        EventName=intent.getStringExtra("event_name");
       // Log.i("eventname",EventName);
        BookingTime=intent.getStringExtra("bookingtime");
        Log.i("bookingtime",BookingTime);
        priceperguest=intent.getStringExtra("priceperguest");
        Log.i("priceperguest",priceperguest);






        totalpaid_amount.setText(TotalFare);
        Event_name_booking.setText(EventName);
        eventdate_booking.setText(EventDate);
        numberofguest_booking.setText(NoOfGuest);
        if(BookingTime.contains("Select time")){
        booking_time.setText("");}

        else {
            booking_time.setText(BookingTime);
        }

        request_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(EventDate.length()==0){
//                    Toast.makeText(getApplicationContext(),"please select date",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                Intent intent=new Intent(ConfirmBooking.this,StripeGateway.class);
//                startActivity(intent);}
            }
        });



        request_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  if(intent.getStringExtra("eventdate")==null){

                    Toast.makeText(getApplicationContext(),"please select date",Toast.LENGTH_SHORT).show();
                }
                else if(BookingTime.contains("Select time")||intent.getStringExtra("bookingtime")==null){

                      Toast.makeText(getApplicationContext(),"please select time",Toast.LENGTH_SHORT).show();

                  }

                else if (profile_prefrence.contains("first_name_up")&&profile_prefrence.contains("last_name_up")&&profile_prefrence.contains("gender_up")&&profile_prefrence.contains("agegroup_up")&&
                        profile_prefrence.contains("role_up")&&profile_prefrence.contains("phonenumber_up")&&profile_prefrence.contains("fulladdress_up")&&profile_prefrence.contains("unitno_up")&&
                        profile_prefrence.contains("city_up")&&profile_prefrence.contains("state_up")&&profile_prefrence.contains("zip_up")&&profile_prefrence.contains("country_up")){

                    //ConfirmBookingMethod();
                    Intent intent=new Intent(ConfirmBooking.this,StripeGateway.class);
                    intent.putExtra("host_auth_id",HostAuthId);
                    intent.putExtra("service_id",ServiceId);
                    intent.putExtra("cateogry",CateogryName);
                    intent.putExtra("event_price",event_price);
                    intent.putExtra("event_name",EventName);
                    intent.putExtra("total_no_guest",NoOfGuest);
                    intent.putExtra("eventdate",EventDate);
                    intent.putExtra("totalpayableamount",TotalFare);
                    intent.putExtra("bookingtime",BookingTime);
                    intent.putExtra("priceperguest",priceperguest);

                    startActivity(intent);
                }


                else {

                    Intent intent=new Intent(ConfirmBooking.this, GuestProfileScreen.class);
                    intent.putExtra("bookingprofile","bookingprofile");
                    startActivity(intent);
                }

//                Intent intent=new Intent(ConfirmBooking.this, ThankyouPage.class);
//                    startActivity(intent);
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
}

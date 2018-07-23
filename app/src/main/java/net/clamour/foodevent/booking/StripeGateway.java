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
import android.widget.Toast;

import net.clamour.foodevent.GuestHomeScreen.GuestHomeScreen;
import net.clamour.foodevent.R;
import net.clamour.foodevent.eventlist.EventListStorage;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.Stripe;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class StripeGateway extends AppCompatActivity {


    Button pay;
    String isSucess;
    String EventName,NoOfGuest,HostAuthId,ServiceId,CateogryName,event_price,EventDate,TotalFare,TotalFare1,user_outh_id,bookingtime;
    SharedPreferences profile_prefrence;
    ProgressDialog pDialog;
    SharedPreferences Registration_preferences;
    SharedPreferences login_prefrence;
    CardMultilineWidget mCardInputWidget;
    String token_st,order_no,payment_id,hostedby,title,booking_date,total_price,priceperguest_st,noofguest,bookingtime_st;
    Token token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_gateway);

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("Enter Card Details");

        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        profile_prefrence= this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestProfileScreen", MODE_PRIVATE);

        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        login_prefrence = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);

        user_outh_id=Registration_preferences.getString("outh_id","");
        user_outh_id=login_prefrence.getString("outh_id","");
        Log.i("outhidlogin",user_outh_id);

        final Intent intent=getIntent();
        HostAuthId=intent.getStringExtra("host_auth_id");
        Log.i("hostid",HostAuthId);
        ServiceId=intent.getStringExtra("service_id");
        Log.i("service_id",ServiceId);
        CateogryName=intent.getStringExtra("cateogry");
        Log.i("cateogryid",CateogryName);
        event_price=intent.getStringExtra("event_price");
         Log.i("event_price",event_price);
        EventDate=intent.getStringExtra("eventdate");
       // Log.i("eventdate",EventDate);
        TotalFare=intent.getStringExtra("totalpayableamount");
         Log.i("totalfare",TotalFare);
          TotalFare1=TotalFare.replace("CAD","");
        Log.i("totalfare11",TotalFare1);
        NoOfGuest=intent.getStringExtra("total_no_guest");
        Log.i("noofguest",NoOfGuest);
        EventName=intent.getStringExtra("event_name");
        bookingtime=intent.getStringExtra("bookingtime");
        priceperguest_st=intent.getStringExtra("priceperguest");
        Log.i("priceperguest",priceperguest_st);




       mCardInputWidget = (CardMultilineWidget) findViewById(R.id.card_multiline_widget);


        pay=(Button)findViewById(R.id.save_payment);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if (!mCardInputWidget.validateAllFields()){

    Toast.makeText(getApplicationContext(),"Please enter the valid details",Toast.LENGTH_LONG).show();

}
else {
                getCardData();}

            }
        });


//        Card card = new Card("4242424242424242", 12, 2019, "123");
//      //  Card card = mCardInputWidget.getCard();
//        if (card == null) {
//           // mErrorDialogHandler.showError("Invalid Card Data");
//        }
////        else if(card!=null){
////
////            String cvv= mCardInputWidget.getCard().getCVC();
////            int exp= mCardInputWidget.getCard().getExpMonth();
////            int exp_year= mCardInputWidget.getCard().getExpYear();
////            String card_num= mCardInputWidget.getCard().getNumber();
////
////            card = new Card(card_num, exp, exp_year, cvv);
////        }
//
//
////stripe.createToken(
////        card,
////            new TokenCallback() {
////        public void onSuccess(Token token) {
////            // Send token to your server
////            Log.i("tokeennnnn",token.toString());
////            Log.i("inserteeeeedddd","inserteddddddd");
////        }
////    public void onError(Exception error) {
////        // Show localized error message
//////        Toast.makeText(getApplicationContext(),
//////                error.getLocalizedString(StripeGateway.this),
//////                Toast.LENGTH_LONG
//////        ).show();
////    }
////}
////);
//



    }

    public void getCardData(){
        pDialog = new ProgressDialog(StripeGateway.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        pDialog.show();
        Stripe stripe = new Stripe(StripeGateway.this, "pk_live_lYeNlrfGu5u4iZqV61LtTKZv");

        stripe.createToken(
                new Card(mCardInputWidget.getCard().getNumber(),mCardInputWidget.getCard().getExpMonth(), mCardInputWidget.getCard().getExpYear(), mCardInputWidget.getCard().getCVC()),
                new TokenCallback() {
                    public void onSuccess(final Token token) {
                        // Send token to your own web service
                        //  MyServer.chargeToken(token);
                        Log.i("tokennn", token.getId());


                        if(token.getId()==null){
                        }
else {


                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/booking.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                                        Log.i("bookingresponse", response);





                                        if (pDialog.isShowing())
                                            pDialog.dismiss();
                                        Log.e("response=",response);


                                        try {


                                                JSONObject jsonObject=new JSONObject(response);
                                                isSucess=jsonObject.getString("success");
                                                Log.i("sucess",isSucess);
                                                payment_id=jsonObject.getString("payment_id");
                                                Log.i("payid",payment_id);
                                                order_no=jsonObject.getString("ordernumber");
                                                Log.i("order_no",order_no);
                                                String firstname=jsonObject.getString("fname");
                                                String lastname=jsonObject.getString("lname");
                                                hostedby=firstname+" "+lastname;
                                                total_price=jsonObject.getString("totalprice");
                                                priceperguest_st=jsonObject.getString("priceperguest");
                                                title=jsonObject.getString("description");
                                                booking_date=jsonObject.getString("bookingdate");
                                                noofguest=jsonObject.getString("noofguest");
                                                bookingtime_st=jsonObject.getString("bookingtime");


                                        }

                                        catch (Exception e){
                                        }
                                        if((isSucess.contains("success"))) {



                                            // Intent i = new Intent(GuestRegistration.this,GuestRegistration.class);
                                            //  i.putExtra("send",item);
                                            //startActivity(i);

                                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                                    StripeGateway.this).create();

                                            // Setting Dialog Title
                                            alertDialog.setTitle("                 Alert!");

                                            // Setting Dialog Message
                                            alertDialog.setMessage("       Booked Successfull");

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
                                                    Intent intent=new Intent(StripeGateway.this,ThankyouPage.class);
                                                    intent.putExtra("order_no",order_no);
                                                    intent.putExtra("paymentid",payment_id);
                                                    intent.putExtra("totalFare",total_price);
                                                    intent.putExtra("noofguest",noofguest);
                                                    intent.putExtra("priceperguest",priceperguest_st);
                                                    intent.putExtra("bookingdate",booking_date);
                                                    intent.putExtra("title",title);
                                                    intent.putExtra("hostedby",hostedby);
                                                    intent.putExtra("bookingtime",bookingtime_st);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });

                                            // Showing Alert Message
                                            alertDialog.show();


                                        }
                                        else if(isSucess.contains("declined")){

                                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                                    StripeGateway.this).create();

                                            // Setting Dialog Title
                                            alertDialog.setTitle("                 Alert!");

                                            // Setting Dialog Message
                                            alertDialog.setMessage("         Booking Failed ");

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
                                                    Intent intent=new Intent(StripeGateway.this,GuestHomeScreen.class);
                                                    startActivity(intent);
                                                }
                                            });

                                            // Showing Alert Message
                                            alertDialog.show();

                                        }
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
                                params.put("hostid",HostAuthId);
                                params.put("description",EventName);
                                params.put("serviceid",ServiceId);
                                params.put("noofguest",NoOfGuest);
                                params.put("bookingdate",EventDate);
                                params.put("servicetype",CateogryName);
                                params.put("user_id",user_outh_id);
                                params.put("token",token.getId());
                                params.put("currency","usd");
                                params.put("amount",TotalFare1);
                                params.put("bookingtime",bookingtime);
                                params.put("priceperguest",priceperguest_st);

                                return params;
                            }

                        };

                        RequestQueue requestQueue1 = Volley.newRequestQueue(StripeGateway.this);
                        requestQueue1.add(stringRequest1);



                    }}

                    public void onError(Exception error) {
//                        Toast.makeText(getContext(),
//                                error.getLocalizedMessage(),
//                                Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        Log.i("error", error.getLocalizedMessage());
                    }
                }
        );

    }


    public void ConfirmBooking(){




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

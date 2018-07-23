package net.clamour.foodevent.GuestHomeScreen;

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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.clamour.foodevent.GuestProfile.GuestLogin;
import net.clamour.foodevent.R;
import net.clamour.foodevent.eventlist.EventListStorage;
import net.clamour.foodevent.sidebar.MyHealthyExperience;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuestRiview extends AppCompatActivity {
    @BindView(R.id.ratingBar)RatingBar ratingBar;
    @BindView(R.id.rating_comment)EditText rating_comment;
    @BindView(R.id.button_rating)Button submit_rating;
    String rating_comment_str;
    ProgressDialog pDialog;

    String ratedValue;
    SharedPreferences Registration_preferences;
    SharedPreferences login_prefrence;
    String user_outh_id,host_id;
    Boolean isSucess;
    int rated_value_star;
    int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_riview);

        ButterKnife.bind(this);

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("Add Review");


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

        Intent intent=getIntent();
        host_id=intent.getStringExtra("hostid");
        Log.i("host_id",host_id);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar rtBar, float rating,boolean fromUser) {
                rated_value_star = (int) rating;
                ///Toast.makeText(GuestRiview.this, "Rating:"+String.valueOf(rated_value_star), Toast.LENGTH_LONG).show();
            }
        });


//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating,
//                                        boolean fromUser) {
//                ratedValue = String.valueOf(ratingBar.getRating());
//                Log.i("rated_value",ratedValue);
//                rated_value_star=Integer.valueOf(ratedValue);
//                Log.i("rated_value_star",rated_value_star+"");
//
////                rateMessage.setText("You have rated the Product : "
////                        + ratedValue + "/5.");
//
//               // Toast.makeText(getApplicationContext(),"You have rated the Product : "
//                       // + ratedValue,Toast.LENGTH_SHORT).show();
//            }
//        });

        submit_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(GuestRiview.this, MyHealthyExperience.class);
                //startActivity(intent);

                addRiview();
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

    public void addRiview(){
        pDialog = new ProgressDialog(GuestRiview.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);
        rating_comment_str=rating_comment.getText().toString();


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/add_reviews.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("eventriview", response);


                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);


                        try {
JSONObject jsonObject=new JSONObject(response);
isSucess=jsonObject.getBoolean("status");
//

                        }
                        catch (Exception e){
                        }
                      //  eventListAdapter.notifyDataSetChanged();
                        if((isSucess==true)) {





                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    GuestRiview.this).create();

                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("your review has been successfully submitted");

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
                                    Intent i = new Intent(GuestRiview.this,MyHealthyExperience.class);

                                    startActivity(i);
                                    finish();
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
                params.put("hostid",host_id);
                params.put("user_id",user_outh_id);
                params.put("message",rating_comment_str);
                params.put("rating",rated_value_star+"");



                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);




    }
}

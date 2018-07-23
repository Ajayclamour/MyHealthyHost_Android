package net.clamour.foodevent.GuestProfile;

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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import net.clamour.foodevent.GuestHomeScreen.GuestHomeScreen;
import net.clamour.foodevent.HostProfile.HostHomeScreen;
import net.clamour.foodevent.R;
import net.clamour.foodevent.hostsidebar.HostProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Submit_Code_Screen extends AppCompatActivity {
    @BindView(R.id.submit_code)Button submit_code;
    @BindView(R.id.edt_emailcode)EditText edt_emailcode;
    String email_st,user_code;
    ProgressDialog pDialog;
    Boolean isSucess;
    String oth_id,user_mode,error_code,email_get,firstname_get,lastname_get;
    SharedPreferences Registration_preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit__code__screen);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);


        ButterKnife.bind(this);

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("SUBMIT SCREEN");


        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        Intent intent=getIntent();
        email_st=intent.getStringExtra("email_register");
        Log.i("email_register",email_st);


        submit_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent=new Intent(OtpScreen.this, MapsActivity.class);
                //startActivity(intent);

                if(edt_emailcode.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Please Enter the Code", Toast.LENGTH_SHORT).show();

                }
                else {

                    verifyOtp();
                }

            }
        });

    }

    public void verifyOtp(){

        pDialog = new ProgressDialog(Submit_Code_Screen.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        user_code=edt_emailcode.getText().toString();


         pDialog.show();

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/confirm_email.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("responseotp", response);



                        //   arrayList=new ArrayList<>();

                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);


                        try {



                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                isSucess = jsonObject.getBoolean("success");
                                firstname_get=jsonObject.getString("first_name");
                                lastname_get=jsonObject.getString("last_name");
                                oth_id=jsonObject.getString("oauth_uid");
                                email_get=jsonObject.getString("email");
                                user_mode=jsonObject.getString("usertype");
                                error_code=jsonObject.getString("errorcode");
                                Log.i("errorcode",error_code);

                            }


                        }
                        catch (Exception e){
                        }
                        if((isSucess==true)&&(user_mode.contains("Guest"))) {


                            saveData();
                            // Intent i = new Intent(GuestRegistration.this,GuestRegistration.class);
                            //  i.putExtra("send",item);
                            //startActivity(i);

                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    Submit_Code_Screen.this).create();

                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("Successfully Registered");

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
                                    Intent i = new Intent(Submit_Code_Screen.this,GuestHomeScreen.class);

                                    startActivity(i);
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();


                        }
                        else if((isSucess==true)&&(user_mode.contains("Host"))){
                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    Submit_Code_Screen.this).create();
                            saveData();
                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("    Host Sucessfully Registered");

                            // Setting Icon to Dialog


                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                                    emailIntent.setType("text/plain");
//                                    startActivity(emailIntent);
                                    // Write your code here to execute after dialog closed
                                    // alertDialog.dismiss();
                                    Intent i = new Intent(Submit_Code_Screen.this,HostProfile.class);

                                    startActivity(i);


                                    Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_LONG).show();
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();}
                        else if(isSucess==false){

                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    Submit_Code_Screen.this).create();
                            // saveData();
                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("    Incorrect Verification Code");

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
                params.put("email",email_st);
                params.put("confirmcode",user_code);

                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);

    }
    public void saveData(){
        SharedPreferences.Editor editor=Registration_preferences.edit();
        editor.putString("outh_id",oth_id);
        editor.putString("first_name",firstname_get);
        editor.putString("last_name",lastname_get);
        editor.putString("user_mode",user_mode);
        editor.putString("email",email_get);
        editor.commit();

    }
}

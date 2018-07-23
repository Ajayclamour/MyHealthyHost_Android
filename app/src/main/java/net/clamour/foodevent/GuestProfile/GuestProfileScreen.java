package net.clamour.foodevent.GuestProfile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hbb20.CountryCodePicker;

import net.clamour.foodevent.GuestHomeScreen.GuestHomeScreen;
import net.clamour.foodevent.R;
import net.clamour.foodevent.booking.ConfirmBooking;
import net.clamour.foodevent.sidebar.TermsConditions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuestProfileScreen extends AppCompatActivity {

    @BindView(R.id.capture_button)Button caputre_gallery;
    @BindView(R.id.person_profile_image)ImageView profile_imagel;
    @BindView(R.id.age_grp)Spinner agegrp_spinner;
    @BindView(R.id.gender_spinner)Spinner gender_spinner;
    @BindView(R.id.service_spinner)Spinner service_spinner;
    @BindView(R.id.country_spinnerguest)Spinner Country_spinner;
    @BindView(R.id.state_spinnerguest)Spinner State_spinner;
    @BindView(R.id.termstext)TextView termscondition;

    @BindView(R.id.firstname_profile)EditText First_name;
    @BindView(R.id.lastname_profile)EditText Last_name;
    @BindView(R.id.email_host)EditText email_guest;
    @BindView(R.id.alternate_emailhost)EditText alternate_email;
    @BindView(R.id.phone_numberhost)EditText phone_no_guest;
    @BindView(R.id.alternate_phonehost)EditText alternate_phoneguest;

    @BindView(R.id.unitno_guest)EditText unitno_guest;
    @BindView(R.id.streetaddress_guest)EditText streetaddress_guest;
    //   @BindView(R.id.streetno_guest)EditText streetno_guest;
    @BindView(R.id.city_guest)EditText city_guest;
    //  @BindView(R.id.department_host)EditText department_guest;
    @BindView(R.id.zip_guest)EditText zip_guest;
    //  @BindView(R.id.new_password)EditText new_password;
    // @BindView(R.id.repeat_password)EditText repeat_password;


    @BindView(R.id.save_profile_host)Button saveprofile_guest;

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";

    SharedPreferences profile_prefrence;
    Bitmap bitmap;


    String gender[] = {"Male","Female","Others"};
    String agegroup[]={"18-25","25-30","30-40","40-50","50-above"};
    String service[]={"Traveller","Student/Professional","Local"};

    ArrayList<String>Country_array;
    ArrayList<String>State_array;
    ArrayList<CountryDataStorage>countryarray_modal;
    ArrayList<CountryDataStorage>statearray_modal;
    String country_name,country_id,state_name,state_id;

    Uri selectedImageUri;
    String firstname_up,lastname_up,gender_up,agegroup_up,role_up,email_up,alternateemail_up,phonenumber_up,alternatephone_up,streetaddress_up,unitno_up,streetnumber_up,city_up,state_up,zip_up,country_up,usertype_up,password_up,guest_profileimage_up;
    ProgressDialog pDialog;
    String  firstname_st,lastname_st,email_st,alternate_email_st,phoneno_st,alternatephone_st,streetaddress_st,streetnumber_st,street_st,city_st,unitno_st,zip_st;
    Boolean isSucess;

    SharedPreferences login_prefrence;
    SharedPreferences Registration_preferences;

    String auth_id,first_name,last_name,complete_name,email,user_type;
    String selected_gender,selected_role,selected_agegroup;

    String path;
    String user_type_prefrence,profilepage;
    Intent bookingintent;
    CountryCodePicker ccp,ccp_alternate;
    String countrycode,alternatephone_code;
    String firstname_set,last_name_set,gender_set,age_group_set,role_set,alternate_emailset,phoneno_set,alternatephone_set,fulladdress_set,number_set,Street_set,city_set,department_set,state_set,Zip_set,country_set,password_set,guest_profileimage_set,completeguestimage_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_profile_screen);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN

        );

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("My Profile");


        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);

        bookingintent=getIntent();
          profilepage=bookingintent.getStringExtra("bookingprofile");

        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        login_prefrence = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);

        profile_prefrence= this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestProfileScreen", MODE_PRIVATE);

        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp_alternate=(CountryCodePicker)findViewById(R.id.ccp_alternate);
        countrycode=ccp.getSelectedCountryCode();
        ccp.setCountryForNameCode("CA");
        alternatephone_code=ccp_alternate.getSelectedCountryCode();
        Log.i("codeee",ccp.getSelectedCountryCode()+""+ccp.getDefaultCountryCodeWithPlus());
        ccp_alternate.setCountryForNameCode("CA");




        if(Registration_preferences.contains("outh_id")){


            auth_id=Registration_preferences.getString("outh_id","");
            Log.i("auth_id_register",auth_id);
            saveUpdatedProfileData();

        }
        else if(login_prefrence.contains("outh_id")){
            auth_id = login_prefrence.getString("outh_id", "");
            Log.i("auth_id_login",auth_id);
            saveUpdatedProfileData();
        }

       // Log.i("authid_login", auth_id);
        user_type_prefrence=Registration_preferences.getString("user_mode","");
        Log.i("Registrationpref", user_type_prefrence);

        user_type_prefrence=login_prefrence.getString("user_mode","");
        Log.i("Loginpref", user_type_prefrence);






//        if(Registration_preferences.contains("user_mode")&&Registration_preferences.contains("outh_id")&&Registration_preferences.contains("first_name")&&Registration_preferences.contains("last_name")&&Registration_preferences.contains("email")&&user_type_prefrence.contains("Guest")){
//            auth_id=Registration_preferences.getString("outh_id","");
//            Log.i("auth_id_register",auth_id);
//
//
//            user_type=Registration_preferences.getString("user_mode","");
//            Log.i("usertype_register",user_type);
//
//            first_name=Registration_preferences.getString("first_name","");
//            last_name=Registration_preferences.getString("last_name","");
//            email=Registration_preferences.getString("email","");
//
//            First_name.setText(first_name);
//            Last_name.setText(last_name);
//            email_guest.setText(email);
//
//            complete_name=first_name+""+last_name;
//            Log.i("complete_name",complete_name);
//
//            saveUpdatedProfileData();
//        }
//        else if(login_prefrence.contains("user_mode")&&login_prefrence.contains("outh_id")&&login_prefrence.contains("first_name")&&login_prefrence.contains("last_name")&&login_prefrence.contains("email")&&user_type_prefrence.contains("Guest")) {
//
//            user_type = login_prefrence.getString("user_mode", "");
//            Log.i("usertype_login", user_type);
//
//            first_name = login_prefrence.getString("first_name", "");
//            Log.i("firstname_login",first_name);
//            last_name = login_prefrence.getString("last_name", "");
//            email = login_prefrence.getString("email", "");
//
//            First_name.setText(first_name);
//            Last_name.setText(last_name);
//            email_guest.setText(email);
//
//            complete_name = first_name + "" + last_name;
//            Log.i("complete_name", complete_name);
//
//            saveUpdatedProfileData();
//
//
//        }
//data set after updation

//        firstname_set=profile_prefrence.getString("first_name_up","");
//        Log.i("firstnamesettttt",firstname_set);
//        last_name_set=profile_prefrence.getString("last_name_up","");
//        Log.i("lastnameset",last_name_set);
//        gender_set=profile_prefrence.getString("gender_up","");
//        Log.i("genderset",gender_set);
//        age_group_set=profile_prefrence.getString("agegroup_up","");
//        Log.i("agerp",age_group_set);
//        role_set=profile_prefrence.getString("role_up","");
//        Log.i("roleset",role_set);
//        alternate_emailset=profile_prefrence.getString("alternateemail_up","");
//        Log.i("alternateemailset",alternate_emailset);
//        phoneno_set=profile_prefrence.getString("phonenumber_up","");
//        Log.i("phonenoset",phoneno_set);
//        alternatephone_set=profile_prefrence.getString("alternatephone_up","");
//        Log.i("alternatephoneset",alternatephone_set);
//        fulladdress_set=profile_prefrence.getString("fulladdress_up","");
//        Log.i("fulladdressset",fulladdress_set);
//        number_set=profile_prefrence.getString("number_up","");
//        Log.i("numberset",number_set);
//        Street_set=profile_prefrence.getString("street_up","");
//        Log.i("streetset",Street_set);
//        city_set=profile_prefrence.getString("city_up","");
//        Log.i("cityset",city_set);
//        department_set=profile_prefrence.getString("department_up","");
//        Log.i("departmentset",department_set);
//        state_set=profile_prefrence.getString("state_up","");
//        Log.i("stateset",state_set);
//        Zip_set=profile_prefrence.getString("zip_up","");
//        Log.i("zipset",Zip_set);
//        country_set=profile_prefrence.getString("country_up","");
//        Log.i("countryset",country_set);
//        password_set=profile_prefrence.getString("password","");
//        Log.i("passwordset",password_set);
        guest_profileimage_set=profile_prefrence.getString("guset_profileimage","");
        Log.i("guestprofileset",guest_profileimage_set);

        Glide.with(GuestProfileScreen.this).load(guest_profileimage_set)
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile_imagel);


        final ArrayAdapter<String> gender_adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, gender);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(gender_adapter);

        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selected_gender=gender_spinner.getSelectedItem().toString();
                Log.i("selected_gender",selected_gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> service_adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, service);
        service_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        service_spinner.setAdapter(service_adapter);

        service_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selected_role=service_spinner.getSelectedItem().toString();
                Log.i("selectedrole",selected_role);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> agegroup_adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, agegroup);
        agegroup_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agegrp_spinner.setAdapter(agegroup_adapter);

        agegrp_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_agegroup=agegrp_spinner.getSelectedItem().toString();
                Log.i("selectedage",selected_agegroup);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        caputre_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // loadImagebyGallery();

                selectImage();
            }
        });

        termscondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuestProfileScreen.this,TermsConditions.class);
                startActivity(intent);

            }
        });

        saveprofile_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveGuestProfile();
            }
        });

        Country_array=new ArrayList<>();
        countryarray_modal=new ArrayList<>();
        State_array=new ArrayList<>();
        statearray_modal=new ArrayList<>();

        ShowCountryData();


    }

    public void selectImage(){

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



        AlertDialog.Builder builder = new AlertDialog.Builder(GuestProfileScreen.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {

                   Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1 );
                }

                else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);



                }

                else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();


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
    public void loadImagebyGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Bundle extras = data.getExtras();
                 bitmap = (Bitmap) extras.get("data");

                profile_imagel.setImageBitmap(bitmap);

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                Toast.makeText(GuestProfileScreen.this,"Here "+ getRealPathFromURI(tempUri),Toast.LENGTH_LONG).show();
            }


            else if (requestCode == 2) {


                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    // profile_imagel.setImageURI(selectedImageUri);

                    try {
                        //getting bitmap object from uri
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                        // profile_prefrence.edit().remove("guset_profileimage").apply();

                        //displaying selected image to imageview
                        profile_imagel.setImageBitmap(bitmap);

                        //calling the method uploadBitmap to upload image

                    } catch (IOException e) {
                        e.printStackTrace();
                    }}}


        }}





    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



    public void saveGuestProfile(){
        pDialog = new ProgressDialog(GuestProfileScreen.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);


        firstname_st=First_name.getText().toString();
        lastname_st=Last_name.getText().toString();
        alternate_email_st=alternate_email.getText().toString();
        phoneno_st=phone_no_guest.getText().toString();
        alternatephone_st=alternate_phoneguest.getText().toString();
        unitno_st=unitno_guest.getText().toString();
        Log.i("unitno",unitno_st);
        streetaddress_st=streetaddress_guest.getText().toString();
        //  streetnumber_st=streetno_guest.getText().toString();
        city_st=city_guest.getText().toString();
        zip_st=zip_guest.getText().toString();

        // fulladdress_st=fulladdress_guest.getText().toString();
        //  number_st=number_guest.getText().toString();
        //  street_st=street_guest.getText().toString();
        //  city_st=city_guest.getText().toString();
        //  department_st=department_guest.getText().toString();
        //   Log.i("department_gueststring",department_st);

        // zip_st=zip_guest.getText().toString();
        // password_st=new_password.getText().toString();
        // new_password_st=repeat_password.getText().toString();

        pDialog.show();

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,"http://myhealthyhost.com/api/edit_profile.php", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Log.i("responsereprofilesaveedittt", resultResponse);
                try {
                    JSONArray jsonArray = new JSONArray(resultResponse);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        isSucess=jsonObject.getBoolean("success");
                        Log.i("issucesssss",isSucess.toString());
                        //   String picture=jsonObject.getString("picture");
                        // Log.i("picture",picture);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if((isSucess==true)) {



                    saveUpdatedProfileData();

                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            GuestProfileScreen.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("                 Alert!");

                    // Setting Dialog Message
                    alertDialog.setMessage("profile is updated successfully");

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
                            // saveUpdatedProfileData();

//                            Intent i = new Intent(GuestProfileScreen.this, GuestProfileScreen.class);
//
//                            startActivity(i);
//                           if (profilepage.contains("bookingprofile")){
//
                              Intent i = new Intent(GuestProfileScreen.this, GuestHomeScreen.class);
                               startActivity(i);
//                           }
                         //  else {


                            alertDialog.cancel();}
                      //  }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                }}


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("oauth_uid",auth_id);
                params.put("usertype","Guest");
                params.put("first_name",firstname_st);
                params.put("last_name",lastname_st);
                params.put("old_picture",guest_profileimage_up);
                params.put("gender",selected_gender);
                params.put("email_alternate",alternate_email_st);
                params.put("phone",phoneno_st);
                params.put("phone_alternate",alternatephone_st);
                params.put("address",streetaddress_st);
                //  params.put("number",streetnumber_st);
                params.put("streetno",unitno_st);
                params.put("city",city_st);
                params.put("state",state_id);
                params.put("zip",zip_st);
                params.put("country",country_id);
                params.put("role",selected_role);
                params.put("agegroup",selected_agegroup);
                params.put("phone_code",countrycode);
                params.put("alternate_phone_code",alternatephone_code);
              //  params.put("")
                // params.put("usertype",user_type);

                //  params.put("password",password_st);
                // params.put("username_host","");
                // params.put("service_host","");
                // params.put("dob_host","");

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                //params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                if (bitmap!=null){
                    params.put("picture",new DataPart(path,getFileDataFromDrawable(bitmap)));
                    Log.i("have","have");
                }
                else if(bitmap==null) {
                    //  params.put("picture",new DataPart("profileImage.png",getFileDataFromDrawable(bitmap)));
                    Log.i("Don'thave","Don'thave");
                }


                // params.put("old_picture",new DataPart("profileImage.png",getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(multipartRequest);
    }








    public void showStateData(){

        pDialog = new ProgressDialog(GuestProfileScreen.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        Log.i("instatedata","instatedata");

        // pDialog.show();

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/state_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("responsestatelist", response);



                        //   arrayList=new ArrayList<>();

                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);
//                        Intent intent=new Intent(GuestRegistration.this,OtpScreen.class);
//                        intent.putExtra("mobile_no",mobile_no_st);
//                        startActivity(intent);


                        try {



                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                CountryDataStorage countryDataStorage=new CountryDataStorage();

                                state_name=jsonObject.getString("name");
                                Log.i("state_name",state_name);
                                state_id=jsonObject.getString("id");
                                countryDataStorage.setState_id(jsonObject.getString("id"));
                                State_array.add(state_name);
                                statearray_modal.add(countryDataStorage);




                            }


                        }
                        catch (Exception e){
                        }

                        setStateSpinner();



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
                params.put("country_id",country_id);



                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);


    }
    public void ShowCountryData(){
        pDialog = new ProgressDialog(GuestProfileScreen.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);



        // pDialog.show();

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/country_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("responseCountry", response);

                        //   arrayList=new ArrayList<>();

                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);
//                        Intent intent=new Intent(GuestRegistration.this,OtpScreen.class);
//                        intent.putExtra("mobile_no",mobile_no_st);
//                        startActivity(intent);


                        try {



                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                CountryDataStorage countryDataStorage=new CountryDataStorage();
                                country_name=jsonObject.getString("name");
                                country_id=jsonObject.getString("id");
                                countryDataStorage.setCountry_name(jsonObject.getString("name"));
                                countryDataStorage.setCountry_id(jsonObject.getString("id"));
                                Country_array.add(country_name);
                                countryarray_modal.add(countryDataStorage);




                            }


                        }

                        catch (Exception e){
                        }

                        setcountrySpinner();





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



                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);

    }
    public void setcountrySpinner(){
        Country_spinner.setAdapter(new ArrayAdapter<String>(GuestProfileScreen.this, android.R.layout.simple_spinner_dropdown_item, Country_array));


        Country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                country_id=countryarray_modal.get(position).getCountry_id();
                Log.i("country_id",country_id);
                State_array.clear();
                showStateData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setStateSpinner(){
        Log.i("setspinner","setspinner");
//        State_spinner.notify();

        State_spinner.setAdapter(new ArrayAdapter<String>(GuestProfileScreen.this, android.R.layout.simple_spinner_dropdown_item, State_array));

        State_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                state_id=statearray_modal.get(position).getState_id();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        First_name.setText(firstname_set);
//        Last_name.setText(last_name_set);
//        alternate_email.setText(alternate_emailset);
//        phone_no_guest.setText(phoneno_set);
//        alternate_phoneguest.setText(alternatephone_set);
//     //   fulladdress_guest.setText(fulladdress_set);
//      //  number_guest.setText(number_set);
//      //  street_guest.setText(Street_set);
//      //  city_guest.setText(city_set);
//      //  department_guest.setText(department_set);
//        zip_guest.setText(Zip_set);
//        //new_password.setText(password_set);
//        // repeat_password.setText(password_set);




    }

    public void saveUpdatedProfileData(){
      //  pDialog = new ProgressDialog(GuestProfileScreen.this);
      //  pDialog.setMessage("Please wait...");
       // pDialog.setCancelable(true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/profile_details.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("responseupdatedprofile", response);





                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);


                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                firstname_up=jsonObject.getString("first_name");
                                Log.i("first_nameupdated",firstname_up);
                                lastname_up=jsonObject.getString("last_name");
                                gender_up=jsonObject.getString("gender");
                                agegroup_up=jsonObject.getString("agegroup");
                                Log.i("agegroupuppp",agegroup_up);
                                role_up=jsonObject.getString("role");
                                alternateemail_up=jsonObject.getString("email_alternate");
                                Log.i("alternateemailupp",alternateemail_up);
                                phonenumber_up=jsonObject.getString("phone");
                                alternatephone_up=jsonObject.getString("phone_alternate");
                                streetaddress_up=jsonObject.getString("address");
                                Log.i("streetaddress",streetaddress_up);
                                //  streetnumber_up=jsonObject.getString("streetno");
                                unitno_up=jsonObject.getString("streetno");
                                Log.i("unitnoo",unitno_up);
                                city_up=jsonObject.getString("city");
                                email_up=jsonObject.getString("email");
                                // department_up=jsonObject.getString("Dipartment");
                                Log.i("emaillllllll",email_up);
                                state_up=jsonObject.getString("state");
                                zip_up=jsonObject.getString("zipcode");
                                country_up=jsonObject.getString("country");
                                usertype_up=jsonObject.getString("usertype");
                                //password_up=jsonObject.getString("password");
                                guest_profileimage_up=jsonObject.getString("picture");
                                Log.i("guset_profileupp",guest_profileimage_up);


                                completeguestimage_url="http://myhealthyhost.com/"+guest_profileimage_up;
                                Log.i("gusetimage",completeguestimage_url);


                            }


                        }
                        catch (Exception e){
                        }
                        setData();
                        saveUpdatedData();
                        // setUpdatedData();

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
                params.put("oauth_uid",auth_id);




                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);


    }

    public void saveUpdatedData(){

        Log.i("saveupdatedddd","saveupdated");
        SharedPreferences.Editor editor=profile_prefrence.edit();


        editor.putString("usertype_up",usertype_up);
        editor.putString("first_name_up",firstname_up);
        editor.putString("last_name_up",lastname_up);
        editor.putString("gender_up",gender_up);
        editor.putString("agegroup_up",agegroup_up);
        editor.putString("role_up",role_up);
        editor.putString("alternateemail_up",alternateemail_up);
        editor.putString("phonenumber_up",phonenumber_up);
        editor.putString("alternatephone_up",alternatephone_up);
        editor.putString("fulladdress_up",streetaddress_up);
        //editor.putString("number_up",streetnumber_up);
        editor.putString("unitno_up",unitno_up);
        editor.putString("city_up",city_up);
        editor.putString("state_up",state_up);
        editor.putString("zip_up",zip_up);
        editor.putString("country_up",country_up);
        // editor.putString("password_up",password_up);
        editor.putString("guset_profileimage",completeguestimage_url);
        editor.commit();

    }
    public void setData(){

        First_name.setText(firstname_up);
        Last_name.setText(lastname_up);
        alternate_email.setText(alternateemail_up);
        phone_no_guest.setText(phonenumber_up);
        alternate_phoneguest.setText(alternatephone_up);
        unitno_guest.setText(unitno_up);
        streetaddress_guest.setText(streetaddress_up);
//streetno_guest.setText(streetnumber_up);
        city_guest.setText(city_up);
        zip_guest.setText(zip_up);
        email_guest.setText(email_up);

//        Glide.with(GuestProfileScreen.this).load(completeguestimage_url)
//                .thumbnail(0.5f)
//                .crossFade()
//                .placeholder(0)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(profile_imagel);


    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}

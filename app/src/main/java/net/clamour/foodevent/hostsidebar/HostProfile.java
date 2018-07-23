package net.clamour.foodevent.hostsidebar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.icu.util.Output;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import net.clamour.foodevent.GuestProfile.CountryDataStorage;
import net.clamour.foodevent.GuestProfile.GuestProfileScreen;
import net.clamour.foodevent.GuestProfile.VolleyMultipartRequest;
import net.clamour.foodevent.R;
import net.clamour.foodevent.sidebar.TermsConditions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.android.gms.vision.barcode.Barcode;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HostProfile extends DrawerHostBaseActivity  {


    @BindView(R.id.host_display_name)EditText host_title;
    @BindView(R.id.firstname_profile)EditText host_first_name;
    @BindView(R.id.lastname_profile)EditText host_last_name;
    @BindView(R.id.gender_spinner)Spinner gender_spinner;
    @BindView(R.id.email_host)EditText email_host;
    @BindView(R.id.alternateemail_host)EditText alternate_email_host;
    @BindView(R.id.phonenumber_host)EditText phone_number_host;
    @BindView(R.id.alternate_phonehost)EditText alternate_phone_host;
    @BindView(R.id.age_grphost)Spinner ageGroup_spinner;
    //  @BindView(R.id.service_spinner)Spinner service_spinner;
    @BindView(R.id.unitno_host)EditText unit_no;
    @BindView(R.id.streetaddress_host)EditText streetaddress_host;
    @BindView(R.id.country_spinner_host)Spinner Country_spinner ;
    @BindView(R.id.spinner_state_host)Spinner State_spinner;
    @BindView(R.id.city_host)EditText city_host;
    @BindView(R.id.zip_host)EditText zip_host;
    @BindView(R.id.proffession_host)EditText proffession_host;
    //   @BindView(R.id.language_spinner_host)Spinner language_host;
    @BindView(R.id.currency_spinnerhost)Spinner currency_host;
    @BindView(R.id.aboutus_host)EditText aboutUs_host;
    @BindView(R.id.termstext)TextView termscondition;
    @BindView(R.id.capture_button)Button caputre_gallery;
    @BindView(R.id.person_profile_image)ImageView profile_imagel;
    @BindView(R.id.button_save_host)Button save_host_profile;

    String firstname_up,lastname_up,gender_up,agegroup_up,aboutus_up,role_up,alternateemail_up,phonenumber_up,alternatephone_up,fulladdress_up,number_up,street_up,department_up,state_up,country_up,usertype_up,password_up,guest_profileimage_up,displayname_up,email_up,unitno_up,city_up,zip_up,proffesion_up,hosttitle_up,outhid_up;
    String completeguestimage_url;



    String auth_id;

    String host_title_st,host_firstname_st,host_lastname_st,email_host_st,alternate_email_host_st,
            phone_number_host_st,alternate_phone_number_host_st,gender_st,agegroup_st,service_st,unit_no_st,street_st,country_st,state_st,city_st,zip_st,proffession_st,language_st,currency_st,aboutus_st;
    Boolean isSucess;

    Bitmap bitmap;

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";
    SharedPreferences preferences;

    String gender[] = {"Select Gender","Male","Female","TransGender"};
    String agegroup[]={"18-25","25-30","30-40","40-50","50-above"};
    String service[]={"Social Dining","Tiffin Delivery","Catering","Private Events"};



    String currency[]={"Select Currency","Canadian Dollar","United States Dollar"};

    String language[]={"English","Afar","Abkhazian","Afrikaans","Amharic","Arabic","Assamese","Aymara","Azerbaijani","Bashkir","Belarusian","Bulgarian","Bihari","Bislama","Bengali/Bangla","Tibetan","Breton","Catalan","Corsican","Czech","Welsh","Danish","German","Bhutani","Greek","Esperanto","Spanish","Estonian","Basque","Persian","Finnish","Fiji","Faeroese","French","Frisian","Irish","Scots/Gaelic","Galician","Guarani","Gujarati","Hausa","Hindi","Croatian","Hungarian","Armenian","Interlingua","Interlingue","Inupiak","Indonesian","Icelandic","Italian","Hebrew","Japanese","Yiddish","Javanese","Georgian","Kazakh","Greenlandic","Cambodian","Kannada","Korean","Kashmiri","Kurdish","Kirghiz","Latin","Lingala","Laothian","Lithuanian","Latvian/Lettish","Malagasy","Maori","Macedonian","Malayalam","Mongolian","Moldavian","Marathi","Malay","Maltese","Burmese","Nauru","Nepali","Dutch","Norwegian","Occitan","(Afan)/Oromoor/Oriya","Punjabi","Polish","Pashto/Pushto","Portuguese","Quechua","Rhaeto-Romance","Kirundi","Romanian","Russian","Kinyarwanda","Sanskrit","Sindhi","Sangro","Serbo-Croatian","Singhalese","Slovak","Slovenian","Samoan","Shona","Somali","Albanian","Serbian","Siswati","Sesotho","Sundanese","Swedish","Swahili","Tamil","Telugu","Tajik","Thai","Tigrinya","Turkmen","Tagalog","Setswana","Tonga","Turkish","Tsonga","Tatar","Twi","Ukrainian","Urdu","Uzbek","Vietnamese","Volapuk","Wolof","Xhosa","Yoruba","Chinese","Zulu"};


    private TextView Output;
    private Button changeDate;

    private int year;
    private int month;
    private int day;

    static final int DATE_PICKER_ID = 1111;
    ProgressDialog pDialog;
    Uri selectedImageUri;
    String path;

    ArrayList<String>Country_array;
    ArrayList<String>State_array;
    ArrayList<CountryDataStorage>countryarray_modal;
    ArrayList<CountryDataStorage>statearray_modal;
    String country_name,country_id,state_name,state_id;
    SharedPreferences Registration_preferences;
    SharedPreferences login_prefrence;
    SharedPreferences profile_prefrence;
    String user_type_prefrence;
    String lat_st,long_st;
    CountryCodePicker ccp,ccp_alternate;
    String countrycode,alternatePhoneCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_profile);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        ButterKnife.bind(this);
        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.main_page_toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("My Profile");
        setSupportActionBar(toolbar1);
        setDrawer();

        MultiSelectionSpinner serv_spin=(MultiSelectionSpinner)findViewById(R.id.service_spinner);
        MultiSelectionSpinner language_spin=(MultiSelectionSpinner)findViewById(R.id.language_spinner_host);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.setCountryForPhoneCode(+1);

        ccp_alternate=(CountryCodePicker)findViewById(R.id.ccp_alternate);
        countrycode=ccp.getSelectedCountryCode();
        ccp.setCountryForPhoneCode(+1);
        Log.i("codeee",ccp.getSelectedCountryCode()+""+ccp.getDefaultCountryCodeWithPlus());
        alternatePhoneCode=ccp_alternate.getSelectedCountryCode();


        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        login_prefrence = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);

        profile_prefrence= this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestProfileScreen", MODE_PRIVATE);
        auth_id=Registration_preferences.getString("outh_id","");
        Log.i("auth_id_register",auth_id);


        auth_id = login_prefrence.getString("outh_id", "");

        Log.i("authid_login", auth_id);
        user_type_prefrence=Registration_preferences.getString("user_mode","");
        Log.i("Registrationpref", user_type_prefrence);

        user_type_prefrence=login_prefrence.getString("user_mode","");
        Log.i("Loginpref", user_type_prefrence);




        // String service[]={"Social Dining","Tiffin Delivery","Catering","Private Events"};

        List<String>service_array=new ArrayList<>();
        service_array.add("Social Dining");
        service_array.add("Tiffin Delivery");
        service_array.add("Catering");
        service_array.add("Paying Guest");


        //serv_spin.setSelec;

        serv_spin.setItems(service_array);

        serv_spin.setSelection(0);
        serv_spin.setSelection(1);
        serv_spin.setSelection(2);
        serv_spin.setSelection(3);

//        serv_spin.setSelection(0);
//          serv_spin.setSelection(1);
     //   serv_spin.setSelection(2);
     //   serv_spin.setSelection(3);





        List<String>language_array=new ArrayList<>();
        language_array.add("English");
        language_array.add("Afar");
        language_array.add("Abkhazian");
        language_array.add("Afrikaans");
        language_array.add("Amharic");
        language_array.add("Arabic");
        language_array.add("Assamese");
        language_array.add("Aymara");
        language_array.add("Azerbaijani");
        language_array.add("Bashkir");
        language_array.add("Belarusian");
        language_array.add("Bulgarian");
        language_array.add("Bihari");
        language_array.add("Bislama");
        language_array.add("Bengali/Bangla");
        language_array.add("Tibetan");
        language_array.add("Breton");
        language_array.add("Catalan");
        language_array.add("Corsican");
        language_array.add("Czech");
        language_array.add("Welsh");
        language_array.add("Danish");
        language_array.add("German");
        language_array.add("Bhutani");
        language_array.add("Greek");
        language_array.add("Esperanto");
        language_array.add("Spanish");
        language_array.add("Estonian");
        language_array.add("Estonian");
        language_array.add("Basque");
        language_array.add("Persian");
        language_array.add("Finnish");
        language_array.add("Fiji");
        language_array.add("Faeroese");
        language_array.add("French");
        language_array.add("Frisian");
        language_array.add("Irish");
        language_array.add("Scots/Gaelic");
        language_array.add("Galician");
        language_array.add("Guarani");
        language_array.add("Gujarati");
        language_array.add("Hausa");
        language_array.add("Hindi");
        language_array.add("Croatian");
        language_array.add("Hungarian");
        language_array.add("Armenian");
        language_array.add("Interlingua");
        language_array.add("Interlingue");
        language_array.add("Inupiak");
        language_array.add("Indonesian");
        language_array.add("Icelandic");
        language_array.add("Italian");
        language_array.add("Hebrew");
        language_array.add("Japanese");
        language_array.add("Yiddish");
        language_array.add("Javanese");
        language_array.add("Georgian");
        language_array.add("Kazakh");
        language_array.add("Greenlandic");
        language_array.add("Cambodian");
        language_array.add("Kannada");
        language_array.add("Korean");
        language_array.add("Kashmiri");
        language_array.add("Kurdish");
        language_array.add("Kirghiz");
        language_array.add("Latin");
        language_array.add("Lingala");
        language_array.add("Laothian");
        language_array.add("Lithuanian");
        language_array.add("Latvian/Lettish");
        language_array.add("Malagasy");
        language_array.add("Maori");
        language_array.add("Macedonian");
        language_array.add("Malayalam");
        language_array.add("Mongolian");
        language_array.add("Moldavian");
        language_array.add("Marathi");
        language_array.add("Malay");
        language_array.add("Maltese");
        language_array.add("Burmese");
        language_array.add("Nauru");
        language_array.add("Nepali");
        language_array.add("Dutch");
        language_array.add("Norwegian");
        language_array.add("Occitan");
        language_array.add("(Afan)/Oromoor/Oriya");
        language_array.add("Punjabi");
        language_array.add("Polish");
        language_array.add("Pashto/Pushto");
        language_array.add("Portuguese");language_array.add("Quechua");
        language_array.add("Rhaeto-Romance");
        language_array.add("Kirundi");language_array.add("Romanian");
        language_array.add("Russian");
        language_array.add("Kinyarwanda");
        language_array.add("Sanskrit");
        language_array.add("Sindhi");
        language_array.add("Sangro");
        language_array.add("Serbo-Croatian");
        language_array.add("Singhalese");
        language_array.add("Slovak");
        language_array.add("Slovenian");
        language_array.add("Samoan");language_array.add("Shona");
        language_array.add("Somali");
        language_array.add("Albanian");
        language_array.add("Serbian");
        language_array.add("Siswati");
        language_array.add("Sesotho");
        language_array.add("Sundanese");
        language_array.add("Swedish");
        language_array.add("Swahili");
        language_array.add("Tamil");
        language_array.add("Telugu");
        language_array.add("Tajik");
        language_array.add("Thai");
        language_array.add("Tigrinya");
        language_array.add("Turkmen");
        language_array.add("Tagalog");language_array.add("Setswana");
        language_array.add("Tonga");
        language_array.add("Turkish");
        language_array.add("Tsonga");language_array.add("Tatar");language_array.add("Twi");
        language_array.add("Ukrainian");
        language_array.add("Urdu");language_array.add("Uzbek");
        language_array.add("Vietnamese");
        language_array.add("Volapuk");
        language_array.add("Wolof");
        language_array.add("Xhosa");
        language_array.add("Yoruba");
        language_array.add("Chinese");
        language_array.add("Zulu");


        language_spin.setItems(language_array);

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



        //  saveUpdatedProfileData();


        save_host_profile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatLang();
                savehostProfile();
            }
        });



        Country_array=new ArrayList<>();
        countryarray_modal=new ArrayList<>();
        State_array=new ArrayList<>();
        statearray_modal=new ArrayList<>();

        ShowCountryData();



        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        // Show current date

//        dateofbirth_et.setText(new StringBuilder()
//                // Month is 0 based, just add 1
//                .append(month + 1).append("-").append(day).append("-")
//                .append(year).append(" "));
//
//
//        dateofbirth_et.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(DATE_PICKER_ID);
//            }
//        });



        ArrayAdapter<String> gender_adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, gender);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(gender_adapter);

        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender_st=gender_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> agegroup_adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, agegroup);
        agegroup_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageGroup_spinner.setAdapter(agegroup_adapter);

        ageGroup_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                agegroup_st=ageGroup_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> currency_adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, currency);
        currency_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency_host.setAdapter(currency_adapter);

        currency_host.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency_st=currency_host.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //  multiSelectionSpinner.setItems(service);
        //   multiSelectionSpinner.setSelection(new int[]{1, 2});
        //multiSelectionSpinner.setListener(this);


//        ArrayAdapter<String> service_adapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_spinner_item, service);
//        service_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        service_spinner.setAdapter(service_adapter);




//        ArrayAdapter<String> language_adapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_spinner_item, language);
//        language_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        language_spinner.setAdapter(language_adapter);




        caputre_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagebyGallery();
            }
        });

        termscondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(net.clamour.foodevent.hostsidebar.HostProfile.this,TermsConditions.class);
                startActivity(intent);

            }
        });



    }

    public void ShowCountryData(){
        pDialog = new ProgressDialog(HostProfile.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);


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
        Country_spinner.setAdapter(new ArrayAdapter<String>(HostProfile.this, android.R.layout.simple_spinner_dropdown_item, Country_array));


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

        State_spinner.setAdapter(new ArrayAdapter<String>(HostProfile.this, android.R.layout.simple_spinner_dropdown_item, State_array));

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

    public void showStateData(){

        pDialog = new ProgressDialog(HostProfile.this);
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






    public void loadImagebyGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
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
                    }

//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("image", String.valueOf(selectedImageUri));
//                    editor.commit();
                }
            }
        }
    }

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




//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case DATE_PICKER_ID:
//
//                // open datepicker dialog.
//                // set date picker for current date
//                // add pickerListener listner to date picker
//                return new DatePickerDialog(this, pickerListener, year, month,day);
//        }
//        return null;
//    }

//    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
//
//        // when dialog box is closed, below method will be called.
//        @Override
//        public void onDateSet(DatePicker view, int selectedYear,
//                              int selectedMonth, int selectedDay) {
//
//            year  = selectedYear;
//            month = selectedMonth;
//            day   = selectedDay;
//
//            // Show selected date
//            dateofbirth_et.setText(new StringBuilder().append(month + 1)
//                    .append("-").append(day).append("-").append(year)
//                    .append(" "));
//
//        }
//    };


    public void savehostProfile(){

        pDialog = new ProgressDialog(HostProfile.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);



        pDialog.show();

        VolleyMultipartRequestHost multipartRequest = new VolleyMultipartRequestHost(Request.Method.POST,"http://myhealthyhost.com/api/edit_profile.php", new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Log.i("responsereprofilesaveedittthostt", resultResponse);
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


                    //     saveUpdatedProfileData();

                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            HostProfile.this).create();

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

//                            Intent i = new Intent(HostProfile.this, GuestProfileScreen.class);
//
//                            startActivity(i);
                           // alertDialog.cancel();

//                            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//                            browserIntent.setData(Uri.parse("http://myhealthyhost.com/dashboard.php"));
//                            startActivity(browserIntent);
                            //alertDialog.dismiss();
                        }
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
                params.put("usertype","Host");
                params.put("username_host",host_title_st);
                params.put("first_name",host_firstname_st);
                params.put("last_name",host_lastname_st);
                params.put("gender",gender_st);
                params.put("agegroup",agegroup_st);
                params.put("service_host","Social Dining");
                params.put("email_alternate",alternate_email_host_st);
                params.put("phone",phone_number_host_st);
                params.put("phone_alternate",alternate_phone_number_host_st);
                params.put("streetno",unit_no_st);
                //params.put("street",street_st);
                params.put("country",country_id);
                params.put("state",state_id);
                params.put("zip",zip_st);
                params.put("profession",proffession_st);
                params.put("language","arabic");
                params.put("currency",currency_st);
                params.put("description",aboutus_st);
                params.put("username_host",host_title_st);
                params.put("city",city_st);
                params.put("lat",lat_st);
                params.put("lng",long_st);
                params.put("old_picture",guest_profileimage_up);
                params.put("phone_code",countrycode);
                params.put("alternate_phone_code",alternatePhoneCode);


                return params;
            }
            @Override
            protected Map<String,DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                //params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                if(bitmap!=null){
                    params.put("picture",new DataPart("profileImage.png",getFileDataFromDrawable(bitmap)));
                }
                else if(bitmap==null){

                }
                return params;
            }

        };

        Volley.newRequestQueue(this).add(multipartRequest);


    }
    public void getLatLang(){
        host_title_st=host_title.getText().toString();
        host_firstname_st=host_first_name.getText().toString();
        host_lastname_st=host_last_name.getText().toString();
        alternate_email_host_st=alternate_email_host.getText().toString();
        phone_number_host_st=phone_number_host.getText().toString();
        alternate_phone_number_host_st=alternate_phone_host.getText().toString();
        unit_no_st=unit_no.getText().toString();
        street_st=streetaddress_host.getText().toString();
        city_st=city_host.getText().toString();
        zip_st=zip_host.getText().toString();
        proffession_st=proffession_host.getText().toString();
        aboutus_st=aboutUs_host.getText().toString();

        String complete_address=country_name+state_name+unit_no_st+street_st+city_st+zip_st;
        Log.i("completeaddress",complete_address);

        try {
            Geocoder gc = new Geocoder(HostProfile.this);
            if(gc.isPresent()){
                List<Address> list = gc.getFromLocationName(complete_address,1);
                Address address = list.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();

                lat_st=lat+"";
                Log.i("latttt",lat_st);
                long_st=lng+"";
                Log.i("long",long_st);
            }


        }
        catch (Exception e){


        }
    }

    public void saveUpdatedProfileData(){
//        pDialog = new ProgressDialog(HostProfile.this);
//        pDialog.setMessage("Please wait...");
//        pDialog.setCancelable(true);


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
                                fulladdress_up=jsonObject.getString("address");
                              //  number_up=jsonObject.getString("streetno");
                                street_up=jsonObject.getString("streetno");
                                city_up=jsonObject.getString("city");
                                 email_up=jsonObject.getString("email");
                                 aboutus_up=jsonObject.getString("description");
                                 hosttitle_up=jsonObject.getString("username_host");
                                 outhid_up=jsonObject.getString("oauth_uid");

                                // department_up=jsonObject.getString("Dipartment");
                                //  Log.i("departmentuppp",department_up);
                                state_up=jsonObject.getString("state");
                                zip_up=jsonObject.getString("zipcode");
                                country_up=jsonObject.getString("country");
                                usertype_up=jsonObject.getString("usertype");
                                password_up=jsonObject.getString("password");
                                guest_profileimage_up=jsonObject.getString("picture");
                                Log.i("guset_profileupp",guest_profileimage_up);


                                completeguestimage_url="http://myhealthyhost.com/"+guest_profileimage_up;
                                Log.i("gusetimage",completeguestimage_url);
                                unitno_up=jsonObject.getString("streetno");
                                proffesion_up=jsonObject.getString("profession");
                                displayname_up=jsonObject.getString("username_host");


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
        editor.putString("fulladdress_up",fulladdress_up);
        editor.putString("number_up",number_up);
        editor.putString("street_up",street_up);
        editor.putString("city_up",city_up);
        editor.putString("department_up",department_up);
        editor.putString("state_up",state_up);
        editor.putString("zip_up",zip_up);
        editor.putString("country_up",country_up);
        editor.putString("outh_id",outhid_up);
        // editor.putString("password_up",password_up);
        editor.putString("guset_profileimage",completeguestimage_url);
        editor.commit();
    }
    public void setData(){
//    First_name.setText(firstname_set);
//    Last_name.setText(last_name_set);
//    alternate_email.setText(alternate_emailset);
//    phone_no_guest.setText(phoneno_set);
//    alternate_phoneguest.setText(alternatephone_set);
//    fulladdress_guest.setText(fulladdress_set);
//    number_guest.setText(number_set);
//    street_guest.setText(Street_set);
//    city_guest.setText(city_set);
//    department_guest.setText(department_set);
//    zip_guest.setText(Zip_set);

        host_first_name.setText(firstname_up);
        host_last_name.setText(lastname_up);

        alternate_email_host.setText(alternateemail_up);
        phone_number_host.setText(phonenumber_up);
        alternate_phone_host.setText(alternatephone_up);
        //unit_no.setText(u);
        streetaddress_host.setText(street_up);
        zip_host.setText(zip_up);
        unit_no.setText(unitno_up);
        city_host.setText(city_up);
        email_host.setText(email_up);
        proffession_host.setText(proffesion_up);
        host_title.setText(hosttitle_up);
        aboutUs_host.setText(aboutus_up);


        Glide.with(HostProfile.this).load(completeguestimage_url)
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile_imagel);



    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
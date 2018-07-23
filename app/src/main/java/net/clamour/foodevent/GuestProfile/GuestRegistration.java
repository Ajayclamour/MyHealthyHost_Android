package net.clamour.foodevent.GuestProfile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import net.clamour.foodevent.GuestHomeScreen.GuestHomeScreen;
import net.clamour.foodevent.HostProfile.HostHomeScreen;
import net.clamour.foodevent.R;
import net.clamour.foodevent.hostsidebar.HostProfile;
import net.clamour.foodevent.webservice.ApiClient;
import net.clamour.foodevent.webservice.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class GuestRegistration extends AppCompatActivity {

    ProgressDialog pDialog;
    ApiInterface apiInterface;

    @BindView(R.id.register)Button registration;

    @BindView(R.id.firstname_guest_reg)EditText first_name ;
    @BindView(R.id.lasname_guest_reg)EditText last_name;
    @BindView(R.id.email_guest_reg)EditText email;
    @BindView(R.id.password_guest_reg)EditText password;
    @BindView(R.id.repeatpassword_reg)EditText repeat_password;
    @BindView(R.id.radioButton1)RadioButton guest_radio;
    @BindView(R.id.radioButton2)RadioButton host_radio;
    @BindView(R.id.radioGroup)RadioGroup radioGroup;

   // @BindView(R.id.face_book_button)Button Facebook_button;
    //@BindView(R.id.gmail_button)Button Gmail_button;

    private CallbackManager callbackManager ;

    private RadioButton radioButton;

    String firstname_st,lastname_st,email_st,password_st,repeatpassword_st,guest_st,host_st,usertype_st;
    Boolean isSucess,isSucess_fb,isSucess_gmail;
    String oth_id,user_mode,error_code,email_get,firstname_get,lastname_get;
    SharedPreferences Registration_preferences;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String TAG = GuestRegistration.class.getSimpleName();
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 6969;
    private static final int FB_SIGN_IN=7979;

    ArrayList<RegistrationResponse>register_response_array;
    RadioButton selectedRadioButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_registration);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

//        Facebook_button=(Button)findViewById(R.id.face_book_button);
//
//        callbackManager = CallbackManager.Factory.create();
//
//        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//
//                Profile profile = Profile.getCurrentProfile();
//                if (profile != null) {
//                  String  facebook_id=profile.getId();
//                    String  f_name=profile.getFirstName();
//                    String  m_name=profile.getMiddleName();
//                    String  l_name=profile.getLastName();
//                    String   full_name=profile.getName();
//                    String  profile_image=profile.getProfilePictureUri(400, 400).toString();
//                }
//
//                graphRequest(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });
//
//
//        Facebook_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                LoginManager.getInstance().logInWithReadPermissions(GuestRegistration.this, Arrays.asList("public_profile", "user_friends", "email"));
//
//
//            }
//        });
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        Gmail_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, RC_SIGN_IN);
//            }
//        });






        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(GuestRegistration.this,GuestLogin.class);
                //startActivity(intent);

                //registrationUser();
                int selectedRadioButtonID = radioGroup.getCheckedRadioButtonId();

                // If nothing is selected from Radio Group, then it return -1
                if (selectedRadioButtonID != -1) {

                     selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    usertype_st = selectedRadioButton.getText().toString();

                    Log.i("selectedradiobutton",usertype_st);

                    //tv_result.setText(selectedRadioButtonText + " selected.");
                }
                else{
                    //tv_result.setText("Nothing selected from Radio Group.");
                }

                if(first_name.getText().toString().trim().equals("")){
                    first_name.setError("please enter first name");
                }
                else if(last_name.getText().toString().trim().equals("")){
                    last_name.setError("please enter last name");
                }
                else if(email.getText().toString().trim().equals("")){

                    email.setError("Please check your Email Id");
                }
                else if (!email.getText().toString().matches(emailPattern)){
                    email.setError("Please enter valid Email Id ");
                }

                else if(password.getText().toString().trim().equals("")){
                    password.setError("Please enter password");
                }
                else if(password.getText().toString().length()<6||password.getText().toString().length()>10){

                    password.setError("Please enter the password between 6-10 characters");
                }
                else if(repeat_password.getText().toString().trim().equals("")){

                    repeat_password.setError("Please ReType password");
                }
                else if(!password.getText().toString().matches(repeat_password.getText().toString())){
                    repeat_password.setError("Password Not Matched");
                }
                else if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(),"Please Select the mode",Toast.LENGTH_SHORT).show();
                }

//                else if(!host_radio.isChecked()){
//
//                    Toast.makeText(getApplicationContext(),"Please Select the mode1",Toast.LENGTH_SHORT).show();
//                }
//                else if(!password_st.matches(repeatpassword_st)){
//                    Toast.makeText(getApplicationContext(),"Password Not Matched",Toast.LENGTH_SHORT).show();
//                }
                else {
                    verifyUser();
                }



//                repeat_password.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        password_st=password.getText().toString();
//                        repeatpassword_st=repeat_password.getText().toString();
//
//                        if (password_st.equals(repeatpassword_st)) {
//                            //   error.setText(R.string.settings_pwd_equal);
//                            Toast.makeText(getApplicationContext(),"Password Matched",Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Toast.makeText(getApplicationContext(),"Password Not Matched",Toast.LENGTH_SHORT).show();
//                        }
//
//
//
//
//                    }
//                });



            }
        });


    }




    public void saveData(){
        SharedPreferences.Editor editor=Registration_preferences.edit();
        editor.putString("outh_id",oth_id);
        editor.putString("first_name",firstname_get);
        editor.putString("last_name",lastname_get);
        editor.putString("user_mode",usertype_st);
        editor.putString("email",email_get);
        editor.commit();
    }

    public void verifyUser(){


        pDialog = new ProgressDialog(GuestRegistration.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);
        pDialog.show();

        firstname_st=first_name.getText().toString();
        lastname_st=last_name.getText().toString();
        email_st=email.getText().toString();
        password_st=password.getText().toString();
        Log.i("password",password_st);

        repeatpassword_st=repeat_password.getText().toString();
        Log.i("password",repeatpassword_st);



        // Get the checked Radio Button ID from Radio Grou[
        int selectedRadioButtonID = radioGroup.getCheckedRadioButtonId();

        // If nothing is selected from Radio Group, then it return -1
        if (selectedRadioButtonID != -1) {

           selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
            usertype_st = selectedRadioButton.getText().toString();

            Log.i("selectedradiobutton",usertype_st);

            //tv_result.setText(selectedRadioButtonText + " selected.");
        }
        else{
            //tv_result.setText("Nothing selected from Radio Group.");
        }


        register_response_array = new ArrayList<>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<ArrayList<RegistrationResponse>>call=apiInterface.User_Registration(firstname_st,lastname_st,email_st,usertype_st,password_st);

        call.enqueue(new Callback<ArrayList<RegistrationResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<RegistrationResponse>> call, retrofit2.Response<ArrayList<RegistrationResponse>> response) {
//                pDialog.cancel();

                register_response_array=response.body();
                Log.d("onResponse: ",response.body().toString());

                for (int i=0;i<register_response_array.size();i++){

                    isSucess=register_response_array.get(i).getSuccess();
                    Log.d("isSucess",isSucess.toString());
                    email_get=register_response_array.get(i).getEmail();



                }

                if((isSucess==true)) {



                    //saveData();
//                             Intent i = new Intent(GuestRegistration.this,Submit_Code_Screen.class);
//                              i.putExtra("email_register",email_get);
//
//                            startActivity(i);

                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            GuestRegistration.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("                 Alert!");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please Check Your Email Id for Verification Code");

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
                            Intent i = new Intent(GuestRegistration.this,Submit_Code_Screen.class);
                            i.putExtra("email_register",email_get);
                            startActivity(i);
                            finish();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();


                }
                else if(isSucess==false){

                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            GuestRegistration.this).create();
                    // saveData();
                    // Setting Dialog Title
                    alertDialog.setTitle("                 Alert!");

                    // Setting Dialog Message
                    alertDialog.setMessage("    Email Alredy Exist");

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

            @Override
            public void onFailure(Call<ArrayList<RegistrationResponse>> call, Throwable t) {

            }
        });}}
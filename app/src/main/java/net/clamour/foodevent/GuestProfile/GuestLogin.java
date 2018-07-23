package net.clamour.foodevent.GuestProfile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class GuestLogin extends AppCompatActivity {

    @BindView(R.id.signup_guest)Button login_guest;
    @BindView(R.id.signup_text)TextView signup_text;
    @BindView(R.id.forgetpass_text)TextView forgetpass_text;

    @BindView(R.id.email_login)EditText email_login;
    @BindView(R.id.password_login)EditText password_login;

    @BindView(R.id.face_book_button)Button Facebook_button;
    @BindView(R.id.gmail_button)Button Gmail_button;

    private CallbackManager callbackManager ;
    ProgressDialog pDialog;
    String email_login_st,password_login_st;
    String oth_id,user_mode,error_code,email_get,firstname_get,lastname_get;

    Button submit_email,submit_new_pass;

    ImageView cross_dialog;
    AlertDialog alertDialog,alertDialog1;
    Boolean isSucess,isSucess1;


    SharedPreferences login_prefrence;
    SharedPreferences Registration_preferences;
    EditText forgot_email;
    String new_password,forgotemail_st;
    String firstname_fb,lastname_fb,uid_fb,emailid_fb;

    String user_type;
    private static final String TAG = GuestRegistration.class.getSimpleName();
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 6969;
    private static final int FB_SIGN_IN=7979;

    ApiInterface apiInterface;
    ArrayList<ForgotPasswordResponse>forgotpass_array;
    String gmail_firstname,gmail_last_name,gmail_email,gmail_uid;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_login);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        ButterKnife.bind(this);




        Facebook_button=(Button)findViewById(R.id.face_book_button);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    String  facebook_id=profile.getId();
                    String  f_name=profile.getFirstName();
                    String  m_name=profile.getMiddleName();
                    String  l_name=profile.getLastName();
                    String   full_name=profile.getName();
                    String  profile_image=profile.getProfilePictureUri(400, 400).toString();
                }

                graphRequest(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        Facebook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(GuestLogin.this, Arrays.asList("public_profile", "user_friends", "email"));


            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Gmail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        Typeface face2 = Typeface.createFromAsset(getAssets(), "Lato-Bold.ttf");
        Typeface face3 = Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf");

        forgetpass_text.setTypeface(face3);
        signup_text.setTypeface(face3);


        login_prefrence = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);
        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        user_type=Registration_preferences.getString("user_mode","");
        user_type=login_prefrence.getString("user_mode","");

        Log.i("user_type",user_type);

        String outh_id=Registration_preferences.getString("outh_id","");
        Log.i("auth_iduseeeerrrrrr",outh_id);


        if(Registration_preferences.contains("outh_id")&&user_type.contains("Guest")){
            Intent intent=new Intent(GuestLogin.this,GuestHomeScreen.class);
            startActivity(intent);
            finish();

        }

        else if(Registration_preferences.contains("outh_id")&&user_type.contains("Host")) {

            Intent intent=new Intent(GuestLogin.this,HostProfile.class);
            startActivity(intent);
            finish();
        }
//        else if(login_prefrence.contains("outh_id")){
//            Intent intent=new Intent(GuestLogin.this,GuestHomeScreen.class);
//            startActivity(intent);
//
//        }
        else if(login_prefrence.contains("outh_id")&&user_type.contains("Guest")){
            Intent intent=new Intent(GuestLogin.this,GuestHomeScreen.class);
            startActivity(intent);
            finish();
        }

        else if(login_prefrence.contains("outh_id")&&user_type.contains("Host")) {

            Intent intent=new Intent(GuestLogin.this,HostProfile.class);
            startActivity(intent);
            finish();

        }


        login_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(GuestLogin.this, GuestHomeScreen.class);
//                startActivity(intent);

                if(email_login.getText().toString().trim().equals("")){
                    email_login.setError("please enter EmailId");
                }
                else if (!email_login.getText().toString().matches(emailPattern)){
                    email_login.setError("Please enter valid Email Id ");
                }
                else if(password_login.getText().toString().trim().equals("")){
                    password_login.setError("please enter Password");
                }

else {
                loginUser();}
            }
        });

        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuestLogin.this,GuestRegistration.class);
                startActivity(intent);
            }
        });

        forgetpass_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotEmailDialog();
            }
        });
    }

    public void openForgotEmailDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);



        LayoutInflater inflater = LayoutInflater.from(GuestLogin.this);


        View subView = inflater.inflate(R.layout.forgot_password_email, null);
        builder.setView(subView);
        alertDialog = builder.create();

        forgot_email=(EditText)subView.findViewById(R.id.current_password);
//        confirm_password=(EditText)subView.findViewById(R.id.confirm_password);
//        pass_matched=(TextView)subView.findViewById(R.id.pass_matched);
        submit_email=(Button)subView.findViewById(R.id.reset_button_email);


        submit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ForgotPassword();
            }
        });
        // set the custom dialog components - text, image and button

        cross_dialog = (ImageView) subView.findViewById(R.id.cross_icon);
        //image.setImageResource(R.drawable.ic_launcher);

        // Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);


        // if button is clicked, close the custom dialog
        cross_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();}



    public void loginUser(){

        pDialog = new ProgressDialog(GuestLogin.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        email_login_st=email_login.getText().toString();
        password_login_st=password_login.getText().toString();





        pDialog.show();

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("responselogin", response);



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
                                isSucess=jsonObject.getBoolean("success");
                                firstname_get=jsonObject.getString("first_name");
                                lastname_get=jsonObject.getString("last_name");
                                oth_id=jsonObject.getString("oauth_uid");
                                email_get=jsonObject.getString("email");
                                user_mode=jsonObject.getString("usertype");
                                //error_code=jsonObject.getString("errorcode");
                                //  Log.i("errorcode",error_code);



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
                                    GuestLogin.this).create();

                            // Setting Dialog Title
                            alertDialog.setTitle("                    Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("                Login Sucessfully");

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
                                    Intent i = new Intent(GuestLogin.this,GuestHomeScreen.class);

                                    startActivity(i);
                                    finish();
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();


                        }
                        else if((isSucess==true)&&(user_mode.contains("Host"))){
                             android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    GuestLogin.this).create();
                            saveData();
                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("    Login Sucessfully");

                            // Setting Icon to Dialog


                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                                    emailIntent.setType("text/plain");
//                                    startActivity(emailIntent);
                                    // Write your code here to execute after dialog closed
                                    // alertDialog.dismiss();
                                    Intent i = new Intent(GuestLogin.this,HostProfile.class);

                                    startActivity(i);

                                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_LONG).show();
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();}

                        else if(isSucess==false){

                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    GuestLogin.this).create();
                            saveData();
                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("    Invalid Email & Password");

                            // Setting Icon to Dialog


                            // Setting OK Button
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                                    emailIntent.setType("text/plain");
//                                    startActivity(emailIntent);
                                    // Write your code here to execute after dialog closed

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

                params.put("email",email_login_st);
                params.put("password",password_login_st);

                return params;
            }

        };



        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);


    }
    public void saveData(){

        SharedPreferences.Editor editor=login_prefrence.edit();
        editor.putString("outh_id",oth_id);
        editor.putString("first_name",firstname_get);
        editor.putString("last_name",lastname_get);
        editor.putString("user_mode",user_mode);
        editor.putString("email",email_get);
        editor.commit();

    }

    public void ForgotPassword(){

        forgotemail_st=forgot_email.getText().toString();


        pDialog = new ProgressDialog(GuestLogin.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);
        pDialog.show();





        forgotpass_array = new ArrayList<>();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<ArrayList<ForgotPasswordResponse>> call=apiInterface.forgotPassword(forgotemail_st);

        call.enqueue(new Callback<ArrayList<ForgotPasswordResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ForgotPasswordResponse>> call, retrofit2.Response<ArrayList<ForgotPasswordResponse>> response) {

                pDialog.cancel();

                forgotpass_array = response.body();
                Log.d("onResponse: ", response.body().toString());
                try {
                    for (int i = 0; i < forgotpass_array.size(); i++) {

                        isSucess1 = forgotpass_array.get(i).getSuccess();
//                        Log.d("isSucess", isSucess.toString());




                    }
                } catch (Exception e) {

                }


                if (isSucess1 == true) {


                    // Intent i = new Intent(GuestRegistration.this,GuestRegistration.class);
                    //  i.putExtra("send",item);
                    //startActivity(i);

                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            GuestLogin.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("                 Alert!");

                    // Setting Dialog Message
                    alertDialog.setMessage("Password is sucessfully sent to Email Id ");

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
                          // alertDialog.cancel();
                            Intent intent=new Intent(GuestLogin.this,GuestLogin.class);
                            startActivity(intent);

                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                }


            }

            @Override
            public void onFailure(Call<ArrayList<ForgotPasswordResponse>> call, Throwable t) {

            }
        });

    }



    public void graphRequest(AccessToken token){
        GraphRequest request = GraphRequest.newMeRequest(token,new GraphRequest.GraphJSONObjectCallback(){

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {


              //  Toast.makeText(getApplicationContext(),object.toString(),Toast.LENGTH_LONG).show();

                Log.i("Responsefb",object.toString());
                //JSONObject jsonObject;

                String jsondata=object.toString()+"";


//                Intent intent=new Intent(GuestRegistration.this, GuestHomeScreen.class);
//                startActivity(intent);

                try {
                    object=new JSONObject(jsondata);
                    firstname_fb=object.getString("first_name");
                    Log.i("first_name",firstname_fb);
                    lastname_fb=object.getString("last_name");
                    Log.i("last_name",lastname_fb);

                    uid_fb=object.getString("id");
                    Log.i("user_id",uid_fb);

                    if(object.has("email")) {
                        Log.i("getinsideemail","getinsideemail");

                        emailid_fb = object.getString("email");
                        Log.i("user_email", emailid_fb);
                        sendFaceBookDatatoServer();
                    }
                    else{

                        Toast.makeText(getApplicationContext(),"please verify your email in facebook account", Toast.LENGTH_SHORT).show();
                    }
                    //  String email=object.getString("email");
                    //Log.i("email",email);

//                    String profile_pic_data =object.getString("picture");
//                    JSONObject jsonObject=new JSONObject(profile_pic_data);
//                    String profile=jsonObject.getString("data");
//                    JSONObject jsonObject1=new JSONObject(profile);
//                    String url=jsonObject1.getString("url");
//                    Log.i("image_url",url);


                    // String user_email =response.getJSONObject().getString("email");
                    // Log.i("user_email",user_email);
                    //  isSucess_fb=jsonObject1.getBoolean("is_silhouette");



//                 String   profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
//                    Picasso.with(this).load(profile_pic_url.getString("url"))
//                            .into(user_picture);

                } catch(Exception e){
                    e.printStackTrace();
                }


//                if(emailid_fb.isEmpty()){
//                    Log.i("gjdhagjhsdgs","gsdgghashgdfhg");
//
//                }
//                else {
//                    Log.i("gjdhagjhsdgsmmmmmm","gsdgghashgdfhg");
//
//                }




            }
        });

        Bundle b = new Bundle();
        b.putString("fields", "last_name,first_name,email,picture.type(large)");
        request.setParameters(b);
        request.executeAsync();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        else {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            if (account != null) {
                String personName = account.getDisplayName();
                gmail_firstname = account.getGivenName();
                gmail_last_name = account.getFamilyName();
                gmail_email = account.getEmail();
                gmail_uid = account.getId();

                Uri personPhoto = account.getPhotoUrl();
                String personIdToken = account.getIdToken();
                String personServerAuthCode =account.getServerAuthCode();
                String resp = personName+"\n"+gmail_firstname+"\n"+gmail_last_name+"\n"+gmail_email+"\n"+gmail_uid+"\n"+personPhoto;
                //  responceView.setText(resp);
                Log.i("gmail_response",resp);

                sendGmailDatatoServer();

            }

        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }


    public void sendFaceBookDatatoServer(){

        Log.i("apiiii","apiiiii");

        pDialog = new ProgressDialog(GuestLogin.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);






        pDialog.show();

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/social_media_login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("responseloginfacebook", response);



                        //   arrayList=new ArrayList<>();

                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);
//                        Intent intent=new Intent(GuestRegistration.this,OtpScreen.class);
//                        intent.putExtra("mobile_no",mobile_no_st);
//                        startActivity(intent);


                        try {



                            JSONObject jsonObject=new JSONObject(response);
                            isSucess=jsonObject.getBoolean("status");
                            Log.i("status",isSucess.toString());
                            JSONObject jsonObject1=jsonObject.getJSONObject("result");

                            firstname_get=jsonObject1.getString("first_name");
                            Log.i("firstname",firstname_get);
                            lastname_get=jsonObject1.getString("last_name");
                            Log.i("lastname",lastname_get);
                            oth_id=jsonObject1.getString("oauth_uid");
                            Log.i("outh_id",oth_id);
                            email_get=jsonObject1.getString("email");
                            Log.i("email_get",email_get);





                        }
                        catch (Exception e){
                        }
                        if((isSucess==true)) {



                            saveData();
                            // Intent i = new Intent(GuestRegistration.this,GuestRegistration.class);
                            //  i.putExtra("send",item);
                            //startActivity(i);

                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    GuestLogin.this).create();

                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("          Login Sucessfully");

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
                                    Intent i = new Intent(GuestLogin.this,GuestHomeScreen.class);

                                    startActivity(i);
                                    finish();
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();


                        }
//                        else if((isSucess==true)&&(user_mode.contains("Host"))){
//                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
//                                    GuestLogin.this).create();
//                            saveData();
//                            // Setting Dialog Title
//                            alertDialog.setTitle("                 Alert!");
//
//                            // Setting Dialog Message
//                            alertDialog.setMessage("    Login Sucessfully");
//
//                            // Setting Icon to Dialog
//
//
//                            // Setting OK Button
//                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
////                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
////                                    emailIntent.setType("text/plain");
////                                    startActivity(emailIntent);
//                                    // Write your code here to execute after dialog closed
//                                    // alertDialog.dismiss();
//                                    Intent i = new Intent(GuestLogin.this,HostHomeScreen.class);
//
//                                    startActivity(i);
//
//                                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_LONG).show();
//                                }
//                            });
//
//                            // Showing Alert Message
//                            alertDialog.show();}
//
                        else if(isSucess==false){

                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    GuestLogin.this).create();
                            saveData();
                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("    Invalid Email & Password");

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

                params.put("firstname",firstname_fb);
                params.put("lastname",lastname_fb);
                params.put("email",emailid_fb);
                params.put("provider","facebook");
                params.put("uid",uid_fb);


                return params;
            }

        };



        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);
    }

    public void sendGmailDatatoServer(){
        pDialog = new ProgressDialog(GuestLogin.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);






        pDialog.show();

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/social_media_login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("responseloginfacebook", response);



                        //   arrayList=new ArrayList<>();

                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);
//                        Intent intent=new Intent(GuestRegistration.this,OtpScreen.class);
//                        intent.putExtra("mobile_no",mobile_no_st);
//                        startActivity(intent);


                        try {



                           // JSONArray jsonArray = new JSONArray(response);

                                JSONObject jsonObject=new JSONObject(response);
                                isSucess=jsonObject.getBoolean("status");
                                Log.i("status",isSucess.toString());
                                JSONObject jsonObject1=jsonObject.getJSONObject("result");

                               firstname_get=jsonObject1.getString("first_name");
                               Log.i("firstname",firstname_get);
                                lastname_get=jsonObject1.getString("last_name");
                                Log.i("lastname",lastname_get);
                                oth_id=jsonObject1.getString("oauth_uid");
                                Log.i("outh_id",oth_id);
                                email_get=jsonObject1.getString("email");
                                Log.i("email_get",email_get);
//                                user_mode=jsonObject.getString("usertype");
                                //error_code=jsonObject.getString("errorcode");
                                //  Log.i("errorcode",error_code);






                        }
                        catch (Exception e){
                        }
                        if((isSucess==true)) {



                            saveData();
                            // Intent i = new Intent(GuestRegistration.this,GuestRegistration.class);
                            //  i.putExtra("send",item);
                            //startActivity(i);

                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    GuestLogin.this).create();

                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("           Login Sucessfully");

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
                                    Intent i = new Intent(GuestLogin.this,GuestHomeScreen.class);

                                    startActivity(i);
                                    finish();
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();


                        }
//                        else if((isSucess==true)&&(user_mode.contains("Host"))){
//                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
//                                    GuestLogin.this).create();
//                            saveData();
//                            // Setting Dialog Title
//                            alertDialog.setTitle("                 Alert!");
//
//                            // Setting Dialog Message
//                            alertDialog.setMessage("    Login Sucessfully");
//
//                            // Setting Icon to Dialog
//
//
//                            // Setting OK Button
//                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
////                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
////                                    emailIntent.setType("text/plain");
////                                    startActivity(emailIntent);
//                                    // Write your code here to execute after dialog closed
//                                    // alertDialog.dismiss();
//                                    Intent i = new Intent(GuestLogin.this,HostHomeScreen.class);
//
//                                    startActivity(i);
//
//                                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_LONG).show();
//                                }
//                            });
//
//                            // Showing Alert Message
//                            alertDialog.show();}
//
                        else if(isSucess==false){

                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                    GuestLogin.this).create();
                            saveData();
                            // Setting Dialog Title
                            alertDialog.setTitle("                 Alert!");

                            // Setting Dialog Message
                            alertDialog.setMessage("    Invalid Email & Password");

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

                params.put("firstname",gmail_firstname);
                params.put("lastname",gmail_last_name);
                params.put("email",gmail_email);
                params.put("provider","gmail");
                params.put("uid",gmail_uid);


                return params;
            }

        };



        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);


    }
}


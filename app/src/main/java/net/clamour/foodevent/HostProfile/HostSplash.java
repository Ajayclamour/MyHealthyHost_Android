package net.clamour.foodevent.HostProfile;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.clamour.foodevent.R;
import net.clamour.foodevent.hostsidebar.HostProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HostSplash extends Activity {
    private static int SPLASH_TIME_OUT = 3000;

    String auth_id,first_name,last_name,complete_name,user_type;

    SharedPreferences login_prefrence;
    SharedPreferences Registration_preferences;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_splash);


        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        login_prefrence = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);

        user_type=Registration_preferences.getString("user_mode","");
        user_type=login_prefrence.getString("user_mode","");

        auth_id=Registration_preferences.getString("outh_id","");
        Log.i("auth_id",auth_id);

        auth_id=login_prefrence.getString("outh_id","");


        first_name=Registration_preferences.getString("first_name","");
        last_name=Registration_preferences.getString("last_name","");

        complete_name=first_name+" "+last_name;
        Log.i("complete_name",complete_name);

        first_name=login_prefrence.getString("first_name","");
        last_name=login_prefrence.getString("last_name","");



        complete_name=first_name+" "+last_name;
        Log.i("complete_name",complete_name);


        if(user_type.contains("Guest")){
            changeMode();
        }


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(HostSplash.this, HostProfile.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
public void changeMode(){

    pDialog = new ProgressDialog(HostSplash.this);
    pDialog.setMessage("Please wait...");
    pDialog.setCancelable(true);







    // pDialog.show();

    StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://emergingncr.com/my-healthy-host/api/change_mode.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String compurl=R.string.BASEURL+"change_mode.php";
                    Log.i("compppurl",compurl);
                    Log.i("responseuserHost", response);



                    //   arrayList=new ArrayList<>();

                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Log.e("response=",response);


                    try {



                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i <= jsonArray.length(); i++) {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);




                        }


                    }
                    catch (Exception e){
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

            params.put("oauth_uid",auth_id);
            params.put("usertype",user_type);

            return params;
        }

    };

    RequestQueue requestQueue1 = Volley.newRequestQueue(this);
    requestQueue1.add(stringRequest1);


}

}

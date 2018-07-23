package net.clamour.foodevent.HostProfile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.clamour.foodevent.GuestHomeScreen.CustomAdapterDrawer;
import net.clamour.foodevent.GuestHomeScreen.GuestHomeScreen;
import net.clamour.foodevent.GuestProfile.GuestLogin;
import net.clamour.foodevent.GuestProfile.GuestProfileScreen;
import net.clamour.foodevent.R;
import net.clamour.foodevent.guestsearch.SearchActivity;
import net.clamour.foodevent.hostsidebar.CustomAdapterDrawerHost;
import net.clamour.foodevent.hostsidebar.DrawerHostBaseActivity;
import net.clamour.foodevent.hostsidebar.HealthyGuestList;
import net.clamour.foodevent.hostsidebar.HostEvents;
import net.clamour.foodevent.hostsidebar.HostProfile;
import net.clamour.foodevent.hostsidebar.RiviewRatingsHostList;
import net.clamour.foodevent.sidebar.GuestSwitchsplash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HostHomeScreen extends DrawerHostBaseActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    SharedPreferences preferences;
    String auth_id,first_name,last_name,complete_name,user_type;

    SharedPreferences login_prefrence;
    SharedPreferences Registration_preferences;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_home_screen);

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
             makeGuestasHost();
        }
        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.main_page_toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("My Healthy Host");


        setSupportActionBar(toolbar1);
        setDrawer();}



//        ListView lt = (ListView) findViewById(R.id.left_drawer);
//        String St[] = {"","","Home","My Profile","My Services","Review & Comments","Guest List","Logout"};
//        Integer imgs[] = {0,0,R.drawable.home_host,R.drawable.host_profile,R.drawable.host_event,R.drawable.host_rating,R.drawable.guestlist_host,R.drawable.logout_host,};
//
//
//        CustomAdapterDrawerHost CAD = new CustomAdapterDrawerHost(this, St, imgs);
//        lt.setAdapter(CAD);
//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.main_page_toolbar);
//        toolbar1.setTitleTextColor(Color.WHITE);
//        toolbar1.setTitle("My Healthy Host");
//
//        lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                if(position==1){
//
//                    Intent intent=new Intent(HostHomeScreen.this,GuestSwitchsplash.class);
//                    startActivity(intent);
//                }
//
//                if(position==2){
//                    Intent intent=new Intent(HostHomeScreen.this,HostHomeScreen.class);
//                    startActivity(intent);
//                }
//                if(position==3){
//
//                    Intent intent=new Intent(HostHomeScreen.this, HostProfile.class);
//                    startActivity(intent);
//                }
//
//                if(position==4){
//                    Intent intent=new Intent(HostHomeScreen.this,HostEvents.class);
//                    startActivity(intent);
//
//                }
//                if(position==5){
//                    Intent intent=new Intent(HostHomeScreen.this,RiviewRatingsHostList.class);
//                    startActivity(intent);
//
//                }
//                if(position==6){
//                    Intent intent=new Intent(HostHomeScreen.this,HealthyGuestList.class);
//                    startActivity(intent);
//
//                }
//                if(position==7){
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                            HostHomeScreen.this);
//
//                    // set title
//                    alertDialogBuilder.setTitle("Alert!");
//
//                    // set dialog message
//                    alertDialogBuilder
//                            .setMessage("Are you really want to logut")
//                            .setCancelable(false)
//                            .setPositiveButton("YES",new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,int id) {
//
//                                    login_prefrence.edit().remove("outh_id").apply();
//                                    Registration_preferences.edit().remove("outh_id").apply();
//                                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                                    intent.addCategory(Intent.CATEGORY_HOME);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
//                                    finish();
//
//                                }
//                            })
//                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // if this button is clicked, just close
//                                    // the dialog box and do nothing
//                                    dialog.cancel();
//
//                                }
//                            });
//
//                    // create alert dialog
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//
//                    // show it
//                    alertDialog.show();
//
//
//
//
//                }
//
//            }
//        });



//        actionBarDrawerToggle = new ActionBarDrawerToggle(
//                this,
//                drawerLayout,
//                toolbar1,
//                R.string.app_name,
//                R.string.app_name
//        )
//
//        {
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                invalidateOptionsMenu();
//                syncState();
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                invalidateOptionsMenu();
//                syncState();
//            }
//        };
//        drawerLayout.setDrawerListener(actionBarDrawerToggle);
//
//        //Set the custom toolbar
//        if (toolbar1 != null) {
//            setSupportActionBar(toolbar1);
//        }
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        actionBarDrawerToggle.syncState();
//
//
//    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//
//        }
//        switch (item.getItemId()) {
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }

    public void makeGuestasHost(){

        pDialog = new ProgressDialog(HostHomeScreen.this);
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

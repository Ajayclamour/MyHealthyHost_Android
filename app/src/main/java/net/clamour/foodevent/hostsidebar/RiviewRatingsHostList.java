package net.clamour.foodevent.hostsidebar;

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
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.clamour.foodevent.GuestHomeScreen.RecentActivityStorage;
import net.clamour.foodevent.R;
import net.clamour.foodevent.guestsearch.EventsNearMe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RiviewRatingsHostList extends DrawerHostBaseActivity {
    ListView listView;
    ArrayList<HostRiviewModal>host_riview_array;
    HostRiviewAdapter hostRiviewAdapter;
    ProgressDialog pDialog;
    Boolean isSucess;
    SharedPreferences Registration_preferences;
    SharedPreferences login_prefrence;
    String user_outh_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riview_ratings_host_list);

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.main_page_toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("GUEST REVIEW");

        setSupportActionBar(toolbar1);
        setDrawer();

        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        login_prefrence = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);

        user_outh_id=Registration_preferences.getString("outh_id","");
        user_outh_id=login_prefrence.getString("outh_id","");
        Log.i("user_outhid",user_outh_id);


        listView=(ListView)findViewById(R.id.list);
        host_riview_array=new ArrayList<>();
        hostRiviewAdapter=new HostRiviewAdapter(RiviewRatingsHostList.this,host_riview_array);
        listView.setAdapter(hostRiviewAdapter);

        addRiview();
    }

public void addRiview(){

    pDialog = new ProgressDialog(RiviewRatingsHostList.this);
    pDialog.setMessage("Please wait...");
    pDialog.setCancelable(true);


    StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/reviews.php",
            new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                    Log.i("reviewhost", response);


                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Log.e("response=",response);


                    try {

                        JSONObject jsonObject=new JSONObject(response);
                         isSucess=jsonObject.getBoolean("status");
                         Log.i("status",isSucess.toString());
                        String response_data=jsonObject.getString("result");
                        Log.i("responsedata",response_data);




                        JSONArray jsonArray = new JSONArray(response_data);
                        for (int i = 0; i <= jsonArray.length(); i++) {
                            HostRiviewModal hostRiviewModal=new HostRiviewModal();
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            String ratingstar=jsonObject1.getString("rating");
                            Log.i("rating",ratingstar);


                            hostRiviewModal.setGusetname(jsonObject1.getString("first_name")+" "+jsonObject1.getString("last_name"));
                            hostRiviewModal.setComments(jsonObject1.getString("message"));
                            hostRiviewModal.setGuest_image("http://myhealthyhost.com/"+jsonObject1.getString("picture"));
                            hostRiviewModal.setRating_date(jsonObject1.getString("date"));
                            hostRiviewModal.setRating_count(Integer.valueOf(jsonObject1.getString("rating")));





//                            recentActivityStorage.setFirst_name(jsonObject1.getString("first_name"));
//                            recentActivityStorage.setLast_name(jsonObject1.getString("last_name"));
//                            recentActivityStorage.setAddress(address+""+city);
//                            recentActivityStorage.setCity(jsonObject1.getString("city"));
//                            recentActivityStorage.setCountry(jsonObject1.getString("country"));
//                            recentActivityStorage.setMy_offered_cuisine_host(jsonObject1.getString("my_offered_cuisine_host"));
//                            recentActivityStorage.setProfession(jsonObject1.getString("profession"));
//                            recentActivityStorage.setService_host(jsonObject1.getString("service_host"));
//                            recentActivityStorage.setOauth_uid(jsonObject1.getString("oauth_uid"));
//                            recentActivityStorage.setPicture(Complete_url);

                            host_riview_array.add(hostRiviewModal);


                        }


                    }

                    catch (Exception e){
                    }
                    hostRiviewAdapter.notifyDataSetChanged();
                    if(isSucess==false){

                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                RiviewRatingsHostList.this).create();

                        // Setting Dialog Title
                        alertDialog.setTitle("                 Alert!");

                        // Setting Dialog Message
                        alertDialog.setMessage("                   No Reviews");

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
                                alertDialog.dismiss();
                            }
                        });

                        // Showing Alert Message
                        alertDialog.show();
                    }

                }
            },
            new com.android.volley.Response.ErrorListener() {
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
            params.put("hostid","5af05002e9ee1");




            return params;
        }

    };

    RequestQueue requestQueue1 = Volley.newRequestQueue(this);
    requestQueue1.add(stringRequest1);

}
//    @Override
//
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()) {
//
//            case android.R.id.home:
//                // Respond to the action bar's Up/Home button
//                // adapter.notifyDataSetChanged();
//
//                finish();
//                // NavUtils.navigateUpFromSameTask(this);
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//
//    }
}

package net.clamour.foodevent.sidebar;

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
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.clamour.foodevent.EventDescriptionGuest;
import net.clamour.foodevent.GuestHomeScreen.GuestHomeScreen;
import net.clamour.foodevent.GuestProfile.GuestLogin;
import net.clamour.foodevent.R;
import net.clamour.foodevent.catering.Catering_Evet_Details;
import net.clamour.foodevent.catering.Catering_eventlist;
import net.clamour.foodevent.eventlist.EventListStorage;
import net.clamour.foodevent.guestsearch.SearchEventListDetails;
import net.clamour.foodevent.privateevent.Privateevent_Description;
import net.clamour.foodevent.tiffindelivery.TiffinDeliveryDescription;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WishlistGuest extends AppCompatActivity {

    ListView listView;
    WishListAdapter wishListAdapter;
    ArrayList<wishListStorage> wishlist_array;
    ProgressDialog pDialog;

    SharedPreferences login_prefrence;
    SharedPreferences Registration_preferences;
    String host_id;
    String host_name,host_image,event_name,event_price,event_location,cateogry;
    Boolean isSucess;
    String service_id,hostid,cateogry_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_guest);
        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("FAVOURITES");


        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        login_prefrence = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);

        host_id=Registration_preferences.getString("outh_id","");
        Log.i("auth_iduseeeerrrrrr",host_id);


        host_id=login_prefrence.getString("outh_id","");
        Log.i("auth_idlogin",host_id);


        listView=(ListView)findViewById(R.id.list);
        wishlist_array=new ArrayList<>();
        wishListAdapter=new WishListAdapter(WishlistGuest.this,wishlist_array);
        listView.setAdapter(wishListAdapter);


        addWishlist();
        listClick();
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
//    public void addWishlist(){
//        wishListStorage wishListStorage = new wishListStorage("Dinner Night","Pooja Kumari","Catering",R.drawable.user,"56$","Paris");
//            wishlist_array.add(wishListStorage);
//
//        wishListStorage wishListStorage1 = new wishListStorage("Dinner Night","Pooja Kumari","Catering",R.drawable.user,"56$","Paris");
//        wishlist_array.add(wishListStorage1);
//
//        wishListStorage wishListStorage2 = new wishListStorage("Dinner Night","Pooja Kumari","Catering",R.drawable.user,"56$","Paris");
//        wishlist_array.add(wishListStorage2);
//
//        wishListStorage wishListStorage3 = new wishListStorage("Dinner Night","Pooja Kumari","Catering",R.drawable.user,"56$","Paris");
//        wishlist_array.add(wishListStorage3);
//
//        wishListStorage wishListStorage4 = new wishListStorage("Dinner Night","Pooja Kumari","Catering",R.drawable.user,"56$","Paris");
//        wishlist_array.add(wishListStorage4);
//    }

    public void addWishlist(){
        pDialog = new ProgressDialog(WishlistGuest.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/wishlist_show.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                        Log.i("wishlistresponse", response);


                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        Log.e("response=",response);


                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <= jsonArray.length(); i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                isSucess=jsonObject.getBoolean("success");
                                Log.i("sucess",isSucess.toString());
                                String userrrrr=jsonObject.getString("userinfo");
                                Log.i("userrr",userrrrr);

                                JSONArray jsonArray1=new JSONArray(userrrrr);
                                for (int j=0;j<=jsonArray1.length();j++){
                                    wishListStorage wishListStorage=new wishListStorage();

                                    JSONObject jsonObject1=jsonArray1.getJSONObject(j);
                                    String host_imageurl=jsonObject1.getString("eventimage");
                                    host_image="http://myhealthyhost.com/"+host_imageurl;
                                    cateogry_name=jsonObject1.getString("servicetype");

                                    String event_title=jsonObject1.getString("eventtittle");
                                    Log.i("eventtitle",event_title);

                                    wishListStorage.setAddress(jsonObject1.getString("location"));
                                    wishListStorage.setEventName(jsonObject1.getString("eventtittle"));
                                    wishListStorage.setHostName(jsonObject1.getString("hostname"));
                                    wishListStorage.setServiceName(jsonObject1.getString("servicetype"));
                                   wishListStorage.setHostIcon(host_image);
                                    wishListStorage.setPrice(jsonObject1.getString("eventprice"));
                                    wishListStorage.setWishId(jsonObject1.getString("wishid"));
                                    wishListStorage.setCateogryname(jsonObject1.getString("servicetype"));
                                    wishListStorage.setHostid(jsonObject1.getString("hostid"));
                                    wishListStorage.setServiceid(jsonObject1.getString("serviceid"));


                                    wishlist_array.add(wishListStorage);
                                }



                              //  event_name=jsonObject.getString("eventtittle");
                                //event_price=jsonObject.getString("eventprice");


                              //  Log.i("host_image",host_image);
//                                isSucess=jsonObject.getBoolean("success");
//                                Log.i("sucess",isSucess.toString());





                            }


                        }

                        catch (Exception e){
                        }
                        wishListAdapter.notifyDataSetChanged();

//                        if(isSucess==false) {
//
//
//
//
//                            // Intent i = new Intent(GuestRegistration.this,GuestRegistration.class);
//                            //  i.putExtra("send",item);
//                            //startActivity(i);
//
//                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
//                                    WishlistGuest.this).create();
//
//                            // Setting Dialog Title
//                            alertDialog.setTitle("                 Alert!");
//
//                            // Setting Dialog Message
//                            alertDialog.setMessage("No Items Available in Wishlist");
//
//                            // Setting Icon to Dialog
//
//
//                            // Setting OK Button
//                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
////                                    Intent intent = new Intent(Intent.ACTION_MAIN);
////                                    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
////                                    startActivity(intent);
//                                    // Write your code here to execute after dialog closed
//                                    // alertDialog.dismiss();
//                                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_LONG).show();
//
//                                    // verifyEmail();
//                                    // saveData();
//                                    Intent intent=new Intent(WishlistGuest.this,GuestHomeScreen.class);
//                                    startActivity(intent);
//
//                                }
//                            });
//
//                            // Showing Alert Message
//                            alertDialog.show();


                      //  }


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



                return params;
            }

        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);
    }

    public void listClick(){



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(cateogry_name.equals("paying")){

                    Intent intent=new Intent(WishlistGuest.this, Privateevent_Description.class);
                    intent.putExtra("host_auth_id",wishlist_array.get(position).getHostid());
                    intent.putExtra("service_id",wishlist_array.get(position).getServiceid());
                    intent.putExtra("cateogry_name",wishlist_array.get(position).getCateogryname());
                    intent.putExtra("host_auth_id",wishlist_array.get(position).getHostid());

                    startActivity(intent);
                }
                else if(cateogry_name.equals("social")){

                    Intent intent=new Intent(WishlistGuest.this, EventDescriptionGuest.class);
                    intent.putExtra("host_auth_id",wishlist_array.get(position).getHostid());
                    intent.putExtra("service_id",wishlist_array.get(position).getServiceid());
                    intent.putExtra("cateogry_name",wishlist_array.get(position).getCateogryname());
                    intent.putExtra("host_auth_id",wishlist_array.get(position).getHostid());
                    startActivity(intent);
                }
                else if(cateogry_name.equals("tiffin")){

                    Intent intent=new Intent(WishlistGuest.this, TiffinDeliveryDescription.class);
                    intent.putExtra("host_auth_id",wishlist_array.get(position).getHostid());
                    intent.putExtra("service_id",wishlist_array.get(position).getServiceid());
                    intent.putExtra("cateogry_name",wishlist_array.get(position).getCateogryname());
                    intent.putExtra("host_auth_id",wishlist_array.get(position).getHostid());
                    startActivity(intent);
                }
                else if(cateogry_name.equals("catering")){

                    Intent intent=new Intent(WishlistGuest.this, Catering_Evet_Details.class);
                    intent.putExtra("host_auth_id",wishlist_array.get(position).getHostid());
                    intent.putExtra("service_id",wishlist_array.get(position).getServiceid());
                    intent.putExtra("cateogry_name",wishlist_array.get(position).getCateogryname());
                    intent.putExtra("host_auth_id",wishlist_array.get(position).getHostid());
                    startActivity(intent);
                }
            }
        });

    }
}

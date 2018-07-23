package net.clamour.foodevent.sidebar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.clamour.foodevent.GuestHomeScreen.CompleteHostProfileDetails;
import net.clamour.foodevent.GuestHomeScreen.GuestRiview;
import net.clamour.foodevent.R;
import net.clamour.foodevent.hostsidebar.HostRiviewModal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Clamour on 11/24/2017.
 */

public class WishListAdapter extends ArrayAdapter<wishListStorage> {

    Activity context;
    ArrayList<wishListStorage> wishlist_array;

    String host_id;
    SharedPreferences login_prefrence;
    SharedPreferences Registration_preferences;


    @BindView(R.id.event_title) TextView event_title;
    @BindView(R.id.host_name)TextView host_name;
    @BindView(R.id.service_name)TextView service_name;
    @BindView(R.id.price)TextView price;
    @BindView(R.id.address)TextView address;
    //@BindView(R.id.rating)ImageView rating;
   // @BindView(R.id.event_image)ImageView event_image;
    @BindView(R.id.image_guest)ImageView host_image;
    @BindView(R.id.del_wishlisht)ImageView delete_icon;
    ProgressDialog pDialog;
    Boolean isSucess;




    public WishListAdapter(Activity context,ArrayList<wishListStorage>wishlist_array){
        super(context, R.layout.wishlist_items);

        this.context=context;
        this.wishlist_array=wishlist_array;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.wishlist_items,null,true);
        ButterKnife.bind(this,view);

        Registration_preferences = context.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        login_prefrence = context.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);

        host_id=Registration_preferences.getString("outh_id","");
        Log.i("auth_iduseeeerrrrrr",host_id);


        host_id=login_prefrence.getString("outh_id","");
        Log.i("auth_idlogin",host_id);

        event_title.setText(wishlist_array.get(position).getEventName());
        host_name.setText(wishlist_array.get(position).getHostName());
        service_name.setText(wishlist_array.get(position).getServiceName());
        price.setText("CAD "+wishlist_array.get(position).getPrice());
        address.setText(wishlist_array.get(position).getAddress());
        final String wishid=wishlist_array.get(position).getWishId();
        Log.i("wishid",wishid);


        Glide.with(context).load(wishlist_array.get(position).getHostIcon())
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(host_image);

        delete_icon.setTag(position);
        delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new ProgressDialog(context);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(true);


                StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/delete_wishlist.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                                Log.i("wishlistdel", response);


                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Log.e("response=",response);
                                wishlist_array.remove(wishlist_array.get(position));
                                notifyDataSetChanged();


                                try {

                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i <= jsonArray.length(); i++){
                                        wishListStorage wishListStorage=new wishListStorage();
                                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                                        isSucess=jsonObject.getBoolean("success");




                                    }


                                }

                                catch (Exception e){
                                }
                                notifyDataSetChanged();
                              //  wishListAdapter.notifyDataSetChanged();
                                if(isSucess==true){

                                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                            context).create();

                                    // Setting Dialog Title
                                    alertDialog.setTitle("                 Alert!");

                                    // Setting Dialog Message
                                    alertDialog.setMessage("WishList Removed");

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
                        params.put("user_id",host_id);
                        params.put("wish_id",wishid);


                        return params;
                    }

                };

                RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                requestQueue1.add(stringRequest1);
            }


        });






        return view;


    }

    @Override
    public int getCount() {
        return wishlist_array.size();
    }
}
package net.clamour.foodevent.hostsidebar;

/**
 * Created by clamour_5 on 1/30/2018.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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

import net.clamour.foodevent.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class CustomAdapterDrawerHost extends ArrayAdapter<String> {

    String web[];
    Integer img[];
    Activity context;

    SharedPreferences login_prefrence;
    SharedPreferences Registration_preferences;
    SharedPreferences profile_prefrence;
    String firstname_up,lastname_up,guest_profileimage_up,completeguestimage_url;

    String auth_id,first_name,last_name,complete_name,email,image_url;
    TextView user_name;
    ImageView profile_image;


    public CustomAdapterDrawerHost(Activity context, String[] web,
                               Integer[] img) {
        super(context, android.R.layout.simple_list_item_1, web);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.img=img;
        this.web=web;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.drawer_items_host,null,true);
//        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, 150);
//        rowView.setLayoutParams(params);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.compalint_row_text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.complaint_row_img);

        Typeface face2 = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
        Typeface face3 = Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf");
//
        txtTitle.setTypeface(face3);


        txtTitle.setText(web[position]);
        imageView.setImageResource(img[position]);


        if(position==0){
            rowView=inflater.inflate(R.layout.profile_row_host,null,true);
            //      AbsListView.LayoutParams params1 = new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, 240);
            //   rowView.setLayoutParams(params1);



            user_name=(TextView)rowView.findViewById(R.id.profile_name);
             profile_image=(ImageView)rowView.findViewById(R.id.circleView) ;


            Registration_preferences = context.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestRegistration", MODE_PRIVATE);
            login_prefrence = context.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);
            profile_prefrence= context.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestProfileScreen", MODE_PRIVATE);

            auth_id=Registration_preferences.getString("outh_id","");
            Log.i("auth_id",auth_id);

            auth_id=login_prefrence.getString("outh_id","");
            if(Registration_preferences.contains("outh_id")){
                saveUpdatedProfile();

            }
            else if(login_prefrence.contains("outh_id")){
                saveUpdatedProfile();
            }

//            image_url=profile_prefrence.getString("guset_profileimage","");
//            Log.i("imageurlpro",image_url);
//

//
//            String firstnameprofile=profile_prefrence.getString("first_name_up","");
//
//            Log.i("firstnamrprofile",firstnameprofile);
//
//            String lastnameprofile=profile_prefrence.getString("last_name_up","");
//
//            Log.i("lastnameprofile",lastnameprofile);
//

//
//





//            ImageView user_image=(ImageView) rowView.findViewById(R.id.profileImageView);
//
//            Typeface face1 = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
//            Typeface face0 = Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf");
//
            // user_name.setTypeface(face1);
//            user_detail.setTypeface(face5);
            //user_name.setText("Hi"+" Clamour");


        }
        if(position==1){
            rowView=inflater.inflate(R.layout.guest_swap,null,true);



        }











        return rowView;
    }
    public void saveUpdatedProfile(){
    StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://myhealthyhost.com/api/profile_details.php",
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(JobDetails.this, response, Toast.LENGTH_LONG).show();

                    Log.i("responseupdatedprofile", response);






                    Log.e("response=",response);


                    try {

                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i <= jsonArray.length(); i++) {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            firstname_up=jsonObject.getString("first_name");
                            Log.i("first_nameupdated",firstname_up);
                            lastname_up=jsonObject.getString("last_name");


                            guest_profileimage_up=jsonObject.getString("picture");
                            Log.i("guset_profileupp",guest_profileimage_up);


                            completeguestimage_url="http://myhealthyhost.com/"+guest_profileimage_up;
                            Log.i("gusetimage",completeguestimage_url);


                        }


                    }
                    catch (Exception e){
                    }
                 setData();

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

    RequestQueue requestQueue1 = Volley.newRequestQueue(context);
        requestQueue1.add(stringRequest1);


}

public void setData(){

    Glide.with(context).load(completeguestimage_url)
            .thumbnail(0.5f)
            .crossFade()
            .placeholder(0)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(profile_image);
    user_name.setText(firstname_up+" "+lastname_up);
    }
}

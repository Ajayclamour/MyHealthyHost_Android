package net.clamour.foodevent.GuestHomeScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.clamour.foodevent.ItemClickListener;
import net.clamour.foodevent.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by clamour_5 on 1/2/2018.
 */

public class RecentActivityAdapter extends ArrayAdapter<RecentActivityStorage> {


        public final Activity context;
        ItemClickListener itemClickListener;

        ArrayList<RecentActivityStorage> recent_activity_array;

       @BindView(R.id.host_image)ImageView host_image;
       @BindView(R.id.host_name)TextView host_name;
       @BindView(R.id.host_address)TextView host_address;
      // @BindView(R.id.cusine_offered)TextView cusine_offered;
      // @BindView(R.id.host_proffesion)TextView host_proffesion;
       @BindView(R.id.view_more)TextView view_more;


       String host_name_st,host_address_st,host_email_st,host_mobile_st,host_cusine_st,host_proffession_st,host_services_st,outh_provider_st,picture_st;




        public RecentActivityAdapter(Activity context,ArrayList<RecentActivityStorage>recent_activity_array){
            super(context,R.layout.recentactivitylist,recent_activity_array);

            this.recent_activity_array=recent_activity_array;
            this.context=context;



        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final LayoutInflater inflater=context.getLayoutInflater();
            View view=inflater.inflate(R.layout.recentactivitylist,null,true);

            ButterKnife.bind(this,view);



//            Glide.with(context).load(recent_activity_array.get(position).getPicture())
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .placeholder(0)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(host_image);



            String first_name=recent_activity_array.get(position).getFirst_name();
            Log.d(TAG, "getView: "+first_name);
            String last_name=recent_activity_array.get(position).getLast_name();
            Log.d(TAG, "getView: "+last_name);
            String city=recent_activity_array.get(position).getCity();
            String address=recent_activity_array.get(position).getAddress();
            Log.d(TAG, "getView: "+address);
            String country=recent_activity_array.get(position).getCountry();
            Log.d(TAG, "getView: "+country);
            host_cusine_st=recent_activity_array.get(position).getUsername_host();
            Log.d(TAG, "getView: "+host_cusine_st);
             host_proffession_st=recent_activity_array.get(position).getProfession();
            Log.d(TAG, "getView: "+host_proffession_st);

             host_email_st=recent_activity_array.get(position).getEmail();
            Log.d(TAG, "getView: "+host_email_st);
             host_mobile_st=recent_activity_array.get(position).getPhone();
            Log.d(TAG, "getView: "+host_mobile_st);
             host_services_st=recent_activity_array.get(position).getService_host();
             Log.i("service_host",host_services_st);


            host_address_st=address+","+city+","+country;

          //  cusine_offered.setText(host_cusine_st);
          //  host_proffesion.setText(host_proffession_st);

            picture_st=recent_activity_array.get(position).getPicture();
//            Log.i("picturerecentadapter",picture_st);
//            outh_provider_st=recent_activity_array.get(position).getOauth_provider();
//            Log.i("outh_provider",outh_provider_st);
            String image_url="http://myhealthyhost.com/"+picture_st;

//            if (outh_provider_st.equals("")){
//
//
                Glide.with(context).load(image_url)
                        .thumbnail(0.5f)
                        .crossFade()
                        .placeholder(0)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(host_image);
       //     }
//
//            else if(outh_provider_st.equals("google")){
//
//                Glide.with(context).load(image_url)
//                        .thumbnail(0.5f)
//                        .crossFade()
//                        .placeholder(0)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(host_image);
//            }



            host_name_st=first_name+" "+last_name;
            host_name.setText(host_cusine_st);

            host_address.setText(host_address_st);
            host_image.setTag(position);
            host_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context,CompleteHostProfileDetails.class);
                    int position=(Integer)v.getTag();
                    Log.i("positionlist",position+"");
                    intent.putExtra("position",position);
                    intent.putExtra("host_firstname",recent_activity_array.get(position).getFirst_name());
                    intent.putExtra("host_lastname",recent_activity_array.get(position).getLast_name());
                    intent.putExtra("cusine_name",recent_activity_array.get(position).getUsername_host());
                    intent.putExtra("proffession",recent_activity_array.get(position).getProfession());
                    intent.putExtra("address",recent_activity_array.get(position).getAddress());
                    intent.putExtra("email",recent_activity_array.get(position).getEmail());
                    intent.putExtra("mobile_no",recent_activity_array.get(position).getPhone());
                    intent.putExtra("service_name",recent_activity_array.get(position).getService_host());
                    intent.putExtra("auth_provider",recent_activity_array.get(position).getOauth_provider());
                    intent.putExtra("picture",recent_activity_array.get(position).getPicture());
                    intent.putExtra("address",recent_activity_array.get(position).getAddress());
                    intent.putExtra("city",recent_activity_array.get(position).getCity());
                    intent.putExtra("country",recent_activity_array.get(position).getCountry());
                    intent.putExtra("language",recent_activity_array.get(position).getLanguage());
                    intent.putExtra("currency",recent_activity_array.get(position).getCurrency());
                    intent.putExtra("gender",recent_activity_array.get(position).getGender());
                    intent.putExtra("description",recent_activity_array.get(position).getDescription());
                    intent.putExtra("rating",String.valueOf(recent_activity_array.get(position).getRatting()));

                    context.startActivity(intent);
                }
            });




            return view;
        }

    @Override
    public int getCount() {
        return recent_activity_array.size();
    }
}
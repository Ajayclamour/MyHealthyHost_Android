package net.clamour.foodevent.hostsidebar;

/**
 * Created by clamour_5 on 1/31/2018.
 */

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.clamour.foodevent.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Clamour on 11/24/2017.
 */

public class HostRiviewAdapter extends ArrayAdapter<HostRiviewModal> {

    Activity context;
    ArrayList<HostRiviewModal> Host_riview_array;

    @BindView(R.id.guest_name) TextView Guestname;
    @BindView(R.id.rating_date)TextView rating_date;
    @BindView(R.id.ratingBar)RatingBar ratingBar;
   // @BindView(R.id.event_name)TextView event_name;
    @BindView(R.id.guest_comment)TextView guest_comment;
    @BindView(R.id.guest_image)ImageView guest_image;



    public HostRiviewAdapter(Activity context,ArrayList<HostRiviewModal>Host_riview_array){
        super(context, R.layout.hostriview);

        this.context=context;
        this.Host_riview_array=Host_riview_array;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.hostriview,null,true);
        ButterKnife.bind(this,view);

        Guestname.setText(Host_riview_array.get(position).getGusetname());
        guest_comment.setText(Host_riview_array.get(position).getComments());
       // guest_image.setImageResource(Host_riview_array.get(position).getGuest_image());
        rating_date.setText(Host_riview_array.get(position).getRating_date());
        //rating_count.setText(Host_riview_array.get(position).getRating_count());
       // event_name.setText(Host_riview_array.get(position).getEvent_name());

        Glide.with(context).load(Host_riview_array.get(position).getGuest_image())
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(R.drawable.user)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(guest_image);
ratingBar.setRating(Host_riview_array.get(position).getRating_count());

        //Drawable drawable = ratingBar.getProgressDrawable();
      //  drawable.setColorFilter(Color.parseColor("#d5473b"), PorterDuff.Mode.SRC_ATOP);





        return view;


    }

    @Override
    public int getCount() {
        return Host_riview_array.size();
    }
}
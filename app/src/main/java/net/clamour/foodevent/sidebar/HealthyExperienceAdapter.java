package net.clamour.foodevent.sidebar;

/**
 * Created by clamour_5 on 4/2/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.clamour.foodevent.GuestHomeScreen.GuestRiview;
import net.clamour.foodevent.R;
import net.clamour.foodevent.eventlist.EventListStorage;
import net.clamour.foodevent.eventlist.ItemClickListener;
import net.clamour.foodevent.guestsearch.EventsNearMeStorage;

import java.util.ArrayList;


public class HealthyExperienceAdapter extends RecyclerView.Adapter<net.clamour.foodevent.sidebar.HealthyExperienceAdapter.MyViewHolder> {

    Context context;
    ArrayList<EventListStorage> event_list_current_location;
    ItemClickListener clickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView event_name,event_hostname,event_price;
        public ImageView event_image,host_image;
        public Button add_riview_button;

        public MyViewHolder(View view){
            super(view);

            event_name=(TextView)view.findViewById(R.id.event_title_history);
            event_hostname=(TextView)view.findViewById(R.id.event_hostname_history);
            event_price=(TextView)view.findViewById(R.id.event_price_history);


            event_image=(ImageView)view.findViewById(R.id.event_image_history);
            host_image=(ImageView)view.findViewById(R.id.host_image_history);
            add_riview_button=(Button)view.findViewById(R.id.add_riview_button);


            view.setTag(view);
            view.setOnClickListener(this);







        }
        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
    public HealthyExperienceAdapter(Context context,ArrayList<EventListStorage>event_list_current_location){
        this.context=context;
        this.event_list_current_location=event_list_current_location;
    }

    @Override
    public void onBindViewHolder(net.clamour.foodevent.sidebar.HealthyExperienceAdapter.MyViewHolder holder, int position) {
        final EventListStorage eventListStorage=event_list_current_location.get(position);



//        holder.event_title.setText(eventListStorage.getEvent_title());
//        holder.event_price.setText(eventListStorage.getEvent_price());
//        holder.event_location.setText(eventListStorage.getEvent_location());
//        holder.rating_count.setText(eventListStorage.getRating_count());
//
//        holder.image_person.setImageResource(eventListStorage.getImage_person());
//        holder.event_image.setImageResource(eventListStorage.getImage_event());

        holder.event_name.setText(eventListStorage.getEventNameHistory());
        holder.event_hostname.setText(eventListStorage.getHostNameHistory());
        holder.event_price.setText("CAD "+eventListStorage.getEventPriceHistory());

        Glide.with(context).load(eventListStorage.getEventImageHistory())
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.event_image);

        Glide.with(context).load(eventListStorage.getHostImageHistory())
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.host_image);

        holder.add_riview_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,GuestRiview.class);
                intent.putExtra("hostid",eventListStorage.getHostIdHistory());
                context.startActivity(intent);
            }
        });





//        Typeface face1 = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
//        Typeface face2 = Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf");




    }

    @Override
    public net.clamour.foodevent.sidebar.HealthyExperienceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.healthyexperiencelist,parent,false);
        return new net.clamour.foodevent.sidebar.HealthyExperienceAdapter.MyViewHolder(itemView);

    }

    @Override
    public int getItemCount() {
        return event_list_current_location.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
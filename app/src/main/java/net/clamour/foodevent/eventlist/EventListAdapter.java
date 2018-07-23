package net.clamour.foodevent.eventlist;

/**
 * Created by clamour_5 on 12/21/2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.clamour.foodevent.R;

import java.util.ArrayList;

/**
 * Created by clamour_5 on 12/8/2017.
 */

public class EventListAdapter extends RecyclerView.Adapter<net.clamour.foodevent.eventlist.EventListAdapter.MyViewHolder> {

    Context context;
    ArrayList<EventListStorage> event_list;
    ItemClickListener clickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView event_title,event_location,rating_count,event_price;
        public ImageView event_image,image_person;

        public MyViewHolder(View view){
            super(view);

         event_title=(TextView)view.findViewById(R.id.event_title);
         event_location=(TextView)view.findViewById(R.id.event_location);
         rating_count=(TextView)view.findViewById(R.id.rating_count);
         event_price=(TextView)view.findViewById(R.id.event_price);

         event_image=(ImageView)view.findViewById(R.id.event_image);
         image_person=(ImageView)view.findViewById(R.id.image_guest);


            view.setTag(view);
            view.setOnClickListener(this);







        }
        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
    public EventListAdapter(Context context,ArrayList<EventListStorage>event_list){
        this.context=context;
        this.event_list=event_list;
    }

    @Override
    public void onBindViewHolder(net.clamour.foodevent.eventlist.EventListAdapter.MyViewHolder holder, int position) {
        EventListStorage eventListStorage=event_list.get(position);



        holder.event_title.setText(eventListStorage.getCat_title());
        holder.event_price.setText("CAD "+""+eventListStorage.getCat_price());
        holder.event_location.setText(eventListStorage.getAddress());
        //holder.rating_count.setText(eventListStorage.getRating_count());

        Glide.with(context).load(event_list.get(position).getPicture())
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image_person);

        Glide.with(context).load(event_list.get(position).getCat_coverimg())
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.event_image);


//        Typeface face1 = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
//        Typeface face2 = Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf");




    }

    @Override
    public net.clamour.foodevent.eventlist.EventListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.eventlist_guest,parent,false);
        return new net.clamour.foodevent.eventlist.EventListAdapter.MyViewHolder(itemView);

    }

    @Override
    public int getItemCount() {
        Log.i("sizeeeee",event_list.size()+"");
        return event_list.size();
    }



    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;



    }
}
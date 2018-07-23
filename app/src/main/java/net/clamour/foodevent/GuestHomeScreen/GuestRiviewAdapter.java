package net.clamour.foodevent.GuestHomeScreen;

/**
 * Created by clamour_5 on 4/24/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.clamour.foodevent.ItemClickListener;
import net.clamour.foodevent.R;
import net.clamour.foodevent.eventlist.EventListStorage;

import java.util.ArrayList;

public class GuestRiviewAdapter extends RecyclerView.Adapter<GuestRiviewAdapter.ViewHolder> {

    ArrayList<EventListStorage>guest_riview_array;
    Context context;

    public GuestRiviewAdapter(Context context,ArrayList<EventListStorage>guest_riview_array) {
        super();
        this.context = context;
        this.guest_riview_array = guest_riview_array;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.riview_rating_items, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.guest_name.setText(guest_riview_array.get(position).getGuestname_riview());
       viewHolder.riview_message.setText(guest_riview_array.get(position).getRiview_message());
       viewHolder.riview_date.setText(guest_riview_array.get(position).getRiview_date());



        Glide.with(context).load(guest_riview_array.get(position).getGuest_image_riview())
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.guest_image);


        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
//                if (isLongClick) {
//                    Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
//                    context.startActivity(new Intent(context, MainActivity.class));
//                } else {
                   // Toast.makeText(context, "#" + position + " - " + alName.get(position), Toast.LENGTH_SHORT).show();
               // }
            }
        });
    }

    @Override
    public int getItemCount() {
        return guest_riview_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView guest_image;
        public TextView guest_name;
        public TextView riview_date;
        public TextView riview_message;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            guest_image = (ImageView) itemView.findViewById(R.id.guest_image);
            guest_name = (TextView) itemView.findViewById(R.id.user_name);
            riview_date=(TextView)itemView.findViewById(R.id.riview_date);
            riview_message=(TextView)itemView.findViewById(R.id.riview_message);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

}
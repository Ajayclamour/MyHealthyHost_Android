package net.clamour.foodevent.hostsidebar;

/**
 * Created by Clamour on 11/27/2017.
 */

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import net.clamour.foodevent.R;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Clamour on 11/27/2017.
 */


/**
 * Created by Clamour on 11/22/2017.
 */

public class OrderAdapter extends ArrayAdapter<OrderStorage> {

     Activity context;

    ArrayList<OrderStorage> order_array;
@BindView(R.id.name_order)TextView name;
@BindView(R.id.service_order)TextView service_order;
@BindView(R.id.order_no)TextView order_no;
@BindView(R.id.date_order)TextView date_order;
@BindView(R.id.price_order)TextView price_order;
@BindView(R.id.time_order)TextView time_order;



    public OrderAdapter(Activity context, ArrayList<OrderStorage> order_array){
        super(context, R.layout.ordersitems,order_array);

        this.order_array=order_array;
        this.context=context;


    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View view=inflater.inflate(R.layout.ordersitems,null,true);

        ButterKnife.bind(this,view);

        order_no.setText(order_array.get(position).getOrderno());
        date_order.setText(order_array.get(position).getOrderdate());
        price_order.setText("CAD "+order_array.get(position).getPrice());
        service_order.setText(order_array.get(position).getService());
        time_order.setText(order_array.get(position).getOrderTime());
        name.setText(order_array.get(position).getName());










        return view;
    }

    @Override
    public int getCount() {
        return order_array.size();
    }
}
package net.clamour.foodevent.guestsearch;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import net.clamour.foodevent.R;
import net.clamour.foodevent.eventfilteroption.Cuisine;
import net.clamour.foodevent.eventfilteroption.EventTypes;
import net.clamour.foodevent.eventfilteroption.FoodPrefrences;
import net.clamour.foodevent.eventfilteroption.Languges;
import net.clamour.foodevent.eventfilteroption.PriceperGuest;
import net.clamour.foodevent.eventfilteroption.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventFilterScreen extends AppCompatActivity {
    @BindView(R.id.date_linear)LinearLayout date_linear;
    @BindView(R.id.service_linear)LinearLayout service_linear;
   // @BindView(R.id.noofguest_linear)LinearLayout noofguest_linear;
    @BindView(R.id.cusine_linear)LinearLayout cuisine_linear;
    //@BindView(R.id.foodpre_linear)LinearLayout foodpref_linear;
   // @BindView(R.id.language_linear)LinearLayout language_linear;
    @BindView(R.id.add_image)ImageView addition;
    @BindView(R.id.subtract_image)ImageView subtraction;
    @BindView(R.id.guest_text)TextView guestno_text;
    @BindView(R.id.save_filter)Button Save_filter;

   public static TextView selected_date_text1;
    int minteger = 0;
    String ppp;

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
public static String selected_date_st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_filter_screen);
        ButterKnife.bind(this);
        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("Filters");


        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Save_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventFilterScreen.this,EventsNearMe.class);
                startActivity(intent);
            }
        });



        selected_date_text1=(TextView)findViewById(R.id.date_selection_text);

        date_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderClick();
            }
        });

                service_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventFilterScreen.this, Service.class);
                startActivity(intent);
            }
        });


//        event_type_linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(EventFilterScreen.this, EventTypes.class);
//                startActivity(intent);
//            }
//        });
//
//        priceperguest_linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(EventFilterScreen.this, PriceperGuest.class);
//                startActivity(intent);
//            }
//        });
        cuisine_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventFilterScreen.this, Cuisine.class);
                startActivity(intent);
            }
        });
//        foodpref_linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(EventFilterScreen.this, FoodPrefrences.class);
//                startActivity(intent);
//            }
//        });
//        language_linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(EventFilterScreen.this, Languges.class);
//                startActivity(intent);
//            }
//        });
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
    public void calenderClick(){
        new EventFilterScreen.SimpleCalendarDialogFragment().show(getSupportFragmentManager(), "test-simple-calendar");
    }

    public static class SimpleDialogFragment extends AppCompatDialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new android.app.AlertDialog.Builder(getActivity())
                    .setTitle("Calender")
                    .setMessage("Test Dialog")
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        }
    }

    public static class SimpleCalendarDialogFragment extends AppCompatDialogFragment implements OnDateSelectedListener {

        private TextView textView;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = getActivity().getLayoutInflater();


            //inflate custom layout and get views
            //pass null as parent view because will be in dialog layout
            View view = inflater.inflate(R.layout.dialog_basic, null);

            textView = (TextView) view.findViewById(R.id.textView);

            MaterialCalendarView widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);

            widget.setOnDateChangedListener(this);

            return new android.app.AlertDialog.Builder(getActivity())

                    .setView(view)
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        }

        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            textView.setText(FORMATTER.format(date.getDate()));
            selected_date_st=textView.getText().toString();
            Log.i("date_string",selected_date_st);
          selected_date_text1.setText(selected_date_st);





        }

    }
    public void increaseInteger(View view) {
        subtraction.setEnabled(true);
        minteger = minteger + 1;
        display(minteger);

    }

    public void decreaseInteger(View view) {
        minteger = minteger - 1;
        display(minteger);
        if(minteger==0){
            subtraction.setEnabled(false);
        }
    }

    private void display(int number) {

        guestno_text.setText("" + number);
       String guest_no_st=guestno_text.getText().toString();
       Log.i("guest_no",guest_no_st);



        }
    }


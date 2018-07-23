package net.clamour.foodevent.hostsidebar;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import net.clamour.foodevent.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HostEvents extends DrawerHostBaseActivity {

    String experience[] = {"BreakFast","Dinner","Lunch","Picnic"};
    String cusine[]={"African","Asian","American"};
    String min_guest[]={"1","2","3"};
    String max_guests[]={"10","12","15"};
    String country[]={"India","Cannada","Saudi"};
    String state[]={"noida","saket","Riyadh"};
    String start_time[]={"00:00","12:00","12:30"};
    String end_time[]={"00:00","12:00","12:30"};



    @BindView(R.id.experience_spinner)Spinner experience_spinner;
    @BindView(R.id.cusine_spinner)Spinner cuisine_spinner;
    @BindView(R.id.minguest_spinner)Spinner minimumguest_spinner;
    @BindView(R.id.maxguest_spinner)Spinner maximumguest_spinner;
    @BindView(R.id.starttime_spinner)Spinner starttime_spinner;
    @BindView(R.id.endtime_spinner)Spinner endtime_spinner;
    @BindView(R.id.country_spinner)Spinner country_spinner;
    @BindView(R.id.state_spinner)Spinner state_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_events);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        ButterKnife.bind(this);
        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.main_page_toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("HOST EVENTS");

        setSupportActionBar(toolbar1);
        setDrawer();

//        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
//        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
//
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayAdapter<String> experiecne = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, experience);
        experiecne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experience_spinner.setAdapter(experiecne);


        ArrayAdapter<String> cuisineadapter= new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, cusine);
        cuisineadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisine_spinner.setAdapter(cuisineadapter);

        ArrayAdapter<String>minguest_adapter  = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, min_guest);
        minguest_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minimumguest_spinner.setAdapter(minguest_adapter);

        ArrayAdapter<String> maxguest_adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, max_guests);
        maxguest_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maximumguest_spinner.setAdapter(maxguest_adapter);

        ArrayAdapter<String> starttime_adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, start_time);
        starttime_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        starttime_spinner.setAdapter(starttime_adapter);

        ArrayAdapter<String> endtime_adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, end_time);
        endtime_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endtime_spinner.setAdapter(endtime_adapter);

        ArrayAdapter<String> country_adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, country);
        country_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(country_adapter);

        ArrayAdapter<String> state_adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, state);
        state_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state_spinner.setAdapter(state_adapter);



    }
//    @Override
//
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()) {
//
//            case android.R.id.home:
//                // Respond to the action bar's Up/Home button
//                // adapter.notifyDataSetChanged();
//
//                finish();
//                // NavUtils.navigateUpFromSameTask(this);
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//
//    }
}

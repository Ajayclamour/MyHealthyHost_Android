package net.clamour.foodevent.privateevent;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import net.clamour.foodevent.GuestHomeScreen.CompleteHostProfileDetails;
import net.clamour.foodevent.GuestProfile.GuestLogin;
import net.clamour.foodevent.GuestProfile.GuestProfileScreen;
import net.clamour.foodevent.R;
import net.clamour.foodevent.booking.ConfirmBooking;
import net.clamour.foodevent.guestsearch.EventFilterScreen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static net.clamour.foodevent.guestsearch.EventFilterScreen.selected_date_st;

public class PrivateEventBookingPage extends AppCompatActivity {
    //  @BindView(R.id.edit_address)TextView Edit_address;
    @BindView(R.id.calender_icon)ImageView calender_icon;
    @BindView(R.id.request_book)Button request_book;
    @BindView(R.id.event_nmae_booking)TextView event_name;
    @BindView(R.id.price_per_guest)TextView price_perguest_text;
    @BindView(R.id.add_icon)ImageView add_icon;
    @BindView(R.id.subtract_icon)ImageView subtract_icon;
    @BindView(R.id.guest_count)TextView guest_count;
    @BindView(R.id.total_fare_perguest)TextView total_fare_perguest;
    @BindView(R.id.booking_image_host)ImageView host_image;
    @BindView(R.id.Totalfare)TextView Totalfare;
    @BindView(R.id.select_date_)TextView selectdate_text;
    @BindView(R.id.select_time)TextView select_time;
    @BindView(R.id.selecttime_icon)ImageView selecttime_icon;

    AlertDialog alertDialog;

    public static TextView date_text_payinf;
    public static TextView guest_total;
    public static  String currentdate;


    public static TextView selected_date_text;
    int minteger = 0;
    public static String date_selected_paying;
    String selected_datee;

    // EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private static  DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    public static String selected_date_st;
    String Event_name,host_image_url,price_per_guest,host_auth_id,service_id,cateogry_name,guest_no_st,TotalFareStr,SelectedEventDate;
    public static SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

    private TimePicker timePicker1;
    private TextView time;
    private Calendar calendar;
    private String format = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_event_booking_page);
        ButterKnife.bind(this);



        currentdate = df.format(Calendar.getInstance().getTime());

        Log.i("cureentdate",currentdate.toString());


        date_text_payinf=(TextView)findViewById(R.id.select_date_) ;
        guest_total=(TextView)findViewById(R.id.guest_total);

        Intent intent=getIntent();

        Event_name=intent.getStringExtra("event_name");
        Log.i("event_name",Event_name);

        host_auth_id=intent.getStringExtra("host_auth_id");
        service_id=intent.getStringExtra("service_id");

        price_per_guest=intent.getStringExtra("event_price");

        cateogry_name=intent.getStringExtra("cateogry");
        host_image_url=intent.getStringExtra("host_image_url");


        event_name.setText(Event_name);
        price_perguest_text.setText("CAD "+price_per_guest);
        Glide.with(PrivateEventBookingPage.this).load(host_image_url)
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(host_image);
        total_fare_perguest.setText("CAD "+price_per_guest);
        Totalfare.setText("CAD "+price_per_guest);


        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("Guest Booking");


        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        Intent intent=getIntent();
//        Event_name=intent.getStringExtra("event_name");
//        Log.i("event_name",Event_name);
//
//        event_name.setText(Event_name);

        selecttime_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog();
            }
        });

        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openTimePickerDialog();

//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(GuestBooking.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        select_time.setText( selectedHour + ":" + selectedMinute);
//                    }
//                }, hour, minute, false);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();








            }
        });


        calender_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderClick();
            }
        });
        selectdate_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderClick();
            }
        });

        request_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                 if(date_selected.length()==0){
//
//                    date_text.setError("please select date");
//                }
//                else {
//                Log.i("selectddddate",date_selected);

                Intent intent = new Intent(PrivateEventBookingPage.this, ConfirmBooking.class);

                intent.putExtra("host_auth_id", host_auth_id);
                intent.putExtra("service_id", service_id);
                intent.putExtra("cateogry", cateogry_name);
                intent.putExtra("event_price", price_per_guest);
                intent.putExtra("event_name", Event_name);
                intent.putExtra("total_no_guest", guest_total.getText().toString().trim());
                intent.putExtra("eventdate", date_selected_paying);
                intent.putExtra("totalpayableamount", Totalfare.getText().toString().trim());
                intent.putExtra("bookingtime",select_time.getText().toString());
                intent.putExtra("priceperguest",price_per_guest);
                startActivity(intent);
                //   }

//                else {
//                }
            }
        });

//        Edit_address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(GuestBooking.this, GuestProfileScreen.class);
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

    public void increaseInteger(View view) {
        subtract_icon.setEnabled(true);
        minteger = minteger + 1;
        display(minteger);

    }

    public void decreaseInteger(View view) {
        minteger = minteger - 1;
        display(minteger);
        if(minteger==1){
            subtract_icon.setEnabled(false);
        }
    }

    private void display(int number) {

        guest_count.setText("" + number);
        guest_no_st=guest_count.getText().toString();
        Log.i("guest_no",guest_no_st);

        int totalguestcount=Integer.valueOf(guest_no_st);
        int totalpriceperguest=Integer.valueOf(price_per_guest);
        guest_total.setText("" + number);
        guest_total.setText(guest_no_st);

        int fare_per_guest=totalguestcount*totalpriceperguest;
        TotalFareStr=fare_per_guest+"";
        Log.i("totalfare",TotalFareStr);
        total_fare_perguest.setText("CAD "+TotalFareStr);
        Totalfare.setText("CAD "+TotalFareStr);



    }
    public void calenderClick(){
        new net.clamour.foodevent.privateevent.PrivateEventBookingPage.SimpleCalendarDialogFragment().show(getSupportFragmentManager(), "test-simple-calendar");
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
            View view = inflater.inflate(R.layout.dialogbasic1, null);

            textView = (TextView) view.findViewById(R.id.textView_paying);

            MaterialCalendarView widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);

            widget.setOnDateChangedListener(this);

            return new android.app.AlertDialog.Builder(getActivity())

                    .setView(view)
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        }

        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            if(Calendar.getInstance().getTime().after(date.getDate()))
            {
                // Toast.makeText(getContext(),"agdsah",Toast.LENGTH_LONG).show();


                final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                        getContext()).create();

                // Setting Dialog Title
                alertDialog.setTitle("                 Alert!");

                // Setting Dialog Message
                alertDialog.setMessage("   Past Date Can't be Selected");

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

                    }
                });

                // Showing Alert Message
                alertDialog.show();





            }

            textView.setText(FORMATTER.format(date.getDate()));
            date_selected_paying=textView.getText().toString();
            Log.i("date_string",date_selected_paying);
            date_text_payinf.setText(date_selected_paying);

//            if(date_selected.isEmpty()){
//
//                Toast.makeText(getContext(),"ggsdua",Toast.LENGTH_LONG).show();
//            }


            //selecteddate_text.setText(date_string);



        }

    }
    public void openTimePickerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);



        LayoutInflater inflater = LayoutInflater.from(
                PrivateEventBookingPage.this);


        View subView = inflater.inflate(R.layout.timepickerdialog, null);
        builder.setView(subView);
        alertDialog = builder.create();

        timePicker1 = (TimePicker)subView.findViewById(R.id.timePicker1);
        time = (TextView)subView.findViewById(R.id.textView1);
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);




        alertDialog.show();}

    public void setTime(View view) {
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();
        showTime(hour, min);
        alertDialog.dismiss();


    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
        select_time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }


}
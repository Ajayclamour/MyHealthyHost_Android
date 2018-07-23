package net.clamour.foodevent.booking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.clamour.foodevent.GuestHomeScreen.GuestHomeScreen;
import net.clamour.foodevent.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThankyouPage extends AppCompatActivity {
    String order_no, Payment_id;
    Button continue_shopping;
    TextView ordr_no;
    @BindView(R.id.hostedby)
    TextView hostedby;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.noofguest)
    TextView noofguest;
    @BindView(R.id.bookingdate)
    TextView bookingdate;
    @BindView(R.id.priceguest)
    TextView priceguest;
    @BindView(R.id.totalfare)
        TextView totalfare;
    @BindView(R.id.bookingtime)
    TextView bookingtime;

    String totalfare_st,noofguest_st,bookingdate_st,priceperguest_st,hostedby_st,title_st,booking_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou_page);
        ButterKnife.bind(this);

        continue_shopping = (Button) findViewById(R.id.continue_shopping);
        ordr_no = (TextView) findViewById(R.id.order_no);


        Intent intent = getIntent();
        order_no = intent.getStringExtra("order_no");
        Payment_id = intent.getStringExtra("paymentid");
        totalfare_st=intent.getStringExtra("totalFare");
        noofguest_st=intent.getStringExtra("noofguest");
        bookingdate_st=intent.getStringExtra("bookingdate");
        priceperguest_st=intent.getStringExtra("priceperguest");
        hostedby_st=intent.getStringExtra("hostedby");
        title_st=intent.getStringExtra("title");
        booking_time=intent.getStringExtra("bookingtime");

        ordr_no.setText(order_no);
        bookingtime.setText(booking_time);
        totalfare.setText("CAD "+totalfare_st);
        noofguest.setText(noofguest_st);
        priceguest.setText(priceperguest_st);
        title.setText(title_st);
        hostedby.setText(hostedby_st);
        bookingdate.setText(bookingdate_st);

        continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ThankyouPage.this, GuestHomeScreen.class);
                startActivity(intent1);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

package net.clamour.foodevent.HostProfile;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.clamour.foodevent.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsHost extends AppCompatActivity {


    @BindView(R.id.genral_terms)
    TextView genralTerms;
    @BindView(R.id.payment_terms)
    TextView paymentTerms;
    @BindView(R.id.user_agreement_host)
    TextView userAgreementHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_host);
        ButterKnife.bind(this);

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("TERMS AND CONDITIONS");


        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        genralTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("http://myhealthyhost.com/termsandconditions.php"));
                startActivity(browserIntent);


            }
        });
        paymentTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("http://myhealthyhost.com/privacypolicy.php"));
                startActivity(browserIntent);



            }
        });
userAgreementHost.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse("http://myhealthyhost.com/Useragreementforhost.php"));
        startActivity(browserIntent);

    }
});

    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
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
}

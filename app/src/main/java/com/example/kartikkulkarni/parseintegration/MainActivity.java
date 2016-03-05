package com.example.kartikkulkarni.parseintegration;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NetworkInterface ni;
    private ArrayList<DealModel> dealModelArrayList;
    private GPSHelper gpsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView texty = (TextView)findViewById(R.id.texty);
        ni = NetworkInterface.getInstance(this);
        gpsHelper = new GPSHelper(this);

        DealModel dealObj = ni.createDealObject("$5", "on burrito", "$5 off on a burrito when you buy another at full price",
                 "one per person, cannot be combines with other offers,", "in 2 days!", "dealPicUrl",

                new LatLng(gpsHelper.getLatitude(),gpsHelper.getLongitude()),

                "Curry Up Now", "Indian food with American Twist", "Curry Up Now has multiple branches in bay area and food trucks",
                "logoUrl", "storePicUrl"
        );
        ni.publishDeal(dealObj);

        dealModelArrayList = new ArrayList<>();
        final NetworkInterface.dealLoadNotifier nfy = new NetworkInterface.dealLoadNotifier () {

            @Override
            public void notifyLoad(int noOfItems) {

                texty.setText("total deals: " + noOfItems + " first: " + ((DealModel) dealModelArrayList.get(0)).getDealValue());

            }
        };


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Click to get deals", Snackbar.LENGTH_LONG)
                        .setAction("getDeals()", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ni.getDeals(dealModelArrayList, nfy);

                            }
                        }).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

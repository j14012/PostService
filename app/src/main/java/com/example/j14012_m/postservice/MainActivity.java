package com.example.j14012_m.postservice;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements LocationListener{
    Button b1;
    //フィールドに追加
    private LocationManager locationManager;
    final double[] lat = {0.0};
    final double[] lng = {0.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        locationStart();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AsyncHttp post = new AsyncHttp("Android",lat[0],lng[0]);
                post.execute();
            }
        });
        locationStart();
    }
    //GPSスタート
    private void locationStart() {
        Log.d("debug", "locationStart()");
        //LocationManagerインスタンス生成
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            //GPS設定するように促す
            Intent settingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingIntent);
            Log.d("debug", "gpsEnable, startActivity");
        } else {
            Log.d("debug", "gpsEnabled");
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, (LocationListener) this);
    }
    @Override
    protected void onResume() {
        if (locationManager != null) {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,1000,50,this);
        }
        super.onResume();
    }
    @Override
    protected void onPause() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }

        super.onPause();
    }


    @Override
    public void onLocationChanged(Location location) {
        lat[0]=location.getLatitude();
        lng[0]=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

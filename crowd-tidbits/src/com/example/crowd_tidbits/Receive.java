package com.example.crowd_tidbits;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Receive extends Activity implements OnMyLocationChangeListener {
	int status;
	GoogleMap map;
	double longitude, latitude;
	String time, date;
	Button rec_view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receive);
		
		
		try{
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!enabled) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                Toast.makeText(this, "Enabled :" + enabled, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        
        try{
        	initializeLocationVariable();
        	initialise();
        	onclicks();
        }catch(Exception ex){
        	ex.printStackTrace();
        }
		
	}
	
	public void initialise(){
		rec_view = (Button)findViewById(R.id.rec_view);
	}
	public void onclicks(){
		rec_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Longitude: "+ longitude +"\nLatitude: "+latitude + "\nDate: "+date+"\nTime: "+time, Toast.LENGTH_LONG).show();
			}
		});
	}
	public void initializeLocationVariable(){

        if( status != ConnectionResult.SUCCESS){
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }else{

            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragId)).getMap();
            map.setMyLocationEnabled(true);
            map.setOnMyLocationChangeListener(this);

        }


    }

	@Override
	public void onMyLocationChange(Location loc) {
		// TODO Auto-generated method stub
		
		try{
			longitude = loc.getLongitude();
            latitude = loc.getLatitude();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            time = formattedDate.substring(11);
            date = formattedDate.substring(0, formattedDate.length()-9);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}

package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Toast;

//https://github.com/IanDarwin/Android-Cookbook-Examples/blob/master/OSMIntro/src/com/OSM/OSM.java 2015/03/26
public class MapActivity extends Activity
{
	private MapView mapView;
	private MapController mapController;
	private ScaleBarOverlay mScaleBarOverlay;
	private ItemizedIconOverlay<OverlayItem> mMyLocationOverlay;
	private Location location;
	private DefaultResourceProxyImpl resourceProxy;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview);

		 mapView = (MapView) this.findViewById(R.id.mapview);
		 mapView.setBuiltInZoomControls(true);
		 mapView.setMultiTouchControls(true);
		 mapController = (MapController) this.mapView.getController();
		 mapController.setZoom(8);
		 
	     GeoPoint mapCenter = new GeoPoint(53554070, -2959520); 
	     mapController.setCenter(mapCenter);
		 String option = getIntent().getExtras().getString("option");
	     mapView.invalidate();
		 if(option.equals("add")){
			 //todo  
		 } else {
			 ClaimListController CLC = new ClaimListController();
			 int id = getIntent().getIntExtra("claim_id",0);
			 Claim claim = CLC.getClaim(id);
			 //TODO show Locations
		 }
		 
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	    int actionType = ev.getAction();
	    switch (actionType) {
	    case MotionEvent.ACTION_UP:
	            Projection proj = mapView.getProjection();
	            GeoPoint loc = (GeoPoint) proj.fromPixels((int)ev.getX(), (int)ev.getY()); 
	            GeoPoint overlayPoint = new GeoPoint(loc.getLatitudeE6()+365000,loc.getLongitudeE6()-1000);
	            String longitude = Double.toString(((double)loc.getLongitudeE6())/1000000);
	            String latitude = Double.toString(((double)loc.getLatitudeE6())/1000000);
	            ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
	            overlays.add(new OverlayItem("New Overlay", "Overlay Description", overlayPoint));
	            resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
	            this.mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(overlays, null, resourceProxy);
	            this.mapView.getOverlays().add(this.mMyLocationOverlay);
	            mapView.invalidate();
	            Toast toast = Toast.makeText(getApplicationContext(), "Longitude: "+ longitude +" Latitude: "+ latitude , Toast.LENGTH_SHORT);
	            toast.show();

	    }
	return super.dispatchTouchEvent(ev);
	}

	public void setPoint(){
		//TODO
	}
	
	public void getPoint(){
		//TODO
	}
	
	public void returnLocation(){
		Intent intent = new Intent();
		intent.putExtra("Location",location);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}

package ca.ualberta.cs.cmput301w15t12;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.SimpleLocationOverlay;

//https://github.com/IanDarwin/Android-Cookbook-Examples/blob/master/OSMIntro/src/com/OSM/OSM.java 2015/03/26
public class MapActivity extends Activity
{
	private MapView mapView;
	private MapController mapController;
	private ScaleBarOverlay mScaleBarOverlay;
	private SimpleLocationOverlay mMyLocationOverlay;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview);

		 mapView = (MapView) this.findViewById(R.id.mapview);
		 mapView.setBuiltInZoomControls(true);
		 mapView.setMultiTouchControls(true);
		 mapController = (MapController) this.mapView.getController();
		 mapController.setZoom(2);
		 
		 
		 String option = getIntent().getExtras().getString("option");
		 if(option.equals("add")){
			 //TODO get Location
			 //returnLocation();
		 } else {
			 ClaimListController CLC = new ClaimListController();
			 int id = getIntent().getIntExtra("claim_id",0);
			 Claim claim = CLC.getClaim(id);
			 //TODO show Locations
		 }
		 
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

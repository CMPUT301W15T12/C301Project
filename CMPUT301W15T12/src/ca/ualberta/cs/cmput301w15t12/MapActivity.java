package ca.ualberta.cs.cmput301w15t12;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

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
	}

	public void setPoint(){
		//TODO
	}
	
	public void getPoint(){
		//TODO
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}

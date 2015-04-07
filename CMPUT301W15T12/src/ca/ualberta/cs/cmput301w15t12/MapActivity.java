package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

//https://github.com/IanDarwin/Android-Cookbook-Examples/blob/master/OSMIntro/src/com/OSM/OSM.java 2015/03/26
public class MapActivity extends Activity {
	private MapView mapView;
	private MapController mapController;
	private ItemizedIconOverlay<OverlayItem> mMyLocationOverlay = null;
	//private Location location = new Location("current");
	private DefaultResourceProxyImpl resourceProxy;
	private int minMillisecondThresholdForLongClick = 500;
	private long startTimeForLongClick = 0;
	//variables for code implemented from http://stackoverflow.com/questions/1678493/android-maps-how-to-long-click-a-map
	private float xScreenCoordinateForLongClick;
	private float yScreenCoordinateForLongClick;
	private float xtolerance = 10;
	private float ytolerance = 10;
	private float xlow; 
	private float xhigh; 
	private float ylow;
	private float yhigh; 
	private double latitude = 0.0;
	private double longitude = 0.0;
	private User user;
	private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
	private String string;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview);
		//initializes map variables
		mapView = (MapView) this.findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setMultiTouchControls(true);
		mapController = (MapController) this.mapView.getController();
		mapController.setZoom(8);
		//sets map center
		GeoPoint mapCenter = new GeoPoint(53554070, -2959520);
		mapController.setCenter(mapCenter);
		mapView.invalidate();
		String option = getIntent().getExtras().getString("option");
		if (option.equals("see")){
			String Username = getIntent().getExtras().getString("username");
			//gets the user corresponding to the UserName
			for (int i = 0; i < UserListController.getUserList().size(); i++) {
				if (UserListController.getUserList().get(i).getUserName().equals(Username)) {
					user = UserListController.getUserList().get(i);
				}
			}

			//gets the claim id for the claim whose locations we want to see
			ClaimListController CLC = new ClaimListController();
			final int id = getIntent().getIntExtra("claim_id", 0);
			Claim claim = CLC.getClaim(id);
			ArrayList<Location> otherLocations = claim.getLocations();
			
			//shows icon locations
			try{
				for (int i = 0; i < otherLocations.size(); i ++){
					Location locItem = otherLocations.get(i);
					GeoPoint loc = new GeoPoint(locItem);
					overlays.add(new OverlayItem("", "", loc));
					string = string + locItem;
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
			Location homeLocation = user.getLocation();
			GeoPoint homePoint = new GeoPoint(homeLocation);
			overlays.add(new OverlayItem("", "", homePoint));
			resourceProxy = new DefaultResourceProxyImpl(
					getApplicationContext());
			ItemizedIconOverlay<OverlayItem> locationOverlay = new ItemizedIconOverlay<OverlayItem>(overlays, null, resourceProxy);
			this.mapView.getOverlays().add(
					locationOverlay);
			//TODO need to do view still
			mapView.invalidate();
		}
	}

	/**
	 * finds the location of a clicked point on the map
	 * @param ev
	 * @return true if successful
	 */
	// Implemented from
	// http://stackoverflow.com/questions/1678493/android-maps-how-to-long-click-a-map
	// April 2, 2015
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// get the action from the MotionEvent: down, move, or up
		int actionType = ev.getAction();
		String option = getIntent().getExtras().getString("option");
		if (option.equals("add")) {
			if (actionType == MotionEvent.ACTION_DOWN) {
				startTimeForLongClick = ev.getEventTime();
				xScreenCoordinateForLongClick = ev.getX();
				yScreenCoordinateForLongClick = ev.getY();

			} else if (actionType == MotionEvent.ACTION_MOVE) {
				if (ev.getPointerCount() > 1) {
					startTimeForLongClick = 0; 
				} else {
					float xmove = ev.getX();
					float ymove = ev.getY();
					xlow = xScreenCoordinateForLongClick - xtolerance;
					xhigh = xScreenCoordinateForLongClick + xtolerance;
					ylow = yScreenCoordinateForLongClick - ytolerance;
					yhigh = yScreenCoordinateForLongClick + ytolerance;
					if ((xmove < xlow || xmove > xhigh)
							|| (ymove < ylow || ymove > yhigh)) {
						startTimeForLongClick = 0;
					}
				}

			} else if (actionType == MotionEvent.ACTION_UP) {
				// determine if this was a long click:
				long eventTime = ev.getEventTime();
				long downTime = ev.getDownTime(); 

				// make sure the start time for the original "down event" is the
				// same as this event's "downTime"
				if (startTimeForLongClick == downTime) {
					// see if the event time minus the start time is within the
					// threshold
					if ((eventTime - startTimeForLongClick) > minMillisecondThresholdForLongClick) {
						// make sure we are at the same spot where we started
						// the long click
						float xup = ev.getX();
						float yup = ev.getY();
						xlow = xScreenCoordinateForLongClick - xtolerance;
						xhigh = xScreenCoordinateForLongClick + xtolerance;
						ylow = yScreenCoordinateForLongClick - ytolerance;
						yhigh = yScreenCoordinateForLongClick + ytolerance;
						if ((xup > xlow && xup < xhigh)
								&& (yup > ylow && yup < yhigh)) {
							Projection proj = mapView.getProjection();
							GeoPoint overlayPoint = (GeoPoint) proj.fromPixels(
									(int) ev.getX(), (int) ev.getY() - 110);
							ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
							if (mMyLocationOverlay != null) {
								this.mapView.getOverlays().remove(
										this.mMyLocationOverlay);
							}
							overlays.add(new OverlayItem("",
									"", overlayPoint));
							resourceProxy = new DefaultResourceProxyImpl(
									getApplicationContext());
							this.mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(
									overlays, null, resourceProxy);
							this.mapView.getOverlays().add(
									this.mMyLocationOverlay);
							latitude = overlayPoint.getLatitudeE6() / 1E6;
							longitude = overlayPoint.getLongitudeE6() / 1E6;
							mapView.invalidate();
						}
					}
				}

				}
		} 
		


		return super.dispatchTouchEvent(ev);

	}

	/**
	 * returns the location to the activity that called it
	 * @param view
	 */
	public void returnLocation(View view) {
		Intent intent = getIntent();
		if (latitude != 0.0 & longitude != 0.0){
			Bundle b = new Bundle();
			b.putDouble("latitude", latitude);
			b.putDouble("longitude", longitude);
			intent.putExtras(b);
			setResult(RESULT_OK, intent);

		}
		else{
			setResult(RESULT_CANCELED,intent);
		}
		
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}

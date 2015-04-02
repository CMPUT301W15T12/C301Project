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

//https://github.com/IanDarwin/Android-Cookbook-Examples/blob/master/OSMIntro/src/com/OSM/OSM.java 2015/03/26
public class MapActivity extends Activity {
	private MapView mapView;
	private MapController mapController;
	private ItemizedIconOverlay<OverlayItem> mMyLocationOverlay = null;
	private Location location;
	private DefaultResourceProxyImpl resourceProxy;
	private int minMillisecondThresholdForLongClick = 500;
	private long startTimeForLongClick = 0;
	private float xScreenCoordinateForLongClick;
	private float yScreenCoordinateForLongClick;
	private float xtolerance = 10;// x pixels that your finger can be off but
									// still constitute a long press
	private float ytolerance = 10;// y pixels that your finger can be off but
									// still constitute a long press
	private float xlow; // actual screen coordinate when you subtract the
						// tolerance
	private float xhigh; // actual screen coordinate when you add the tolerance
	private float ylow; // actual screen coordinate when you subtract the
						// tolerance
	private float yhigh; // actual screen coordinate when you add the tolerance

	@Override
	protected void onCreate(Bundle savedInstanceState) {

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
	}

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
				// user pressed the button down so let's initialize the main
				// variables that we care about:
				// later on when the "Action Up" event fires, the "DownTime"
				// should match the "startTimeForLongClick" that we set here
				// the coordinate on the screen should not change much during
				// the long press
				startTimeForLongClick = ev.getEventTime();
				xScreenCoordinateForLongClick = ev.getX();
				yScreenCoordinateForLongClick = ev.getY();

			} else if (actionType == MotionEvent.ACTION_MOVE) {
				// For non-long press actions, the move action can happen a lot
				// between ACTION_DOWN and ACTION_UP
				if (ev.getPointerCount() > 1) {
					// easiest way to detect a multi-touch even is if the
					// pointer count is greater than 1
					// next thing to look at is if the x and y coordinates of
					// the person's finger change.
					startTimeForLongClick = 0; // instead of a timer, just reset
												// this class variable and in
												// our ACTION_UP event, the
												// DownTime value will not match
												// and so we can reset.
				} else {
					// I know that I am getting to the same action as above,
					// startTimeForLongClick=0, but I want the processor
					// to quickly skip over this step if it detects the pointer
					// count > 1 above
					float xmove = ev.getX(); // where is their finger now?
					float ymove = ev.getY();
					// these next four values allow you set a tiny box around
					// their finger in case
					// they don't perfectly keep their finger still on a long
					// click.
					xlow = xScreenCoordinateForLongClick - xtolerance;
					xhigh = xScreenCoordinateForLongClick + xtolerance;
					ylow = yScreenCoordinateForLongClick - ytolerance;
					yhigh = yScreenCoordinateForLongClick + ytolerance;
					if ((xmove < xlow || xmove > xhigh)
							|| (ymove < ylow || ymove > yhigh)) {
						// out of the range of an acceptable long press, reset
						// the whole process
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
						// I don't want the overhead of a function call:
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
							overlays.add(new OverlayItem("New Overlay",
									"Overlay Description", overlayPoint));
							resourceProxy = new DefaultResourceProxyImpl(
									getApplicationContext());
							this.mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(
									overlays, null, resourceProxy);
							this.mapView.getOverlays().add(
									this.mMyLocationOverlay);
							//TODO need to save location still
							mapView.invalidate();
						}
					}
				}

			}
		} else {
			ClaimListController CLC = new ClaimListController();
			int id = getIntent().getIntExtra("claim_id", 0);
			Claim claim = CLC.getClaim(id);
			//TODO need to do view still
		}

		return super.dispatchTouchEvent(ev);

	}

	public void setPoint() {
		// TODO
	}

	public void getPoint() {
		// TODO
	}

	public void returnLocation(View view) {
		Intent intent = new Intent();
		intent.putExtra("Location", location);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}

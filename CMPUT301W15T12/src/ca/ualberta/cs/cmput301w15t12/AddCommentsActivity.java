package ca.ualberta.cs.cmput301w15t12;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddCommentsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comments);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_comments, menu);
        return true;
    }
    
}

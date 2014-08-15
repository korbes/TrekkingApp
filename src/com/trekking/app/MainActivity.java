package com.trekking.app;

import java.text.DecimalFormat;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements ClickListener {
	

	@Override
	public void Clicked() {
		runOnUiThread(new Runnable()
		{

			@Override
			public void run() {
				Step();				
			}		
		});
	}
	
	public void OnStep(View v)
	{
		Step();
	}
	
	public void OnNextStretch(View v)
	{
		
	}
	
	private void Step()
	{
		PlayBeep();
		IncrementDistanceCounter();
	}
	
	private void PlayBeep() 
	{
		toneG.release();
		toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);	
		toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200); 
	}

	private void IncrementDistanceCounter()
	{	
		distanceCovered += stepSize;
		UpdateDistances();
	}

	private void UpdateDistances() 
	{
		TextView sd = (TextView)findViewById(R.id.strech_distance_value);
		sd.setText(new DecimalFormat("#.##").format(distanceCovered));
		sd = (TextView)findViewById(R.id.stretch_distance_total_value);
		sd.setText(((Integer)stretchDistance).toString());		
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        audioManager = ((AudioManager)getSystemService(AUDIO_SERVICE));
        
        registerMediaButton();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onPause()
    {
    	unregisterMediaButton();
    	super.onPause();
    }
    
    @Override
    protected void onResume()
    {
    	registerMediaButton();
    	super.onResume();
    }
    
    @Override
    protected void onDestroy()
    {
    	unregisterMediaButton();
    	super.onDestroy();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

	

	private void registerMediaButton() {
		component = new ComponentName( 
                this,
                HeadsetButtonReceiver.class);
		audioManager.registerMediaButtonEventReceiver(component);
        
        HeadsetButtonBoard.GetBoard().RegisterClickListener(this);
	}


	private void unregisterMediaButton() {
		if (component != null)
			audioManager.unregisterMediaButtonEventReceiver(component);
		
    	HeadsetButtonBoard.GetBoard().UnregisterClickListener(this);
    	component = null;    	
	}
	
	private ComponentName component;
	private AudioManager audioManager;
	private ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);	
	
	private double distanceCovered = 0;
	private double stepSize = 0.71;
	private int stretchDistance = 150;
	
	
}

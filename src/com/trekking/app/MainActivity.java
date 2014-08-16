package com.trekking.app;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements ClickListener {
	
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
        
        stage = StageLoader.load(Environment.getExternalStorageDirectory().getAbsolutePath() + "/trekking.json");
        //stage.setStartTime(new Date());
        //startTimer();
    }
/*
	private void startTimer() {
		mHandler = new Handler() {
			    public void handleMessage(Message msg) {
			    	updateTimeDelay();
			        updateLabels();
			    }
			};	
	    timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
	            mHandler.obtainMessage(1).sendToTarget();
	        }
	    }, 0, 1000);
	};
	
	protected void updateTimeDelay() {
		stage.getTimeDifference(currentStretch, currentLength);
	}

	*/

	@Override
	public void clicked() {
		runOnUiThread(new Runnable()
		{

			@Override
			public void run() {
				step();				
			}		
		});
	}
	
	public void onStep(View v)
	{
		step();
	}
	
	public void onZero(View v)
	{
		currentLength = 0;
		updateLabels();
	}
	
	public void onNextStretch(View v)
	{
		stage.nextStretch();		
		currentLength = 0;
		updateLabels();
	}
	
	private void step()
	{
		playBeep();
		incrementDistanceCounter();
	}
	
	private void playBeep() 
	{
		toneG.release();
		toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);	
		toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200); 
	}

	private void incrementDistanceCounter()
	{	
		currentLength += stepSize;
		updateLabels();
	}

	private void updateLabels() 
	{
		TextView sd = (TextView)findViewById(R.id.strech_distance_value);
		sd.setText(new DecimalFormat("#.##").format(currentLength));
		
		sd = (TextView)findViewById(R.id.stretch_distance_total_value);
		sd.setText(stage.getStretchLengthInfo());
		
		sd = (TextView)findViewById(R.id.current_stretch_label);
		sd.setText(stage.getStageSummary());
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
    	updateLabels();
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
        
        HeadsetButtonBoard.getBoard().registerClickListener(this);
	}


	private void unregisterMediaButton() {
		if (component != null)
			audioManager.unregisterMediaButtonEventReceiver(component);
		
    	HeadsetButtonBoard.getBoard().unregisterClickListener(this);
    	component = null;    	
	}
		
	private ComponentName component;
	private AudioManager audioManager;
	private ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);	
	
	
	private double stepSize = 0.71;
	private double currentLength = 0;
	
	private Stage stage = null;
	//private Timer timer = new Timer();	
	//public Handler mHandler;
	
}

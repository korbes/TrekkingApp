package com.trekking.app;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.ComponentName;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


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

	@Override
	public void Clicked() {
		runOnUiThread(new Runnable()
		{

			@Override
			public void run() {
				IncrementDistanceCounter();				
			}		
		});		
	}
	
	private void IncrementDistanceCounter()
	{	
		// TODO: aqui vai o código para atualizar a tela 
	}
	

	private void registerMediaButton() {
		component = new ComponentName( 
                this,
                HeadsetButtonReceiver.class);
		((AudioManager)getSystemService(AUDIO_SERVICE)).registerMediaButtonEventReceiver(component);
        
        HeadsetButtonBoard.GetBoard().RegisterClickListener(this);
	}


	private void unregisterMediaButton() {
		if (component != null)
			((AudioManager)getSystemService(AUDIO_SERVICE)).unregisterMediaButtonEventReceiver(component);
		
    	HeadsetButtonBoard.GetBoard().UnregisterClickListener(this);
    	component = null;    	
	}
	
	private ComponentName component;

}

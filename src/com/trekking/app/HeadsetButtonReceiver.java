package com.trekking.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class HeadsetButtonReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent intent) {
				
		Bundle bundle = intent.getExtras();
		KeyEvent key = (KeyEvent)bundle.get(Intent.EXTRA_KEY_EVENT);
		
		if (key.getAction() == KeyEvent.ACTION_UP)
			HeadsetButtonBoard.getBoard().clicked();		
	}

}

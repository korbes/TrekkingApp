package com.trekking.app;

import java.util.ArrayList;

public class HeadsetButtonBoard {
	
	private HeadsetButtonBoard(){}
	
	public static HeadsetButtonBoard getBoard() {
		return _board;
	}
	
	public void clicked()
	{
		for (ClickListener l: _listeners)
		{
			l.clicked();
		}
		
	}
	
	public void registerClickListener(ClickListener l)
	{
		if (!_listeners.contains(l))
			_listeners.add(l);
	}
	
	public void unregisterClickListener(ClickListener l)
	{
		_listeners.remove(l);
	}
	
	private static HeadsetButtonBoard _board = new HeadsetButtonBoard();
	private ArrayList<ClickListener> _listeners = new ArrayList<ClickListener>();

}

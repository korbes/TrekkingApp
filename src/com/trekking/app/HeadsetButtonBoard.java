package com.trekking.app;

import java.util.ArrayList;

public class HeadsetButtonBoard {
	
	private HeadsetButtonBoard(){}
	
	public static HeadsetButtonBoard GetBoard() {
		return _board;
	}
	
	public void Clicked()
	{
		for (ClickListener l: _listeners)
		{
			l.Clicked();
		}
		
	}
	
	public void RegisterClickListener(ClickListener l)
	{
		if (!_listeners.contains(l))
			_listeners.add(l);
	}
	
	public void UnregisterClickListener(ClickListener l)
	{
		_listeners.remove(l);
	}
	
	private static HeadsetButtonBoard _board = new HeadsetButtonBoard();
	private ArrayList<ClickListener> _listeners = new ArrayList<ClickListener>();

}

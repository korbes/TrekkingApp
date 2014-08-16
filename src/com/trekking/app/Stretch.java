package com.trekking.app;

public class Stretch {
	
	public Stretch(int distance, int avgSpeed)
	{
		this.length = distance;
		this.avgSpeed = avgSpeed;
	}
	
	public Stretch(int restMinutes)
	{
		this.restMinutes = restMinutes;
		this.length = -1;
	}
	
	public int length;
	public int avgSpeed;
	public int restMinutes;

}

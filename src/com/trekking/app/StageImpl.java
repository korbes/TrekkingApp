package com.trekking.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StageImpl implements Stage {

	public void addStretch(Stretch s){
		stretches.add(s);
	}
	
	@Override
	public int getStretchCount() {
		return stretches.size();
	}

	@Override
	public String getStretchLengthInfo() {
		if (getCurrentStretchLength() == -1)
			return String.format(Locale.US, "N:%d", stretches.get(currentStretch).restMinutes);
		else
			return ((Integer)getCurrentStretchLength()).toString();
		
	}

	
	@Override
	public void nextStretch() {
		if (currentStretch + 1 < getStretchCount()){
			currentStretch++;
		}
	}
	
	@Override
	public String getStageSummary() {
		return String.format(Locale.US, "%d/%d", currentStretch+1, getStretchCount());
	}
	/*
	@Override
	public void setStartTime(Date startTime) {
		// TODO Auto-generated method stub		
	}

	@Override
	public Date getTimeDifference(int currentStretch, double lengthCovered) {
		// TODO Auto-generated method stub
		return new Date();
	}
	*/
	private int getCurrentStretchLength() {
		return stretches.get(currentStretch).length;
	}

	private ArrayList<Stretch> stretches = new ArrayList<Stretch>();
	private int currentStretch = 0;
	
}

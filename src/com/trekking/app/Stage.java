package com.trekking.app;

import java.util.Date;

public interface Stage {
	
	int getStretchCount();
	
	void nextStretch();
	
	String getStageSummary();

	String getStretchLengthInfo();

	/*
	void setStartTime(Date startTime);	
	
	Date getTimeDifference(double lengthCovered);
*/

}

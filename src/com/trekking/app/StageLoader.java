package com.trekking.app;

import java.io.*;
import java.util.*;

import org.json.*;

public class StageLoader {

	public static Stage load(String path) {
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			String read;
			StringBuilder builder = new StringBuilder("");
	
			while((read = bufferedReader.readLine()) != null){
				builder.append(read);
			}
			
			JSONObject json = new JSONObject(builder.toString());
			bufferedReader.close();
			
			return parse(json);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
				
	}
	
	private static Stage parse(JSONObject json) throws JSONException
	{
		StageImpl s = new StageImpl();
		for (int i = 1; i<json.length(); i++)
		{
			JSONArray ar = json.getJSONArray(String.format(Locale.US, "%d", i));
			Stretch st;
			if (ar.length() == 1)
				st = new Stretch(ar.getInt(0));
			else
				st = new Stretch(ar.getInt(0), ar.getInt(0));
			s.addStretch(st);
		}
		return s;	
	}
	
	
	
}

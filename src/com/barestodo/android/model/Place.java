package com.barestodo.android.model;

import com.ocpsoft.pretty.time.PrettyTime;
import org.joda.time.DateTime;

import java.io.Serializable;

public class Place implements Serializable{
	/**
	 * 
	 */
	private String id;
	private String name;
	private String location;
	private DateTime scheduleDate;
	
	private Place(){
		//needed for serializations
	}
	
	public Place(String id, String name,String location) {
        super();
        this.id = id;
        this.name = name;
        this.location=location;
    }

    public Place(String name,String location) {
        super();
        this.name = name;
        this.location=location;
    }

	public Place(String id, String name,String location, DateTime scheduleDate) {
        super();
        this.id = id;
        this.name = name;
        this.location=location;
        this.scheduleDate=scheduleDate;
    }
	
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}

    public String getLocation(){
        return location;
    }
    
    public DateTime getScheduleDate(){
        return scheduleDate;
    }

	 @Override
	 public String toString() {
	        return name;
	 }

    public boolean isPlaned() {
        return scheduleDate!=null;
    }

    public String getRelativeScheduledDateLabel() {
        PrettyTime p = new PrettyTime();
        return isPlaned()?p.format(scheduleDate.toDate()):"";
    }
}

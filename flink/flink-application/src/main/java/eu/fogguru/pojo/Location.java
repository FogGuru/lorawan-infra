package eu.fogguru.pojo;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;

public class Location{
	private int altitude;
	private int latitude;
	private int longitude;

	public Location(JsonNode jsonNode) {
		this(
			jsonNode.get("altitude").intValue(),
			jsonNode.get("latitude").intValue(),
			jsonNode.get("longitude").intValue()
		);
	}

	public Location(int altitude, int latitude, int longitude) {
		this.altitude = altitude;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void setAltitude(int altitude){
		this.altitude = altitude;
	}

	public int getAltitude(){
		return altitude;
	}

	public void setLatitude(int latitude){
		this.latitude = latitude;
	}

	public int getLatitude(){
		return latitude;
	}

	public void setLongitude(int longitude){
		this.longitude = longitude;
	}

	public int getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"Location{" + 
			"altitude = '" + altitude + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}

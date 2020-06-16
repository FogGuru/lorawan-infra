package eu.fogguru.pojo;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;

public class RxInfoItem{
	private int rssi;
	private String name;
	private String uplinkID;
	private Location location;
	private String gatewayID;
	private double loRaSNR;

	public RxInfoItem(JsonNode jsonNode) {
		this(
				jsonNode.get("rssi").intValue(),
				jsonNode.get("name").toString(),
				jsonNode.get("uplinkID").toString(),
				new Location(jsonNode.get("location")),
				jsonNode.get("gatewayID").toString(),
				jsonNode.get("loRaSNR").doubleValue()
		);
	}

	public RxInfoItem(int rssi, String name, String uplinkID, Location location, String gatewayID, double loRaSNR) {
		this.rssi = rssi;
		this.name = name;
		this.uplinkID = uplinkID;
		this.location = location;
		this.gatewayID = gatewayID;
		this.loRaSNR = loRaSNR;
	}

	public void setRssi(int rssi){
		this.rssi = rssi;
	}

	public int getRssi(){
		return rssi;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setUplinkID(String uplinkID){
		this.uplinkID = uplinkID;
	}

	public String getUplinkID(){
		return uplinkID;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}

	public void setGatewayID(String gatewayID){
		this.gatewayID = gatewayID;
	}

	public String getGatewayID(){
		return gatewayID;
	}

	public void setLoRaSNR(double loRaSNR){
		this.loRaSNR = loRaSNR;
	}

	public double getLoRaSNR(){
		return loRaSNR;
	}

	@Override
 	public String toString(){
		return 
			"RxInfoItem{" + 
			"rssi = '" + rssi + '\'' + 
			",name = '" + name + '\'' + 
			",uplinkID = '" + uplinkID + '\'' + 
			",location = '" + location + '\'' + 
			",gatewayID = '" + gatewayID + '\'' + 
			",loRaSNR = '" + loRaSNR + '\'' + 
			"}";
		}
}

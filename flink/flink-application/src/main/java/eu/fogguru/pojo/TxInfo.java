package eu.fogguru.pojo;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;

public class TxInfo{
	private int dr;
	private int frequency;

	public TxInfo(JsonNode jsonNode) {
		this(jsonNode.get("dr").intValue(), jsonNode.get("frequency").intValue());
	}

	public TxInfo(int dr, int frequency) {
		this.dr = dr;
		this.frequency = frequency;
	}

	public void setDr(int dr){
		this.dr = dr;
	}

	public int getDr(){
		return dr;
	}

	public void setFrequency(int frequency){
		this.frequency = frequency;
	}

	public int getFrequency(){
		return frequency;
	}

	@Override
 	public String toString(){
		return 
			"TxInfo{" + 
			"dr = '" + dr + '\'' + 
			",frequency = '" + frequency + '\'' + 
			"}";
		}
}

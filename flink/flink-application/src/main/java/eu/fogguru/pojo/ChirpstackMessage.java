package eu.fogguru.pojo;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ChirpstackMessage implements Cloneable, Serializable {
	private static final long serialVersionUID = -3555130785188765225L;

	private Long id;
	private Long timestamp;

	private List<RxInfoItem> rxInfo;
	private TxInfo txInfo;
	private int fPort;
	private String data;
	private String applicationID;
	private int fCnt;
	private String deviceName;
	private String applicationName;
	private String devEUI;
	private boolean adr;

	private enum FIELD_NAMES {
		rxInfo,
		txInfo,
		fPort,
		data,
		applicationID,
		fCnt,
		deviceName,
		applicationName,
		devEUI,
		adr
	}

	public ChirpstackMessage(JsonNode jsonNode) {
		this(jsonNode.get("fPort").intValue(),
				jsonNode.get("data").toString(),
				jsonNode.get("applicationID").toString(),
				jsonNode.get("fCnt").intValue(),
				jsonNode.get("deviceName").toString(),
				jsonNode.get("applicationName").toString(),
				jsonNode.get("devEUI").toString(),
				jsonNode.get("adr").booleanValue(),
				getRxInfoItemList(jsonNode.get("rxInfo").elements()),
				new TxInfo(jsonNode.get("txInfo"))
		);
	}

	public ChirpstackMessage(int fPort, String data, String applicationID, int fCnt,
							 String deviceName, String applicationName, String devEUI,
							 boolean adr, List<RxInfoItem> rxInfo, TxInfo txInfo) {
		this.id = 0L;
		this.timestamp = new Date().getTime();
		this.fPort = fPort;
		this.data = data;
		this.applicationID = applicationID;
		this.fCnt = fCnt;
		this.deviceName = deviceName;
		this.applicationName = applicationName;
		this.devEUI = devEUI;
		this.adr = adr;
		this.rxInfo = rxInfo;
		this.txInfo = txInfo;
	}

	public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
	public Long getTimestamp() { return this.timestamp; }

	public void setId(Long id) { this.id = id; }
	public Long getId() { return this.id; }

	public void setRxInfo(List<RxInfoItem> rxInfo){
		this.rxInfo = rxInfo;
	}

	public List<RxInfoItem> getRxInfo(){
		return rxInfo;
	}

	public void setTxInfo(TxInfo txInfo){
		this.txInfo = txInfo;
	}

	public TxInfo getTxInfo(){
		return txInfo;
	}

	public void setFPort(int fPort){
		this.fPort = fPort;
	}

	public int getFPort(){
		return fPort;
	}

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return data;
	}

	public void setApplicationID(String applicationID){
		this.applicationID = applicationID;
	}

	public String getApplicationID(){
		return applicationID;
	}

	public void setFCnt(int fCnt){
		this.fCnt = fCnt;
	}

	public int getFCnt(){
		return fCnt;
	}

	public void setDeviceName(String deviceName){
		this.deviceName = deviceName;
	}

	public String getDeviceName(){
		return deviceName;
	}

	public void setApplicationName(String applicationName){
		this.applicationName = applicationName;
	}

	public String getApplicationName(){
		return applicationName;
	}

	public void setDevEUI(String devEUI){
		this.devEUI = devEUI;
	}

	public String getDevEUI(){
		return devEUI;
	}

	public void setAdr(boolean adr){
		this.adr = adr;
	}

	public boolean getAdr(){
		return adr;
	}

	private static List<RxInfoItem> getRxInfoItemList(Iterator<JsonNode> jsonNodeIterator) {
		List rxList = new ArrayList<RxInfoItem>();
		for (Iterator<JsonNode> it = jsonNodeIterator; it.hasNext(); ) {
			JsonNode element = it.next();
			rxList.add(new RxInfoItem(element));
		}
		return rxList;
	}

	@Override
 	public String toString(){
		return 
			"ChirpstackMessage{" + 
			"rxInfo = '" + rxInfo + '\'' + 
			",txInfo = '" + txInfo + '\'' + 
			",fPort = '" + fPort + '\'' + 
			",data = '" + data + '\'' + 
			",applicationID = '" + applicationID + '\'' + 
			",fCnt = '" + fCnt + '\'' + 
			",deviceName = '" + deviceName + '\'' + 
			",applicationName = '" + applicationName + '\'' + 
			",devEUI = '" + devEUI + '\'' + 
			",adr = '" + adr + '\'' + 
			"}";
		}
}
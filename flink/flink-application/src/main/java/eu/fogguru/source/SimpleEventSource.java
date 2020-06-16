package eu.fogguru.source;

import eu.fogguru.pojo.SensorRead;
import jdk.nashorn.internal.ir.ObjectNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.fusesource.mqtt.client.*;

import java.nio.ByteBuffer;
import java.util.Date;

public class SimpleEventSource extends RichSourceFunction<SensorRead> {

    private String host;
    private String topic;
    private int port;
    private boolean running;

    public SimpleEventSource(String host, String topic, String port) {
        this.host = host;
        this.topic = topic;
        this.port = Integer.parseInt(port);
        this.running = true;
    }

    @Override
    public void run(SourceContext<SensorRead> sourceContext) throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setHost(host, port);
        BlockingConnection blockingConnection = mqtt.blockingConnection();
        blockingConnection.connect();

        byte[] qoses = blockingConnection.subscribe(
                        new Topic[] { new Topic(topic, QoS.AT_MOST_ONCE) }
        );

        while (blockingConnection.isConnected() && running) {
            Message msg = blockingConnection.receive();
            msg.ack();
            SensorRead sensorRead = this.getSensorRead(msg.getPayload());
            sourceContext.collect(sensorRead);
        }
        blockingConnection.disconnect();
        this.cancel();
    }

    @Override
    public void cancel() {
        this.running = false;
    }

    private SensorRead getSensorRead(byte[] mqttMsgPayload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new String(mqttMsgPayload));
        return new SensorRead(jsonNode.get("value").asLong());
    }
}

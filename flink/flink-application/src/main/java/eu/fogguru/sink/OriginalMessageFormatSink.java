package eu.fogguru.sink;

import eu.fogguru.pojo.ChirpstackMessage;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.fusesource.hawtbuf.AsciiBuffer;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.util.LinkedList;

public class OriginalMessageFormatSink extends RichSinkFunction<ChirpstackMessage> {

    private String host;
    private int port;
    private String topic;
    private FutureConnection connection;
    private QoS qos;

    public OriginalMessageFormatSink(String host, String topic, String port) {
        this.host = host;
        this.port = Integer.parseInt(port);
        this.topic = topic;
        this.qos = QoS.AT_MOST_ONCE;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);

        MQTT mqtt = new MQTT();
        mqtt.setHost(this.host, this.port);

        this.connection = mqtt.futureConnection();
        this.connection.connect().await();
    }

    @Override
    public void close() throws Exception {
        super.close();
        connection.disconnect().await();
    }

    @Override
    public void invoke(ChirpstackMessage message, Context context) throws Exception {
        String data = message.toString();
        int size = data.length();
        String body = "";
        for (int i = 0; i < size; i++) {
            body += data.charAt(i % size);
        }
        Buffer msg = new AsciiBuffer(body);

        final LinkedList<Future<Void>> queue =
                new LinkedList<Future<Void>>();
        UTF8Buffer topicBuffer = new UTF8Buffer(this.topic);

        queue.add(connection.publish(topicBuffer, msg, this.qos, false));

        // People suggested to remove 1, just to keep the buffer small
        if (queue.size() >= 1000) {
            queue.removeFirst().await();
        }

        // Not so likely to happen but empty message queue
        // Full gambiarra
        while (!queue.isEmpty()) {
            queue.removeFirst().await();
        }
    }
}

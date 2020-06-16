package eu.fogguru;

import eu.fogguru.pojo.ChirpstackMessage;
import eu.fogguru.pojo.SensorRead;
import eu.fogguru.sink.OriginalMessageFormatSink;
import eu.fogguru.sink.SimpleEventSink;
import eu.fogguru.source.OriginalMessageFormatSource;
import eu.fogguru.source.SimpleEventSource;
import eu.fogguru.util.AverageReading;
import eu.fogguru.util.MqttAscendingTimestampExtractor;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

public class StreamingJob {

	public static void main(String[] args) throws Exception {

		final StreamExecutionEnvironment env =
				StreamExecutionEnvironment.getExecutionEnvironment();

		String host = "mosquitto";
		String port = "1883";
		String sourceTopic = "application/+/device/+/rx";
		String sinkTopic = "flink-sink";

		if (args.length == 4) {
			host = args[0];
			port = args[1];
			sourceTopic = args[2];
			sinkTopic = args[3];
		}

		DataStream<ChirpstackMessage> intactMessages = env
				.addSource(new OriginalMessageFormatSource(host, sourceTopic, port)).name("Original message source");

		if (sinkTopic.equals("flink-sink")) {
			intactMessages.addSink(new OriginalMessageFormatSink(host, sinkTopic, port));
		} else {
			intactMessages.print();
		}


		env.execute(StreamingJob.class.getName());
	}
}

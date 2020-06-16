package eu.fogguru.util;

import eu.fogguru.pojo.SensorRead;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;

public class MqttAscendingTimestampExtractor extends AscendingTimestampExtractor<SensorRead> {
    private static final long serialVersionUID = 4311406052896755965L;

    @Override
    public long extractAscendingTimestamp(SensorRead sensorRead) {
        return sensorRead.getTimestamp();
    }
}

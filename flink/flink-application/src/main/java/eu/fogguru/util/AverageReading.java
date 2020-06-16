package eu.fogguru.util;

import eu.fogguru.pojo.SensorRead;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class AverageReading implements AllWindowFunction<SensorRead, SensorRead, TimeWindow> {

    @Override
    public void apply(TimeWindow window, Iterable<SensorRead> iterable, Collector<SensorRead> collector) throws Exception {
        long sum = 0l;
        int count = 0;
        for (SensorRead read : iterable) {
            sum += read.getReading();
            count++;
        }

        SensorRead result = iterable.iterator().next();
        result.setReading(sum / count);
        collector.collect(result);
    }
}

package com.jsjavaprojects.kafkapractice.topology;

import com.jsjavaprojects.kafkapractice.dto.Order;
import com.jsjavaprojects.kafkapractice.dto.OrderHistory;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerde;

import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.ORDER_HISTORY_STORE;
import static com.jsjavaprojects.kafkapractice.utils.CommonStrings.ORDER_HISTORY_TOPIC;

public class OrderTopology {
    private OrderTopology (){}

    public static Topology buildTopology(String topic) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        JsonSerde<Order> orderJsonSerde = new JsonSerde<>(Order.class);
        JsonSerde<OrderHistory> orderHistoryJsonSerde = new JsonSerde<>(OrderHistory.class);
        KStream<String, OrderHistory> stringOrderHistoryKStream = streamsBuilder.stream(
                        topic,
                        Consumed.with(Serdes.String(), orderJsonSerde))
                .groupByKey()
                .aggregate(OrderHistory::new,
                        (key, value, aggregate) -> aggregate.process(value),
                        Materialized.<String, OrderHistory, KeyValueStore<Bytes, byte[]>>as(ORDER_HISTORY_STORE)
                                .withKeySerde(Serdes.String())
                                .withValueSerde(orderHistoryJsonSerde))
                .toStream();
        stringOrderHistoryKStream.to(ORDER_HISTORY_TOPIC, Produced.with(Serdes.String(), orderHistoryJsonSerde));
        return streamsBuilder.build();
    }
}

import alarmOutFlow.{EventIn, ProcessedEvent}
import config.Config._
import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.connector.kafka.sink.{KafkaRecordSerializationSchema, KafkaSink}
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema
import org.apache.flink.streaming.api.scala._
import org.mongodb.scala.MongoClient
import serialization.{EventInDeserializer, ProcessedEventSerializer}
import sinks.MongoSink

object Main extends App {
  if (args.length < 1) {
    println("Kafka topic name expected as argument")
    sys.exit()
  }

  // mongo client
  implicit private val mongoClient: MongoClient = MongoClient(mongoClientSettings)

  // enable checkpointing
  private val env = StreamExecutionEnvironment.getExecutionEnvironment
  env.enableCheckpointing(checkpointInterval, checkpointingMode)
  env.getCheckpointConfig.setMaxConcurrentCheckpoints(maxConcurrentCheckpoints)

  // kafkasource
  val source: KafkaSource[EventIn] = KafkaSource.builder()
    .setBootstrapServers(kafkaServers)
    .setTopics(args(0))
    .setGroupId(kafkaConsumerGroup)
    .setStartingOffsets(OffsetsInitializer.committedOffsets())
    .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(new EventInDeserializer))
    .build()

  val eventStream: DataStream[EventIn] = env.fromSource(source, WatermarkStrategy.noWatermarks(), "KafkaSource" + args(0))
    .name("Source-Kafka-testFlow.EventIn")

  val processedEventStream: DataStream[ProcessedEvent] = eventStream
    .filter(_.signalName == "DQCAlarmOut")
    .name("Filter-testFlow.EventIn-DCQAlarmOut")
    .map(ProcessedEvent.fromEventIn _)
    .name("Map-testFlow.EventIn-To-testFlow.ProcessedEvent")

  processedEventStream.addSink(new MongoSink())
    .name("Sink-Mongo-testFlow.ProcessedEvent")
    .setParallelism(1)

  processedEventStream.sinkTo(KafkaSink.builder[ProcessedEvent]()
    .setBootstrapServers(kafkaServers)
    .setRecordSerializer(KafkaRecordSerializationSchema.builder[ProcessedEvent]()
      .setTopic(kafkaSinkTopic)
      .setValueSerializationSchema(new ProcessedEventSerializer)
      .build())
    .setDeliveryGuarantee(kafkaDeliveryGuarantee)
    .build()
  )
    .name("Sink-Kafka-testFlow.ProcessedEvent")
    .setParallelism(1)
}
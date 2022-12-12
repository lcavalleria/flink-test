package config

import com.mongodb.{ServerApi, ServerApiVersion}
import org.apache.flink.connector.base.DeliveryGuarantee
import org.apache.flink.streaming.api.CheckpointingMode
import org.mongodb.scala.{ConnectionString, MongoClientSettings}


// very simple config. Usually use something like PureConfig.
object Config {
  val mongoDbUrl = "mongodb://localhost:4000"
  val checkpointInterval = 60000
  val checkpointingMode = CheckpointingMode.EXACTLY_ONCE
  val maxConcurrentCheckpoints = 1
  val kafkaServers = "localhost:9092"
  val kafkaConsumerGroup = "flinkTest"
  val kafkaDeliveryGuarantee = DeliveryGuarantee.AT_LEAST_ONCE // I assume this goes to ES
  val kafkaSinkTopic = "DQCEVENTS"
  val database = "DQC"
  val dbDqcEventsCollection = "dqcEvents"

  val mongoClientSettings: MongoClientSettings = MongoClientSettings.builder()
    .applyConnectionString(ConnectionString(mongoDbUrl))
    .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
    .build()

}

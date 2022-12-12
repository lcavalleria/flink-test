package sinks

import alarmOutFlow.ProcessedEvent
import config.Config
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}

class MongoSink()(implicit mongoClient: MongoClient) extends RichSinkFunction[ProcessedEvent] {
  private val DB: MongoDatabase = mongoClient.getDatabase(Config.database)
  private val collection: MongoCollection[Document] = DB.getCollection(Config.dbDqcEventsCollection)

  override def invoke(value: ProcessedEvent, context: SinkFunction.Context): Unit = {
    collection.insertOne(Document(value.toJsonString))
  }
}

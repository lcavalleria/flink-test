package serialization

import alarmOutFlow.ProcessedEvent
import com.github.plokhotnyuk.jsoniter_scala.core._
import org.apache.flink.api.common.serialization.SerializationSchema
import serialization.Codecs.processedEventCodec

class ProcessedEventSerializer extends SerializationSchema[ProcessedEvent] {
  override def serialize(element: ProcessedEvent): Array[Byte] = {
    writeToArray(element)
  }
}

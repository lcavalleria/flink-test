package serialization

import alarmOutFlow.EventIn
import com.github.plokhotnyuk.jsoniter_scala.core._
import org.apache.flink.api.common.serialization.DeserializationSchema
import org.apache.flink.api.common.typeinfo.TypeInformation
import serialization.Codecs.eventInCodec

class EventInDeserializer extends DeserializationSchema[EventIn] {
  override def isEndOfStream(nextElement: EventIn): Boolean = nextElement == null

  override def getProducedType: TypeInformation[EventIn] = TypeInformation.of(classOf[EventIn])

  override def deserialize(message: Array[Byte]): EventIn = {
    readFromArray(message)
  }
}

package serialization

import alarmOutFlow.{EventIn, ProcessedEvent}
import com.github.plokhotnyuk.jsoniter_scala.core.{JsonReader, JsonValueCodec, JsonWriter}
import com.github.plokhotnyuk.jsoniter_scala.macros.{CodecMakerConfig, JsonCodecMaker}

import java.text.SimpleDateFormat
import java.util.Date

object Codecs {
  implicit val dateCodec: JsonValueCodec[Date] = new JsonValueCodec[Date] {
    override def decodeValue(in: JsonReader, default: Date): Date = {
      val dateStr = in.readString("")
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dateStr)
    }

    override def encodeValue(x: Date, out: JsonWriter): Unit =
      out.writeVal(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(x))

    override def nullValue: Date = null
  }
  implicit val eventInCodec: JsonValueCodec[EventIn] = JsonCodecMaker.make[EventIn](CodecMakerConfig)
  implicit val processedEventCodec: JsonValueCodec[ProcessedEvent] = JsonCodecMaker.make[ProcessedEvent](CodecMakerConfig)

}

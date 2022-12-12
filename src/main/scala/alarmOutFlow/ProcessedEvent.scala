package alarmOutFlow

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.{ObjectMapper, ObjectWriter}
import alarmOutFlow.ProcessedEvent.EventValue

import java.util.Date

case class ProcessedEvent(
  Timestamp: Date,
  signalName: String,
  value: EventValue,
  line: String,
  cell: String,
  asset: String,
  subAsset: String,
  operation: String,
  processType: String
) {
  private val writer: ObjectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter()

  def toJsonString: String =
    writer.writeValueAsString(this)
}

object ProcessedEvent {
  case class EventValue(
    description: String,
    traceabilityCode: String,
    status: String,
    working: String,
    startTime: Long,
    endTime: Long,
    feature: String,
    asset: String,
    value: String,
    limit: String,
    deviation: String
  )

  def fromEventIn(in: EventIn): ProcessedEvent = {
    ProcessedEvent(
      in.timestamp,
      in.signalName,
      eventValueFromString(in.value, in.timestamp.getTime),
      in.line,
      in.cell,
      in.asset,
      in.subAsset,
      in.operation,
      in.processType
    )
  }

  private def eventValueFromString(s: String, timestamp: Long): EventValue = {
    val valueFields = s.split(",")
    EventValue(
      description = "Process Alarm",
      traceabilityCode = valueFields(0).substring(1),
      status = valueFields(0).charAt(0).toString,
      working = valueFields(2),
      startTime = timestamp,
      endTime = timestamp,
      feature = valueFields(3),
      asset = valueFields(1),
      value = valueFields(5).trim,
      limit = "",
      deviation = valueFields(6)
    )
  }

}
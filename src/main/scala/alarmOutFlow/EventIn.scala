package alarmOutFlow

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.{JsonFormat, JsonProperty}

import java.util.Date


case class EventIn(
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @JsonProperty("Timestamp")
  timestamp: Date,
  @JsonProperty("SignalName")
  signalName: String,
  @JsonProperty("Value")
  value: String,
  @JsonProperty("Line")
  line: String,
  @JsonProperty("Cell")
  cell: String,
  @JsonProperty("Asset")
  asset: String,
  @JsonProperty("SubAsset")
  subAsset: String,
  @JsonProperty("Operation")
  operation: String,
  @JsonProperty("ProcessType")
  processType: String
)

object EventIn {

}
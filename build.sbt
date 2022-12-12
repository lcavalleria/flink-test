scalaVersion := "2.12.17"

name := "flink-test"
organization := "org.lluis"
version := "0.1"

val flinkVersion = "1.16.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
libraryDependencies += "org.apache.flink" % "flink-core" % flinkVersion
libraryDependencies += "org.apache.flink" %% "flink-streaming-scala" % flinkVersion
libraryDependencies += "org.apache.flink" % "flink-connector-kafka" % flinkVersion
libraryDependencies += "org.apache.flink" %% "flink-connector-cassandra" % flinkVersion
libraryDependencies += "org.apache.flink" % "flink-clients" % flinkVersion

// mongoDB
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.8.0"

// jsoniter
val jsoniterVersion = "2.19.1"
libraryDependencies += "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % jsoniterVersion % Compile
libraryDependencies += "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterVersion % Provided // required only in compile-time



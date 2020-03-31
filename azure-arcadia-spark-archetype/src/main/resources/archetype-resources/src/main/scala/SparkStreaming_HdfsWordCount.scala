/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// scalastyle:off println
package ${package}

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * Counts words in new text files created in the given directory
 */
object SparkStreaming_HdfsWordCount {
  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("HdfsWordCount")
    // Create the context
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, Seconds(2))

   // create an input stream from a queue of RDDs.
   // Pushing more RDDs in the queue to simulate process more events in the batch interval.
    val inputData: mutable.Queue[RDD[String]] = mutable.Queue()
    DataSources.copyright.foreach(line => inputData += sc.parallelize(List(line)))

    val inputStream: InputDStream[String] = ssc.queueStream(inputData)

    val words = inputStream.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}

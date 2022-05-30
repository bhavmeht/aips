package com.aips.traffic

import com.aips.traffic.signal.model.TrafficSample
import com.typesafe.scalalogging.LazyLogging
import resource.managed

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.io.Source

package object signal extends LazyLogging {

  private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

  /** Read file content and create TrafficSample object
   * @param filePath: Full path to traffic samples file
   * @return List[TrafficSamples]: List of parsed traffic samples
   */
  def getTrafficSamples(filePath: String): List[TrafficSample] = {
    getLinesFromFile(filePath).map(_.split(" ").map(_.trim)).map { sample =>
      model.TrafficSample(parse(sample.head), sample.last.toInt)
    }
  }

  /** Convert time in string format to LocalDateTime */
  private def parse(time: String): LocalDateTime = LocalDateTime.from(formatter.parse(time))

  /** Read file content and returns list of strings by line.
   *
   * @param path : Path to input file
   * @return List[String]: Line contents in file
   */
  private def getLinesFromFile(path: String): List[String] = {
    logger.debug(s"Reading input file content from path: $path")
    managed(Source.fromFile(path)("UTF-8")).acquireAndGet(_.getLines().map(_.toLowerCase).toList)
  }

}

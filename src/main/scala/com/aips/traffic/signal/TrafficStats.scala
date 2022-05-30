package com.aips.traffic.signal

import com.aips.traffic.signal.model.TrafficSample

import java.time.{LocalDate, LocalDateTime}

trait TrafficStats[T] {
  /** The number of cars seen in total */
  def getTotalCars(samples: List[T]): Int

  /** The total number of cars by each date */
  def getNumberOfCarsByDay(sample: List[T]): Map[LocalDate, Int]

  /** The top 3 half hours with most number of cars */
  def getTop3MostCarsSeen(samples: List[T]): List[T]

  /** The 1.5 hour period with least cars (i.e. 3 contiguous half hour records) */
  def getLeastCarsInOneAndHalfHour(samples: List[T]): (List[LocalDateTime], Int)
}

object TrafficStats {

  lazy val trafficStatsImpl: TrafficStats[TrafficSample] = new TrafficStats[TrafficSample] {

    def getTotalCars(samples: List[TrafficSample]): Int = samples.map(_.numberOfCars).sum

    def getNumberOfCarsByDay(samples: List[TrafficSample]): Map[LocalDate, Int] =
      samples.groupBy(_.sampleTime.toLocalDate)
        .mapValues(_.map(_.numberOfCars).sum)

    def getTop3MostCarsSeen(samples: List[TrafficSample]): List[TrafficSample] = {
      samples.sortBy(_.numberOfCars)(Ordering[Int].reverse).take(3)
    }

    def getLeastCarsInOneAndHalfHour(samples: List[TrafficSample]): (List[LocalDateTime], Int) = {
      if (samples.nonEmpty) {
        samples.sliding(3)
          .map(samples => (samples.map(_.sampleTime), samples.map(_.numberOfCars).sum))
          .minBy(_._2)
      } else (Nil, 0)
    }
  }

}

package com.aips.traffic.signal

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.aips.traffic.signal.TrafficStats._
import com.aips.traffic.signal.model.TrafficSample

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter

class TrafficStatsSpec extends AnyFlatSpec with Matchers {

  private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

  private def parse(time: String): LocalDateTime = LocalDateTime.from(formatter.parse(time))

  behavior of "getTotalCars"
  it should "return total number of cars seen" in {
    val trafficSamples = List(
      TrafficSample(parse("2021-12-01T05:00:00"), 10),
      TrafficSample(parse("2021-12-01T05:30:00"), 10),
      TrafficSample(parse("2021-12-01T06:00:00"), 10),
      TrafficSample(parse("2021-12-01T06:30:00"), 10),
      TrafficSample(parse("2021-12-01T07:00:00"), 10),
    )
    trafficStatsImpl.getTotalCars(trafficSamples) shouldBe 50
  }

  it should "not return any result if samples are not available" in {
    trafficStatsImpl.getTotalCars(Nil) shouldBe 0
  }

  behavior of "getNumberOfCarsByDay"
  it should "return total number of cars seen by each date" in {
    val trafficSamples = List(
      TrafficSample(parse("2021-12-01T05:00:00"), 10),
      TrafficSample(parse("2021-12-02T05:30:00"), 10),
      TrafficSample(parse("2021-12-02T06:00:00"), 10),
      TrafficSample(parse("2021-12-03T06:30:00"), 10),
      TrafficSample(parse("2021-12-04T07:00:00"), 10),
    )
    val result = trafficStatsImpl.getNumberOfCarsByDay(trafficSamples)
    result.size shouldBe 4
    result(LocalDate.parse("2021-12-01")) shouldBe 10
    result(LocalDate.parse("2021-12-02")) shouldBe 20
    result(LocalDate.parse("2021-12-03")) shouldBe 10
    result(LocalDate.parse("2021-12-04")) shouldBe 10
  }

  it should "return element if sample size is 1" in {
    val trafficSamples = List(
      TrafficSample(parse("2021-12-01T05:00:00"), 10)
    )
    val result = trafficStatsImpl.getNumberOfCarsByDay(trafficSamples)
    result.size shouldBe 1
    result(LocalDate.parse("2021-12-01")) shouldBe 10
  }

  it should "not return any result if samples are not available" in {
    trafficStatsImpl.getNumberOfCarsByDay(Nil) shouldBe Map.empty
  }

  behavior of "getTop3MostCarsSeen"
  it should "return the top 3 half hours with most number of cars" in {
    val trafficSamples = List(
      TrafficSample(parse("2021-12-01T05:00:00"), 10),
      TrafficSample(parse("2021-12-02T05:30:00"), 40),
      TrafficSample(parse("2021-12-02T06:00:00"), 90),
      TrafficSample(parse("2021-12-03T06:30:00"), 20),
      TrafficSample(parse("2021-12-04T07:00:00"), 50),
    )
    val result = trafficStatsImpl.getTop3MostCarsSeen(trafficSamples)
    result.size shouldBe 3
    result.map(_.sampleTime) should contain only(
      parse("2021-12-02T06:00:00"),
      parse("2021-12-04T07:00:00"),
      parse("2021-12-02T05:30:00")
    )
    result.map(_.numberOfCars) should contain only(90, 50, 40)
  }

  it should "return results if number of sample are less than 3" in {
    val trafficSamples = List(
      TrafficSample(parse("2021-12-01T05:00:00"), 10),
      TrafficSample(parse("2021-12-02T05:30:00"), 40)
    )
    val result = trafficStatsImpl.getTop3MostCarsSeen(trafficSamples)
    result.size shouldBe 2
    result.map(_.sampleTime) should contain only(
      parse("2021-12-01T05:00:00"),
      parse("2021-12-02T05:30:00")
    )
    result.map(_.numberOfCars) should contain only(10, 40)
  }

  it should "not return any result if samples are not available" in {
    trafficStatsImpl.getTop3MostCarsSeen(Nil) shouldBe empty
  }

  behavior of "getLeastCarsInOneAndHalfHour"
  it should "return consecutive 3 samples with least number of cars" in {
    val trafficSamples = List(
      TrafficSample(parse("2021-12-01T05:00:00"), 10),
      TrafficSample(parse("2021-12-02T05:30:00"), 40),
      TrafficSample(parse("2021-12-02T06:00:00"), 0),
      TrafficSample(parse("2021-12-03T06:30:00"), 5),
      TrafficSample(parse("2021-12-04T07:00:00"), 50),
      TrafficSample(parse("2021-12-05T07:00:00"), 1),
    )
    val (timeSample, cars) = trafficStatsImpl.getLeastCarsInOneAndHalfHour(trafficSamples)
    cars shouldBe 45
    timeSample.size shouldBe 3
    timeSample should contain only(
      parse("2021-12-02T05:30:00"),
      parse("2021-12-02T06:00:00"),
      parse("2021-12-03T06:30:00")
    )
  }

  it should "return expected results even when number of samples are less than 3" in {
    val trafficSamples = List(
      TrafficSample(parse("2021-12-01T05:00:00"), 10),
      TrafficSample(parse("2021-12-02T05:30:00"), 40)
    )
    val (timeSample, cars) = trafficStatsImpl.getLeastCarsInOneAndHalfHour(trafficSamples)
    cars shouldBe 50
    timeSample.size shouldBe 2
    timeSample should contain only(
      parse("2021-12-01T05:00:00"),
      parse("2021-12-02T05:30:00")
    )
  }

  it should "not return any result if samples are not available" in {
    val (timeSample, cars) = trafficStatsImpl.getLeastCarsInOneAndHalfHour(Nil)
    cars shouldBe 0
    timeSample shouldBe Nil
  }

}

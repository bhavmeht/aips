package com.aips.traffic.signal.model

import java.time.LocalDateTime

case class TrafficSample(sampleTime: LocalDateTime, numberOfCars: Int)

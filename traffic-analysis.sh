#!/usr/bin/env bash

# input files
input_file=$1

sbt clean compile

sbt "runMain com.aips.traffic.signal.TrafficAnalysisMain $input_file"
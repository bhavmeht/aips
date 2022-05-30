# AIPS: Traffic Analysis 
*An automated traffic counter sits by a road and counts the number of cars that go past. 
Every half-hour the counter outputs the number of cars seen and resets the counter to zero.*

The program should output: 
- The number of cars seen in total 
- A sequence of lines where each line contains a date (in yyyy-mm-dd format) 
  and the number of cars seen on that day (eg. 2016-11-23 289) for all days listed in the input file. 
- The top 3 half hours with most cars, in the same format as the input file 
- The 1.5 hour period with least cars (i.e. 3 contiguous half hour records)

###Inputs to application:
- InputFile (Traffic samples where each line contains a timestamp (in yyyy-mm-ddThh:mm:ss format, i.e. ISO 8601) 
  for the beginning of a half-hour and the number of cars seen that half hour)

## Assumptions:
- Input data is well formed and valid.

## Running from command prompt

#### Prerequisite for running this application.

* install sbt on machine, for macOS use [SBT] link to install and make sure that sbt directory is in PATH. 
* install scala2 on machine. I have used scala 2.12 version for this project. Use [Scala] link to install scala.
* make sure sbt and scala runs from command prompt.


###From root folder *aips* run following command
#### Build and Compile application

    sbt clean compile

#### Run Test cases

    sbt clean test

#### Create Jar for running locally

    sbt clean assembly

assembly will create fat jar file *aips-assembly.jar* in target folder.

#### Run with command

      ./traffic-analysis.sh "InputFilePath"
Eg:

      ./traffic-analysis.sh "/src/test/resources/traffic-stats.txt"


### Running in IntelliJ IDEA
Add a Run Configuration with the following:

**Main Class:**
Add "com.aips.traffic.signal.TrafficAnalysisMain" as Main class

**Module:**
Select "aips" as module

**Program Arguments:**

    "~/InputFile.txt"


[SBT]: https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html
[Scala]: https://www.scala-lang.org/download/

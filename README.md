# IoT Collector

##
Simple server for collecting Iot data from multiple devices.
### Pre-Requisite
* Java 17
* maven
* Docker
* docker-compose 

### Run all containers with the solution
If you want simply to test the app and not check the solution

Open a terminal in the project root and run:

`docker-compose --file docker-compose-with-solution.yml up -d`

### Build the app
If you want to check the app and run step by step

Open a terminal in the project root and run:

`mvn clean package`

it will compile build and run all the tests

### Before Run the application
First time for setting up the containers, enter project's main folder and type:

`docker-compose up -d`

next time to start,

`docker-compose start`

to stop;

`docker-compose stop`

### Start the application
#### iot-collector-server
Open a terminal in the project root and run:

`java -jar iot-collector-server/target/iot-collector-server-1.0-SNAPSHOT.jar`

#### iot-sensors-gateway
Open a terminal in the project root and run:

`java -jar iot-sensors-gateway/target/iot-sensors-gateway-1.0-SNAPSHOT.jar`

#### iot-sensor
Open a terminal in the project root and run:

`sh run_sensors.sh`

it will start 3 sensors as java process.

To stop them
Open a terminal in the project root and run:

`ps -ef | grep sensor`

you should see something like:
```shell
503 10884     1   0  5:37PM ttys015    0:02.76 java -jar iot-sensor/target/iot-sensor-1.0-SNAPSHOT-fat.jar serial1 temperature.sensor1 -f 10000
503 10885     1   0  5:37PM ttys015    0:02.72 java -jar iot-sensor/target/iot-sensor-1.0-SNAPSHOT-fat.jar serial2 temperature.sensor2 -f 20000
503 10886     1   0  5:37PM ttys015    0:02.67 java -jar iot-sensor/target/iot-sensor-1.0-SNAPSHOT-fat.jar serial3 temperature.sensor3 -f 30000
```

Then kill them with 
```shell
kill -Term 10884
kill -Term 10885
kill -Term 10886
```



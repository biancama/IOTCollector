#! /bin/bash
java -jar iot-sensor/target/iot-sensor-1.0-SNAPSHOT-fat.jar serial1 temperature.sensor1 -f 10000 &
java -jar iot-sensor/target/iot-sensor-1.0-SNAPSHOT-fat.jar serial2 temperature.sensor2 -f 20000 &
java -jar iot-sensor/target/iot-sensor-1.0-SNAPSHOT-fat.jar serial3 temperature.sensor3 -f 30000 &
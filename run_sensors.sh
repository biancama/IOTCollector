#! /bin/bash
java -jar iot-sensor/target/iot-sensor-1.0-SNAPSHOT-fat.jar serial1 temperature.sensor1 -t TEMP -f 10000 &
java -jar iot-sensor/target/iot-sensor-1.0-SNAPSHOT-fat.jar serial2 heart_rate.sensor2 -t HEART_RATE -f 20000 &
java -jar iot-sensor/target/iot-sensor-1.0-SNAPSHOT-fat.jar serial3 fuel_reader.sensor3 -t FUEL_READER -f 30000 &
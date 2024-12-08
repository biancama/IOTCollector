package com.flexdevit.relay42.iot.sensor;

import picocli.CommandLine;

import java.util.Timer;

public class App
{
    public static void main( String[] args )  {
          var sensor = CommandLine.call(new Processor(), args);
          if (sensor == null || sensor.isEmpty()) {
              System.exit(-1);
          } else {
              new Timer().scheduleAtFixedRate(sensor.get(),0,sensor.get().getPeriod());
          }
    }
}

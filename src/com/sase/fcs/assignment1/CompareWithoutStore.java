package com.sase.fcs.assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Class for Comparing Sensor Data.
 *
 * @author Srijith
 * Reads sensor data from file,
 * populates sensor objects in pairs
 * Compares Sensor data
 * finds the sensor with max and min temperature value
 */
public class CompareWithoutStore implements Comparator<Sensor> {
  /**
   * temperature is stored as 4th column in the file.
   *  This value is used to retrieve temperature value from
   * file
   */
  private static final int SENSOR_TEMPERATURE_COLUMN = 3;
  /**
   * Sensor with max temperature.
   */
  private Sensor maxSensor;
  /**
   * Sensor with min temperature.
   */
  private Sensor minSensor;
  /**
   * Counter to count number of comparisons.
   */
  private static int i = 0;

  /**
   * Reads sensor data from file.
   * Compares temperature of sensors pair wise
   * and finds sensors generating max and min temps
   *
   * @param args
   *          Not used
   * @throws IOException
   *           File related exceptions
   * @throws ParseException
   *           File Related Exception
   */
  public static void main(final String[] args)
      throws IOException, ParseException {

    Sensor s1;
    Sensor s2;
    BufferedReader br = null;
    try {
      CompareWithoutStore c = new CompareWithoutStore();
      br = new BufferedReader(new FileReader(new File("input.txt")));
      int iter = 0, oddSensor = 0;
      String sensorOne = br.readLine();
      String sensorTwo = br.readLine();
      if (sensorOne == null && iter == 1) {
        System.out.println("No input in file");
        System.exit(0);
      }
      while (sensorOne != null && sensorTwo != null) {
        iter++;
        String[] sensorArray = sensorOne.split(",");
        s1 = new Sensor();
        s1.setSensorId(sensorArray[0]);
        s1.setLocation(sensorArray[1]);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        s1.setTimeStamp(fmt.parse(sensorArray[2]));
        s1.setTemperatureValue(
            Float.parseFloat(sensorArray[SENSOR_TEMPERATURE_COLUMN]));
        sensorArray = sensorTwo.split(",");
        s2 = new Sensor();
        s2.setSensorId(sensorArray[0]);
        s2.setLocation(sensorArray[1]);
        s2.setTimeStamp(fmt.parse(sensorArray[2]));
        s2.setTemperatureValue(
            Float.parseFloat(sensorArray[SENSOR_TEMPERATURE_COLUMN]));
        // on first iteration, store the first two values
        // one as max sensor and another as min sensor
        if (iter == 1) {
          CompareWithoutStore.i++;
          if (s1.getTemperatureValue() > s2.getTemperatureValue()) {
            c.maxSensor = s1;
            c.minSensor = s2;
          } else {
            c.maxSensor = s2;
            c.minSensor = s1;
          }
        } else {
          c.compare(s1, s2);
        }
        sensorOne = br.readLine();
        sensorTwo = br.readLine();
      }
      /**
       *    In case of odd number of sensors,
       *    last sensor will not have any pair
       *    Compare the last sensor with actual max
       *    and actual min and change accordingly
       */
        if (sensorOne != null && sensorTwo == null) {
          oddSensor = 1;
        String[] sensorArray = sensorOne.split(",");
        s1 = new Sensor();
        s1.setSensorId(sensorArray[0]);
        s1.setLocation(sensorArray[1]);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        s1.setTimeStamp(fmt.parse(sensorArray[2]));
        s1.setTemperatureValue(
            Float.parseFloat(sensorArray[SENSOR_TEMPERATURE_COLUMN]));
          CompareWithoutStore.i++;
        if (s1.getTemperatureValue() > c.maxSensor.getTemperatureValue()) {
          c.maxSensor = s1;
        } else if (s1.getTemperatureValue()
            < c.minSensor.getTemperatureValue()) {
          c.minSensor = s1;
        }
        CompareWithoutStore.i++;
      }
        int totalNoOfSensors = (iter * 2) + oddSensor;
        System.out.println("Total No of Sensors:" + totalNoOfSensors);
      System.out.println("Details of Sensor with Max Temp Value:"
      + c.maxSensor.toString());
      System.out.println("Details of Sensor with Min Temp value:"
      + c.minSensor.toString());
      System.out.println("No of Comparisons: " + CompareWithoutStore.i);
    } finally {
      try {
        br.close();
      } catch (Exception e) {
        System.out.println("Buffered Reader close Exception");
      }
    }
  }

  /**
   * Compares two sensor objects and finds the min and max.
   *  Then compares the min with actual
   * MinSensor and max with actual maxSensor and changes value accordingly
   *
   * @param s1
   *          Sensor-1
   * @param s2
   *          Sensor-2
   * @return 0 used for nothing
   */
  public final int compare(final Sensor s1, final Sensor s2) {
    CompareWithoutStore.i++;
    if (s1.getTemperatureValue() > s2.getTemperatureValue()) {
      CompareWithoutStore.i++;
      if (s1.getTemperatureValue() > maxSensor.getTemperatureValue()) {
        maxSensor = s1;
      }
      CompareWithoutStore.i++;
      if (s2.getTemperatureValue() < minSensor.getTemperatureValue()) {
        minSensor = s2;
      }
    } else {
      CompareWithoutStore.i++;
      if (s2.getTemperatureValue() > maxSensor.getTemperatureValue()) {
        maxSensor = s2;
      }
      CompareWithoutStore.i++;
      if (s1.getTemperatureValue() < minSensor.getTemperatureValue()) {
        minSensor = s1;
      }
    }
    return 0;
  }
}

package com.example.netcdf;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFiles;
import ucar.nc2.Variable;

@Service
public class NetcdfService {

  private Logger logger = LoggerFactory.getLogger(NetcdfService.class);

  public List<DataPoint> readFile(String filename, int limit) {
    try (NetcdfFile netcdfFile = NetcdfFiles.open(filename)) {
      Variable tos = netcdfFile.findVariable("tos"); // 3d variable (time, lat, lon)
      Variable lat = netcdfFile.findVariable("lat");
      Variable lon = netcdfFile.findVariable("lon");
      Variable time = netcdfFile.findVariable("time");

      Array tosData = tos.read();
      Index tosIndex = tosData.getIndex();

      Array latData = lat.read();
      Index latIndex = latData.getIndex();

      Array lonData = lon.read();
      Index lonIndex = lonData.getIndex();

      Array timeData = time.read();
      Index timeIndex = timeData.getIndex();

      int[] shape = tosData.getShape();

      List<DataPoint> points = new ArrayList<>();

      int count = 0;
      for (int iTime = 0; iTime < shape[0]; iTime++) {
        for (int iLat = 0; iLat < shape[1]; iLat++) {
          for (int iLon = 0; iLon < shape[2]; iLon++) {
            count++;
            float temp = tosData.getFloat(tosIndex.set(iTime, iLat, iLon));
            double latValue = latData.getDouble(latIndex.set(iLat));
            double lonValue = lonData.getDouble(lonIndex.set(iLon));
            double timeValue = timeData.getDouble(timeIndex.set(iTime));

            // logger.info("The temp at [{}, {}] at {} was {} K", latValue, lonValue,
            // timeValue, temp);

            points.add(new DataPoint(latValue, lonValue, timeValue, temp));

            if (count >= limit) {
              return points;
            }

          }
        }
      }

      return points;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}

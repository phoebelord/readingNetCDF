package com.example.netcdf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NetcdfController {

  private NetcdfService netcdfService;

  @Autowired
  public NetcdfController(NetcdfService netcdfService) {
    this.netcdfService = netcdfService;
  }

  @GetMapping("/")
  public ResponseEntity<List<DataPoint>> getJSON() {
    List<DataPoint> points = netcdfService.readFile("netcdf/src/main/resources/tos_O1_2001-2002.nc", 20);
    return new ResponseEntity<>(points, HttpStatus.OK);
  }

}
package com.medicalcenter.receptionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MedicalCenterReceptionApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(MedicalCenterReceptionApiApplication.class, args);
  }
}

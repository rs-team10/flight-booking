/***********************************************************************
 * Module:  Vehicle.java
 * Author:  Filip
 * Purpose: Defines the Class Vehicle
 ***********************************************************************/

import java.util.*;

public class Vehicle {
   private Long id;
   private String manufacturer;
   private String model;
   private Integer year;
   private String fuel;
   private String engine;
   private String transmission;
   private Integer seatsCount;
   private Boolean airCondition;
   private BigDecimal dailyRentalPrice;
   private Double averageFeedback;
   private String image;
   
   private VehicleReservation[] reservations;
   private BranchOffice branchOffice;

}
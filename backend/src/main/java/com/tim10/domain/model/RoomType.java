/***********************************************************************
 * Module:  RoomType.java
 * Author:  Filip
 * Purpose: Defines the Class RoomType
 ***********************************************************************/

import java.util.*;

public class RoomType {
   private Long id;
   private String type;
   private BigDecimal pricePerNight;
   private int capacity;
   private Integer singleBedCount;
   private Integer doubleBedCount;
   private Boolean hasTV;
   private String description;
   private String image;
   private Double averageFeedback;
   
   private java.util.Collection<SpecialRoomPrice> specialRoomPrices;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<SpecialRoomPrice> getSpecialRoomPrices() {
      if (specialRoomPrices == null)
         specialRoomPrices = new java.util.HashSet<SpecialRoomPrice>();
      return specialRoomPrices;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorSpecialRoomPrices() {
      if (specialRoomPrices == null)
         specialRoomPrices = new java.util.HashSet<SpecialRoomPrice>();
      return specialRoomPrices.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newSpecialRoomPrices */
   public void setSpecialRoomPrices(java.util.Collection<SpecialRoomPrice> newSpecialRoomPrices) {
      removeAllSpecialRoomPrices();
      for (java.util.Iterator iter = newSpecialRoomPrices.iterator(); iter.hasNext();)
         addSpecialRoomPrices((SpecialRoomPrice)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newSpecialRoomPrice */
   public void addSpecialRoomPrices(SpecialRoomPrice newSpecialRoomPrice) {
      if (newSpecialRoomPrice == null)
         return;
      if (this.specialRoomPrices == null)
         this.specialRoomPrices = new java.util.HashSet<SpecialRoomPrice>();
      if (!this.specialRoomPrices.contains(newSpecialRoomPrice))
         this.specialRoomPrices.add(newSpecialRoomPrice);
   }
   
   /** @pdGenerated default remove
     * @param oldSpecialRoomPrice */
   public void removeSpecialRoomPrices(SpecialRoomPrice oldSpecialRoomPrice) {
      if (oldSpecialRoomPrice == null)
         return;
      if (this.specialRoomPrices != null)
         if (this.specialRoomPrices.contains(oldSpecialRoomPrice))
            this.specialRoomPrices.remove(oldSpecialRoomPrice);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllSpecialRoomPrices() {
      if (specialRoomPrices != null)
         specialRoomPrices.clear();
   }

}
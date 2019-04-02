/***********************************************************************
 * Module:  VehicleReservation.java
 * Author:  Filip
 * Purpose: Defines the Class VehicleReservation
 ***********************************************************************/

import java.util.*;

public class VehicleReservation {
   private Long id;
   private java.util.Date dateFrom;
   private java.util.Date dateTo;
   private BigDecimal totalPrice;
   
   private java.util.Collection<PriceListItem> additionalServices;
   private Reservation reservation;
   private Review review;
   private Vehicle reservedVehicle;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<PriceListItem> getAdditionalServices() {
      if (additionalServices == null)
         additionalServices = new java.util.HashSet<PriceListItem>();
      return additionalServices;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorAdditionalServices() {
      if (additionalServices == null)
         additionalServices = new java.util.HashSet<PriceListItem>();
      return additionalServices.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newAdditionalServices */
   public void setAdditionalServices(java.util.Collection<PriceListItem> newAdditionalServices) {
      removeAllAdditionalServices();
      for (java.util.Iterator iter = newAdditionalServices.iterator(); iter.hasNext();)
         addAdditionalServices((PriceListItem)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newPriceListItem */
   public void addAdditionalServices(PriceListItem newPriceListItem) {
      if (newPriceListItem == null)
         return;
      if (this.additionalServices == null)
         this.additionalServices = new java.util.HashSet<PriceListItem>();
      if (!this.additionalServices.contains(newPriceListItem))
         this.additionalServices.add(newPriceListItem);
   }
   
   /** @pdGenerated default remove
     * @param oldPriceListItem */
   public void removeAdditionalServices(PriceListItem oldPriceListItem) {
      if (oldPriceListItem == null)
         return;
      if (this.additionalServices != null)
         if (this.additionalServices.contains(oldPriceListItem))
            this.additionalServices.remove(oldPriceListItem);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllAdditionalServices() {
      if (additionalServices != null)
         additionalServices.clear();
   }

}
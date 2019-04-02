/***********************************************************************
 * Module:  RentACar.java
 * Author:  Filip
 * Purpose: Defines the Class RentACar
 ***********************************************************************/

import java.util.*;

public class RentACar extends Company {
   private java.util.Collection<BranchOffice> branchOffices;
   private java.util.Collection<RentACarAdmin> administrators;
   private java.util.Collection<QuickVehicleReservation> quickVehicleReservations;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<BranchOffice> getBranchOffices() {
      if (branchOffices == null)
         branchOffices = new java.util.HashSet<BranchOffice>();
      return branchOffices;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorBranchOffices() {
      if (branchOffices == null)
         branchOffices = new java.util.HashSet<BranchOffice>();
      return branchOffices.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newBranchOffices */
   public void setBranchOffices(java.util.Collection<BranchOffice> newBranchOffices) {
      removeAllBranchOffices();
      for (java.util.Iterator iter = newBranchOffices.iterator(); iter.hasNext();)
         addBranchOffices((BranchOffice)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newBranchOffice */
   public void addBranchOffices(BranchOffice newBranchOffice) {
      if (newBranchOffice == null)
         return;
      if (this.branchOffices == null)
         this.branchOffices = new java.util.HashSet<BranchOffice>();
      if (!this.branchOffices.contains(newBranchOffice))
      {
         this.branchOffices.add(newBranchOffice);
         newBranchOffice.setMainOffice(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldBranchOffice */
   public void removeBranchOffices(BranchOffice oldBranchOffice) {
      if (oldBranchOffice == null)
         return;
      if (this.branchOffices != null)
         if (this.branchOffices.contains(oldBranchOffice))
         {
            this.branchOffices.remove(oldBranchOffice);
            oldBranchOffice.setMainOffice((RentACar)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllBranchOffices() {
      if (branchOffices != null)
      {
         BranchOffice oldBranchOffice;
         for (java.util.Iterator iter = getIteratorBranchOffices(); iter.hasNext();)
         {
            oldBranchOffice = (BranchOffice)iter.next();
            iter.remove();
            oldBranchOffice.setMainOffice((RentACar)null);
         }
      }
   }
   /** @pdGenerated default getter */
   public java.util.Collection<RentACarAdmin> getAdministrators() {
      if (administrators == null)
         administrators = new java.util.HashSet<RentACarAdmin>();
      return administrators;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorAdministrators() {
      if (administrators == null)
         administrators = new java.util.HashSet<RentACarAdmin>();
      return administrators.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newAdministrators */
   public void setAdministrators(java.util.Collection<RentACarAdmin> newAdministrators) {
      removeAllAdministrators();
      for (java.util.Iterator iter = newAdministrators.iterator(); iter.hasNext();)
         addAdministrators((RentACarAdmin)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newRentACarAdmin */
   public void addAdministrators(RentACarAdmin newRentACarAdmin) {
      if (newRentACarAdmin == null)
         return;
      if (this.administrators == null)
         this.administrators = new java.util.HashSet<RentACarAdmin>();
      if (!this.administrators.contains(newRentACarAdmin))
      {
         this.administrators.add(newRentACarAdmin);
         newRentACarAdmin.setCompany(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldRentACarAdmin */
   public void removeAdministrators(RentACarAdmin oldRentACarAdmin) {
      if (oldRentACarAdmin == null)
         return;
      if (this.administrators != null)
         if (this.administrators.contains(oldRentACarAdmin))
         {
            this.administrators.remove(oldRentACarAdmin);
            oldRentACarAdmin.setCompany((RentACar)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllAdministrators() {
      if (administrators != null)
      {
         RentACarAdmin oldRentACarAdmin;
         for (java.util.Iterator iter = getIteratorAdministrators(); iter.hasNext();)
         {
            oldRentACarAdmin = (RentACarAdmin)iter.next();
            iter.remove();
            oldRentACarAdmin.setCompany((RentACar)null);
         }
      }
   }
   /** @pdGenerated default getter */
   public java.util.Collection<QuickVehicleReservation> getQuickVehicleReservations() {
      if (quickVehicleReservations == null)
         quickVehicleReservations = new java.util.HashSet<QuickVehicleReservation>();
      return quickVehicleReservations;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorQuickVehicleReservations() {
      if (quickVehicleReservations == null)
         quickVehicleReservations = new java.util.HashSet<QuickVehicleReservation>();
      return quickVehicleReservations.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newQuickVehicleReservations */
   public void setQuickVehicleReservations(java.util.Collection<QuickVehicleReservation> newQuickVehicleReservations) {
      removeAllQuickVehicleReservations();
      for (java.util.Iterator iter = newQuickVehicleReservations.iterator(); iter.hasNext();)
         addQuickVehicleReservations((QuickVehicleReservation)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newQuickVehicleReservation */
   public void addQuickVehicleReservations(QuickVehicleReservation newQuickVehicleReservation) {
      if (newQuickVehicleReservation == null)
         return;
      if (this.quickVehicleReservations == null)
         this.quickVehicleReservations = new java.util.HashSet<QuickVehicleReservation>();
      if (!this.quickVehicleReservations.contains(newQuickVehicleReservation))
         this.quickVehicleReservations.add(newQuickVehicleReservation);
   }
   
   /** @pdGenerated default remove
     * @param oldQuickVehicleReservation */
   public void removeQuickVehicleReservations(QuickVehicleReservation oldQuickVehicleReservation) {
      if (oldQuickVehicleReservation == null)
         return;
      if (this.quickVehicleReservations != null)
         if (this.quickVehicleReservations.contains(oldQuickVehicleReservation))
            this.quickVehicleReservations.remove(oldQuickVehicleReservation);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllQuickVehicleReservations() {
      if (quickVehicleReservations != null)
         quickVehicleReservations.clear();
   }
   
   public PriceList additionalServicesPriceList;

}
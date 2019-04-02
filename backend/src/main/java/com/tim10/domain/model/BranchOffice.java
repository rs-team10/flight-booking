/***********************************************************************
 * Module:  BranchOffice.java
 * Author:  Filip
 * Purpose: Defines the Class BranchOffice
 ***********************************************************************/

import java.util.*;

public class BranchOffice {
   private Long id;
   private Location location;
   
   private Vehicle[] vehicle;
   private RentACar mainOffice;
   
   
   /** @pdGenerated default parent getter */
   public RentACar getMainOffice() {
      return mainOffice;
   }
   
   /** @pdGenerated default parent setter
     * @param newRentACar */
   public void setMainOffice(RentACar newRentACar) {
      if (this.mainOffice == null || !this.mainOffice.equals(newRentACar))
      {
         if (this.mainOffice != null)
         {
            RentACar oldRentACar = this.mainOffice;
            this.mainOffice = null;
            oldRentACar.removeBranchOffices(this);
         }
         if (newRentACar != null)
         {
            this.mainOffice = newRentACar;
            this.mainOffice.addBranchOffices(this);
         }
      }
   }

}
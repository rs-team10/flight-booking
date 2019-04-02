/***********************************************************************
 * Module:  RentACarAdmin.java
 * Author:  Filip
 * Purpose: Defines the Class RentACarAdmin
 ***********************************************************************/

import java.util.*;

public class RentACarAdmin extends User {
   private Boolean hasCustomPassword;
   
   private RentACar company;
   
   
   /** @pdGenerated default parent getter */
   public RentACar getCompany() {
      return company;
   }
   
   /** @pdGenerated default parent setter
     * @param newRentACar */
   public void setCompany(RentACar newRentACar) {
      if (this.company == null || !this.company.equals(newRentACar))
      {
         if (this.company != null)
         {
            RentACar oldRentACar = this.company;
            this.company = null;
            oldRentACar.removeAdministrators(this);
         }
         if (newRentACar != null)
         {
            this.company = newRentACar;
            this.company.addAdministrators(this);
         }
      }
   }

}
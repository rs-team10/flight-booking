/***********************************************************************
 * Module:  HotelAdmin.java
 * Author:  Filip
 * Purpose: Defines the Class HotelAdmin
 ***********************************************************************/

import java.util.*;

public class HotelAdmin extends User {
   private Boolean hasCustomPassword;
   
   public Hotel company;
   
   
   /** @pdGenerated default parent getter */
   public Hotel getCompany() {
      return company;
   }
   
   /** @pdGenerated default parent setter
     * @param newHotel */
   public void setCompany(Hotel newHotel) {
      if (this.company == null || !this.company.equals(newHotel))
      {
         if (this.company != null)
         {
            Hotel oldHotel = this.company;
            this.company = null;
            oldHotel.removeAdministrators(this);
         }
         if (newHotel != null)
         {
            this.company = newHotel;
            this.company.addAdministrators(this);
         }
      }
   }

}
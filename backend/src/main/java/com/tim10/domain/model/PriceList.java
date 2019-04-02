/***********************************************************************
 * Module:  PriceList.java
 * Author:  Filip
 * Purpose: Defines the Class PriceList
 ***********************************************************************/

import java.util.*;

public class PriceList {
   private Long id;
   
   private java.util.Collection<PriceListItem> priceListItems;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<PriceListItem> getPriceListItems() {
      if (priceListItems == null)
         priceListItems = new java.util.HashSet<PriceListItem>();
      return priceListItems;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorPriceListItems() {
      if (priceListItems == null)
         priceListItems = new java.util.HashSet<PriceListItem>();
      return priceListItems.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newPriceListItems */
   public void setPriceListItems(java.util.Collection<PriceListItem> newPriceListItems) {
      removeAllPriceListItems();
      for (java.util.Iterator iter = newPriceListItems.iterator(); iter.hasNext();)
         addPriceListItems((PriceListItem)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newPriceListItem */
   public void addPriceListItems(PriceListItem newPriceListItem) {
      if (newPriceListItem == null)
         return;
      if (this.priceListItems == null)
         this.priceListItems = new java.util.HashSet<PriceListItem>();
      if (!this.priceListItems.contains(newPriceListItem))
         this.priceListItems.add(newPriceListItem);
   }
   
   /** @pdGenerated default remove
     * @param oldPriceListItem */
   public void removePriceListItems(PriceListItem oldPriceListItem) {
      if (oldPriceListItem == null)
         return;
      if (this.priceListItems != null)
         if (this.priceListItems.contains(oldPriceListItem))
            this.priceListItems.remove(oldPriceListItem);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllPriceListItems() {
      if (priceListItems != null)
         priceListItems.clear();
   }

}
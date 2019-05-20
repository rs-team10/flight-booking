<!-- Rent a car homepage -->
<template>
 
    <v-container>    
        <v-layout row>
        <v-flex xs8>
        <v-layout column>
        <v-toolbar tabs>
            <v-tabs
            v-model="tabs"
            fixed-tabs
            color="transparent"
            >
                <v-tabs-slider></v-tabs-slider>
                <v-tab href="#mobile-tabs-5-1" class="primary--text">
                    <v-icon>info</v-icon>
                </v-tab>

                <v-tab href="#mobile-tabs-5-2" class="primary--text">
                    <v-icon>account_balance</v-icon>
                </v-tab>
                
            </v-tabs>
        
        </v-toolbar>

        
        

            <v-tabs-items v-model="tabs" class="white elevation-1">
                <v-tab-item :value="'mobile-tabs-5-1'">
            
    
                   
                        <component 
                            v-bind:is="component1"
                            :rentACarId="this.realId"
                        > 
                        </component>
                   
        
                </v-tab-item>
                
                <v-tab-item :value="'mobile-tabs-5-2'">
    
                        <component 
                            v-bind:is="component2"
                            :rentACarId="this.realId"
                        > 
                        </component>

                </v-tab-item>

            </v-tabs-items>
        </v-layout>
        </v-flex>
         
        <v-spacer/>
        <v-flex xs3> 
           
            <component :is="component3">
            </component>
       
        </v-flex>
   
    </v-layout>
    </v-container>

</template>




<script>
import ViewBranchOffices from "@/components/rentACarComp/ViewBranchOffices.vue"
import ViewRentACar from "@/components/rentACarComp/ViewRentACar.vue"
import VehiclePriceList from "@/components/rentACarComp/VehiclePriceList.vue"

export default {
    props:{
        rentACarId:{
            type: String,
            default: '0'
        }
    },
    components: {
        'viewBranchOffices' : ViewBranchOffices,
        'viewRentACar' : ViewRentACar,
        'vehiclePriceList' : VehiclePriceList
    },
    data () {
      return {
        component1 : 'viewRentACar',
        component2 : 'viewBranchOffices',
        component3 : 'vehiclePriceList',
        tabs: null,
        text: 'Ovde ce trenutnom adminu da se pokazuju podaci o njegovom rent-a-car servisu',
        realId : 0
      }
    },
    created(){
        this.realId = this.rentACarId
    }
}
</script>

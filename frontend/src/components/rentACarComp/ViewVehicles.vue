<template>
  <div id="view-vehicles">

    <v-layout row>
        <v-flex xs12 sm10 offset-sm1>
         <v-toolbar flat color="white">
      <v-toolbar-title>Available vehicles</v-toolbar-title>
      <v-divider
        class="mx-1"
        inset
        vertical
      ></v-divider>
      <v-spacer></v-spacer>
      <v-text-field
            append-icon="search"
            label="Search"
            single-line
            hide-details
            v-model="search"
         >
      </v-text-field>


      <v-divider
        class="mx-1"
        inset
        vertical
      ></v-divider>
      <v-spacer></v-spacer>
      <!-- Mozda zatreba za neki dijalog
      <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ on }">
          <v-btn color="primary" dark class="mb-3" @click="addItem">Add vehicle</v-btn> verovatno bez dugmeta
        </template>
        <v-card>
          <v-card-text>
            <v-container>
              <v-layout wrap>

                
                 <component 
                  v-bind:is="component"
                  :selectedVehicle="selectedVehicle"
                 > 
                 </component>
  

              </v-layout>
            </v-container>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-toolbar> 
    -->
         <template>

               <v-data-table  
               :headers="headers"
               :items="vehicles"
               :search="search"
               class="elevation-1"
                        >
               <template slot="items" slot-scope="props">
                  <tr @click="showAlert(props.item)">  <!--vehicleSelected(props.item)    -->
                     <td class="text-xs-left">{{ props.item.manufacturer }}</td>
                     <td class="text-xs-left">{{ props.item.model }}</td>
                     <td class="text-xs-left">{{ props.item.year }}</td>
                     <td class="text-xs-left">{{ props.item.fuel }}</td>
                     <td class="text-xs-left">{{ props.item.engine }}</td>
                     <td class="text-xs-left">{{ props.item.transmission }}</td>
                     <td class="text-xs-left">{{ props.item.seatsCount }}</td>
                     <td class="text-xs-left">{{ props.item.airCondition }}</td>
                     <td class="text-xs-left">{{ props.item.dailyRentalPrice }}</td>
                     </tr>
                  </template>
                  <v-alert slot="no-results" :value="true" color="error" icon="warning">
                     Your search for "{{ search }}" found no results.
                  </v-alert>
               </v-data-table>
            </template>
        </v-flex>
    </v-layout>
  </div>
</template>







<script>

import AddVehicle from "@/components/rentACarComp/AddVehicle.vue"
import EditVehicle from "@/components/rentACarComp/EditVehicle.vue"



export default {

   components: {
      //mozda za neki odabir vozila zatreba
   },
  name: 'vehicles',
  data(){
      return{
  
          selectedVehicle  : {},
          search           :  '',
          headers : [
                     {
                        text: 'Manufacturer',
                        align: 'left',
                        sortable: true,
                        value: 'manufacturer'
                     },
                     {
                        text: 'Model',
                        align: 'left',
                        sortable: true,
                        value: 'model'
                     },
                     {
                        text: 'Year',
                        align: 'left',
                        sortable: true,
                        value: 'year'
                     },
                     {
                        text: 'Fuel',
                        align: 'left',
                        sortable: true,
                        value: 'fuel'
                     },
                     {
                        text: 'Engine',
                        align: 'left',
                        sortable: true,
                        value: 'engine'
                     },
                     {
                        text: 'Transmission',
                        align: 'left',
                        sortable: true,
                        value: 'transmission'
                     },
                     {
                        text: 'Seats count',
                        align: 'left',
                        sortable: true,
                        value: 'seatsCount'
                     },
                     {
                        text: 'Air condition',
                        align: 'left',
                        sortable: true,
                        value: 'airCondition'
                     },
                     {
                        text: 'Daily rental price',
                        align: 'left',
                        sortable: true,
                        value: 'dailyRentalPrice'
                     },
                     {
                        text: 'ACTION', 
                        value: 'id', 
                        sortable: false
                     }
                  ],


            component: "addVehicle",
            dialog: false,




          vehicles: [
              {
                id                  : 1,
                manufacturer        : 'BMW',
                model               : '520',
                year                : '2018',
                fuel                : 'Gasoline',
                engine              : 5.0,
                transmission        : 'True',
                seatsCount          : 5,
                airCondition        : 'True',
                dailyRentalPrice    : 10000
                },
          {
                id                  : 2,
                manufacturer        : 'Mercedes',
                model               : '520',
                year                : '2018',
                fuel                : 'Gasoline',
                engine              : 5.0,
                transmission        : 'True',
                seatsCount          : 5,
                airCondition        : 'True',
                dailyRentalPrice    : 100000
          }]
      }
  },
  methods:{
      fetchVehicles: function(){
          this.$axios
          .get('http://localhost:8080/api/branchOffice/vehicles/1')//+branchOffice.id
          .then(response => this.vehicles = response.data)
      },
 
      showAlert: function(){
         alert(this.selectedVehicle.manufacturer) 
      },
   

   //kacin nacin
      vehicleSelected: function(vehicle){
          this.$emit('selectedVehicle', vehicle);
      },
      changeComp: function(vehicle){
            this.selectedVehicle = vehicle;
            this.component = 'editVehicle';
      },

   },

   created(){
      this.fetchVehicles();
   }
}

</script>



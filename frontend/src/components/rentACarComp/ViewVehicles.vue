<template>

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

      <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ on }">
          <v-btn color="primary" dark class="mb-3" @click="addItem">Add vehicle</v-btn>
        </template>
        <v-card>
          <v-card-text>
            <v-container>
              <v-layout wrap>

                
                 <component 
                  v-bind:is="component"
                  :selectedVehicle="selectedVehicle"
                  :myBranch="branchOfficeId"
                 > 
                 </component>
  

              </v-layout>
            </v-container>
          </v-card-text>
        </v-card>
      </v-dialog>

    </v-toolbar> 
         <template>
               <v-flex xs3 sm4>
                  
               </v-flex>
               <v-data-table  
               :headers="headers"
               :items="vehicles"
               :search="search"
               class="elevation-1"
                        >
               <template slot="items" slot-scope="props">
                  <tr><!--<tr @click="showAlert(props.item)">  vehicleSelected(props.item)    -->
                     <td class="text-xs-left">{{ props.item.manufacturer }}</td>
                     <td class="text-xs-left">{{ props.item.model }}</td>
                     <td class="text-xs-left">{{ props.item.year }}</td>
                     <td class="text-xs-left">{{ props.item.fuel }}</td>
                     <td class="text-xs-left">{{ props.item.engine }}</td>
                     <td class="text-xs-left">{{ props.item.transmission }}</td>
                     <td class="text-xs-left">{{ props.item.seatsCount }}</td>
                     <td class="text-xs-left">{{ props.item.airCondition }}</td>
                     <td class="text-xs-left">{{ props.item.dailyRentalPrice }}</td>
                     <td class="justify-center layout px-0">
                        <v-icon
                           small
                           class="mr-2"
                           @click="editItem(props.item)"
                           >
                           edit
                        </v-icon>
                        <v-icon
                           small
                           @click="deleteItem(props.item)"
                           color = "error"
                           >
                           delete
                        </v-icon>
                        </td>
                     </tr>
                  </template>
                  <v-alert slot="no-results" :value="true" color="error" icon="warning">
                     Your search for "{{ search }}" found no results.
                  </v-alert>
               </v-data-table>
            </template>
        </v-flex>
    </v-layout>
</template>







<script>

import AddVehicle from "@/components/rentACarComp/AddVehicle.vue"
import EditVehicle from "@/components/rentACarComp/EditVehicle.vue"



export default {
   props: ['branchOfficeId'],

   components: {
      "addVehicle" : AddVehicle,
      "editVehicle" : EditVehicle
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

         vehicles: []
/*

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
*/
      }

  },
  methods:{
      fetchVehicles: function(){
         
          this.$axios
          .get('http://localhost:8080/api/vehicles/'+ this.branchOfficeId)
          .then(response => this.vehicles = response.data)
          .catch(error => {
                alert(error.resposne.data)
                this.search = 'error';
                this.$slots ='no-results';
               //treba proveriti ako stigne jedan auto da samo njega upise (velicina liste)
               //treba na backendu napraviti da proverava velicinu itema
            });
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
   
      
      
      addItem(){
         this.component = 'addVehicle';
         this.dialog = true;
      },

      editItem (item) {
         this.selectedVehicle = item;
         this.component = 'editVehicle';
         this.dialog = true;  
      },
      deleteItem (item) {
         if(confirm('Are you sure you want to delete this vehicle?')){
            this.$axios
               .delete('http://localhost:8080/api/vehicle/'+item.id)
               .then(response => {
                     this.vehicles = this.vehicles.filter(i=>i !== item);
                     alert(response.data);
               })

           //poziv na backend
         }
      }

   },
   watch: {
      dialog() {
         this.dalogKind = "";
      }
   },
   created(){

      this.fetchVehicles();
   }
}

/*

   
*/


</script>



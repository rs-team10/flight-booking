<template>
  <div id="view-vehicles">
  <h1>Available vehicles</h1> 
    <v-layout row>
        <v-flex xs12 sm8 offset-sm1>
         <template>
               <v-flex xs3 sm4>
                  <v-text-field
                     append-icon="search"
                     label="Search"
                     single-line
                     hide-details
                     v-model="search"
                  >
               </v-text-field>
               </v-flex>
               <v-data-table  
               :headers="headers"
               :items="vehicles"
               :search="search"
               class="elevation-1"
                        >
               <template slot="items" slot-scope="props">
                  <tr @click="showAlert(props.item)"><!--vehicleSelected(props.item)-->
                     <td>{{ props.item.manufacturer }}</td>
                     <td class="text-xs-right">{{ props.item.model }}</td>
                     <td class="text-xs-right">{{ props.item.year }}</td>
                     <td class="text-xs-right">{{ props.item.fuel }}</td>
                     <td class="text-xs-right">{{ props.item.engine }}</td>
                     <td class="text-xs-right">{{ props.item.transmission }}</td>
                     <td class="text-xs-right">{{ props.item.seatsCount }}</td>
                     <td class="text-xs-right">{{ props.item.airCondition }}</td>
                     <td class="text-xs-right">{{ props.item.dailyRentalPrice }}</td>
                     <td>
                        <v-menu
                              open-on-hover
                              offset-y
                              :close-on-content-click="false"
                              lazy
                              :key="props.item.id"
                              >
                              <v-btn
                                 icon
                                 class="mx-0"
                                 slot="activator"
                                 >
                                 <v-icon color="blue">label_important</v-icon>
                              </v-btn>
                              <v-list>
                                    <v-list-tile avatar>
                                       <v-btn
                                          color="error"
                                          @click="removeOne(props.item)"
                                       >
                                          Delete
                                       </v-btn>
         
                                       <v-btn
                                          @click="removeOne(props.item)"
                                       >
                                          Edit
                                       </v-btn>
                                    </v-list-tile>
                                 </v-list>
                              </v-menu>
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
  </div>
</template>


<script>
export default {
  name: 'vehicles',
  data(){
      return{
         
          selectedVehicle  :  [],
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
          .get('http://localhost:8081/api/vehicles')
          .then(response => this.vehicles = response.data)
      },
      vehicleSelected: function(vehicle){
          this.$emit('selectedVehicle', vehicle);
      },
      showAlert:function(a){
         alert('Alert! \n' + a.manufacturer);
      },


      removeOne:function(item){
         this.vehicles = this.vehicles.filter(i=>i !== item)
      }
      
  },
  mounted(){

      this.fetchVehicles();
  }
}
</script>

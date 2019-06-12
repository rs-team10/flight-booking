<template>
    <v-flex>

        

        <v-container>
            <v-card>
                <component 
                    v-bind:is="component1" 
                    @searchVehicle="onSearch"
                    @cancelSearch="cancelSearch"
                > 
                </component>
            </v-card>
        </v-container>

        <v-container>
            <v-layout align-space-around justify-start>
                <v-toolbar tabs>
                    <v-tabs
                    v-model="tabs"
                    fixed-tabs
                    color="transparent"
                >
                
                    
                        <v-tab href="#mobile-tabs-5-1" >
                            <span class="text-uppercase grey--text"> Classic reservation</span>
                        </v-tab>

                        <v-tab href="#mobile-tabs-5-2">
                            <span class="text-uppercase grey--text"> Quick reservation</span>
                        </v-tab>


                    </v-tabs>
                </v-toolbar>
        
            </v-layout>
        </v-container>

        <v-container>

            <v-tabs-items v-model="tabs" class="white elevation-1">
                <v-tab-item :value="'mobile-tabs-5-1'">
            
                    <component
                        :is='component2'
                        :vehicles = vehicles
                    >
                    </component>
                    
        
                </v-tab-item>
                
                <v-tab-item :value="'mobile-tabs-5-2'">
                        
                    <component
                        :is='component3'
                        :vehiclesQuick='vehiclesQuick'
                    >
                    <!--Nekako da mu oznacis da je quic reservation-->
                    </component>

                </v-tab-item>
            </v-tabs-items>

        </v-container> 
    

                    
    </v-flex>


</template>



<script>

import VehicleParamSearch from "@/components/vehicleReservation/VehicleParamSearch.vue"
import VehicleForRes from "@/components/vehicleReservation/VehicleForRes.vue"
import VehicleQuickRes from "@/components/vehicleReservation/VehicleQuickRes.vue"

    export default {

        components: {
            'vehicleParamSearch' : VehicleParamSearch,
            'vehicleForRes' : VehicleForRes,
            'vehicleQuickRes' : VehicleQuickRes
        },

        
        data: () => ({

            component1:'vehicleParamSearch',
            component2: 'vehicleForRes',
            component3: 'vehicleQuickRes',
            
            tabs : null,

            
            vehicles: [],
            vehiclesQuick: []

    }),
    methods : {
        getImgUrl(file) {
            var images = require.context('@/assets/vehicles/', false, /\.jpg$/)
            return images('./' + file)
        },
        priceForPeriod: function(price){
            var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
            var firstDate = Date.parse('2019-01-01'); //this.dateFrom
            var secondDate = Date.parse('2019-01-11'); //this.dateTo (iz propertyja)
        
            return price * (secondDate - firstDate )/(oneDay);
        },
        cancelSearch:function(){
            var inputFrom = '2019-01-01 00:00'; //property pa compute...
            var inputTo = '2019-01-11 00:00';

            var from = this.trim(inputFrom); 
            var to = this.trim(inputTo);
            var country = 'Serbia';

            var countryDate = {
                country : country,
                from : from,
                to : to
            };
            this.fetchVehicles(countryDate);
        },

        fetchVehicles: function(countryDate){
         
            this.$axios
            .get('http://localhost:8080/api/availableVehicles/'+countryDate.from+'/'+countryDate.to+'/'+countryDate.country)
            .then(response => {
                    var vehicles = response.data
                    this.vehicles=[];
                    vehicles.forEach(v => this.vehicles.push({
                                                                id                  : v.id,
                                                                manufacturer        : v.manufacturer,
                                                                model               : v.model,
                                                                year                : v.year,
                                                                fuel                : v.fuel,
                                                                engine              : v.engine,
                                                                transmission        : v.transmission,
                                                                seatsCount          : v.seatsCount,
                                                                airCondition        : v.airCondition,
                                                                dailyRentalPrice    : v.dailyRentalPrice,
                                                                image               : this.getImgUrl(v.image),
                                                                totalPrice          : this.priceForPeriod(v.dailyRentalPrice),
                                                                mainRentACar        : 'My rent-a-car service'
                                                            }));

                }
          )
          .catch(error => {
                alert(error.resposne)
                this.search = 'error';
                this.$slots ='no-results';
               //treba proveriti ako stigne jedan auto da samo njega upise (velicina liste)
               //treba na backendu napraviti da proverava velicinu itema
            });
        },

        onSearch: function(vehicleSearchDTO){
            
            vehicleSearchDTO.dateFrom ='2019-01-01'; //uzimace iz propertyja
            vehicleSearchDTO.dateTo =  '2019-01-11';

            this.$axios
            .post('http://localhost:8080/api/vehicleSearch/serbia',vehicleSearchDTO)
            .then(response => {
                    var vehicles = response.data
                    this.vehicles=[];
                    vehicles.forEach(v => this.vehicles.push({
                                                                id                  : v.id,
                                                                manufacturer        : v.manufacturer,
                                                                model               : v.model,
                                                                year                : v.fuel,
                                                                fuel                : v.fuel,
                                                                engine              : v.engine,
                                                                transmission        : v.transmission,
                                                                seatsCount          : v.seatsCount,
                                                                airCondition        : v.airCondition,
                                                                dailyRentalPrice    : v.dailyRentalPrice,
                                                                image               : this.getImgUrl(v.image),
                                                                totalPrice          : this.priceForPeriod(v.dailyRentalPrice),
                                                                mainRentACar        : 'My rent-a-car service'
                                                            }));

                }
          )
          .catch(error => {
                alert(error.resposne)
                this.search = 'error';
                this.$slots ='no-results';
               //treba proveriti ako stigne jedan auto da samo njega upise (velicina liste)
               //treba na backendu napraviti da proverava velicinu itema
            });
        },

        trim: function(date){
            return date.substring(0, date.length - 6);
        }

    },

    created(){
        var inputFrom = '2019-01-01 00:00'; //property pa compute...
        var inputTo = '2019-01-10 00:00';

        var from = this.trim(inputFrom); 
        var to = this.trim(inputTo);
        var country = 'Serbia';             //property

        var countryDate = {
            country : country,
            from : from,
            to : to
        };
        this.fetchVehicles(countryDate);
    }
  }

</script>


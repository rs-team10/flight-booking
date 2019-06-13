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
                        :from='from'
                        :to='to'
                    >
                    </component>
                    
        
                </v-tab-item>
                
                <v-tab-item :value="'mobile-tabs-5-2'">
                        
                    <component
                        :is='component3'
                        :vehicles='vehiclesQuick'
                        :from='from'
                        :to='to'
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


    export default {

        components: {
            'vehicleParamSearch' : VehicleParamSearch,
            'vehicleForRes' : VehicleForRes,
        },

        
        data: () => ({

            component1:'vehicleParamSearch',
            component2: 'vehicleForRes',
            component3: 'vehicleForRes',
            
            tabs : null,


            vehicles: [],
            vehiclesQuick: [],

            fromInput: '2019-07-30',  //datum koji stize u komponentu (hc zbog testiranja)
            toInput: '2019-08-30',
            country: 'Serbia'          //takodje prop
    }),
    methods : {
        getImgUrl(file) {
            var images = require.context('@/assets/vehicles/', false, /\.jpg$/)
            return images('./' + file)
        },
        priceForPeriod: function(price){
            var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
            var firstDate = Date.parse(this.from); //this.dateFrom
            var secondDate = Date.parse(this.to); //this.dateTo (iz propertyja)
        
            return price * (secondDate - firstDate )/(oneDay);
        },
        cancelSearch:function(){


            var countryDate = {
                country : this.country,
                from : this.from,
                to : this.to
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

        fetchQuickVehicles: function(countryDate){
         
            this.$axios
            .get('http://localhost:8080/api/quickResVehicles/'+countryDate.from+'/'+countryDate.to+'/'+countryDate.country)
            .then(response => {
                    var vehicleQuick = response.data
                    this.vehiclesQuick=[];
                    vehicleQuick.forEach(v => this.vehiclesQuick.push({
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
                                                                totalPrice          : v.dailyRentalPrice, //vrvtno ce da ide ocena xD
                                                                //mainRentACar        : 'My rent-a-car service',
                                                                quick               : true 
  
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
            
            vehicleSearchDTO.dateFrom =this.from; //uzimace iz propertyja
            vehicleSearchDTO.dateTo = this.to;

            this.$axios
            .post('http://localhost:8080/api/vehicleSearch/'+this.country,vehicleSearchDTO)
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
                                                                //mainRentACar        : 'My rent-a-car service'
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
            return date.substring(0, 10);
        }

    },

    created(){

        var countryDate = {
            country : this.country,
            from : this.from,
            to : this.to
        };
        this.fetchVehicles(countryDate);
        this.fetchQuickVehicles(countryDate);
    },
    computed:{
        from(){
            return this.fromInput.substring(0,10);
        },
        to(){
            return this.toInput.substring(0,10);
        }
    }
  }

</script>


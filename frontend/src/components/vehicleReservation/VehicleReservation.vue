<template>
    <v-flex>

        <v-dialog v-model="dateDialog" max-width="600px" persistent>
            <v-card>
                <v-card-title>
                    <span class="ml-3 headline font-weight-light black--text">Please put in information about your reservation</span>
                </v-card-title>
                
                <v-card-text>
                    <!-- datum od -->
                    <v-layout row>
                    <v-menu 
                    v-model="fromMenuDialog"
                    :close-on-content-click="true"
                    :nudge-right="40"
                    lazy
                    transition="scale-transition"
                    offset-y
                    full-width
                    min-width="290px"
                    >
                    <template v-slot:activator="{ on }">
                    <v-flex xs6 md6>
                        <v-text-field readonly
                            v-model="from"
                            append-icon="event"
                            label="Check-in*"
                            class="mx-3"
                            max-width="30px"
                            v-on="on">
                        </v-text-field>
                    </v-flex>
                    </template>
                    <v-date-picker :min="from" disabled v-model="from" @input="fromMenuDialog=false"></v-date-picker>
                    </v-menu>
                    
                    <!-- datum do -->
                    <v-menu
                    v-model="toMenuDialog"
                    :close-on-content-click="true"
                    :nudge-right="40"
                    lazy
                    transition="scale-transition"
                    offset-y
                    full-width
                    min-width="290px"
                    >
                    <template v-slot:activator="{ on }">
                    <v-flex xs6 md6>
                        <v-text-field
                            v-model="to"
                            append-icon="event"
                            label="Check-out*"
                            class="mx-3"
                            v-on="on">
                        </v-text-field>
                    </v-flex>
                    </template>
                    <v-date-picker :min="from" v-model="to" @input="toMenuDialog=false"></v-date-picker>
                    </v-menu>
                    </v-layout>

                    <v-layout row class="px-3">
                        <v-text-field
                            label="Country"
                            class = "pl-2"
                            hide-details
                            single-line
                            v-model="country"
                        ></v-text-field>
                    </v-layout>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn flat color="black" @click="confirmDialog">Confirm</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        

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

    var yourConfig = {headers: { Authorization: "Bearer " + localStorage.getItem("token")}}
    export default {

        components: {
            'vehicleParamSearch' : VehicleParamSearch,
            'vehicleForRes' : VehicleForRes,
        },

        
        data: () => ({


            //start dialog

            dateDialog: false,

            fromMenu: false,
            fromMenuDialog: false,
            fromDate: localStorage.getItem('arrivalDate'),
            toMenu: false,
            toMenuDialog: false,
            toDate: localStorage.getItem('arrivalDate'),
            minDate: new Date().toISOString().substr(0, 10),



            component1:'vehicleParamSearch',
            component2: 'vehicleForRes',
            component3: 'vehicleForRes',
            
            tabs : null,


            vehicles: [],
            vehiclesQuick: [],

            to:'',
            country: ''

           
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
            this.fetchQuickVehicles(countryDate);
        },

        fetchVehicles: function(countryDate){
         
            this.$axios
            .get('/api/availableVehicles/'+countryDate.from+'/'+countryDate.to+'/'+countryDate.country, yourConfig)
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
                this.$swal("Error", error.response.data, 'error');
                this.search = 'error';
                this.$slots ='no-results';
               //treba proveriti ako stigne jedan auto da samo njega upise (velicina liste)
               //treba na backendu napraviti da proverava velicinu itema
            });
        },

        fetchQuickVehicles: function(countryDate){
         
            this.$axios
            .get('/api/quickResVehicles/'+countryDate.from+'/'+countryDate.to+'/'+countryDate.country, yourConfig)
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
                this.$swal("Error", error.response.data, 'error');
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
            .post('/api/vehicleSearch/'+this.country,vehicleSearchDTO, yourConfig)
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
                this.$swal("Error", error.response.data, 'error');
                this.search = 'error';
                this.$slots ='no-results';
               //treba proveriti ako stigne jedan auto da samo njega upise (velicina liste)
               //treba na backendu napraviti da proverava velicinu itema
            });

            this.$axios
            .post('/api/vehicleSearchQuick/'+this.country,vehicleSearchDTO, yourConfig)
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
                this.$swal("Error", error.response.data, 'error');
                this.search = 'error';
                this.$slots ='no-results';
               //treba proveriti ako stigne jedan auto da samo njega upise (velicina liste)
               //treba na backendu napraviti da proverava velicinu itema
            });
        },

        trim: function(date){
            return date.substring(0, 10);
        },
        //change
        confirmDialog(){
            var countryDate = {
                country : this.country,
                from : this.from,
                to : this.to
            };
            if(this.country == ''){
                this.dateDialog = true;
                return;
            }
            if(this.to == ''){
                this.dateDialog = true;
                return;
            }

            this.fetchVehicles(countryDate);
            this.fetchQuickVehicles(countryDate);
            
            this.dateDialog = false;
        },

    },
    mounted(){
        this.dateDialog = true;
        
        var countryDate = {
            country : this.country,
            from : this.from,
            to : this.to
        };
        
    },

    computed:{
        from(){
            var from = localStorage.getItem('arrivalDate');
            if(from != null)
                return from.substring(0,10);
            else{
                this.$router.push("/");
                return '';    
            }
        }
    }
  }

</script>


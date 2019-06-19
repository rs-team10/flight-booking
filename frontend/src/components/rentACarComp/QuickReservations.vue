<template>
    
    <v-card>
        <v-dialog v-model="dialog" max-width="1000px"> 
            <component 
                :is='component'
                :overview ='overview'
                :vehicles ='vehicles'
            >
            <!-- Treba da ide dialog sa quickvehicleom -->
            </component>
        </v-dialog>

         <v-toolbar flat>   
            <v-icon>update</v-icon> 
            <v-toolbar-title>Quick reservations</v-toolbar-title>      
        </v-toolbar>
        <v-list
          two-line
        >
        <template v-for="item in this.quickReservations">
          <v-list-tile :key="item.id">
              
                <v-list-tile-content>

                    <v-list-tile-title>{{item.vehicle.manufacturer}} {{item.vehicle.model}} €{{item.totalPrice}}</v-list-tile-title>
                    <v-list-tile-sub-title class="text--primary">Discount: {{item.discount}}%</v-list-tile-sub-title>
                    <v-list-tile-sub-title>{{item.dateFrom}} - {{item.dateTo}}</v-list-tile-sub-title>

                </v-list-tile-content>
                <v-list-tile-action v-if='adminsPage'>
                    <v-layout row>
                    <v-icon
                            small
                            class="mr-2"
                            @click="editItem(item)"
                            >
                            edit
                        </v-icon>
                        <v-icon
                            small
                            @click="deleteItem(item)"
                            color = "error"
                            >
                            delete
                        </v-icon>
                    </v-layout>
                </v-list-tile-action>
            
          </v-list-tile>
        </template>

        </v-list>
        <v-layout column  align-center v-if = 'adminsPage'>
            <v-btn fab small dark color="primary" @click="addItem" flat><v-icon dark>add</v-icon></v-btn>
        </v-layout>

    </v-card>
    
</template>


<script>
import VehicleReservationPre from "@/components/vehicleReservation/VehicleReservationPre.vue"
import VehicleForRes from "@/components/vehicleReservation/VehicleForRes.vue"


var yourConfig = {headers: { Authorization: "Bearer " + localStorage.getItem("token")}}
export default {
    props:[
            'quickReservations',
            'rentACarId'
        ],


    components:{
        'pehicleReservationPre': VehicleReservationPre,
        'vehicleForRes' : VehicleForRes
    },
    data:() => ({
        component : 'pehicleReservationPre', //neki drugi dialog
        overview:{
                vehicle : '',
                rentACarId: '',
                rentACarName: '',
                branchOfficeId: '',
                country: "",
                city    : "",
                vehicleId: '',
                priceList: {
                    id: '',
                    priceListItems: []
                },
                edit: false
            },

        vehicles: [],

        quickVehicleSelected : '',

        dialog : null,

    }),
    methods:{
        getImgUrl(file) {
            var images = require.context('@/assets/vehicles/', false, /\.jpg$/)
            return images('./' + file)
        },
        fetchVehicles: function(){

            this.$axios
            .get('http://localhost:8080/api/vehiclesRentACar/'+this.rentACarId, yourConfig)
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
                                                                totalPrice          : v.dailyRentalPrice,
                                                                mainRentACar        : this.rentACarId
                                                            }));

                }
          )
          .catch(error => {
                this.$slots ='no-results';
               //treba proveriti ako stigne jedan auto da samo njega upise (velicina liste)
               //treba na backendu napraviti da proverava velicinu itema
            });
        },
        editItem:function(item){
            /*
            this.quickVehicleSelected = item;
            this.component='pehicleReservationPre';

            this.$axios
                .get('http://localhost:8080/api/vehicleReservationPrew/'+item.id, yourConfig)
                .then(respone =>{
                    var datas = respone.data
                    this.overview.rentACarId = datas.rentACarId;
                    this.overview.rentACarName = datas.rentACarName;
                    this.overview.branchOfficeId = datas.branchOfficeId;
                    this.overview.country = datas.country;
                    this.overview.city = datas.city;
                    this.overview.vehicleId = datas.vehicleId;
                    this.overview.priceList = datas.priceList;
                })
                
                this.overview.vehicle = item
                this.overview.edit = true;
            this.dialog= true;
            */

        },
        deleteItem:function(item){
            /*
            this.quickVehicleSelected = item
            */

        },
        addItem:function(){
            this.component='vehicleForRes';
            this.fetchVehicles();
            this.dialog= true;
        }
    },
    computed: {
        adminsPage(){
            return localStorage.getItem("rentACarId") == this.rentACarId
        }
    }
}
</script>

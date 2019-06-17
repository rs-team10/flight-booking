<template>
    <v-card>
        <v-container>
            
            <v-flex xs10>
                <v-toolbar flat>
                    <v-toolbar-title class="font-weight-medium display-1">{{overview.rentACarName}} {{overview.city}}, {{overview.country}}  </v-toolbar-title>
                </v-toolbar>
            </v-flex>
            <v-layout column>
                <v-layout row>
                    <v-flex xs6>
                        <v-layout column>
                            <v-toolbar flat>
                                <v-toolbar-title class="font-weight-light headline">From <b>{{overview.from}}</b> to <b>{{overview.to}}</b></v-toolbar-title>
                            </v-toolbar>
                            <v-img
                                :aspect-ratio="16/9"
                                :src="overview.vehicle.image"
                            >
                            </v-img>
                            <v-layout row>
                            <v-divider vertical/>
                            <v-list>
                                <v-list-tile>
                                    <v-list-tile-content>
                                    <v-list-tile-title>{{overview.vehicle.manufacturer}}</v-list-tile-title>
                                    <v-list-tile-sub-title>Manufacturer</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                </v-list-tile>
                                

                                <v-list-tile>
                                    <v-list-tile-content>
                                    <v-list-tile-title>{{overview.vehicle.model}}</v-list-tile-title>
                                    <v-list-tile-sub-title>Model</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                </v-list-tile>

                            <v-list-tile>
                                    <v-list-tile-content>
                                    <v-list-tile-title>{{overview.vehicle.year}}</v-list-tile-title>
                                    <v-list-tile-sub-title>Year</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                </v-list-tile>

                                <v-list-tile>
                                    <v-list-tile-content>
                                    <v-list-tile-title>{{overview.vehicle.fuel}}</v-list-tile-title>
                                    <v-list-tile-sub-title>Fuel</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                </v-list-tile>
                            </v-list>
                            <v-spacer />
                            <v-list>
                                <v-list-tile>
                                    <v-list-tile-content>
                                    <v-list-tile-title>{{overview.vehicle.engine}}</v-list-tile-title>
                                    <v-list-tile-sub-title>Engine</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                </v-list-tile>
                                <v-list-tile>
                                    <v-list-tile-content>
                                    <v-list-tile-title>{{overview.vehicle.transmission}}</v-list-tile-title>
                                    <v-list-tile-sub-title>Transmission</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                </v-list-tile>
                                <v-list-tile>
                                    <v-list-tile-content>
                                    <v-list-tile-title>{{overview.vehicle.seatsCount}}</v-list-tile-title>
                                    <v-list-tile-sub-title>Seats count</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                </v-list-tile>
                                <v-list-tile>
                                    <v-list-tile-content>
                                    <v-list-tile-title>{{overview.vehicle.airCondition}}</v-list-tile-title>
                                    <v-list-tile-sub-title>Aircondition</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                </v-list-tile>
                            </v-list>

                            </v-layout>
                        </v-layout>
                    </v-flex>
                    <v-divider vertical/>
                    <v-flex xs4>
                        <v-toolbar flat color="gray">
                            <v-toolbar-title class="font-weight-light headline">Additional service</v-toolbar-title>
                        </v-toolbar>
                        <v-list
                        two-line
                        >
                            <template v-for="item in this.overview.priceList.priceListItems">
                                <v-list-tile :key="item.id">
                                        
                                        <v-list-tile-content>

                                            <v-list-tile-title>{{item.name}} €{{item.price}}</v-list-tile-title>
                                            <v-list-tile-sub-title class="text--primary">Discount: {{item.discount}}%</v-list-tile-sub-title>
                                            <v-list-tile-sub-title>{{item.description}}</v-list-tile-sub-title>

                                        </v-list-tile-content>
                                        <v-list-tile-action v-if="!quickReser">
                                            
                                            <v-checkbox @change="itemHandler(item)"></v-checkbox>
                                            
                                        </v-list-tile-action>
                                        
                                    
                                </v-list-tile>
                            </template>
                        </v-list>
                        <!--UBACIO -->
                        <v-layout row v-if="quickV">
                                <v-menu
                                v-model="fromDPMenu"
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
                                        v-model="fromDPDate"
                                        append-icon="event"
                                        label="From*"
                                        class="mx-3"
                                        max-width="30px"
                                        v-on="on">
                                    </v-text-field>
                                </v-flex>
                                </template>
                                <v-date-picker v-model="fromDPDate" @input="fromDPMenu=false" :max="maximalFromDate" :min="minimalFromDate"></v-date-picker>
                                </v-menu>
                                
                                <!-- datum do -->
                                <v-menu
                                v-model="toDPMenu"
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
                                        v-model="toDPDate"
                                        append-icon="event"
                                        label="To*"
                                        class="mx-3"
                                        v-on="on">
                                    </v-text-field>
                                </v-flex>
                                </template>
                                <v-date-picker v-model="toDPDate" @input="toDPMenu=false" :min="minimalToDate"></v-date-picker>
                                </v-menu>
                                </v-layout>
                            <!--UBACIO -->
                    </v-flex>
                </v-layout>

                
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-divider/>
                </v-card-actions>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <p  class="font-weight-light headline">Total price: <b>€{{overview.vehicle.totalPrice}}</b></p>
                </v-card-actions>
                
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn @click='confirmResrvation'>Accept</v-btn>
                    <v-btn @click='cancel'>Cancel</v-btn>
                    
                </v-card-actions>

            </v-layout>
        </v-container>
    </v-card>
</template>




<script>
export default {



    props: ['overview'],
 

    data:()=>({

        
        //<!--UBACIO -->
        dateDialog: false,
        fromDPMenu: false,
        fromDPMenuDialog: false,
        fromDPDate: new Date().toISOString().substr(0, 10),
        toDPMenu: false,
        toDPMenuDialog: false,
        toDPDate: '',
        minDate: new Date().toISOString().substr(0, 10),



        //<!--UBACIO -->

        selected : [],
        date: '2018-03-02'

    }),
    methods:{
        itemHandler:function(item){

            for(var i = 0; i<this.selected.length; i++){
                if(this.selected[i].id === item.id){
                    this.selected.splice(i,1);    
                    this.overview.vehicle.totalPrice = (this.overview.vehicle.totalPrice - item.price) * (1/(1-item.discount/100))
                    return;
                }
            }
            this.overview.vehicle.totalPrice = this.overview.vehicle.totalPrice * (1-item.discount/100) + item.price; 
            this.selected.push(item);
        },

        confirmResrvation:function(){

            if(this.quickV){
                var vehicleReservationDTO = {
                    dateFrom : this.fromDPDate,
                    dateTo : this.toDPDate,
                    additionalServices : this.selected,
                    vehicleId : this.overview.vehicleId,
                    rentACarId : this.rentACarId
                };
                if(this.overview.edit){
                    this.$axios //mora drugi rest da se gadja 
                    .post('http://localhost:8080/api/vehicleReservation/', vehicleReservationDTO)
                    .then(response =>{
                        alert(response.data);
                        this.$router.go(0);
                    })
                    .catch(error => {
                        alert(error.resposne)
                    });
                }else{
                    //imas atribut toatalPrice da stavis additionalDiscount
                    //i dodaj vraper oko boxova za datume i tu gurni neki textbox...
                    this.$axios //mora drugi rest da se gadja 
                    .post('http://localhost:8080/api/quickVehicleReservation/', vehicleReservationDTO) //create
                    .then(response =>{
                        alert(response.data);
                        this.$router.go(0);
                    })
                    .catch(error => {
                        alert(error.resposne.data)
                    });
                }
            }else{
                
                

                if(this.quickReser){
                    
                    this.$axios
                    .post('http://localhost:8080/api/confirmQuickVehicle/1/'+this.overview.reservationId)//1 je za main rezervaciju
                    .then(response =>{
                        this.$swal("Yoohoo!", response.data, 'success');
                        //this.$router.go(0);
                    })
                    .catch(error => {
                        this.$swal("Error", "Something went wrong.", 'error');
                    });

                }
                else{
                    
                    var vehicleReservationDTO1 = {
                        dateFrom : this.overview.from,
                        dateTo : this.overview.to,
                        additionalServices : this.selected,
                        vehicleId : this.overview.vehicleId
                    };
                    this.$axios
                    .post('http://localhost:8080/api/vehicleReservation/1', vehicleReservationDTO1)//1 je za main rezervaciju
                    .then(response =>{
                        this.$swal("Yoohoo!", response.data, 'success');
                        //this.$router.go(0);
                    })
                    .catch(error => {
                        this.$swal("Error", "You can't make reservation right now!", 'error');
                    });
                }
            }
           
                
        },
        cancel:function(){
         
            this.$router.go(0);
           
        },

        confirmAsync: async function (){
            
        }
        

    },
    computed:{
        minimalFromDate() {
            var today = new Date();
            var tomorrow = new Date();
            tomorrow.setDate(today.getDate() + 1);
            return tomorrow.toISOString().substr(0, 10);
        },

        minimalToDate() {
            return this.fromDPDate;
        },
        maximalFromDate(){
            return this.toDPDate
        },
        quickV(){
            var currentUrl = this.$route.path;
            return !currentUrl.includes("vehicleReservation");
        },
        rentACarId(){
            
            var currentUrl = this.$route.path;
            if(!currentUrl.includes("vehicleReservation")){
                var idLast = currentUrl.split('/');

                return idLast[idLast.length-1]
            }else{
                return null
            }
        },
        quickReser(){
            return this.overview.reservationId == -1 ? false : true;
        } 
    }

    
}
</script>


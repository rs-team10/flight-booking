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
                                <v-toolbar-title class="font-weight-light headline">From <b>{{dateFrom}}</b> to <b>{{dateTo}}</b></v-toolbar-title>
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
                                        <v-list-tile-action>
                                            
                                            <v-checkbox @change="itemHandler(item)"></v-checkbox>
                                            
                                        </v-list-tile-action>
                                        
                                    
                                </v-list-tile>
                            </template>
                        </v-list>
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
                </v-card-actions>

            </v-layout>
        </v-container>
    </v-card>
</template>




<script>
import VehiclePriceList from "@/components/rentACarComp/VehiclePriceList.vue"
export default {



    props: ['overview'],
 

    data:()=>({
        dateFrom : "2019-01-01",
        dateTo : "2019-01-11",
        selected : []

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
            var vehicleReservationDTO = {
                dateFrom : this.dateFrom,
                dateTo : this.dateTo,
                additionalServices : this.selected,
                vehicleId : this.overview.vehicleId
            };

            this.$axios
                .post('http://localhost:8080/api/vehicleReservation/1', vehicleReservationDTO)
                .then(response =>{
                    alert(response.data);
                    this.$router.go(0);
                })
                .catch(error => {
                    alert(error.resposne)
                });
            
                
        }
    }
    
}
</script>


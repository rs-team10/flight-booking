<template>
    <div>
        <v-snackbar
            v-model="empty"
            right
            top
            color="indigo">
            We're all out of airlines :(
            <v-btn
            color="white"
            flat
            @click="empty = false"
            >
            Close
            </v-btn>
        </v-snackbar>
        <v-item-group>
            <v-layout row>
            <v-flex xs4 sm4 md4>
                <v-layout column>
                <v-card flat color="grey lighten-5">
                    <v-card-text class="display-2 text-uppercase font-weight-light blue--text">
                        <v-icon color="blue" class="mb-2" size="40">flight</v-icon> airlines 
                    </v-card-text>
                </v-card>
                <v-card flat color="grey lighten-5" >
                <v-card-text class="title text-uppercase font-weight-light blue--text">
                    Looking for a specific flight?<br>Click <v-btn color="blue" outline class="mb-2" flat router to="/searchFlights"><u>here</u></v-btn> 
                </v-card-text>
                </v-card>
                </v-layout>
            </v-flex>
            <v-flex xs8 sm8 md8>
            <v-layout column>
                <v-list class="scroll-y pt-0" style="height: 830px; background-color: #FAFAFA;" >
                <v-flex
                    v-for="airline in this.airlines"
                    :key="airline.airlineProfileDTO.name"
                    class="d-inline align-center">
                <v-item width="100%">
                    <div>
                        <v-card flat color="grey lighten-5">
                        <v-container fluid class="pb-0 pt-2">
                            <v-layout row wrap>
                            <v-flex xs12 sm12 md12>
                                <v-card flat color="grey lighten-4">
                                <v-layout>
                                    <v-flex xs3 md3 sm3>
                                        <v-card-title primary-title>
                                            <div>
                                                <div class="display-1 font-weight-light pl-1">{{airline.airlineProfileDTO.name}}</div>
                                                <div class="pl-0">
                                                    <v-icon big class="mr-1">location_on</v-icon>
                                                    <u @click="viewAddress(airline.airlineProfileDTO.location)" class="blue--text text--darken-2 subheading">{{airline.airlineProfileDTO.location.formattedAddress}}</u>
                                                </div>
                                            
                                                <div class="d-flex">
                                                    <v-rating
                                                        :value="airline.airlineProfileDTO.averageFeedback"
                                                        color="amber"
                                                        background-color="grey"
                                                        dense
                                                        half-increments
                                                        readonly
                                                        size="28"
                                                    ></v-rating>
                                                </div>   
                                            </div>
                                        </v-card-title>
                                    </v-flex>
                                    <v-flex xs8 sm8 md8>
                                        <v-card-text class="grey--text text-sm-left font-weight-light title">
                                        {{ airline.airlineProfileDTO.description }}
                                        </v-card-text>
                                    </v-flex>

                                    <v-flex xs1 sm1 md1>
                                        <v-tooltip bottom>
                                            <template v-slot:activator="{ on }">
                                            <v-btn icon class="mt-5">
                                                <v-icon @click="showAirlineProfile(airline)" medium color="blue" dark v-on="on">arrow_forward_ios</v-icon>
                                            </v-btn>
                                            </template>
                                            <span>Show profile</span>
                                        </v-tooltip>
                                    </v-flex>
                                </v-layout>
                                </v-card>
                            </v-flex>
                            </v-layout>
                        </v-container>
                        </v-card>
                    </div>
                </v-item>
                </v-flex>   
                </v-list>
            </v-layout> 
            
            <v-layout justify-center>
            <v-btn block flat v-if="this.page > 0" @click="previousPage"><v-icon>arrow_backward</v-icon></v-btn>
            <v-btn block flat disabled v-else><v-icon>arrow_backward</v-icon></v-btn>

            <v-btn block flat @click="nextPage"><v-icon>arrow_forward</v-icon></v-btn>
            </v-layout>
            </v-flex> 

            </v-layout>
        </v-item-group>
        
        <v-dialog v-model="dialog" width="800px">
            <v-card>
            <location-map :selectedHotelLocation="selectedAirlineLocation"></location-map>
            </v-card>
        </v-dialog>

        <v-dialog v-if="profileDialog" v-model="profileDialog">
            <v-card>
            <airline-profile :selectedAirline="selectedAirline" @closeProfileDialog="profileDialog = false"></airline-profile>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import LocationMap from "@/components/LocationMap.vue"
import AirlineProfile from "@/components/UnregisteredUser/AirlineProfile.vue"

export default {
    components: {
        'location-map' : LocationMap,
        'airline-profile' : AirlineProfile
    },

    data(){
        return{
            page: 0,
            size: 10,
            airlines: [],
            empty: false,
            dialog: false,
            selectedAirlineLocation: {},
            selectedAirline: {},
            profileDialog: false
        }
    },
    methods: {
        fetchAirlines(){
            var yourConfig = {
                headers: { Authorization: "Bearer " + localStorage.getItem("token") }
            }
            this.$axios
                .get('http://localhost:8080/api/airlines/airlinePage', {
                    params: {
                        page: this.page,
                        size: this.size
                    }, yourConfig
                }).then(response => {
                    this.airlines = response.data
                })
        },
        nextPage(){
        this.page += 1;
        this.$axios
        .get('http://localhost:8080/api/airlines/airlinePage', {
            params: {
                page: this.page,
                size: this.size
        }})
        .then(response => {
            if(response.data.length > 0){
                this.airlines = response.data;  
            }else{
                this.page -= 1;
                this.empty = true; 
                setTimeout(() => {
                    this.empty = false;
                }, 3000)      
            }
        })
      },
      previousPage(){
            this.page -= 1;
            this.fetchAirlines();
      },
      viewAddress(location){
        this.selectedAirlineLocation = {
          lat: location.latitude,
          lng: location.longitude
        }
        this.dialog = true
      },
      showAirlineProfile(airline){
        this.selectedAirline = airline;
        this.profileDialog = true;
        
      },
    },
    mounted(){
        this.fetchAirlines()
    }
}
</script>

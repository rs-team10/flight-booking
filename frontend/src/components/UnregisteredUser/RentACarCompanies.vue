<template>
    <div>
        <!-- <v-snackbar
            v-model="empty"
            right
            top
            color="indigo">
            We're all out of rent-a-cars :(
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
                    <v-card-text class="display-2 text-uppercase font-weight-light red--text">
                        <v-icon color="red darken-4" class="mb-2" size="40">directions_car</v-icon> rent-a-cars 
                    </v-card-text>
                </v-card>
                <v-card flat color="grey lighten-5" >
                <v-card-text class="title text-uppercase font-weight-light red--text">
                    Looking for a specific vehicle? Click <u>here</u> 
                </v-card-text>
                </v-card>
                </v-layout>
            </v-flex>
            <v-flex xs8 sm8 md8>
            <v-layout column>
                <v-list class="scroll-y pt-0" style="height: 830px; background-color: #FAFAFA;" >
                <v-flex
                    v-for="rcs in this.rentacars"
                    :key="rcs.name"
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
                                                <div class="display-1 font-weight-light pl-1">{{rcs.name}}</div>
                                                <div class="pl-0">
                                                    <v-icon big class="mr-1">location_on</v-icon>
                                                    <u @click="viewAddress(rcs.location)" class="blue--text text--darken-2 subheading">{{rcs.location.formattedAddress}}</u>
                                                </div>
                                            
                                                <div class="d-flex">
                                                    <v-rating
                                                        :value="rcs.averageFeedback"
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
                                        {{ rcs.description }}
                                        </v-card-text>
                                    </v-flex>

                                    <v-flex xs1 sm1 md1>
                                        <v-tooltip bottom>
                                            <template v-slot:activator="{ on }">
                                            <v-btn icon class="mt-5">
                                                <v-icon @click="showRentacarProfile(rentacar)" medium color="red" dark v-on="on">arrow_forward_ios</v-icon>
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
            <location-map :selectedHotelLocation="selectedRentacarLocation"></location-map>
            </v-card>
        </v-dialog>

        <v-dialog v-if="profileDialog" v-model="profileDialog">
            <v-card>
            <rent-a-car-profile :selectedRentACar="selectedRentacar" @closeProfileDialog="profileDialog = false"></rent-a-car-profile>
            </v-card>
        </v-dialog> -->
    </div>
</template>

<script>
// import LocationMap from "@/components/LocationMap.vue"
// import RentACarProfile from "@/components/UnregisteredUser/RentACarProfile.vue"

// export default {
//     components: {
//         'location-map' : LocationMap,
//         'rent-a-car-profile' : RentACarProfile
//     },

//     data(){
//         return{
//             page: 0,
//             size: 10,
//             rentacars: [],
//             empty: false,
//             dialog: false,
//             selectedRentacarLocation: {},
//             selectedRentacar: {},
//             profileDialog: false,
//         }
//     },
//     methods: {
//         fetchRentACars(){
//             var yourConfig = {
//                 headers: { Authorization: "Bearer " + localStorage.getItem("token") }
//             }
//             this.$axios
//                 .get('http://localhost:8080/api/rentACars/rentACarPage', {
//                     params: {
//                         page: this.page,
//                         size: this.size
//                     }, yourConfig
//                 }).then(response => {
//                     this.rentacars = response.data
//                     console.log(this.rentacars)
//                 })
//         },
//         nextPage(){
//         this.page += 1;
//         this.$axios
//         .get('http://localhost:8080/api/rentACars/rentACarPage', {
//             params: {
//                 page: this.page,
//                 size: this.size
//         }})
//         .then(response => {
//             if(response.data.length > 0){
//                 this.rentacars = response.data;  
//             }else{
//                 this.page -= 1;
//                 this.empty = true; 
//                 setTimeout(() => {
//                     this.empty = false;
//                 }, 3000)      
//             }
//         })
//       },
//       previousPage(){
//             this.page -= 1;
//             this.fetchRentACars();
//       },
//       viewAddress(location){
//         this.selectedRentacarLocation = {
//           lat: location.latitude,
//           lng: location.longitude
//         }
//         this.dialog = true
//       },
//       showRentacarProfile(rcs){
//         this.selectedRentacars = rcs;
//         this.profileDialog = true;
        
//       },
//     },
//     mounted(){
//         this.fetchRentACars()
//     }
// }
</script>

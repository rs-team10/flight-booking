<template>
  <div class="hotelReservation">
    
    <v-snackbar
      v-model="empty"
      right
      top
      color="indigo"
    >
    We're all out of hotels :(
        <v-btn
        color="white"
        flat
        @click="empty = false"
        >
        Close
        </v-btn>
    </v-snackbar>
    <v-stepper v-model="e6" vertical>
        <v-stepper-step :complete="e6 > 1" step="1">Pick a hotel</v-stepper-step>

        <v-stepper-content step="1">
            <v-flex class="d-flex">
                <v-flex xs4 md4>
                    <div id="form">
                        <form>
                            <v-flex>
                            <v-layout column >
                                <div class="ml-3 title font-weight-light indigo--text">Please select your check-in and check-out date</div>
                                <br>
                                <!-- datum od -->
                                <v-layout row>
                                <v-menu
                                v-model="checkInMenu"
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
                                        v-model="checkInDate"
                                        append-icon="event"
                                        label="Check-in*"
                                        class="mx-3"
                                        max-width="30px"
                                        v-on="on">
                                    </v-text-field>
                                </v-flex>
                                </template>
                                <v-date-picker v-model="checkInDate" @input="checkInMenu=false" color="indigo lighten-1"></v-date-picker>
                                </v-menu>
                                
                                <!-- datum do -->
                                <v-menu
                                v-model="checkOutMenu"
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
                                        v-model="checkOutDate"
                                        append-icon="event"
                                        label="Check-out*"
                                        class="mx-3"
                                        v-on="on">
                                    </v-text-field>
                                </v-flex>
                                </template>
                                <v-date-picker v-model="checkOutDate" @input="checkOutMenu=false" color="indigo lighten-1"></v-date-picker>
                                </v-menu>
                                </v-layout>
                                <v-divider></v-divider><br>
                                <v-layout row>
                                    <v-flex xs6 md6>
                                    <v-text-field
                                    append-icon="search"
                                    label="Hotel name or destination"
                                    class="mx-3">
                                    </v-text-field>
                                    </v-flex>
                                    <v-flex xs6 md6>
                                    <v-text-field
                                        append-icon="person"
                                        label="Guests"
                                        class="mx-3"
                                        type="number">
                                    </v-text-field>
                                    </v-flex>
                                </v-layout>
                                

                                <v-flex>
                                <v-card-text>
                                    <v-range-slider
                                        label="Price range"
                                    ></v-range-slider>
                                </v-card-text>
                                </v-flex>

                                <v-btn outline color="indigo">
                                    <span>Search hotels</span>
                                </v-btn>

                                <small>* indicates a required field</small>
                            </v-layout>
                            </v-flex>
                        </form>
                    </div>
                </v-flex>

                <div id="hotelList">
                    <v-item-group>
                        <v-layout column>
                            <v-list class="scroll-y" style="height: 700px">
                            <v-flex
                                v-for="hotel in this.hotels"
                                :key="hotel.name"
                                class="d-inline align-center">
                            <v-item width="100%">
                                <div style="margin: auto;">
                                    <v-card flat>
                                    <v-container fluid>
                                        <v-layout row wrap>
                                        <v-flex xs12>
                                            <v-card>
                                            <v-layout>
                                                <v-flex xs4>
                                                <v-img
                                                    :src="image"
                                                    height="100%"
                                                    max-height="216px"
                                                    max-width="372px"
                                                ></v-img>
                                                </v-flex>
                                                <v-flex xs5>
                                                    <v-card-title primary-title>
                                                        <div>
                                                            <div class="headline">{{hotel.name}}</div>
                                                            <div>
                                                                <v-icon small class="mr-1">location_on</v-icon>
                                                                <span class="grey--text text--darken-2 ">{{hotel.location.street}}</span>
                                                            </div>
                                                        
                                                            <div class="d-flex">
                                                                <v-rating
                                                                    :value="rating"
                                                                    color="amber"
                                                                    dense
                                                                    half-increments
                                                                    readonly
                                                                ></v-rating>
                                                            </div>   
                                                        </div>
                                                    </v-card-title>
                                                </v-flex>

                                                <v-flex xs3>
                                                    <v-card-actions>
                                                        <v-spacer></v-spacer>
                                                        <v-btn flat @click="hotelSelected(hotel)">
                                                            Reserve
                                                            <v-icon right>input</v-icon>
                                                        </v-btn>
                                                    </v-card-actions>
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
                            
                            <v-layout justify-center>
                                <v-btn block flat v-if="this.page > 0" @click="previousPage"><v-icon>arrow_backward</v-icon></v-btn>
                                <v-btn block flat disabled v-else><v-icon>arrow_backward</v-icon></v-btn>

                                <v-btn block flat @click="nextPage"><v-icon>arrow_forward</v-icon></v-btn>
                            </v-layout>
                            </v-list>
                        </v-layout>  
 
                    </v-item-group>
                </div>
            </v-flex>
            
            <v-btn flat>Cancel</v-btn>
        </v-stepper-content>


        <v-stepper-step :complete="e6 > 2" step="2">Pick a room</v-stepper-step>

            <v-stepper-content step="2">
                <component 
                    :is="component2"
                    :selectedHotel="selectedHotel"
                    :days="days"
                    @continueReservation="finishReservation($event)"
                ></component>

                <v-btn flat @click="e6 = 3">Next</v-btn>
                <v-btn flat @click="e6 -= 1">Back</v-btn>
            </v-stepper-content>

        <v-stepper-step :complete="e6 > 3" step="3">Additional services</v-stepper-step>

        <v-stepper-content step="3">
            <component 
                    :is="component3"
                    :days="days"
                    :reservation="reservation"
            ></component>

            <v-btn flat>Cancel</v-btn>
        </v-stepper-content>

        
    </v-stepper>

  </div>
</template>

<script>
import Rooms from "./Rooms.vue"
import AdditionalServices from "./AdditionalServices.vue"

export default {
    components: {
       'rooms' : Rooms,
       'additionalServices' : AdditionalServices
    },
    data(){
        return{
            component2 : 'rooms',
            component3: 'additionalServices',
            selectedHotel: {},
            reservation: {},
            listOfRooms: [],
            e6: 1,
            
            page: 0,
            size: 5,
            hotels: [],
            //Snackbar------------------------
            empty: false,
            //Temporary-----------------------
            rating: 3.46,
            image: "https://media.cntraveler.com/photos/53da828b6dec627b149eeee6/4:3/w_420,c_limit/fairmont-kea-lani-hawaii-maui.jpg",
            //--------------------------------
            
            //Search params-------------------
            hotelNameDest: '',
            guests: '',

            checkInMenu: false,
            checkInDate: new Date().toISOString().substr(0, 10),
            checkOutMenu: false,
            checkOutDate: new Date().toISOString().substr(0, 10),

            days : '',
            totalRoomPrice: ''


            //--------------------------------
        }
    },
    methods:{
        fetchHotels(){
            this.$axios
            .get('http://localhost:8080/api/hotels/resHotels?page='+this.page+'&size='+this.size)
            .then(response => {
                this.hotels = response.data;  
            })
        },
        nextPage(){
            this.page += 1;
            this.$axios
            .get('http://localhost:8080/api/hotels/resHotels?page='+this.page+'&size='+this.size)
            .then(response => {
                if(response.data.length > 0){
                    this.hotels = response.data;  
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
            this.fetchHotels();
        },
        hotelSelected(hotel){
            this.selectedHotel = hotel;
            //Object.assign(this.selectedHotel, hotel)
            if(this.validateDates())
                this.e6 = 2;
            
        },
        validateDates(){
            if((this.checkOutDate == this.checkInDate) || (this.checkOutDate < this.checkInDate)){
                this.$swal("Invalid check-in/check-out dates", "", "error");
                return false;
            }
            var days = (new Date(this.checkOutDate) - new Date(this.checkInDate)) / (1000*60*60*24);
            this.days = days;
            return true;

        },
        finishReservation(reservation){
            this.reservation = reservation;
            console.log(this.reservation);

            this.e6 = 3;
        }
    },
    mounted(){
        this.fetchHotels();
    }
}
</script>

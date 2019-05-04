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
                            <v-layout column>
                                <v-text-field
                                append-icon="search"
                                label="Hotel name or destination"
                                single-line
                                class="mx-3">
                                </v-text-field>

                                <!-- datum od -->
                                <v-layout row>
                                <v-menu
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
                                        append-icon="event"
                                        label="Check-in"
                                        single-line
                                        class="mx-3"
                                        max-width="30px"
                                        v-on="on">
                                    </v-text-field>
                                </v-flex>
                                </template>
                                <v-date-picker></v-date-picker>
                                </v-menu>
                                
                                <!-- datum do -->
                                <v-menu
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
                                        append-icon="event"
                                        label="Check-out"
                                        single-line
                                        class="mx-3"
                                        v-on="on">
                                    </v-text-field>
                                </v-flex>
                                </template>
                                <v-date-picker></v-date-picker>
                                </v-menu>
                                </v-layout>

                                <v-layout row>
                                <v-flex xs6 md6>
                                <v-text-field
                                    append-icon="hotel"
                                    label="Rooms"
                                    single-line
                                    class="mx-3">
                                </v-text-field>
                                </v-flex>

                                <v-flex xs6 md6>
                                <v-text-field
                                    append-icon="person"
                                    label="Guests"
                                    single-line
                                    class="mx-3">
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
                            </v-layout>
                            </v-flex>

                        </form>
                    </div>
                </v-flex>

                <div id="hotelList">
                    <v-item-group>
                        <v-layout column >
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
                                                        <v-btn flat @click="e6 = 2">
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
                <component :is="component2  "></component>

                <v-btn color="primary" @click="e6 = 3">Continue</v-btn>
                <v-btn flat @click="e6 -= 1">Back</v-btn>
            </v-stepper-content>

        <v-stepper-step :complete="e6 > 3" step="3">Additional services</v-stepper-step>

        <v-stepper-content step="3">
            <v-btn color="primary" @click="e6 = 1">Continue</v-btn>
            <v-btn flat>Cancel</v-btn>
        </v-stepper-content>

        
    </v-stepper>


  </div>
</template>

<script>
import Rooms from "./Rooms.vue"

export default {
    components: {
       'rooms' : Rooms
    },
    data(){
        return{
            component2 : 'rooms',
            e6: 1,
            temp: [
                { name: "Hotel1", rating: 3.46, image: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_380,c_fill,g_auto,h_214,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F160726150143-us-beautiful-hotels-17-bellagio-2.jpg"},
                { name: "Hotel2", rating: 3.46, image: "https://d27k8xmh3cuzik.cloudfront.net/wp-content/uploads/2018/04/FotoJetkb6592covernuwara.jpg"},
                { name: "Hotel3", rating: 3.46, image: "https://i.dailymail.co.uk/i/pix/2014/11/14/1415989082721_wps_4_The_Jade_Hotel_exterior_j.jpg"},
                { name: "Hotel4", rating: 3.46, image: "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fcdn-image.travelandleisure.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1600x1000%2Fpublic%2F1546553575%2Fritz-carlton-bal-harbour-miami-florida-HOTELBATHS0119.jpg%3Fitok%3DBoGkpR6S&q=85"},
                { name: "Hotel5", rating: 3.46, image: "https://3.bp.blogspot.com/--2G5-blY_NE/U8LBLJLDwDI/AAAAAAAAIrQ/wQtHbEhbzNI/s1600/luxury-hotel-HD-Wallpapers.jpg"},
                { name: "Hotel6", rating: 3.46, image: "https://media.cntraveler.com/photos/53da828b6dec627b149eeee6/4:3/w_420,c_limit/fairmont-kea-lani-hawaii-maui.jpg"}
            ],
            page: 0,
            size: 5,
            hotels: [],
            //Snackbar------------------------
            empty: false,
            timeout: 3000,
            //Temporary-----------------------
            rating: 3.46,
            image: "https://media.cntraveler.com/photos/53da828b6dec627b149eeee6/4:3/w_420,c_limit/fairmont-kea-lani-hawaii-maui.jpg"
            //--------------------------------
        }
    },
    methods:{
        fetchHotels(){
            this.$axios
            .get('http://localhost:8080/api/hotels/pageHotels?page='+this.page+'&size='+this.size)
            .then(response => {
                console.log(response.data)
                this.hotels = response.data;  
            })
        },
        nextPage(){
            this.page += 1;
            this.$axios
            .get('http://localhost:8080/api/hotels/pageHotels?page='+this.page+'&size='+this.size)
            .then(response => {
                console.log(response.data)
                if(response.data.length > 0){
                    this.hotels = response.data;  
                }else{
                    this.page -= 1;
                    this.empty = true; 
                    setTimeout(() => {
                        this.empty = false;
                    }, this.timeout)      
                }
            })
        },
        previousPage(){
            this.page -= 1;
            this.fetchHotels();
        },
        isHotelsEmpty(){

        }
    },
    mounted(){
        this.fetchHotels();
    }
}
</script>

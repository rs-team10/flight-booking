<template>
  <div class="hotels">
  <v-snackbar
      v-model="empty"
      right
      top
      color="indigo">
      We're all out of hotels :(
      <v-btn
      color="white"
      flat
      @click="empty = false"
      >
      Close
      </v-btn>
  </v-snackbar>
  <v-flex xs12 sm6 offset-sm3 id="search">
  <v-layout row>

    <v-text-field
      v-model="hotelName"
      append-icon="search"
      label="Hotel name"
      single-line
      class="mx-3">
    </v-text-field>

    <v-text-field
      v-model="hotelLocation"
      append-icon="location_on"
      label="Hotel location"
      single-line
      class="mx-3">
    </v-text-field>

    <!-- datum od -->
    <!-- <v-menu
      v-model="startDateMenu"
      :close-on-content-click="true"
      :nudge-right="40"
      lazy
      transition="scale-transition"
      offset-y
      full-width
      min-width="290px"
    >
    <template v-slot:activator="{ on }">
      <v-text-field
        append-icon="event"
        label="From"
        single-line
        class="mx-3"
        v-on="on">
      </v-text-field>
    </template>
    <v-date-picker v-model="startDate" @input="startDateMenu = false"></v-date-picker>
    </v-menu> -->
    
    <!-- datum do -->
    <!-- <v-menu
      v-model="endDateMenu"
      :close-on-content-click="true"
      :nudge-right="40"
      lazy
      transition="scale-transition"
      offset-y
      full-width
      min-width="290px"
    >
    <template v-slot:activator="{ on }">
      <v-text-field
        append-icon="event"
        label="To"
        single-line
        class="mx-3"
        v-on="on">
      </v-text-field>
    </template>
    <v-date-picker v-model="endDate" @input="endDateMenu = false"></v-date-picker>
    </v-menu> -->

    <v-btn flat outline color="indigo" class="mt-2" @click="fetchHotels()">
      <span>Search</span>
    </v-btn>

  </v-layout>
  </v-flex>

  <v-item-group>
      <v-flex xs10 offset-sm1>
      <v-layout row wrap>
        <v-flex xs4
          v-for="hotel in hotels"
          :key="hotel.name"
          class="d-inline-flex"
          >
          <v-item>
            <v-card
              class="mt-2 mx-1"
              width="344"
            >
            <v-img v-once
              :aspect-ratio="16/9"
              :src="mockPics[Math.floor(Math.random()*mockPics.length)]">
            </v-img>

            <v-card-title>
              <div>
                <span class="headline">{{ hotel.name }}</span><br>
                <v-icon small class="mr-1">location_on</v-icon>
                <u @click="viewAddress(hotel.location)" class="blue--text text--darken-2 body-1">{{ hotel.location.formattedAddress }}</u>
                <v-layout row >
                  <v-rating
                    :value="hotel.averageFeedback"
                    color="amber"
                    dense
                    half-increments
                    readonly
                    size="14"
                  ></v-rating>
                  <div class="ml-2 grey--text text--darken-2">
                    <span>{{ hotel.averageFeedback }}</span>
                    <span>({{ hotel.numberOfFeedbacks }})</span>
                  </div>
                </v-layout>
              </div>
              <v-spacer></v-spacer>

              <!-- TODO: Treba izbaciti-->
              <!-- <v-btn icon @click="hotelSelected(hotel)" v-if="checkAdmin()">
                <v-icon medium color="indigo">edit</v-icon>
              </v-btn> -->
              <!-- <v-btn icon @click="generateReports(hotel)" v-if="checkAdmin()">
                <v-icon medium color="indigo">assessment</v-icon>
              </v-btn> -->
              <!-- -->

              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <v-btn icon>
                    <v-icon @click="showHotelProfile(hotel)" medium color="indigo" dark v-on="on">arrow_forward_ios</v-icon>
                  </v-btn>
                </template>
                <span>Show profile</span>
              </v-tooltip>

            </v-card-title>

            </v-card>
          </v-item>
          </v-flex>
      </v-layout>
      <v-layout justify-center v-if="hotels.length != 0">
        <v-btn block flat v-if="this.page > 0" @click="previousPage"><v-icon>arrow_backward</v-icon></v-btn>
        <v-btn block flat disabled v-else><v-icon>arrow_backward</v-icon></v-btn>

        <v-btn block flat @click="nextPage"><v-icon>arrow_forward</v-icon></v-btn>
      </v-layout>

      </v-flex>

  </v-item-group>

  <v-dialog v-model="dialog" width="800px">
    <v-card>
      <location-map :selectedHotelLocation="selectedHotelLocation"></location-map>
    </v-card>
  </v-dialog>

  <v-dialog v-if="profileDialog" v-model="profileDialog">
    <v-card>
      <hotel-profile :selectedHotel="selectedHotel" @closeProfileDialog="profileDialog = false"></hotel-profile>
    </v-card>
  </v-dialog>

  </div>
</template>

<script>
import LocationMap from "@/components/LocationMap.vue"
import HotelProfile from "@/components/UnregisteredUser/HotelProfile.vue"
import { setTimeout } from 'timers';
var yourConfig = {
    headers: { Authorization: "Bearer " + localStorage.getItem("token") }
}
export default {
  components: {
    'location-map' : LocationMap,
    'hotel-profile' : HotelProfile
  },
  name: 'hotels',
  data(){
      return{
          hotels: [],
          selectedHotel: {},
          page: 0,
          size: 6,
          //Snackbar----------
          empty: false,
          //Temporary---------
          value: 3.8,
          reviews: 356,
          //------------------
          hotelName: '',
          hotelLocation: '',

          startDate: new Date().toISOString().substr(0, 10),
          startDateMenu: false,
          endDate: new Date().toISOString().substr(0, 10),
          endDateMenu: false,
          
          selectedHotelLocation: {},
          dialog: false,
          profileDialog: false,
          //-----------------------------------------------
          mockPics: [
            'https://www.realestate.com.au/blog/images/2000x1500-fit,progressive/2018/01/08153753/Pic-1.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/01/MIRACLE-GENERAL-15_preview-1.jpeg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/02/96725900-1.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/02/47165351-1.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/02/RAAS-Devigarh-all-suites-resort-Udaipur-India-1.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/01/54365905.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/02/Low_VSEP_59917408_Sunset.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/01/Low_AKIH_61614149_Front_pool_view.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/02/Gili-Lankanfushi-Resort-Maldives-2.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/02/1-Baros-Resort-Maldives-5-Star-Luxury-Resort.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/01/52508017.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/02/6-Soneva-Jani-Resort-Maldives.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/01/68482593-1.jpg',
            'https://beautiful-hotels.com/wp-content/uploads/2018/02/hpc_1141425020-1.jpg'
          ]
      }
  },
  methods:{
      fetchHotels(){
          this.$axios
          .get('http://localhost:8080/api/hotels/searchHotels', {
            params: {
              page: this.page,
              size: this.size,
              hotelName: this.hotelName,
              hotelLocation: this.hotelLocation
            }
          }, yourConfig)
          .then(response => {
            this.hotels = response.data;
          })
      },
      nextPage(){
        this.page += 1;
        this.$axios
        .get('http://localhost:8080/api/hotels/searchHotels', {
            params: {
                page: this.page,
                size: this.size,
                hotelName: this.hotelName, 
                hotelLocation: this.hotelLocation
        }})
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
      hotelSelected: function(hotel){
        this.$axios
        .get('http://localhost:8080/api/hotels/getHotelRooms/' + hotel.id, yourConfig)
        .then(response => {
          //hotel.rooms = response.data
          hotel = response.data
          this.$emit('hotelSelected', hotel);
        })
      },
      generateReports(hotel){
        this.$emit('generateReports', hotel);
      },
      checkAdmin(){
        if(localStorage.getItem("role") == "ROLE_HOTEL_ADMIN")
          return true;     
        return false;
      },
      showHotelProfile(hotel){
        this.profileDialog = true;
        this.selectedHotel = hotel;
      },
      viewAddress(location){
        this.selectedHotelLocation = {
          lat: location.latitude,
          lng: location.longitude
        }
        this.dialog = true
      },
      getRandomInt(max){
        return Math.floor(Math.random() * Math.floor(max));
      }
  },
  mounted(){
      this.fetchHotels();
  }
}
</script>

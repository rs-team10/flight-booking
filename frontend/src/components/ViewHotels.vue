<template>
  <div class="hotels">
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
    <v-menu
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
    </v-menu>
    
    <!-- datum do -->
    <v-menu
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
    </v-menu>

    <v-btn @click="searchHotels">
      <span>Search</span>
    </v-btn>

  </v-layout>
  </v-flex>

  
  <v-item-group>
      <v-flex xs10 offset-sm1 >
      <v-layout wrap>
        <v-flex
          v-for="hotel in hotels"
          :key="hotel.name"
          class="d-inline-flex"
          >
          <v-item>
            <v-card
              class="mt-2 mx-1"
              width="344"
            >
            <v-img
              :aspect-ratio="16/9"
              src="https://3.bp.blogspot.com/--2G5-blY_NE/U8LBLJLDwDI/AAAAAAAAIrQ/wQtHbEhbzNI/s1600/luxury-hotel-HD-Wallpapers.jpg">
            </v-img>

            <v-card-title>
              <div>
                <span class="headline">{{ hotel.name }}</span><br>
                <v-icon small class="mr-1">location_on</v-icon>
                <span class="grey--text text--darken-2 body-1">{{ hotel.location.street }}</span>
                <div class="d-flex">
                  <v-rating
                    :value="value"
                    color="amber"
                    dense
                    half-increments
                    readonly
                    size="14"
                  ></v-rating>
                  <div class="ml-2 grey--text text--darken-2">
                    <span>{{ value }}</span>
                    <span>({{ reviews }})</span>
                  </div>
                </div>
              </div>
              <v-spacer></v-spacer>
              <v-btn icon @click="hotelSelected(hotel)">
                <v-icon medium color="indigo">edit</v-icon>
              </v-btn>
              <v-btn icon @click="generateReports(hotel)">
                <v-icon medium color="indigo">assessment</v-icon>
              </v-btn>
            </v-card-title>

            </v-card>
          </v-item>
          </v-flex>
      </v-layout>
      </v-flex>

  </v-item-group>


  </div>
</template>

<script>
var yourConfig = {
    headers: { Authorization: "Bearer " + localStorage.getItem("token") }
}
export default {
  name: 'hotels',
  data(){
      return{
          hotels: [],
          selectedHotel: {},
          page: 0,
          size: 5,
          //Temporary---------
          value: 3.8,
          reviews: 356,
          //------------------
          hotelName: '',
          hotelLocation: '',

          startDate: new Date().toISOString().substr(0, 10),
          startDateMenu: false,
          endDate: new Date().toISOString().substr(0, 10),
          endDateMenu: false
      }
  },
  methods:{
      fetchHotels: function(){
          this.$axios
          .get('http://localhost:8080/api/hotels', yourConfig)
          .then(response => {
            this.hotels = response.data
          })
            
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
      searchHotels(){
        this.$axios
        .get("http://localhost:8080/api/hotels/searchHotels", {
          params: {
            page: this.page,
            size: this.size,
            hotelName: this.hotelName, 
            hotelLocation: this.hotelLocation
          }
        })
        .then(response => {
          console.log(response);
          this.hotels = response.data
        }).catch(error => {
          console.log(error.response.data);
        })
      }
  },
  mounted(){
      this.fetchHotels();
  }
}
</script>

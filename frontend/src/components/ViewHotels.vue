<template>
  <div class="hotels">
  <h1>Available hotels</h1> 
  <!-- <v-layout row>
    <v-flex xs12 sm6 md6 offset-sm3>
      <v-card>
        <v-list two-line>
          <template v-for="hotel in hotels">
              
            <v-list-tile :key="hotel.name">

                <v-list-tile-content v-on:click="hotelSelected(hotel)">
                <v-list-tile-title>{{ hotel.name }}</v-list-tile-title>
                <v-list-tile-sub-title class="text--primary">{{ hotel.location.street }}</v-list-tile-sub-title>
                <v-list-tile-sub-title>{{ hotel.description }}</v-list-tile-sub-title>
                </v-list-tile-content>

            </v-list-tile>
            
          </template>
        </v-list>
      </v-card>
    </v-flex>
  </v-layout> -->
  <v-flex xs12 sm6 offset-sm3>
  <v-layout row>
    <v-text-field
      v-model="searchParam"
      append-icon="search"
      label="Search hotels"
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
    <v-container grid-list-md>
      <v-layout row wrap>
        <v-flex
          v-for="hotel in hotels"
          :key="hotel.name"
          class="d-flex align-center"
          >
          <v-item>
            <v-card
              class="mx-1"
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
              <v-btn icon v-on:click="hotelSelected(hotel)">
                <v-icon medium color="purple">edit</v-icon>
              </v-btn>
            </v-card-title>

            </v-card>
          </v-item>
          </v-flex>
      </v-layout>
    </v-container>
  </v-item-group>


  </div>
</template>

<script>
export default {
  name: 'hotels',
  data(){
      return{
          hotels: [],
          selectedHotel: '',
          //Temporary---------
          value: 3.8,
          reviews: 356,
          //------------------
          searchParam: '',
          startDate: new Date().toISOString().substr(0, 10),
          startDateMenu: false,
          endDate: new Date().toISOString().substr(0, 10),
          endDateMenu: false
      }
  },
  methods:{
      fetchHotels: function(){
          this.$axios
          .get('http://localhost:8080/api/hotels')
          .then(response => 
            this.hotels = response.data)
      },
      hotelSelected: function(hotel){
          this.$emit('hotelSelected', hotel);
      },
      searchHotels(){
        this.$axios
        .get("http://localhost:8081/api/hotels/" + this.searchParam)
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

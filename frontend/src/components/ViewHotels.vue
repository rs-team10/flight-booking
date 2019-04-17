<template>
  <div>
  <h1>Available hotels</h1> 
  <v-layout row>
    <v-flex xs12 sm6 offset-sm3>
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
  </v-layout>
  </div>
</template>


<script>


export default {
  name: 'hotels',
  data(){
      return{
          hotels: [],
          selectedHotel: ''
      }
  },
  methods:{
      fetchHotels: function(){
          this.$axios
          .get('http://localhost:8081/api/hotels')
          .then(response => 
            this.hotels = response.data)
      },
      hotelSelected: function(hotel){
          this.$emit('hotelSelected', hotel);
      }
  },
  mounted(){
      this.fetchHotels();
  }
}
</script>

<template>
  <div>
  <h1>Available Rent-a-cars</h1> 
  <v-layout row>
    <v-flex xs12 sm6 offset-sm3>
      <v-card>
        <v-list two-line>
          <template v-for="rentACar in rentACars">
              
            <v-list-tile :key="rentACar.name">

                <v-list-tile-content v-on:click="rentACarSelected(rentACar)">
                <v-list-tile-title>{{ rentACar.name }}</v-list-tile-title>
            <!--<v-list-tile-sub-title class="text--primary">{{ rentACar.address }}</v-list-tile-sub-title> -->
                <v-list-tile-sub-title>{{ rentACar.description }}</v-list-tile-sub-title>
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
  name: 'rentACars',
  data(){
      return{
          rentACars: [],
          selectedRentACar: ''
      }
  },
  methods:{
      fetchRentACars: function(){
          this.$axios
          .get('http://localhost:8081/api/rentACars')
          .then(response => this.rentACars = response.data)
      },
      rentACarSelected: function(rentACar){
          alert("Selected" + rentACar.name);
          this.$emit('selectedRentACar', rentACar);
      }
  },
  mounted(){
      this.fetchRentACars();
  }
}
</script>

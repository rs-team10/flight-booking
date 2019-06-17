<template>
  <div class="rentACars">
  <v-flex xs12 sm6 offset-sm3 id="search">
  <v-layout row>
    <v-text-field
      v-model="searchParam"
      append-icon="search"
      label="Rent a car name"
      single-line
      class="mx-3">
    </v-text-field>


    <v-btn @click="searchRentACars">
      <span>Search</span>
    </v-btn>

  </v-layout>
  </v-flex>

  
  <v-item-group>
      <v-flex xs10 offset-sm1 >
      <v-layout wrap>
        <v-flex
          v-for="rentACar in rentACars"
          :key="rentACar.name"
          class="d-inline-flex"
          >
          <v-item>
            <v-card
              class="mt-2 mx-1"
              width="344"
            >
            <v-img
              :aspect-ratio="16/9"
              src="https://images6.alphacoders.com/383/383251.jpg"
              @click="rentACarSelected(rentACar)">
            </v-img>

            <v-card-title>
              <div>
                <span class="headline">{{ rentACar.name }}</span><br>
                <v-icon small class="mr-1">location_on</v-icon>
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
  data(){
      return{
          rentACars: [],
          tempHolder : [],
          page: 0,
          size: 5,
          //Temporary---------
          value: 3.8,
          reviews: 356,
          //------------------
          searchParam: '',


      }
  },
  methods:{
      fetchRentACars: function(){
          this.$axios
          .get('http://localhost:8080/api/rentACars', yourConfig)
          .then(response => {
            this.tempHolder = response.data
            this.rentACars = this.tempHolder
          })
            
      },
      rentACarSelected: function(rentACarSelected){
        var id = rentACarSelected.id
        this.$router.push('/rentACar/'+id)
      },
      searchRentACars(){
        this.rentACars = this.tempHolder;
        this.rentACars = this.rentACars.filter(r => r.name.includes(this.searchParam))
      }
  },
  mounted(){
      this.fetchRentACars();
  }
}
</script>

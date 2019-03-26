<template>
  <div class="hotels">
    <div id="hotel-buttons">
        <button v-on:click="component = 'hotels'">View hotels</button>
        <button v-on:click="component = 'add-hotel'">Add hotel</button>
    </div>
    <component v-bind:is="component" 
        v-on:hotelSelected="changeComp($event)" 
        v-bind:selectedHotel="selectedHotel"></component>

    
  </div>
</template>

<script>
// @ is an alias to /src
import ViewHotels from "@/components/ViewHotels.vue"
import AddHotel from "@/components/AddHotel.vue"
import EditHotel from "@/components/EditHotel.vue"

export default {
    components: {
        'hotels' : ViewHotels,
        'add-hotel' : AddHotel,
        'edit-hotel' : EditHotel
    },
    data(){
        return{
            component : 'hotels',
            selectedHotel : {}
        }
    },
    methods:{
        changeComp: function(hotel){
            this.selectedHotel = hotel;
            this.component = 'edit-hotel';
        },
        fetchHotels: function(){
            this.$axios
            .get('http://localhost:8081/api/hotels')
            .then(response => this.hotels = response.data)
        }
    }
}
</script>

<style scoped>

div#hotel-buttons button{
    display: inline-block;
    margin-right: 20px;
}
ul{
    list-style-type: none;
    padding: 0;
    border: 1px solid #ddd;
}
ul li {
  padding: 8px 16px;
  border-bottom: 1px solid #ddd;
}
</style>
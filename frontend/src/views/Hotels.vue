<template>
  <div class="hotels">
    <!--<a href="#" class="dodaj" role="button">Add hotel</a>-->
    <div id="hotel-buttons">
        <button v-on:click="component = 'hotels'">View hotels</button>
        <button v-on:click="component = 'add-hotel'">Add hotel</button>
        <button v-on:click="editHotel">Edit hotel</button>
    </div>
    <component v-bind:is="component"></component>

    
  </div>
</template>

<script>
// @ is an alias to /src
import ViewHotels from "@/components/ViewHotels.vue"
import AddHotel from "@/components/AddHotel.vue"

export default {
    components: {
        'hotels' : ViewHotels,
        'add-hotel' : AddHotel
    },
    data(){
        return{
            component : 'hotels'
        }
    },
    methods:{
        addHotel: function(){
            this.$axios
                .post('http://localhost:8081/api/hotels', {
                    name: 'HotelDummy' + Math.random()
                }).then(function(response){
                    console.log(response);
                }).catch(function(error) {
                    console.error ( error )
                });
            //this.fetchHotels();
        },
        editHotel: function(){
            alert("Hotel izmenjen!");
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
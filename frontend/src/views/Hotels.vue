<template>
  <div class="hotels">

    <v-tabs fixed-tabs color="#F5F4F9">
        <v-tab v-on:click="component = 'hotels'" outline color="indigo">View hotels</v-tab>
        <v-tab v-on:click="component = 'add-hotel'" outline color="indigo">Add hotel</v-tab>
    </v-tabs>

    <component v-bind:is="component" 
        v-on:hotelSelected="changeComp($event)" 
        v-on:goToQuickReservations="component = 'quick-room-reservations'"
        v-on:goBack="component = 'edit-hotel'"
        v-bind:selectedHotel="selectedHotel"></component>

  </div>
</template>

<script>
// @ is an alias to /src
import ViewHotels from "@/components/ViewHotels.vue"
import AddHotel from "@/components/SysAdmin/AddHotel.vue"
import EditHotel from "@/components/HotelAdmin/EditHotel.vue"
import QuickRoomReservations from "@/components/HotelAdmin/QuickRoomReservations.vue"

export default {
    components: {
        'hotels' : ViewHotels,
        'add-hotel' : AddHotel,
        'edit-hotel' : EditHotel,
        'quick-room-reservations' : QuickRoomReservations
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
            .get('http://localhost:8080/api/hotels')
            .then(response => this.hotels = response.data)
        }
    }
}
</script>

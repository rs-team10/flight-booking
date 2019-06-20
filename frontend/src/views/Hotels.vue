<template>
  <div class="hotels">

    <!-- <component v-bind:is="component" 
        @hotelSelected="changeComp($event)"
        @generateReports="generateReports($event)" 
        @goToQuickReservations="component = 'quick-room-reservations'"
        @goBack="component = 'edit-hotel'"
        :selectedHotel="selectedHotel"></component> -->

  </div>
</template>

<script>
// // @ is an alias to /src
// import ViewHotels from "@/components/ViewHotels.vue"
// import EditHotel from "@/components/HotelAdmin/EditHotel.vue"
// import QuickRoomReservations from "@/components/HotelAdmin/QuickRoomReservations.vue"
// import Reports from "@/components/HotelAdmin/Reports.vue"

// export default {
//     components: {
//         'hotels' : ViewHotels,
//         'edit-hotel' : EditHotel,
//         'quick-room-reservations' : QuickRoomReservations,
//         'reports' : Reports
//     },
//     data(){
//         return{
//             component : 'hotels',
//             selectedHotel : {}
//         }
//     },
//     methods:{
//         changeComp: function(hotel){
//             this.selectedHotel = hotel;
//             this.component = 'edit-hotel';
//         },
//         generateReports(hotel){
//             this.selectedHotel = hotel;
//             this.component = 'reports';
//         },
//         fetchHotels: function(){
//             this.$axios
//             .get('http://localhost:8080/api/hotels')
//             .then(response => this.hotels = response.data)
//         }
//     }
// }
</script>

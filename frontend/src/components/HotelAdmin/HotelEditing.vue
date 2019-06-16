<template>
    <div>
        <component 
        :is="component" 
        @goToQuickReservations="goToQuickRes($event)" 
        @goBack="component = 'edit-hotel'"
        :selectedHotel="hotel"></component>
    </div>
</template>

<script>
import EditHotel from "@/components/HotelAdmin/EditHotel.vue"
import QuickRoomReservations from "@/components/HotelAdmin/QuickRoomReservations.vue"
export default {
    components: {
        'edit-hotel' : EditHotel,
        'quick-room-reservations' : QuickRoomReservations
    },
    data(){
        return{
            component: 'edit-hotel',
            hotel : {}
        }
    },
    methods: {
        goToQuickRes(e){
            this.hotel = e;
            this.component = 'quick-room-reservations';
        }
    }
}
</script>

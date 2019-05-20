<template>
    <div id="reserveHotel">
        <component 
            :is="component"
            :hotelId="hotelId"
            :checkInDate="checkInDate"
            :checkOutDate="checkOutDate"
            @goToQuickReservations="changeComp($event)">
        </component>
    </div>
</template>

<script>
import HotelReservation from './HotelReservation.vue'
import ViewQuickReservations from './ViewQuickReservations.vue'

export default {
    components: {
        "hotelReservation" : HotelReservation,
        "viewQuickReservations" : ViewQuickReservations
    },

    data(){
        return{
            component: 'hotelReservation',
            hotelId: '',
            checkInDate: '',
            checkOutDate: ''
        }
    },
    methods:{
        changeComp(dataToPass){
            this.hotelId = dataToPass.hotelId;
            this.checkInDate = dataToPass.checkInDate;
            this.checkOutDate = dataToPass.checkOutDate;
            this.component = 'viewQuickReservations';
        }
    }
}
</script>

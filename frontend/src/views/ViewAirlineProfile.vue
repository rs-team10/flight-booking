<template>
    <div class="view-airline-profile">
        <v-container grid-list>
            <airline-profile/>
            <business-locations/>
            <flights/>
            <price-list/>
            <quick-flight-reservations/>
        </v-container>
    </div>
</template>

<script>
import AirlineProfile from "@/components/airline/AirlineProfile.vue"
import BusinessLocations from "@/components/airline/BusinessLocations.vue"
import Flights from "@/components/airline/Flights.vue"
import PriceList from "@/components/airline/PriceList.vue"
import QuickFlightReservations from "@/components/airline/QuickFlightReservations.vue"

export default {
    components: {
        'airline-profile': AirlineProfile,
        'business-locations': BusinessLocations,
        'flights': Flights,
        'price-list': PriceList,
        "quick-flight-reservations": QuickFlightReservations
    },
    data() {
        return {

        }
    },
    methods: {
    },
    created() {
    }
}
</script>

<style>
.airline-data {
    min-width: 300px;
    max-width: 500px;
}

</style>
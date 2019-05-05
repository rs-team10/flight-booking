<template>
    <div class="view-airline-profile">
        <v-container grid-list>
            <airline-profile/>
            <business-locations/>
            <flights/>
        </v-container>
    </div>
</template>

<script>
import AirlineProfile from "@/components/airline/AirlineProfile.vue"
import BusinessLocations from "@/components/airline/BusinessLocations.vue"
import Flights from "@/components/airline/Flights.vue"

export default {
    components: {
        'airline-profile': AirlineProfile,
        'business-locations': BusinessLocations,
        'flights': Flights
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
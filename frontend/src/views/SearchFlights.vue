<template>
    <div class="search-flights">
        <flights-search/>
    </div>
</template>

<script>
import FlightsSearch from "@/components/flightsSearch/FlightSearch.vue"

export default {
    components: {
        'flights-search': FlightsSearch
    },
    data() {
        return {

    // FILTER

    // PO AVIOKOMPANIJI
    // PO UKUPNOM TRAJANJU LETA
    // PO CENI
    // PO BROJU PRESEDANJA
        }
    },
    methods: {
    },
    created() {
    }
}
</script>

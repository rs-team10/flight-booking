<template>
    <div class="view-airline-profile">
        <v-container grid-list>
            <airline-profile/>
        </v-container>
    </div>
</template>

<script>
import AirlineProfile from "@/components/airline/AirlineProfile.vue"

export default {
    components: {
        'airline-profile': AirlineProfile,

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
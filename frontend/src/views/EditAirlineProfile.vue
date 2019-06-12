<template>
    <div class="edit-airline-profile">
        <v-container grid-list>
            <edit-airline/>
        </v-container>
    </div>
</template>

<script>
import EditAirline from "@/components/airline/EditAirline.vue"

export default {
    components: {
        'edit-airline': EditAirline
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

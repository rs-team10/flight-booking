<template>
    <div class="airline-reports">
        <v-container grid-list>
            <reports/>
        </v-container>
    </div>
</template>

<script>
import Reports from "@/components/airline/Reports.vue"

export default {
    components: {
        'reports': Reports
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

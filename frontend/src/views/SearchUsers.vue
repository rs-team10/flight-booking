<template>
    <div class="search-users">
        <v-container grid-list>
            <v-layout row>
                <search-users/>
            </v-layout>
        </v-container>
    </div>
</template>

<script>
import SearchUsers from "@/components/userProfile/SearchUsers.vue"

export default {
    components: {
        'search-users': SearchUsers
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

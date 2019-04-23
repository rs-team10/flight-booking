<template>
    <div class="edit-user-profile">
        <v-container grid-list>
            <v-layout row>
                <user-profile/>
                <edit-user/>
            </v-layout>
        </v-container>
    </div>
</template>

<script>
import UserProfile from "@/components/userProfile/UserProfile.vue"
import EditUser from "@/components/userProfile/EditUser.vue"

export default {
    components: {
        'user-profile': UserProfile,
        'edit-user': EditUser
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

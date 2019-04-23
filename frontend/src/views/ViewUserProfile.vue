<template>
    <div class="view-user-profile">
        <v-container grid-list>
            <v-layout row>
                <user-profile/>
                <v-tabs color="light blue" dark slider-color="red">
                    <v-tab>Friends list</v-tab>
                    <v-tab ripple>Friend requests</v-tab>
                    <v-spacer></v-spacer>
                    <v-tab @click="redirectToSearch()"><v-btn>Search users</v-btn></v-tab>
                    <v-tab-item>
                        <v-card>
                            <friendships/>
                        </v-card>
                    </v-tab-item>
                    <v-tab-item>
                        <v-card>
                            <friend-requests/>
                        </v-card>
                    </v-tab-item>   
                </v-tabs>
            </v-layout>
            
        </v-container>
    </div>
</template>

<script>
import UserProfile from "@/components/userProfile/UserProfile.vue"
import Friendships from "@/components/userProfile/Friendships.vue"
import FriendRequests from "@/components/userProfile/FriendRequests.vue"

export default {
    components: {
        'user-profile': UserProfile,
        'friendships': Friendships,
        'friend-requests': FriendRequests
    },
    data() {
        return {

        }
    },
    methods: {
        redirectToSearch() {
            this.$router.push('searchUsers');
        }
    },
    created() {
    }
}
</script>

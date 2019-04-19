<template>
    <div class="user-profile">
        <v-container grid-list>
            <v-layout row>
                <v-layout column align-center justify-center ma-4 v-if="showProfile">

                    <v-avatar :tile="false" :size="192" color="grey lighten-4">
                        <img src="@/assets/img/user.png" alt="avatar">
                    </v-avatar>

                    <h2>FirstName LastName</h2>
                    <h4>test@booking.com</h4>
                    <h4>+381637331063</h4>
                    <h4>Novi Sad, Serbia</h4>

                    <div class="text-xs-center">
                        <v-btn v-on:click="changeToEdit()" round small flat color="primary" dark>Edit Profile</v-btn>
                    </div>
                    <v-spacer></v-spacer>
                </v-layout>
                <component v-bind:is="activeComponent"
                   v-on:showFriendshipsClicked="changeToFriendships($event)"
                   v-on:searchUsersClicked="changeToSearch($event)"
            />
            </v-layout>

        </v-container>

    </div>
</template>

<script>
import EditUser from "@/components/EditUser.vue"
import Friendships from "@/components/Friendships.vue"
import SearchUsers from "@/components/SearchUsers.vue"

export default {
    components: {
        'edit-user' : EditUser,
        'friendships': Friendships,
        'search-users' : SearchUsers
    },
    props: {
        userAvatar: {
            type: String,
            default: require("@/assets/img/user.png")
        }
    },
    data() {
        return {
            activeComponent: 'friendships',
            showProfile: true
        };
    },
    methods: {
        changeToEdit: function() {
            this.activeComponent = 'edit-user';
            this.showProfile = true;
        },
        changeToSearch: function() {
            this.activeComponent = 'search-users';
            this.showProfile = false;
        }
    }
}
</script>

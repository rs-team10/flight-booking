<template>
    <div class="user-profile">
        <v-container grid-list>
            <v-layout row>
                <v-layout class="user-data" column align-center justify-center ma-4 v-if="showProfile">

                    <v-avatar :tile="false" :size="192" color="grey lighten-4">
                        <img src="@/assets/img/user.png" alt="avatar">
                    </v-avatar>

                    <h2 class="text-xs-center">{{ user.firstName + ' ' + user.lastName }}</h2>
                    <h4 class="text-xs-center">{{ user.email }}</h4>
                    <h4 class="text-xs-center">{{ user.phone }}</h4>
                    <h4 class="text-xs-center">{{ user.address }}</h4>

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

var MOCK_USER_ID = 1;

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
            showProfile: true,
            user: {
                firstName: 'TestName',
                lastName: 'TestSurname',
                email: 'test@flightbooking.com',
                phone: '123123123',
                address: 'Toronto, ON, Canada'
            }
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
    },
    created() {
        this.$axios.get('http://localhost:8081/api/registeredUsers/' + MOCK_USER_ID).then((response) => {
            this.user = response.data;
        }).catch(function(error) {
                alert(error.response.data.message);
            });;
    }
}
</script>

<style>
.user-data {
    max-width: 800px;
}
</style>

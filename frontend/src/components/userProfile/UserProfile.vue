<template>
    <v-layout class="user-data" column align-center justify-center ma-4>
        <v-avatar :tile="false" :size="192" color="grey lighten-4">
            <img src="@/assets/img/user.png" alt="avatar">
        </v-avatar>
        <h2 class="text-xs-center">{{ user.firstName + ' ' + user.lastName }}</h2>
        <h4 class="text-xs-center">{{ user.email }}</h4>
        <h4 class="text-xs-center">{{ user.phone }}</h4>
        <h4 class="text-xs-center">{{ user.address }}</h4>
        <div class="text-xs-center">
            <v-layout column>
                <v-btn @click="redirectToEdit()" round small flat color="primary" dark>Edit Profile</v-btn>
                <v-divider/>
            </v-layout>
        </div>
        <v-spacer></v-spacer>
    </v-layout>
</template>

<script>

export default {
    props: {
        userAvatar: {
            type: String,
            default: require("@/assets/img/user.png")
        }
    },
    data() {
        return {
            user: {
                firstName: '',
                lastName: '',
                email: '',
                phone: '',
                address: ''
            }
        };
    },
    methods: {
        redirectToEdit() {
            this.$router.push('editUserProfile');
        }
    },
    created() {

        var yourConfig = {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        };

        this.$axios.get('/api/currentUser/', yourConfig).then((response) => {
            this.user = response.data;
        }).catch((error) => {
            this.$swal("Error", error.response.data.message, 'error');
        });
    }
}
</script>

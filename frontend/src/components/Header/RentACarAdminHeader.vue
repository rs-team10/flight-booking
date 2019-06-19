<template>
    <v-toolbar-items>

        <v-btn flat @click='go'>
            <v-icon left>directions_car</v-icon>
            <span>Rent a car service</span>
        </v-btn>

        <v-btn flat router to="/rentACarAdminProfile">
            <v-icon left>account_circle</v-icon>
            <span>My profile</span>
        </v-btn>

        <v-btn flat @click="$emit('logOut')">
            <u>Log out</u>
        </v-btn>
    </v-toolbar-items>
</template>

<script>
export default {
    methods:{
        go:function(){
            this.$router.push('/rentACar/'+localStorage.getItem("rentACarId"));
            //this.$router.go();
        }
    }
}
</script>
<template>
    <v-toolbar-items>
        <v-btn flat blur router to="/searchFlights" color="green">
            <span>Reserve</span>
        </v-btn>

        <v-btn flat router to="/airlines">
            <v-icon left>flight</v-icon>
            <span>Airlines</span>
        </v-btn>

        <v-btn flat router to="/hotels" blur>
            <v-icon left>hotel</v-icon>
            <span>Hotels</span>
        </v-btn>

        <v-btn flat router to="/rentACars" blur>
            <v-icon left>directions_car</v-icon>
            <span>Rent-a-car</span>
        </v-btn>

        <v-btn flat router to="/userProfile">
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
    
}
</script>
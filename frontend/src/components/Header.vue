<template>
    <nav>
        <v-toolbar flat app>
            <v-toolbar-title class="text-uppercase grey--text">
                <router-link to="/">
                    <span class="font-weight-light">Flight</span>
                    <span>Booking</span>
                </router-link>
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn router to="/editUser">
                <v-icon left>account_circle</v-icon>
                <span>Edit User</span>
            </v-btn>
            <v-btn router to="/editAirline">
                <v-icon left>flight</v-icon>
                <span>Edit Airline</span>
            </v-btn>
            <v-btn router to="/hotels">
                <v-icon left>hotel</v-icon>
                <span>Hotels</span>
            </v-btn>
        </v-toolbar>
    </nav>
</template>

<script>
export default {}
</script>

<style scoped>
.v-toolbar__title a{
  color: black;
  text-decoration: none;
}
</style>

<template>
    <v-toolbar-items>
        <v-btn flat blur router to="/editHotel">
            <span>Edit hotel</span>
        </v-btn>

        <v-btn flat blur router to="/hotelReport">
            <v-icon left>assessment</v-icon>
            <span>Hotel report</span>
        </v-btn>

        <v-btn flat router to="/hotelAdminProfile">
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

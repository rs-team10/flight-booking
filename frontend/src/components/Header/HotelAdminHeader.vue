<template>
    <v-toolbar-items>
        <v-btn flat router to="/hotels" blur>
            <v-icon left>hotel</v-icon>
            <span>Hotels</span>
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

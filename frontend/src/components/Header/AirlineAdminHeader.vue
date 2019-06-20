<template>
    <v-toolbar-items>
        <v-btn flat blur router to="/airlineProfile">
            <span>Edit airline</span>
        </v-btn>

        <v-btn flat blur router to="/airlineReports">
            <v-icon left>assessment</v-icon>
            <span>Airline report</span>
        </v-btn>

        <v-btn flat>
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

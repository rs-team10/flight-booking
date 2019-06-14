<template>
    <v-toolbar-items>

        <v-btn flat>
            <v-icon left>flight</v-icon>
            <span>Airlines</span>
        </v-btn>

        <v-btn flat router to="/hotels" blur>
            <v-icon left>hotel</v-icon>
            <span>Hotels</span>
        </v-btn>

        <v-btn flat>
            <v-icon left>directions_car</v-icon>
            <span>Rent-a-car</span>
        </v-btn>
        <!-- <v-divider vertical></v-divider> -->

        <v-btn flat @click="$emit('logIn')">
            <u>Login</u>
        </v-btn>
        <v-btn flat @click="$emit('signUp')">
            <u>Signup</u>
        </v-btn>

    </v-toolbar-items>
</template>

<script>
export default {
}
</script>

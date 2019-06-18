<template>
    <v-toolbar-items>
        <v-btn flat router to="/sysAdmin">
            <span>Dashboard</span>
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

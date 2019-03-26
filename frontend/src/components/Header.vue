<template>
    <header>
        <h1>{{ msg }}</h1>  
        <router-link to="/">Home</router-link> |
        <router-link to="/hotels">Hotels</router-link>
    </header>  
</template>

<script>
export default {
    data(){
        return{
            msg: "Ovo je header sa linkovima"
        }
    }
}
</script>

<style scoped>

</style>


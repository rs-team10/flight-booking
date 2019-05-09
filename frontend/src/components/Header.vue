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
            <v-toolbar-items>
                <v-btn flat router to="/userProfile">
                    <v-icon left>account_circle</v-icon>
                    <span>My profile</span>
                </v-btn>
                <v-btn flat router to="/editAirline">
                    <v-icon left>flight</v-icon>
                    <span>Airlines</span>
                </v-btn>

                <v-btn flat router to="/hotels" blur>
                    <v-icon left>hotel</v-icon>
                    <span>Hotels</span>
                </v-btn>
                    
                <v-btn flat router to="/rentACars">
                    <v-icon left>directions_car</v-icon>
                    <span>Rent a cars</span>
                </v-btn>
                
                <v-btn flat router to="/rentACar/1">
                    <v-icon left>directions_boat</v-icon>
                    <span>RentACar flow</span>
                </v-btn>

                <v-btn flat router v-bind:to = "route" @click = "log()">
                    <v-icon left>rowing</v-icon>
                    <span>{{this.title}}</span>
                </v-btn>

                <v-btn v-if="showSignUp" flat router to="/signup">
                    <span>Sign up</span>
                </v-btn>
            
                
            </v-toolbar-items>
        </v-toolbar>
    </nav>
</template>

<script>
export default {
    data(){ 
        return{
            title:'login',
            route: '/login'
        }
    },

    methods: { 

        log: function(){

            if(localStorage.getItem("token") == undefined){
                this.title = 'login';
                this.route = '/login';
                console.log("LOGIN TREBA DA PISE!")
            }else{
                this.title = 'logout';
                this.route = '/logout';
                console.log("LOGOUT TREBA DA PISE!")
            }
            

        }


    },
    computed: {
        showSignUp(){
            if(localStorage.getItem("token") === null)
                return true
            return false
        }
    },
    mounted(){
        this.log();
    }


}
</script>

<style scoped>
.v-toolbar__title a{
  color: black;
  text-decoration: none;
}
</style>

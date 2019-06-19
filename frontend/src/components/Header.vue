<template>
    <nav>
        <v-toolbar flat app dense>
            <v-toolbar-title class="text-uppercase grey--text">
                <router-link to="/">
                    <span class="font-weight-light">Flight</span>
                    <span>Booking</span>
                </router-link>
            </v-toolbar-title>
            <v-spacer></v-spacer>
            
            <component 
                :is="currentHeader" 
                @logIn="logIn()"
                @logOut="logOut()"
                @signUp="signUp()"
            >
            </component>
                
        </v-toolbar>
    </nav>
</template>

<script>
import HotelAdminHeader from "@/components/Header/HotelAdminHeader.vue"
import UnregisteredUserHeader from "@/components/Header/UnregisteredUserHeader.vue"
import SysAdminHeader from "@/components/Header/SysAdminHeader.vue"
import RegisteredUserHeader from "@/components/Header/RegisteredUserHeader.vue"
import RentACarAdminHeader from "@/components/Header/RentACarAdminHeader.vue"

export default {
    components: {
        "hotel-admin-header" : HotelAdminHeader,
        "unregistered-user-header" : UnregisteredUserHeader,
        "sys-admin-header" : SysAdminHeader,
        "registered-user-header" : RegisteredUserHeader,
        "rentACarAdminHeader"   : RentACarAdminHeader
    },

    data(){ 
        return{
            title:'login',
            route: '/login',
            currentHeader : 'unregistered-user-header'

        }
    },
    
    methods: { 
        currentUser: function(){
            if(localStorage.getItem("token") == null){
                this.currentHeader= 'unregistered-user-header'
            }else{
                if(localStorage.getItem("role") == "ROLE_HOTEL_ADMIN")
                    this.currentHeader= 'hotel-admin-header'
                else if(localStorage.getItem("role") == "ROLE_SYSTEM_ADMIN")
                    this.currentHeader= 'sys-admin-header'
                else if(localStorage.getItem('role') == "ROLE_REGISTERED_USER")
                    this.currentHeader= 'registered-user-header'
                else if(localStorage.getItem('role') == "ROLE_RENT_A_CAR_ADMIN")
                    this.currentHeader = 'rentACarAdminHeader'
            }
        },
        logIn(){
            //this.currentUser();
            this.$router.push("/login");
        },

        logOut(){
            localStorage.removeItem("token");
            localStorage.removeItem("username");
            localStorage.removeItem("role");
            localStorage.removeItem("rentACarId");
            this.currentUser();
            this.$router.push("/login");
            //this.$router.go();
        },
        signUp(){
            this.$router.push("signup")
        }

    },
    mounted(){
        this.currentUser();
    }
    // ,
    // mounted(){
    //     this.log();
    // }


}


// <v-btn flat router to="/userProfile">
//                     <v-icon left>account_circle</v-icon>
//                     <span>My profile</span>
//                 </v-btn>
//                 <v-btn flat router to="/airlineProfile">
//                     <v-icon left>flight</v-icon>
//                     <span>Airlines</span>
//                 </v-btn>
//                 <v-btn flat router to="/searchFlights">
//                     <v-icon left>search</v-icon>
//                     <span>Search Flights</span>
//                 </v-btn>

//                 <v-btn flat router to="/hotels" blur>
//                     <v-icon left>hotel</v-icon>
//                     <span>Hotels</span>
//                 </v-btn>
                    
//                 <v-btn flat router to="/rentACars">
//                     <v-icon left>directions_car</v-icon>
//                     <span>Rent a cars</span>
//                 </v-btn>
                
//                 <v-btn flat router to="/rentACar/1">
//                     <v-icon left>directions_boat</v-icon>
//                     <span>RentACar flow</span>
//                 </v-btn>

//                 <v-btn flat router v-bind:to = "route" @click = "log()">
//                     <v-icon left>rowing</v-icon>
//                     <span>{{this.title}}</span>
//                 </v-btn>

//                 <v-btn v-if="showSignUp" flat router to="/signup">
//                     <span>Sign up</span>
//                 </v-btn>
</script>

<style scoped>
.v-toolbar__title a{
  color: black;
  text-decoration: none;
}
</style>



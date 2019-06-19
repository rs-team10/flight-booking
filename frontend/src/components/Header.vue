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
import AirlineAdminHeader from "@/components/Header/AirlineAdminHeader.vue"

export default {
    components: {
        "hotel-admin-header" : HotelAdminHeader,
        "unregistered-user-header" : UnregisteredUserHeader,
        "sys-admin-header" : SysAdminHeader,
        "registered-user-header" : RegisteredUserHeader,
        "rentACarAdminHeader"   : RentACarAdminHeader,
        "airline-admin-header" : AirlineAdminHeader
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
                else if(localStorage.getItem('role') == "ROLE_AIRLINE_ADMIN")
                    this.currentHeader = 'airline-admin-header'
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
}
</script>

<style scoped>
.v-toolbar__title a{
  color: black;
  text-decoration: none;
}
</style>



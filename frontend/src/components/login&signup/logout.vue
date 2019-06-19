<template>
    <div id="login">
        <h1> Logout</h1>
        <div>
            <v-alert
                :value="success"
                type="success"
                transition="scale-transition"
            >
                {{this.success}}
                
            </v-alert>


            <v-alert
                :value="error"
                type="error"
                >
                {{this.error}}
            </v-alert>

        </div>
    </div>
    
</template>

<script>

export default {

    data () {
        return {
            username: "",
            success: false,
            error: false
        }
    },

    methods: { 

        logOut: function(){

            if(localStorage.getItem("token") == undefined){
                this.error = 'There is no logged user!';
            }else{
                console.log(localStorage.getItem("username"));
                this.username = localStorage.getItem("username");
                localStorage.removeItem("token");
                localStorage.removeItem("username");
                localStorage.removeItem("role");
                localStorage.removeItem("rentACarId");
                this.success = "Logout DONE!\n User "+this.username+" is successfully logged out!";
            }
            

        }
    },
    created(){
        this.logOut();
    }

}
</script>
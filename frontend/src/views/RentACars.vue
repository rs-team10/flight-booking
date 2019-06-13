<template>
  <div class="rentACars">
    <div id="rentACar-buttons">
        <v-btn v-on:click="component = 'rentACars'" outline color="indigo">View rent-a-cars</v-btn>
        <v-btn v-on:click="component = 'add-rentACar'" outline color="indigo">Add rent-a-car</v-btn>
    </div>
    <component v-bind:is="component" 
        v-on:selectedRentACar="changeComp($event)" 
        v-bind:selectedRentACar="selectedRentACar"></component>

  </div>
</template>

<script>
// @ is an alias to /src
import ViewRentACars from "@/components/ViewRentACars.vue"
import AddRentACar from "@/components/SysAdmin/AddRentACar.vue"
import EditRentACar from "@/components/EditRentACar.vue"

export default {
    components: {
        'rentACars' : ViewRentACars,
        'add-rentACar' : AddRentACar,
        'edit-rentACar' : EditRentACar
    },
    data(){
        return{
            component : 'rentACars',
            selectedRentACar : {}
        }
    },
    methods:{
        changeComp: function(rentACar){
            this.selectedRentACar = rentACar;
            this.component = 'edit-rentACar';
        },
        fetchRentACars: function(){
            var yourConfig = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token") 

                }
            }

            this.$axios.interceptors.request.use(request => {
            console.log('Starting Request', request)
            return request
            })

            this.$axios
            .get('http://localhost:8080/api/rentACars',yourConfig)
            .then(response => this.rentACars = response.data)
        }
    }
}
</script>

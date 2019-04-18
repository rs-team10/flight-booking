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
import AddRentACar from "@/components/AddRentACar.vue"
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
        fetchHotels: function(){
            this.$axios
            .get('http://localhost:8081/api/rentACars')
            .then(response => this.rentACars = response.data)
        }
    }
}
</script>

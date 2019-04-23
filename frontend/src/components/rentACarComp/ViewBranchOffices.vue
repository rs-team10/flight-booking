<template>
  <div>
     
    <v-layout rowt>
      <v-flex xs12 sm10 md10 offset-sm1>
        <h1>Branch offices</h1>
        <v-card>
          <v-expansion-panel focusable>
            <v-expansion-panel-content
              v-for="(branch, i) in branches"
              :key="i"
            >
              <template v-slot:header>
                <div v-on:click="branchSelected(branch)">
                  {{ branch.location.city }}, {{ branch.location.country }}
                  <v-menu
                    open-on-hover
                    offset-y
                    :close-on-content-click="false"
                    lazy
                    :key="i"
                    >
                </div>
              </template>
              <v-card>
                <ViewVehiclesA />
              </v-card>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-card>
      </v-flex>
    </v-layout>
    
  </div>
</template>




<script>
import ViewVehiclesA from "@/components/rentACarComp/ViewVehiclesA.vue"


export default {
  components: {
        ViewVehiclesA
  },
  name: 'branches',
  data(){
      return{
          branches: [
                    
                      {
                          id : 1,
                          location : {
                              city : 'Smederevo',
                              country: 'Srbija'
                          }
                      },
                      {
                          id : 2,
                          location : {
                              city : 'Stara pazova',
                              country: 'Srbija'
                          }
                      }
            ],
          selectedBranch: ''
      }
  },
  methods:{
      fetchBranches: function(){
          this.$axios
          .get('http://localhost:8080/api/branchOffices')//+rentACar.id
          .then(response => 
            this.branches = response.data)
      },
      
  },
  mounted(){
      //this.fetchBranches();
  }
}
</script>

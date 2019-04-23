<template>
  <div>
    <v-card
      class="hide-overflow"
      color="grey lighten-4"            
    >
    <v-toolbar
      card
      color="grey lighten-4"
    >
      <v-toolbar-title class="headline font-weight-regular black--text">Branch offices</v-toolbar-title>
    </v-toolbar>
    <v-layout rowt>
      <v-flex xs12 sm10 md10 offset-sm1>
        <v-card>
          <v-expansion-panel focusable>
            <v-expansion-panel-content
              v-for="(branch, i) in branches"
              :key="i"
            >
              <template v-slot:header>
                <div v-on:click="branchSelected(branch)">
                  {{ branch.city }}, {{ branch.country }}
                  <v-menu
                    open-on-hover
                    offset-y
                    :close-on-content-click="false"
                    lazy
                    :key="i"
                    >
                    <v-btn
                        icon
                        class="mx-0"
                        slot="activator"
                        >
                        <v-icon color="blue">label_important</v-icon>
                    </v-btn>
                    <v-list>
                          <v-list-tile avatar>
                              <v-btn
                                color="error"
                                @click="removeOne(branch)"
                              >
                                Delete
                              </v-btn>

                              <v-btn
                                @click="removeOne(branch)"
                              >
                                Edit
                              </v-btn>
                          </v-list-tile>
                        </v-list>
                    </v-menu>
                </div>
              </template>


               <component 
                  v-bind:is="component"
                  :branchOfficeId="branch.id"
                 > 
                </component>


            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-card>
      </v-flex>
    </v-layout>
    </v-card>
    
  </div>
</template>




<script>
import ViewVehiclesA from "@/components/rentACarComp/ViewVehiclesA.vue"


export default {
  props: ['rentACarId'],
  components: {
      'viewVehiclesA' :  ViewVehiclesA
  },
  name: 'branches',
  data(){
      return{

          
          component: 'viewVehiclesA',

          branches: [
                    
                      {
                        id : 1,
                        city : 'Smederevo',
                        country: 'Srbija'
                         
                      },
                      {
                        id : 2,
                        city : 'Stara pazova',
                        country: 'Srbija'
               
                      }
            ],
          selectedBranch: ''
      }
  },
  methods:{
      fetchBranches: function(){
          this.$axios
          .get('http://localhost:8080/api/branchOffices/'+this.rentACarId)
          .then(response => 
            this.branches = response.data)
      },
      branchSelected: function(branch){
          this.$emit('branchSelected', branch);
      },
      removeOne:function(item){
         this.branches = this.branches.filter(i=>i !== item)
      }
  },
  mounted(){
      this.fetchBranches();
  }
}
</script>

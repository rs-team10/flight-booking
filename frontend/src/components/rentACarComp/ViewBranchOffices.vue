<template>
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
    

    <v-layout column  align-center>
      <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ on }">
          <v-btn fab small dark color="primary" @click="addItem"><v-icon dark>add</v-icon></v-btn>
        </template>
        <v-card>
          <v-card-text>

                 <component 
                  v-bind:is="component1"
                  :rentACarId="rentACarId"
                 > 
                 </component>
  
          </v-card-text>
        </v-card>
      </v-dialog>
      
    </v-layout>

  </v-expansion-panel>

</template>




<script>
import ViewVehicles from "@/components/rentACarComp/ViewVehicles.vue"
import AddBranchOffice from "@/components/rentACarComp/AddBranchOffice.vue"

export default {
  props: ['rentACarId'],
  components: {
      'viewVehicles' :  ViewVehicles,
      'addBranchOffice' : AddBranchOffice
  },
  name: 'branches',
  data(){
      return{

          
          component: 'viewVehicles',
          component1: 'addBranchOffice',

          branches: [],
/*
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
*/
          selectedBranch: '',
          dialog: false
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
      },
      addItem(){
         this.dialog = true;
      }
  },
  mounted(){
      this.fetchBranches();
  }
}
</script>

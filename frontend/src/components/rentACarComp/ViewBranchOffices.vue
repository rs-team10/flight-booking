<template>

  <v-expansion-panel focusable>

     <v-dialog v-model="dialog" max-width="500px"> 
        <v-card>
          <v-card-text>
                 <component 
                  v-bind:is="component1"
                  :rentACarId="rentACarId"
                  :branchOfficeInc = "selectedBranchOffice"
                  :currentMapCenter = "currentMapCenter"
                 > 
                 </component>
          </v-card-text>
        </v-card>
      </v-dialog>

    <v-expansion-panel-content
      v-for="(branch, i) in branches"
      :key="i"
    >
      <template v-slot:header>
        <div >
          {{ branch.city }}, {{ branch.country }}
          <v-menu
            v-if = 'adminsPage'
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
                    
                    <v-btn @mouseover="branchSelected(branch)" @click = "editItem">Edit</v-btn>
          

                    <v-btn
                      color="error"
                      @click="removeOne(branch)"
                    >
                      Delete
                    </v-btn>

                      
                  </v-list-tile>
                </v-list>
            </v-menu>
        </div>
      </template>

      <v-card>
        <component 
          v-bind:is="component"
          :rentACarId="rentACarId"
          :branchOfficeId="branch.id"
          > 
        </component>
      </v-card>
    </v-expansion-panel-content>
    

    <v-layout column  align-center v-if = 'adminsPage'>
       <v-btn fab small dark color="primary" @click="addItem"><v-icon dark>add</v-icon></v-btn>
    </v-layout>

  </v-expansion-panel>



</template>




<script>
import ViewVehicles from "@/components/rentACarComp/ViewVehicles.vue"
import AddBranchOffice from "@/components/rentACarComp/AddBranchOffice.vue"
import EditBranchOffice from "@/components/rentACarComp/EditBranchOffice.vue"

var yourConfig = {headers: { Authorization: "Bearer " + localStorage.getItem("token")}}
export default {
  props: ['rentACarId'],
  components: {
      'viewVehicles' :  ViewVehicles,
      'addBranchOffice' : AddBranchOffice,
      'editBranchOffice': EditBranchOffice
  },
  name: 'branches',
  data(){
      return{

          
          component: 'viewVehicles',
          component1: 'addBranchOffice',

          branches: [],
          dialog: false,
          selectedBranchOffice : '',
          currentMapCenter : ''

      }
  },
  methods:{
      fetchBranches: function(){
          this.$axios
          .get('http://localhost:8080/api/branchOffices/'+this.rentACarId, yourConfig)
          .then(response => 
            this.branches = response.data)
      },
      removeOne:function(item){
        
        this.$axios
          .delete('api/branchOffice/'+item.id)
          .then(response => {
              this.branches = this.branches.filter(i=>i !== item)
              alert(response.data)
            }
          )
          .catch(error => {

                alert(error.response.data);
                
            }
          )

      },
      addItem(){
        this.component1 = 'addBranchOffice';
        this.dialog = true;
      },
      editItem(){
        this.component1 = 'editBranchOffice';
        this.dialog = true;
      },
      branchSelected( branch ){
        this.component1 = 'editBranchOffice';
        this.selectedBranchOffice = {
                                      id : branch.id,
                                      location : {
                                        latitude : branch.latitude,
                                        longitude : branch.longitude
                                      }
                                    };
        this.currentMapCenter = {
                lat: branch.latitude,
                lng: branch.longitude
            }

      }
  },
  mounted(){
      this.fetchBranches();
  },
    computed: {
        adminsPage(){
            return localStorage.getItem("rentACarId") == this.rentACarId
        }
    }
}
</script>

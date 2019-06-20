<template>
    
    <v-card>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                ref = 'btn'
                :dark = "true"
                color="black"
                fab
                small
                @click="changeCanc"
            >
                <v-icon v-if="isEditing">close</v-icon>

                <v-icon v-else>edit</v-icon>
            </v-btn>
        </v-card-actions>
            
        <v-card-text v-if="isEditing">
        
                 
            <v-flex center xs12 sm6 md10 offset-sm1>

                <v-text-field
                    v-model.lazy="rentACar.name"
                    label="Name"
                    placeholder="Rent-a-car name"
                    outline
                ></v-text-field>

                <v-textarea
                    v-model.lazy="rentACar.description"
                    outline
                    label="Description"
                    placeholder="Rent-a-car descsriptin"
                    
                ></v-textarea>
            </v-flex>
                
        </v-card-text>
        <v-card-text v-else>
    
                
            <v-flex center xs12 sm6 md10 offset-sm1>

                <v-text-field
                    @focus.stop = "noClick"
                    v-model.lazy="rentACar.name"
                    label="Name"
                    placeholder="Rent-a-car name"
                    outline
                ></v-text-field>

                <v-textarea
                    @focus.stop = "noClick"
                    v-model.lazy="rentACar.description"
                    outline
                    label="Description"
                    placeholder="Rent-a-car descsriptin"
                    
                ></v-textarea>
            </v-flex>
            
        </v-card-text>

        <v-card-actions>

            <v-spacer></v-spacer>
            <v-btn
                :dark = "true"
                :disabled="!isEditing"
                color="black"
                @click="save"
            >
                Save
            </v-btn>
        </v-card-actions>
        
        <v-snackbar
            v-model="hasSaved"
            :timeout="2000"
            absolute
            bottom
            left
        >
        Rent a car profile has been updated
        </v-snackbar>

  </v-card>
</template>


<script>
var yourConfig = {headers: { Authorization: "Bearer " + localStorage.getItem("token")}}
  export default {
    //fali validacija na frontu 
    props : ['rentACarId'],

    data () {
      return {

        rentACar :{
                id : 0,
                name : '',
                description : ''
        },
        beforeChange : {
                         name : '',
                         description : ''

                        },
        hasSaved: false,
        isEditing: false,
        no: ''

      }
    },

    methods: {
        fetchRentACar: function(){
            this.$axios
            .get('/api/rentACar/'+ this.rentACarId, yourConfig)
            .then(response => {
                                this.rentACar.id = response.data.id;
                                this.rentACar.name  = response.data.name;
                                this.rentACar.description = response.data.description;
                                })
        },
        noClick:function(){
            this.$refs.btn.$el.focus()
        },
        changeCanc:function(){

            if(this.isEditing == false){
                this.beforeChange.name = this.rentACar.name;
                this.beforeChange.description = this.rentACar.description
            }else{
                this.rentACar.name  = this.beforeChange.name;
                this.rentACar.description = this.beforeChange.description;
            }

            this.isEditing = !this.isEditing;

            
            

        },
        save () {
            this.isEditing = !this.isEditing;

            this.editOnBackend();
            //this.hasSaved = true; //delete

        },
        editOnBackend: function(){
            this.$axios
            .put('/api/rentACars/',this.rentACar, yourConfig)
            .then(() => {
                this.beforeChange.name = this.rentACar.name;
                this.beforeChange.description = this.rentACar.description;
                this.hasSaved=true; 
                })
            .catch(error => {
                this.$swal("Error",error.response.data.message, 'error');
                this.rentACar.name  = this.beforeChange.name;
                this.rentACar.description = this.beforeChange.description;

            });
            
             
        }
    },
    created(){
        this.fetchRentACar();
    }
  }
</script>
<template>
    
  <v-card
    color="grey lighten-4"
  >
    <v-toolbar
      card
      color="grey lighten-4"
    >
      <v-toolbar-title class="headline font-weight-regular black--text">Informations</v-toolbar-title>
      
    </v-toolbar>
    <v-flex xs12 sm10 md10 offset-sm1>
        <v-card
        color="white"
        >
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

        <v-divider></v-divider>

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
    </v-flex>
  </v-card>
</template>


<script>
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
            .get('http://localhost:8080/api/rentACar/'+ this.rentACarId)
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
        customFilter (item, queryText, itemText) {
            const textOne = item.name.toLowerCase()
            const textTwo = item.abbr.toLowerCase()
            const searchText = queryText.toLowerCase()

            return textOne.indexOf(searchText) > -1 ||
            textTwo.indexOf(searchText) > -1
        },
        save () {
            this.isEditing = !this.isEditing;

            this.editOnBackend();
            //this.hasSaved = true; //delete

        },
        editOnBackend: function(){
            this.$axios
            .put('http://localhost:8080/api/rentACars/',this.rentACar)
            .then(response => {
                this.beforeChange.name = this.rentACar.name;
            this.beforeChange.description = this.rentACar.description;
                this.hasSaved=true; 
                })
            .catch(error => {
                alert(error.response.data.message);
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
<!-- System admin -->
<template>
    <div id="add-hotel">
        
        <div>
            <v-alert
                :value="success"
                type="success"
                transition="scale-transition"
            >
            Hotel: <b>{{ hotel.name }}</b> registered successfully.
            </v-alert>

            <v-alert
                :value="error"
                type="error"
                dismissible
                >
                {{this.error}}
            </v-alert>

        </div>
        <div id="add-form" >
            <v-flex xs12 sm6 offset-sm3>
                <h1 class="text-xs-center indigo--text">Register new hotel</h1>
                <form>
                    <v-text-field
                        v-model.lazy="hotel.name"
                        :error-messages="nameErrors"
                        label="Name"
                        required>
                    </v-text-field>

                    <v-text-field
                        v-model.lazy="hotel.location.street"
                        :error-messages="addressErrors"
                        label="Address"
                        required>
                    </v-text-field>

                    <v-layout row justify-center>
                        <v-dialog v-model="adminDialog" persistent max-width="600px">
                        <div>
                            <v-alert
                                :value="adminError"
                                type="error"
                                dismissible
                                >
                                {{this.adminError}}
                            </v-alert>
                        </div>

                        <template v-slot:activator="{ on }">
                            <v-btn block color="indigo darken-2" dark v-on="on">Add administrator</v-btn>
                        </template>
                        <v-card>
                            <v-card-title>
                                <span class="headline">Admin Profile</span>
                            </v-card-title>
                            <v-card-text>
                                <v-container grid-list-md>
                                    <v-layout wrap>
                                    <v-flex xs12 sm6 md6>
                                        <v-text-field label="First name" v-model="tempAdmin.firstName"></v-text-field>
                                    </v-flex>
                                    <v-flex xs12 sm6 md6>
                                        <v-text-field label="Last name" v-model="tempAdmin.lastName"></v-text-field>
                                    </v-flex>
                                    <v-flex xs12 sm6 md6>
                                        <v-text-field label="Username*" v-model="tempAdmin.username" required></v-text-field>
                                    </v-flex>
                                    <v-flex xs12 sm6 md6>
                                        <v-text-field label="Password*" v-model="tempAdmin.password" required></v-text-field>
                                    </v-flex>
                                    <v-flex xs12>
                                        <v-text-field label="Email*" v-model="tempAdmin.email" required></v-text-field>
                                    </v-flex>
                                    </v-layout>
                                </v-container>
                                <small>*indicates required field</small>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="indigo darken-1" flat @click="close">Close</v-btn>
                                <v-btn color="indigo darken-1" flat @click="save">Save</v-btn>
                                </v-card-actions>
                        </v-card>
                        </v-dialog>
                    </v-layout>

                    <v-layout row>
                        <v-flex>
                        <v-card>
                            <v-toolbar color="indigo" dark>
                            <v-toolbar-title class="text-xs-center">Hotel administrators</v-toolbar-title>
                            <v-spacer></v-spacer>
                            </v-toolbar>
                                <v-list 
                                    two-line 
                                    class="indigo darken-2 scroll-y" 
                                    dark
                                    style="height: 400px">
                                    <template v-for="admin in this.hotel.administrators"> 
                                        <v-list-tile :key="admin.id">
                                            <v-list-tile-content>
                                                <v-list-tile-title class="font-weight-bold">
                                                    <span class="font-weight-black">Username:</span> {{ admin.username }}
                                                    <span class="font-weight-black">Password:</span> {{ admin.password }}
                                                </v-list-tile-title>
                                                <v-list-tile-sub-title>{{ admin.firstName }}  {{admin.lastName}}</v-list-tile-sub-title>
                                                <v-list-tile-sub-title>{{ admin.email }}</v-list-tile-sub-title>
                                            </v-list-tile-content>
                                        </v-list-tile>
                                    </template>
                                </v-list>
                        </v-card>
                        </v-flex>
                    </v-layout>

                    

                    <div >
                        <v-btn block @click="submit">submit</v-btn>
                    </div>
                </form>
            </v-flex>
        </div>

    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required } from 'vuelidate/lib/validators'

export default {
    mixins: [validationMixin],

    validations: {
        hotel: {
            name: { required },
            location: {
                street: { required }
            }
        }
    },
      
    data(){
        return {
            hotel: {
                name: '',
                location: {
                    street: ''
                },
                description: '',
                administrators: []
            },
            success: false,
            error: false,
            adminError: false,
            adminDialog: false,
            tempAdmin: {}        }
    },
    computed: {
        
        nameErrors () {
            const errors = []
            if (!this.$v.hotel.name.$dirty) return errors
            !this.$v.hotel.name.required && errors.push('Name is required.')
            return errors
        },
        addressErrors() {
            const errors = []
            if (!this.$v.hotel.location.street.$dirty) return errors
            !this.$v.hotel.location.street.required && errors.push('Address is required.')
            return errors
        }
    },

    methods:{
        submit () {
            this.$v.$touch();

            if(!this.$v.$invalid){
                this.addHotel();
                
            }
        },
        addHotel: function(){
            this.$axios
            .post('http://localhost:8081/api/hotels', this.hotel)
            .then(response => {
                this.success = true;
                setTimeout(() => {
                    this.success = false
            }, 3000)
            }).catch(error => {
                console.log(error.response.data);
                this.error = error.response.data;
            });
        },
        close(){
            this.adminDialog = false;
            this.tempAdmin = Object.assign({}, {});
        }, 
        save(){
            if(!this.adminExists(this.tempAdmin.username)){
                this.hotel.administrators.push(this.tempAdmin);
                this.close();
            }else{
                this.adminError = "Administrator with that username already added";
            }
        },
        adminExists(usr){
            for(var i=0; i < this.hotel.administrators.length; i++){
                if( this.hotel.administrators[i].username == usr)
                    return true
            }
            return false
      }
      
    }
}

</script>

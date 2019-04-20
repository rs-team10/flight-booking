<!-- System admin -->
<template>
    <div id="add-airline">
        
        <div id="alerts">
            <v-alert
                :value="success"
                type="success"
                transition="scale-transition"
            >
            Airline: <b>{{ airline.name }}</b> registered successfully.
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
                <h1 class="text-xs-center indigo--text">Register new airline</h1>
                <v-form>
                    <v-text-field
                        v-model.lazy="airline.name"
                        :error-messages="nameErrors"
                        label="Name"
                        required>
                    </v-text-field>

                    <v-text-field
                        v-model.lazy="airline.location.street"
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
                        
                        <v-form ref="adminForm">
                        <v-card>
                            <v-card-title>
                                <span class="headline">New Airline administrator</span>
                            </v-card-title>
                            <v-card-text>
                                
                                <v-container grid-list-md>
                                    <v-layout wrap>
                                    <v-flex xs12 sm6 md6>
                                        <v-text-field 
                                            label="First name" 
                                            v-model="tempAdmin.firstName">
                                        </v-text-field>
                                    </v-flex>
                                    <v-flex xs12 sm6 md6>
                                        <v-text-field 
                                            label="Last name" 
                                            v-model="tempAdmin.lastName">
                                        </v-text-field>
                                    </v-flex>
                                    <v-flex xs12 sm6 md6>
                                        <v-text-field 
                                            label="Username*" 
                                            v-model="tempAdmin.username"
                                            :error-messages="usernameErrors">
                                        </v-text-field>
                                    </v-flex>
                                    <v-flex xs12 sm6 md6>
                                        <v-text-field 
                                            label="Password*" 
                                            v-model="tempAdmin.password"
                                            :error-messages="passwordErrors">
                                        </v-text-field>
                                    </v-flex>
                                    <v-flex xs12>
                                        <v-text-field 
                                            label="Email*" 
                                            v-model="tempAdmin.email"
                                            :error-messages="emailErrors">
                                        </v-text-field>
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
                        </v-form>
                        </v-dialog>
                    </v-layout>

                    <v-layout row>
                        <v-flex>
                        <v-card>
                            <v-toolbar color="indigo" dark>
                            <v-toolbar-title class="text-xs-center">Airline administrators</v-toolbar-title>
                            <v-spacer></v-spacer>
                            </v-toolbar>
                                <v-list 
                                    two-line 
                                    class="indigo darken-2 scroll-y" 
                                    dark
                                    style="height: 400px">
                                    <template v-for="admin in this.airline.administrators"> 
                                        <v-list-tile :key="admin.id">
                                            <v-list-tile-content>
                                                <v-list-tile-title class="font-weight-bold">
                                                    <span class="font-weight-black">Username:</span> {{ admin.username }}
                                                    <span class="font-weight-black">Password:</span> {{ admin.password }}
                                                </v-list-tile-title>
                                                <v-list-tile-sub-title>{{ admin.firstName }}  {{admin.lastName}}</v-list-tile-sub-title>
                                                <v-list-tile-sub-title>{{ admin.email }}</v-list-tile-sub-title>
                                            </v-list-tile-content>
                                            <v-icon
                                                class="mr-1"
                                                @click="deleteAdmin(admin)">
                                                delete
                                            </v-icon>
                                        </v-list-tile>
                                    </template>
                                </v-list>
                        </v-card>
                        </v-flex>
                    </v-layout>
                    <div >
                        <v-btn block @click="submit">submit</v-btn>
                    </div>
                </v-form>
            </v-flex>
        </div>

    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, email, minLength } from 'vuelidate/lib/validators'

export default {
    mixins: [validationMixin],

    validations: {
        airline: {
            name: { required },
            location: {
                street: { required }
            }
        },
        tempAdmin: {
            username: { required, minLength: minLength(5) },
            password: { required, minLength: minLength(6) },
            email: { required, email }
        }
    },
      
    data(){
        return {
            airline: {
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
            tempAdmin: {}
        }
    },
    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.airline.name.$dirty) return errors
            !this.$v.airline.name.required && errors.push('Name is required.')
            return errors
        },
        addressErrors() {
            const errors = []
            if (!this.$v.airline.location.street.$dirty) return errors
            !this.$v.airline.location.street.required && errors.push('Address is required.')
            return errors
        },
        usernameErrors(){
            const errors = []
            if(!this.$v.tempAdmin.username.$dirty) return errors
            !this.$v.tempAdmin.username.minLength && errors.push('Username must be at least 5 characters long')
            !this.$v.tempAdmin.username.required && errors.push('Username is required.')
            return errors
        },
        passwordErrors () {
            const errors = []
            if (!this.$v.tempAdmin.password.$dirty) return errors
            !this.$v.tempAdmin.password.minLength && errors.push('Password must be at least 6 characters long')
            !this.$v.tempAdmin.password.required && errors.push('Password is required.')
            return errors
        },
        emailErrors () {
            const errors = []
            if (!this.$v.tempAdmin.email.$dirty) return errors
            !this.$v.tempAdmin.email.email && errors.push('E-mail invalid.')
            !this.$v.tempAdmin.email.required && errors.push('Email is required.')
            return errors
        }
    },

    methods:{
        submit () {
            this.$v.airline.$touch();

            if(!this.$v.airline.$invalid){
                if(!this.adminEmpty()){
                    this.addAirline();
                }else{
                    this.error = "At least one administrator must be added!";
                }
            }
        },
        addAirline: function(){
            this.$axios
            .post('http://localhost:8081/api/airlines', this.airline)
            .then(() => {
                this.success = true;
                setTimeout(() => {
                    this.success = false;
                }, 3000)
            }).catch(error => {
                this.error = error.response.data;
            });
        },
        close(){
            this.adminDialog = false;
            this.tempAdmin = Object.assign({}, {});
        }, 
        save(){
            this.$v.tempAdmin.$touch();
            if(!this.$v.tempAdmin.$invalid){
                if(!this.adminExists(this.tempAdmin.username)){
                    this.airline.administrators.push(this.tempAdmin);
                    this.close();
                }
                else{
                    this.adminError = "Administrator with that username already added";
                }
            }      
        },
        adminExists(usr){
            for(var i=0; i < this.airline.administrators.length; i++){
                if( this.airline.administrators[i].username == usr)
                    return true
            }
            return false
        },
        adminEmpty(){
            if(this.airline.administrators.length == 0)
                return true;
            else
                return false;
        },
        deleteAdmin(admin){
            this.airline.administrators.pop(admin);
        }
    }
}
</script>

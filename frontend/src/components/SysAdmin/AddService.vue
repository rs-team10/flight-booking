<!-- System admin -->
<template>
    <v-flex xs9 sm9 md9 id="add-service">
        <v-card class="mr-3 mt-2" style="height: 870px">
        <v-toolbar flat dense color="white">
            <v-toolbar-title class="indigo--text text-uppercase">
                <span class="font-weight-light">Register new service</span>
            </v-toolbar-title>  
        </v-toolbar>

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
                    
                    <v-form ref="adminForm">
                    <v-card>
                        <v-card-title>
                            <span class="headline">New administrator</span>
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

            <v-card-text>
            <v-form>
            <v-layout row>
                <v-flex xs7 sm7 md7>
                <v-layout column class="mr-3">
                    <v-text-field solo 
                        v-model.lazy="service.name"
                        :error-messages="nameErrors"
                        label="Name"
                        required>
                    </v-text-field>
                    <v-select class="mt-0"
                    :items="services"
                    v-model="pickedService"
                    label="Service"
                    solo dense 
                    ></v-select>

                    <GmapAutocomplete
                        class="custom-input-field mt-0 elevation-2"
                        @place_changed="getLocationData">
                    </GmapAutocomplete>
                    <GmapMap
                        :center="currentMapCenter"
                        :zoom="14"
                        map-type-id="terrain"
                        class="custom-map"
                        :options="{
                            zoomControl: true,
                            mapTypeControl: true,
                            scaleControl: false,
                            streetViewControl: false,
                            rotateControl: false,
                            fullscreenControl: false,
                            disableDefaultUi: false
                        }">
                        <GmapMarker
                            v-if="this.service.location.latitude"
                            :position="currentMapCenter"
                            :clickable="false"
                            :draggable="false"/>
                    </GmapMap>
                </v-layout>
                </v-flex>


            <v-flex xs5 sm5 md5>
            <v-layout column>
                <v-card>
                    <v-toolbar dense flat color="white">
                    <v-toolbar-title class="text-xs-center font-weight-light indigo--text">Administrators</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn icon @click="adminDialog = true">
                        <v-icon color="indigo">add</v-icon>
                    </v-btn>
                    </v-toolbar>
                        <v-list 
                            two-line 
                            class="scroll-y" 
                            style="height: 700px">
                            <template v-for="admin in this.service.administrators"> 
                                <v-list-tile :key="admin.id">
                                    <v-list-tile-content>
                                        <v-list-tile-title class="font-weight-medium indigo--text">
                                            <span class="font-weight-light">Username:</span> {{ admin.username }}
                                            <span class="font-weight-light ml-4">Password:</span> {{ admin.password }}
                                        </v-list-tile-title>
                                        <v-list-tile-sub-title>{{ admin.firstName }}  {{admin.lastName}}</v-list-tile-sub-title>
                                        <v-list-tile-sub-title>{{ admin.email }}</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                    <v-icon
                                        class="mr-1"
                                        color="indigo"
                                        @click="deleteAdmin(admin)">
                                        delete
                                    </v-icon>    
                                </v-list-tile>
                                <v-divider :key="admin.id"></v-divider>
                            </template>
                        </v-list>
                    </v-card>
                    <div>
                        <v-btn block flat outline color="indigo lighten-2" @click="submit">submit</v-btn>
                    </div>
            </v-layout>
            </v-flex>
            </v-layout>
        </v-form>
        </v-card-text>
        </v-card>
    </v-flex>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, email, minLength } from 'vuelidate/lib/validators'

export default {
    mixins: [validationMixin],

    validations: {
        service: {
            name: { required },
            location: { required }
        },
        tempAdmin: {
            username: { required, minLength: minLength(5) },
            password: { required, minLength: minLength(6) },
            email: { required, email }
        }
    },
      
    data(){
        return {
            service: {
                name: '',
                location: {},
                administrators: []
            },
            success: false,
            error: false,
            adminError: false,
            adminDialog: false,
            tempAdmin: {},
            currentMapCenter : {
                lat: 21.0,
                lng: 42.0
            },
            services: ['AIRLINE', 'HOTEL', 'RENT-A-CAR'],
            pickedService: null
        }
    },
    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.service.name.$dirty) return errors
            !this.$v.service.name.required && errors.push('Name is required.')
            return errors
        },
        addressErrors() {
            const errors = []
            if (!this.$v.service.location.street.$dirty) return errors
            !this.$v.service.location.street.required && errors.push('Address is required.')
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
            this.$v.service.$touch();

            if(!this.$v.service.$invalid){
                if(!this.adminEmpty()){
                    this.addservice();
                }else{
                    this.$swal("At least one administrator must be added!", "", "warning");
                }
            }
        },
        addservice(){
            if(this.pickedService == null){
                this.$swal("You must pick a service!", "", "warning")
                return;
            }
            if(this.pickedService == "AIRLINE")
                this.saveAirline()
            else if(this.pickedService == "HOTEL")
                this.saveHotel()
            else if(this.pickedService == "RENT-A-CAR")
                this.saveRentACar()
            
        },
        saveAirline(){
            var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};
            this.$axios
            .post('/api/airlines', this.service, yourConfig)
            .then(response => {
                this.$swal("Registration successfull", "Airline " + response.data.name + " registered successfully", "success");
            }).catch(error => {
                this.$swal(error.response.data, "", "error");
            });
        },
        saveHotel(){
            var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};
            this.$axios
            .post('/api/hotels', this.service, yourConfig)
            .then(response => {
                this.$swal("Registration successfull", "Hotel " + response.data.name + " registered successfully", "success");
            }).catch(error => {
                this.$swal(error.response.data, "", "error");
            });
        },
        saveRentACar(){
            var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};
            this.$axios
            .post('/api/rentACars', this.service, yourConfig)
            .then(response => {
                this.$swal("Registration successfull", "Rent-a-car service " + response.data.name + " registered successfully", "success");
            }).catch(error => {
                this.$swal(error.response.data, "", "error");
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
                    this.service.administrators.push(this.tempAdmin);
                    this.close();
                }
                else{
                    this.adminError = "Administrator with that username already added";
                }
            }      
        },
        adminExists(usr){
            for(var i=0; i < this.service.administrators.length; i++){
                if( this.service.administrators[i].username == usr)
                    return true
            }
            return false
        },
        adminEmpty(){
            if(this.service.administrators.length == 0)
                return true;
            else
                return false;
        },
        deleteAdmin(admin){
            this.service.administrators.pop(admin);
        },
        getLocationData(place) {
            if(place) {
                this.service.location = this.extractLocationData(place);
                this.currentMapCenter = {
                    lat: this.service.location.latitude,
                    lng: this.service.location.longitude
                }
            }
        },
        extractLocationData(place) {
            var locationToReturn = {};

            locationToReturn.latitude = place.geometry.location.lat();
            locationToReturn.longitude = place.geometry.location.lng();
            locationToReturn.street = place.name;
            locationToReturn.formattedAddress = place.formatted_address;

            place.address_components.forEach(element => {
                if(element.types.includes('country'))
                    locationToReturn.country = element.long_name;
                else if(element.types.includes('locality'))
                    locationToReturn.city = element.long_name;
            });
            return locationToReturn;
        }
    }
}
</script>

<style scoped>
.custom-input-field {
    position: relative;
    padding: 9px 0 9px 12px;
    margin: 3px 0 8px 0px;
    /*border: 2px solid #A0A0A0;*/
    outline: 0;
    background-color: transparent;
    font-family: 'Roboto', sans-serif;
    font-size: 15px;
    width: 100%;
}

.custom-map {
    position: relative;
    padding: 5px 0;
    width: 100%;
    height: 590px;
}
</style>

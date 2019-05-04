<template>
    <div id="airline-business-locations" class="ma-3">
        <v-flex xs12 sm6 offset-sm3>

            <v-layout row justify-center>
                <v-dialog v-model="showBusinessLocationDialog" persistent max-width="400px">
                    
                    <template v-slot:activator="{ on }">
                        <v-btn block v-on="on">Add Business Location</v-btn>
                    </template>

                    <v-form ref="businessLocationForm">
                        <v-card>
                            <v-card-title>
                                <span class="headline">New Business Location</span>
                            </v-card-title>

                            <v-card-text>
                                <v-container grid-list-md>
                                    <v-layout wrap>
                                        
                                        <v-flex>
                                            <v-text-field
                                                label="Destination Name"
                                                v-model="newBusinessLocation.name"
                                                :error-messages="nameErrors">
                                            </v-text-field>
                                        </v-flex>

                                        <v-flex>
                                            <v-text-field
                                                label="Airport Name"
                                                v-model="newBusinessLocation.airportName"
                                                :error-messages="airportNameErrors">
                                            </v-text-field>
                                        </v-flex>

                                        <v-flex>
                                            <v-text-field
                                                label="Airport IATA Code"
                                                mask="AAA"
                                                v-model="newBusinessLocation.airportCode"
                                                :error-messages="airportCodeErrors">
                                            </v-text-field>
                                        </v-flex>

                                        <v-flex>
                                            <GmapAutocomplete
                                                class="custom-input-field"
                                                @place_changed="getLocationData">
                                            </GmapAutocomplete>
                                        </v-flex>

                                    </v-layout>
                                </v-container>
                            </v-card-text>

                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn flat @click="close">Close</v-btn>
                                <v-btn flat @click="save">Save</v-btn>
                            </v-card-actions>

                        </v-card>
                    </v-form>

                </v-dialog>
            </v-layout>


            <v-layout row>
                <v-flex>
                    <v-card>
                        <v-toolbar>
                            <v-toolbar-title class="text-xs-center">Business Locations</v-toolbar-title>
                            <v-spacer></v-spacer>
                        </v-toolbar>

                        <v-list 
                            three-line
                            class="scroll-y" 
                            style="height: 700px">
                            <template v-for="businessLocation in this.businessLocations">
                                <v-list-tile :key="businessLocation.id" class="ma-2">
                                    <v-list-tile-content>
                                        <v-list-tile-title class="font-weight-bold">{{ businessLocation.name }}</v-list-tile-title>
                                        <v-list-tile-sub-title>{{ businessLocation.airportCode }}</v-list-tile-sub-title>
                                        <v-list-tile-sub-title>{{ businessLocation.airportName }}</v-list-tile-sub-title>
                                        <v-list-tile-sub-title>{{ businessLocation.location.postalCode }}</v-list-tile-sub-title>
                                    </v-list-tile-content>
                                    <v-icon
                                        class="mr-1"
                                        @click="deleteBusinessLocation(businessLocation)">
                                        delete
                                    </v-icon>
                                    <v-icon
                                        class="mr-1"
                                        @click="editBusinessLocation(businessLocation)">
                                        edit
                                    </v-icon>
                                </v-list-tile>
                            </template>
                        </v-list>
                    </v-card>
                </v-flex>
            </v-layout>
        </v-flex>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, minLength, maxLength } from 'vuelidate/lib/validators'

var yourConfig = {
    headers: { Authorization: "Bearer " + localStorage.getItem("token") }
}

export default {
    mixins: [validationMixin],

    validations: {
        newBusinessLocation: {
            name: { required },
            airportName: { required },
            airportCode: { required, minLength: minLength(3), maxLength: maxLength(3) },
        }
    },

    data() {
        return {
            showBusinessLocationDialog: false,
            businessLocations: [],
            newBusinessLocation: {
                location: {

                }
            }
        }
    },

    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.newBusinessLocation.name.$dirty) return errors
            !this.$v.newBusinessLocation.name.required && errors.push('Name is required.')
            return errors
        },
        airportNameErrors () {
            const errors = []
            if (!this.$v.newBusinessLocation.airportName.$dirty) return errors
            !this.$v.newBusinessLocation.airportName.required && errors.push('Airport Name is required.')
            return errors
        },
        airportCodeErrors () {
            const errors = []
            if (!this.$v.newBusinessLocation.airportCode.$dirty) return errors
            !this.$v.newBusinessLocation.airportCode.minLength && errors.push("Airport Code must be 3 characters long.")
            !this.$v.newBusinessLocation.airportCode.maxLength && errors.push("Airport Code must be 3 characters long.")
            !this.$v.newBusinessLocation.airportCode.required && errors.push('Airport Code is required.')
            return errors
        },
    },

    methods: {
        getLocationData: function (place) {
            
            if(place) {
                
                this.newBusinessLocation.location.latitude = place.geometry.location.lat();
                this.newBusinessLocation.location.longitude = place.geometry.location.lng();
                this.newBusinessLocation.location.street = place.name;
                this.newBusinessLocation.location.city = place.vicinity;
                this.newBusinessLocation.location.country = "Unknown";

                this.newBusinessLocation.location.postalCode = place.formatted_address;

                place.address_components.forEach(element => {
                    
                    if(element.types.includes('country')) {
                        this.newBusinessLocation.location.country = element.long_name;
                    }
                });
            }
        },
        close() {
            this.$v.newBusinessLocation.$reset();
            this.showBusinessLocationDialog = false;
            this.newBusinessLocation = {
                location: {  
                }
            }
        },
        save() {

            this.$v.newBusinessLocation.$touch();
            if(!this.$v.newBusinessLocation.$invalid) {

                if(!this.newBusinessLocation.location.latitude) {
                    this.$swal("Error", "Location is required.", 'warning');
                } else {
                    
                    // TODO POST kreirane business location na server

                    this.businessLocations.push(this.newBusinessLocation);
                    this.close();
                }
            }
        },
        deleteBusinessLocation(businessLocation) {

            const index = this.businessLocations.indexOf(businessLocation);
            this.businessLocations.splice(index, 1);

            // TODO DELETE izabrane business location sa servera
        },
        editBusinessLocation(businessLocation) {

            // TODO izmena izabrane business location i cuvanje na serveru
        }

    },
    
    created() {
        // TODO GET svih business locations date aviokompanije sa servera
    }
    
}
</script>

<style scoped>

.custom-input-field {
    position: relative;
    padding: 5px 0;
    border: 0;
    outline: 0;
    margin-top: 10px;
    border-bottom: 1px solid gray;
    background-color: transparent;
    font-family: inherit;
    font-size: inherit;
    width: 100%;
}

.custom-input-field:focus {
  border-bottom: 2px solid #5C6BC0;
}

</style>
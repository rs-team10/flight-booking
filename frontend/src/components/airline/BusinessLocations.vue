<template>
    <div id="airline-business-locations" class="ma-3">
        <v-flex>
            
            <v-toolbar>
                <v-toolbar-title>Business Locations</v-toolbar-title>
                <v-spacer></v-spacer>

                <v-dialog v-model="showBusinessLocationDialog" persistent max-width="400px">
                    
                    <template v-slot:activator="{ on }">
                        <v-btn v-on="on">New Business Location</v-btn>
                    </template>

                    <v-card>
                        <v-card-title>
                            <span class="headline">Add Business Location</span>
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
                                            :value="newBusinessLocation.location.formattedAddress"
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
                </v-dialog>
            </v-toolbar>

            <v-list 
                three-line
                class="scroll-y" 
                style="height: 300px">
                <template v-for="businessLocation in this.businessLocations">
                    <v-list-tile :key="businessLocation.airportCode" class="ma-2">
                        <v-list-tile-content>
                            <v-list-tile-title class="font-weight-bold">{{ businessLocation.name }}</v-list-tile-title>
                            <v-list-tile-sub-title class="font-weight-bold">{{ "Code: " + businessLocation.airportCode }}</v-list-tile-sub-title>
                            <v-list-tile-sub-title class="font-weight-bold">{{ "Airport Name: " + businessLocation.airportName }}</v-list-tile-sub-title>
                            <v-list-tile-sub-title>{{ "Address: " + businessLocation.location.formattedAddress }}</v-list-tile-sub-title>
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

        </v-flex>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, minLength, maxLength } from 'vuelidate/lib/validators'

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
            if(place)
                this.newBusinessLocation.location = this.extractLocationData(place);
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

                    var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

                    this.$axios.put('http://localhost:8080/api/airlines/addBusinessLocation/', this.newBusinessLocation, yourConfig)
                        .then((data) => {
                            this.$swal('Success', 'Business location added successfuly', 'success');
                            this.businessLocations.push(this.newBusinessLocation);
                            this.close();
                        }).catch((error) => {
                            this.$swal("Error", error.response.data.message, 'error');
                        });
                }
            }
        },
        deleteBusinessLocation: function(businessLocation) {

            this.$swal({
                title: 'Are you sure?',
                text: 'You can\'t revert your action',
                type: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes',
                cancelButtonText: 'No',
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                showCloseButton: true,
            }).then((result) => {

                if(result.value) { 
                    const index = this.businessLocations.indexOf(businessLocation);
                    this.businessLocations.splice(index, 1);

                    var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

                    this.$axios.put('http://localhost:8080/api/airlines/removeBusinessLocation/', businessLocation, yourConfig)
                        .then((data) => {
                            this.$swal('Success', 'Business location removed successfuly', 'success');
                        }).catch((error) => {
                            this.$swal("Error", error.response.data.message, 'error');
                        });
                }
            });
        },
        editBusinessLocation(businessLocation) {
            // TODO: Izmena izabrane business location i cuvanje na serveru [OPCIONA FUNKCIONALNOST]
        }

    },
    created() {

        var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

        this.$axios.get('http://localhost:8080/api/airlines/getBusinessLocations', yourConfig)
            .then((response) => {
                this.businessLocations = response.data;
            }).catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });
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
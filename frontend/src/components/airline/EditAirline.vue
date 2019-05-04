<template>
    <v-flex xs12 sm6 offset-sm3>
        <h2>Edit Airline profile</h2>
        <form>

            <v-text-field
                v-model="airline.name"
                :error-messages="nameErrors"
                label="Airline Name"
                required
                @input="$v.airline.name.$touch()"
                @blur="$v.airline.name.$touch()"
            ></v-text-field>

            <v-textarea
                v-model="airline.description"
                :error-messages="descriptionErrors"
                auto-grow
                box
                label="Promo Description"
                required
                @input="$v.airline.description.$touch()"
                @blur="$v.airline.description.$touch()"
            ></v-textarea>
            <!--
            <v-text-field
                v-model="airline.address"
                :error-messages="addressErrors"
                label="Headquarters"
                required
                @input="$v.airline.address.$touch()"
                @blur="$v.airline.address.$touch()"
            ></v-text-field>
            
            <vuetify-google-autocomplete
                id="location"
                classname="form-control"
                label="Headquarters Location"
                v-on:placechanged="getLocationData"
                required
            >
            </vuetify-google-autocomplete>
           -->

            <v-text-field
                v-model="formattedAddress"
                label="Current headquarters address"
                disabled
            ></v-text-field>
            
            <GmapAutocomplete
                class="custom-input-field"
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
                }"
            >
                 
                <GmapMarker
                    v-if="this.airline.location.latitude"
                    :position="currentMapCenter"
                    :clickable="false"
                    :draggable="false"
                />
                
            </GmapMap>

            <v-btn @click="editAirline">submit</v-btn>

        </form>
    </v-flex>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, minLength } from 'vuelidate/lib/validators'

var MOCK_ID = 1;                                // TODO: This is hardcoded. Change!

export default {
    mixins: [validationMixin],

    data () {
        return {
            airline: {
                id: MOCK_ID,                            
                name: '',
                description: '',
                location: {}
            },
            currentMapCenter : {
                lat: 0.0,
                lng: 0.0
            },
            formattedAddress : ""
        }
    },

    validations: {
        airline: {
            name: { required, minLength: minLength(6) },
            description: { required, minLength: minLength(60) },
            location: { required }
        }
    },

    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.airline.name.$dirty) return errors
            !this.$v.airline.name.minLength && errors.push('Name must be at least 6 characters long')
            !this.$v.airline.name.required && errors.push('Name is required.')
            return errors
        },
        descriptionErrors () {
            const errors = []
            if (!this.$v.airline.description.$dirty) return errors
            !this.$v.airline.description.minLength && errors.push('Description must be at least 60 characters long')
            !this.$v.airline.description.required && errors.push('Description is required.')
            return errors
        },
        locationErrors () {
            const errors = []
            if (!this.$v.airline.location.$dirty) return errors
            !this.$v.airline.location.required && errors.push('Location is required.')
            return errors
        },
    },

    methods: {
        editAirline: function() {
            this.$v.$touch()

            if(!this.$v.$invalid) {

                var yourConfig = {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                };

                this.$axios.put('http://localhost:8080/api/airlines/', this.airline, yourConfig).then((data) => {
                    this.$swal('Success', 'Airline profile edited successfuly', 'success');
                });
            }
        },
        getLocationData: function (place) {
            
            if(place) {
                
                this.airline.location.latitude = place.geometry.location.lat();
                this.airline.location.longitude = place.geometry.location.lng();
                this.airline.location.street = place.name;
                this.airline.location.city = place.vicinity;
                this.airline.location.country = "Unknown";

                this.airline.location.postalCode = place.formatted_address;

                place.address_components.forEach(element => {
                    
                    if(element.types.includes('country')) {
                        this.airline.location.country = element.long_name;
                    }
                });

                this.currentMapCenter = {
                    lat: this.airline.location.latitude,
                    lng: this.airline.location.longitude
                }
            }
        }
    },

    created() {

        var yourConfig = {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        };

        this.$axios.get('http://localhost:8080/api/airlines/' + this.airline.id, yourConfig).then((response) => {
            this.airline = response.data;
            this.currentMapCenter = {
                lat: this.airline.location.latitude,
                lng: this.airline.location.longitude
            };
            this.formattedAddress = this.airline.location.postalCode;
        }).catch(function(error) {
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
    background-color: transparent;
    font-family: 'Roboto', sans-serif;
    font-size: 1em;
    width: 100%;
}

.custom-map {
    position: relative;
    padding: 5px 0;
    width: 100%;
    height: 300px;
}

</style>
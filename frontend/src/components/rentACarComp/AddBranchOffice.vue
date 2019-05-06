<template>
    <v-flex xs12>
        <h2>Add branch office</h2>
        <form>

            <!--
            <v-text-field
                v-model="formattedAddress"
                label="Current headquarters address"
                disabled
            ></v-text-field>
            -->
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
                    v-if="this.branchOffice.location.latitude"
                    :position="currentMapCenter"
                    :clickable="false"
                    :draggable="false"
                />
                
            </GmapMap>

            <v-btn @click="addBranchOffice">submit</v-btn>

        </form>
    </v-flex>
</template>

<script>

import  required from 'vuelidate/lib/validators'

export default {
    props: ['rentACarId'],
    data () {
        return {
            branchOffice: {
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
        branchOffice: {
            location: { required }
        }
    },
    /*
    computed: {
 
        locationErrors () {
            const errors = []
            if (!this.$v.branchOffice.location.$dirty) return errors
            !this.$v.branchOffice.location.required && errors.push('Location is required.')
            return errors
        }
    },
    */
    methods: {
        addBranchOffice: function() {
            /*
            this.$v.$touch()
            if(!this.$v.$invalid) {
                
                var yourConfig = {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                };
                */
                this.$axios.post('http://localhost:8080/api/branchOffice/'+this.rentACarId, this.branchOffice) //, yourConfig
                .then(response => {
                    
                    this.$swal.fire({
                        title: 'Success', 
                        html: response.data,
                        type: 'success',
                        showConfirmButton: false,
                        timer: 2000
                    }
                )
                .then((result) => {
                    if (result.dismiss === this.$swal.DismissReason.timer) {
                        this.$router.go(-1);
                    }
                });
                
                }).catch((error) => {
                    this.$swal("Error", error.response.data.message, 'error');
                });
            //}
        },


        getLocationData: function (place) {
            
            if(place) {
                this.branchOffice.location = this.extractLocationData(place);
                this.currentMapCenter = {
                    lat: this.branchOffice.location.latitude,
                    lng: this.branchOffice.location.longitude
                }
            }
        },
        extractLocationData(place) {
            var locationToReturn = {};
            locationToReturn.id = this.branchOffice.location.id;
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
/* ,
    created() {
        #TODO posalji lokaciju iz rentacara u headq        
    }
*/
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
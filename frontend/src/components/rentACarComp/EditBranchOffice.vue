<template>
    <v-flex xs12 @mouseover="focusDialog">
        <h2>Edit branch office</h2>
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
                :center="this.currentMapCenter"
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
                    v-if="this.branchOfficeInc.location.latitude"
                    :position="this.currentMapCenter"
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

var yourConfig = {headers: { Authorization: "Bearer " + localStorage.getItem("token")}}

export default {
    props:['branchOfficeInc'
            ],

    data () {
        return {
            branchOffice: {
                location: {}
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

        focusDialog: function(){
            this.currentMapCenter.lat = this.branchOfficeInc.location.latitude;
            this.currentMapCenter.lng = this.branchOfficeInc.location.longitude
        },

        addBranchOffice: function() {
                this.branchOffice.id = this.branchOfficeInc.id;
                this.$axios.put('/api/branchOffice/', this.branchOffice, yourConfig)
                .then(response => {
                    
                    this.$swal("Yoohoo!",response.data, 'success');
                    this.$router.go(0);
                })
                .catch((error) => {
                    this.$swal("Error", error.response.data, 'error');
                });
        },


        getLocationData: function (place) {
            
            if(place) {
                this.branchOffice.location = this.extractLocationData(place);
                this.branchOfficeInc.location.latitude = this.branchOffice.location.latitude;
                this.branchOfficeInc.location.longitude =   this.branchOffice.location.longitude;
                
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
    },
    computed:{
        
        currentMapCenter : function() {
                return  {
                    lat: this.branchOfficeInc.location.latitude,
                    lng: this.branchOfficeInc.location.longitude
                }
            }
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
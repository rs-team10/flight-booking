<template>
    <v-flex class='d-flex'>

        <v-layout class="airline-data" column align-center ma-4>
            <h2 class="text-xs-center">{{ airline.name }}</h2>
            <h3 class="text-xs-center mb-1">{{ formattedAddress }}</h3>
            <h4 class="text-xs-center ma-1">{{ airline.description }}</h4>
            <div class="text-xs-center">
                <v-btn @click="redirectToEdit()" round small flat color="primary" dark>Edit Airline Profile</v-btn>
            </div>
        </v-layout>

        <v-flex>
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
        </v-flex>

     </v-flex>
</template>

<script>

var MOCK_ID = 1;                                // TODO: This is hardcoded. Change!

export default {
    data() {
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
    methods: {
        redirectToEdit() {
            this.$router.push('editAirlineProfile');
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

.custom-map {
    position: relative;
    padding: 5px 0;
    width: 100%;
    height: 300px;
}

</style>
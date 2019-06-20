<template>
<div>
    <v-toolbar color="white" flat dense>
        <v-spacer></v-spacer>
        <v-btn icon @click="$emit('closeProfileDialog')"><v-icon>close</v-icon></v-btn>
    </v-toolbar>

    <v-card-text>
        <v-layout row wrap>
            
            <v-flex xs6 sm6 md6>
            <v-card flat height="300px">

                <v-card-text>
                    <span class="display-2 font-weight-light">{{selectedAirline.airlineProfileDTO.name}}</span><br>
                    <v-icon big class="mr-1 mt-3">location_on</v-icon>
                    <span class="blue--text text--darken-2 title">{{ selectedAirline.airlineProfileDTO.location.formattedAddress }}</span>
                    <div class="d-flex mt-2">
                    <v-rating
                        :value="selectedAirline.airlineProfileDTO.averageFeedback"
                        color="amber"
                        half-increments
                        dense
                        readonly
                        size="35"
                    ></v-rating>
                    </div>
                </v-card-text>

                <v-card-actions class="indigo--text font-weight-light title ml-2 pt-0">
                    {{ selectedAirline.airlineProfileDTO.description }}
                </v-card-actions>
            </v-card>
            </v-flex>

            <v-flex xs6 sm6 md6>
                <v-card flat>
                <v-card-text>
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
                        :position="currentMapCenter"
                        :clickable="false"
                        :draggable="false"/>
                </GmapMap>
                </v-card-text>
                </v-card>
            </v-flex>

            <v-flex xs12 sm12 md12>
                <v-card flat>
                <v-toolbar flat>
                    <v-toolbar-title class="text-uppercase indigo--text font-weight-light">Business locations</v-toolbar-title>
                </v-toolbar>
                </v-card>
                <v-layout column style="height: 300px">
                <v-flex style="overflow: auto">
                    <v-list 
                        three-line
                        class="scroll-y" 
                        style="height: 300px">
                        <template v-for="businessLocation in selectedAirline.destinations">
                            <v-list-tile :key="businessLocation.airportCode" class="ma-2">
                                <v-list-tile-content>
                                    <v-list-tile-title class="font-weight-bold">{{ businessLocation.name }}</v-list-tile-title>
                                    <v-list-tile-sub-title class="font-weight-bold">{{ "Code: " + businessLocation.airportCode }}</v-list-tile-sub-title>
                                    <v-list-tile-sub-title class="font-weight-bold">{{ "Airport Name: " + businessLocation.airportName }}</v-list-tile-sub-title>
                                    <v-list-tile-sub-title>{{ "Address: " + businessLocation.location.formattedAddress }}</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>
                        </template>
                    </v-list>
                </v-flex>
                </v-layout>
            </v-flex>

            <v-flex xs12 sm12 md12>
                <v-card flat>
                <v-toolbar flat>
                    <v-toolbar-title class="text-uppercase indigo--text font-weight-light">Quick flight reservations</v-toolbar-title>
                </v-toolbar>
                </v-card>
                <v-layout column style="height: 300px">
                <v-flex style="overflow: auto">
                    <v-list 
                        three-line
                        class="scroll-y" 
                        style="height: 300px">
                        <template v-for="quickReservation in selectedAirline.quickFlightReservations">
                            <v-list-tile :key="quickReservation.id" class="ma-2">
                                <v-list-tile-content>
                                    <v-list-tile-title class="font-weight-bold">{{ "Flight no. " + quickReservation.flightNumber + "  from " + quickReservation.departure + " to " + quickReservation.destination }}</v-list-tile-title>
                                    <v-list-tile-sub-title class="font-weight-bold">{{ "Date: " + quickReservation.date + " Seat: " + quickReservation.seatNumber }}</v-list-tile-sub-title>
                                    <v-list-tile-sub-title>{{ "Original Price: " + quickReservation.originalPrice + "€ Discount: " + quickReservation.discount + "%"}}</v-list-tile-sub-title>
                                </v-list-tile-content>
                                <v-btn flat @click="reserveFlight(quickReservation)">BOOK FLIGHT</v-btn>
                            </v-list-tile>
                        </template>
                    </v-list>
                </v-flex>
                </v-layout>
            </v-flex>

            </v-layout>

    </v-card-text>
</div>
</template>

<script>
export default {
    props: ['selectedAirline'],

    data(){
        return{
        }
    },
    computed:{
        currentMapCenter() {
            return {
                lat: this.selectedAirline.airlineProfileDTO.location.latitude,
                lng: this.selectedAirline.airlineProfileDTO.location.longitude
            }
        }
    },
    methods: {
        reserveFlight(quickReservation) {
            // TODO: Slanje zahteva za quick reservation na backend

            var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") } }
            this.$axios.post('http://localhost:8080/api/reservations/reserveQuickFlight', quickReservation, yourConfig)
                .then(response => {
                                        
                    this.$swal({
                            title: 'Success', 
                            html: 'Flight reservation successfull',
                            type: 'success',
                            showConfirmButton: false,
                            timer: 2000,
                            allowOutsideClick: false
                        }).then((result) => {
                            this.$router.go(-1);
                        });

                }).catch((error) => {
                   
                    this.$swal("Error", "Unsuccessfull reservation. The selected quick reservation has already been sold.", 'error');
                });
        }
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

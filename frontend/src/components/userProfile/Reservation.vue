<template>
    <v-card>
        <v-flex>
            <v-layout column justify-center fill-height>
                <v-layout row justify-space-between>
                    <p class="headline font-weight-light ml-3">AIRLINE: </p>
                    <p class="headline font-weight-light font-italic mr-3"><b>{{overview.airlineName}}</b></p>
                    <v-rating
                        v-if="showRate"
                        v-model="overview.ratingAirline"
                        color="yellow darken-3"
                        background-color="grey darken-1"
                        empty-icon="$vuetify.icons.ratingFull"
                        half-increments
                        hover>
                    </v-rating>
                    
                </v-layout>
                <v-layout row justify-space-between>
                    <p class="headline font-weight-light ml-3">FLIGHT: </p>
                    <p class="headline font-weight-light font-italic mr-3"> <b>{{overview.departureName}}</b> to <b>{{overview.destinationName}}</b></p>
                    <v-rating
                        v-if="showRate"
                        v-model="overview.ratingFlight"
                        color="yellow darken-3"
                        background-color="grey darken-1"
                        empty-icon="$vuetify.icons.ratingFull"
                        half-increments
                        hover>
                    </v-rating>
                </v-layout>
                <v-divider/>

                <v-layout row justify-space-between v-if="allowHotel">
                    <p class="headline font-weight-light ml-3">HOTEL:</p>
                    <p class="headline font-weight-light font-italic mr-3"><b>{{overview.hotelName}}</b></p>
                    <v-rating
                        v-if="showRate"
                        v-model="overview.ratingHotel"
                        color="yellow darken-3"
                        background-color="grey darken-1"
                        empty-icon="$vuetify.icons.ratingFull"
                        half-increments
                        hover>
                    </v-rating>
                </v-layout>
                <v-layout row justify-space-between  v-if="allowHotel">
                    <p class="headline font-weight-light ml-3">ROOM TYPE: </p>
                    <p class="headline font-weight-light font-italic mr-3"><b>{{overview.roomTypeName}}</b></p>
                    <v-rating
                        v-if="showRate"
                        v-model="overview.ratingRoomType"
                        color="yellow darken-3"
                        background-color="grey darken-1"
                        empty-icon="$vuetify.icons.ratingFull"
                        half-increments
                        hover>
                    </v-rating>
                    <v-btn flat icon color="error" v-else  @click='declineRoomReservation'><v-icon>delete</v-icon></v-btn>
                </v-layout>
                <v-divider/>
                
                <v-layout row justify-space-between  v-if="allowRentACar">
                    <p class="headline font-weight-light ml-3">RENT A CAR: </p>
                    <p class="headline font-weight-light font-italic mr-3"><b>{{overview.rentACarName}}</b></p>
                    <v-rating
                        v-if="showRate"
                        v-model="overview.ratingRentACar"
                        color="yellow darken-3"
                        background-color="grey darken-1"
                        empty-icon="$vuetify.icons.ratingFull"
                        half-increments
                        hover>
                    </v-rating>
                </v-layout>
                <v-layout row justify-space-between  v-if="allowRentACar">
                    <p class="headline font-weight-light ml-3">VEHICLE: </p>
                    <p class="headline font-weight-light font-italic mr-3"><b>{{overview.manufacturer}} {{overview.model}}</b></p>
                    <v-rating
                        v-if="showRate"
                        v-model="overview.ratingVehicle"
                        color="yellow darken-3"
                        background-color="grey darken-1"
                        empty-icon="$vuetify.icons.ratingFull"
                        half-increments
                        hover>
                    </v-rating>
                    <v-btn flat icon color="error" v-else @click='declineVehicleReservation'><v-icon>delete</v-icon></v-btn>
                    
                </v-layout>
                <v-layout row v-if="showRate">
                    <v-spacer/>
                    <v-btn @click="confirmAct">Confirm</v-btn>
                </v-layout>
            </v-layout>
        </v-flex>
    </v-card>
</template>



<script>
var yourConfig = {headers: {Authorization: "Bearer " + localStorage.getItem("token")}};
export default {
    props:[
        'overview',
        'rate'
    ],
    data(){
        return{
             
        }
    },
    methods:{
        confirmAct : function()
        {
            this.$axios.post('http://localhost:8080/api/setReview/', this.overview, yourConfig).then((response) => {
                this.$swal("Yoohoo!",response.data, 'success');
            }).catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });
        },
        declineRoomReservation : function()
        {

            this.$axios.post('http://localhost:8080/api/declineRoomReservation/', this.overview, yourConfig).then((response) => {
                this.$swal("Yoohoo!",response.data, 'success');
            }).catch((error) => {
                this.overview.hotelId=null;
                this.$swal("Error", error.response.data.message, 'error');
            });
        },
        declineVehicleReservation : function()
        {
            var yourConfig = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            };
            this.$axios.post('http://localhost:8080/api/declineVehicleReservation', this.overview, yourConfig).then((response) => {
                this.overview.rentACarId==null;
                this.$swal("Yoohoo!",response.data, 'success');

            }).catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });
        }
        
    
    },
    computed:{
        showRate(){
            return this.rate != null;
        },
        allowHotel(){
            return this.overview.hotelId != null && this.overview.roomTypeId != null;
        },
        allowRentACar(){
            return this.overview.rentACarId != null && this.overview.vehicleId != null;
        }

    }
}
</script>



<template>
    <v-layout 
        column 
        align-start
        id="scroll-target"
        style="max-height: 400px"
        class="scroll-y">


        <v-list two-line style="width:100%;">
        <template v-for="(reservation, index) in this.reservations" >

            <v-divider
                v-if="index!=0"
                :key="index">
            </v-divider>

            <v-list-tile
            :key="reservation.reservationId + reservation.departureName"
            >
                <v-list-tile-avatar>
                    <v-icon>flight</v-icon>
                </v-list-tile-avatar>

                <v-list-tile-content>
                    <v-list-tile-title> <i><b>{{reservation.departureName}}</b> to <b>{{reservation.destinationName}}</b></i></v-list-tile-title>
                    <v-list-tile-sub-title>{{reservation.departureDate.substring(0,10)}}</v-list-tile-sub-title>
                </v-list-tile-content>

                <v-list-tile-action>  
                    <v-icon color="yellow" @click="">star</v-icon>  
                </v-list-tile-action>

            </v-list-tile> 
        </template>
        </v-list>

    </v-layout>  
</template>


<script>
export default {

    data(){
        return{

            reservation:{
                reservationId   : '',
                    
                flightId        : '',
                departureName   : '',
                destinationName : '',
                departureDate   : '',
                airlineId       : '',

                roomTypeId  : '',
                hotelId     : '',

                vehicleId   : '',
                rentACarId  : ''

            },

            
            reservations:[],

            reservationsTest:[

                {
                    reservationId   : 1,
                   
                    flightId        : 1,
                    departureName   : 'Berlin',
                    destinationName : 'Belgrade',
                    departureDate   : '2019-06-30',
                    airlineId       : 1,
            
                    roomTypeId      : 1,
                    hotelId         : 1,

                    vehicleId       : 1,
                    rentACarId      : 1
                   
                },
                {
                    reservationId   : 2,
                    
                    flightId        : 2,
                    departureName   : 'Sidney',
                    destinationName : 'Belgrade',
                    departureDate   : '2019-07-07',
                    airlineId       : 1,

                    roomTypeId      : 2,
                    hotelId         : 1,

                    vehicleId       : 2,
                    rentACarId      : 1
                   
                },
                {
                    reservationId   : 3,
                    
                    flightId        : 3,
                    departureName   : 'Viena',
                    destinationName : 'Belgrade',
                    departureDate   : '2019-07-14',
                    airlineId       : 1,

                    roomTypeId  : 3,
                    hotelId     : 1,

                    vehicleId   : 3,
                    rentACarId  : 1
                   
                },
                {
                    reservationId   : 4,
                    
                    flightId        : 4,
                    departureName   : 'Zagreb',
                    destinationName : 'Belgrade',
                    departureDate   : '2019-07-21',

                    airlineId       : 1,
                
                    roomTypeId      : 4,
                    hotelId         : 1,
            
                    vehicleId       : 4,
                    rentACarId      : 1
                    
                },
                {
                    reservationId   : 5,
                    
                    flightId        : 5,
                    departureName   : 'London',
                    destinationName : 'Belgrade',
                    departureDate   : '2019-07-28',
                    airlineId       : 1,

                    roomTypeId  : 5,
                    hotelId     : 1,
                
                    vehicleId   : 5,
                    rentACarId  : 1
                    
                },
            ]
        }
    },
    created(){
        var yourConfig = {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        };

        this.$axios.get('http://localhost:8080/api/reservationHistory/', yourConfig).then((response) => {
            this.reservations = response.data;
        }).catch((error) => {
            this.$swal("Error", error.response.data.message, 'error');
        });
    }
    
}
</script>

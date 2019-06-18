<template>
    <div id="quick-flight-reservations" class="ma-3">
        <v-flex>

            <v-toolbar>
                <v-toolbar-title>Quick flight reservations</v-toolbar-title>
                <v-spacer></v-spacer>
            </v-toolbar>

            <v-list 
                three-line
                class="scroll-y" 
                style="height: 300px">
                <template v-for="reservation in this.quickFlightReservations">
                    <v-list-tile :key="reservation.id" class="ma-2">
                        <v-list-tile-content>
                            <v-list-tile-title class="font-weight-bold">{{ "Flight no. " + reservation.flightNumber + "  from " + reservation.departure + " to " + reservation.destination }}</v-list-tile-title>
                            <v-list-tile-sub-title class="font-weight-bold">{{ "Date: " + reservation.date + " Seat: " + reservation.seatNumber }}</v-list-tile-sub-title>
                            <v-list-tile-sub-title>{{ "Original Price: " + reservation.originalPrice + "€ Discount: " + reservation.discount + "%"}}</v-list-tile-sub-title>
                        </v-list-tile-content>
                        <v-icon
                            class="mr-1"
                            @click="deleteQuickFlightReservation(reservation)">
                            delete
                        </v-icon>
                    </v-list-tile>
                </template>
            </v-list>

        </v-flex>
    </div>
</template>


<script>
export default {
    data() {
        return {
            quickFlightReservations: []
        }
    },
    methods: {
        deleteQuickFlightReservation: function(quickFlightReservation) {
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
                    const index = this.quickFlightReservations.indexOf(quickFlightReservation);
                    this.quickFlightReservations.splice(index, 1);

                    var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

                    this.$axios.put('http://localhost:8080/api/airlines/deleteQuickFlightReservation/', quickFlightReservation, yourConfig)
                        .then((data) => {
                            this.$swal('Success', 'Quick flight reservation deleted successfuly', 'success');
                        }).catch((error) => {
                            this.$swal("Error", error.response.data.message, 'error');
                        });
                }
            });
        }
    },
    created() {

        var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

        this.$axios.get('http://localhost:8080/api/airlines/getQuickFlightReservations', yourConfig)
            .then((response) => {
                this.quickFlightReservations = response.data;
            }).catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });
    }
}
</script>

<template>
    <v-layout row justify-center>
        <v-dialog v-model="showDialog" persistent max-width="800px">
            <v-card>

                <v-toolbar dark color="primary">
                    <v-btn icon dark @click="closeThisDialog()">
                        <v-icon>close</v-icon>
                    </v-btn>
                    <v-toolbar-title>Create quick flight reservation</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-toolbar-items>
                        <v-btn dark flat @click="submitChanges()">Save</v-btn>
                    </v-toolbar-items>
                </v-toolbar>

                <v-card-text>
                    <v-container grid-list-md>
                        <v-layout row align-start justify-center>
                            <v-item-group multiple>

                                <table v-if="lastRowIsFull">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th v-for="k in columns" :key="k">
                                                {{ String.fromCharCode(64 + k) }}
                                            </th>
                                        </tr>
                                    </thead>
                                    <tr v-for="i in rows" :key="i">
                                        <td>{{ i }}</td>

                                        <td v-for="j in columns" :key="j">
                                            <v-item>
                                                <v-card
                                                    
                                                    hover
                                                    :selected="seats[(i - 1) * columns + j - 1].isSelected"
                                                    :color="calculateColor((i - 1) * columns + j - 1)"
                                                    class="d-flex align-center"
                                                    dark
                                                    height="50"
                                                    width="50"
                                                    @click="clickOnSeat((i - 1) * columns + j - 1)"
                                                >
                                                    <v-scroll-y-transition>
                                                        <div class="text-xs-center">
                                                            {{ seats[(i - 1) * columns + j - 1].red + String.fromCharCode(64 + seats[(i - 1) * columns + j - 1].kolona) }}
                                                        </div>
                                                    </v-scroll-y-transition>
                                                </v-card>
                                            </v-item>
                                        </td>
                                    </tr>
                                </table>

                            </v-item-group>
                            <v-layout column align-start justify-start fill-height>

                                <v-layout row class="mx-4">
                                        <v-text-field
                                            v-model="discount"
                                            label="Discount (%)"
                                            prepend-icon="trending_down"
                                            :error-messages="discountErrors"
                                            @input="$v.discount.$touch()"
                                            @blur="$v.discount.$touch()"
                                            >
                                        </v-text-field>
                                </v-layout>

                                <h2 class="ma-4">Legend:</h2>
                                <v-layout row class="mx-4">
                                    <v-card color="deep-purple lighten-1" class="ma-2" dark height="30" width="30" ma-4></v-card>
                                    <v-card flat class="ma-2" height="30" width="250">First class seats</v-card>
                                </v-layout>
                                <v-layout row class="mx-4">
                                    <v-card color="blue lighten-1" class="ma-2" dark height="30" width="30" ma-4></v-card>
                                    <v-card flat class="ma-2" height="30" width="250">Business class seats</v-card>
                                </v-layout>
                                <v-layout row class="mx-4">
                                    <v-card color="grey darken-3" class="ma-2" dark height="30" width="30" ma-4></v-card>
                                    <v-card flat class="ma-2" height="30" width="250">Economy class seats</v-card>
                                </v-layout>
                                <v-layout row class="mx-4">
                                    <v-card color="green" class="ma-2" dark height="30" width="30" ma-4></v-card>
                                    <v-card flat class="ma-2" height="30" width="250">Selected seats</v-card>
                                </v-layout>
                                <v-layout row class="mx-4">
                                    <v-card color="red lighten-4" class="ma-2" dark height="30" width="30" ma-4></v-card>
                                    <v-card flat class="ma-2" height="30" width="250">Inactive seats</v-card>
                                </v-layout>

                            </v-layout>
                        </v-layout>
                    </v-container>
                </v-card-text>

            </v-card>
        </v-dialog>
    </v-layout>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, numeric, between } from 'vuelidate/lib/validators'

export default {

    props: {
        showDialog: Boolean,
        receivedSeats: Array,
        flight: Number
    },
    mixins: [validationMixin],

    data() {
        return {
            seats: [],
            showThisDialog: false,
            selectedSeatsCount: 0,
            selectedSeatId: 0,
            discount: 0,
        }
    },
    validations: {
        discount: { required, numeric, between: between(1, 80) },
    },
    computed: {
        discountErrors () {
            const errors = []
            if (!this.$v.discount.$dirty) return errors
            !this.$v.discount.numeric && errors.push('Discount value invalid.')
            !this.$v.discount.required && errors.push('Discount is required.')
            !this.$v.discount.between && errors.push('Discount out of specified range.')
            return errors
        },
        rows() {
            if(!this.lastSeat)
                return 0;
            return this.lastSeat.red;
        },
        columns() {
            // Best algorithm ever :)
            for(var i = 0; i < this.seats.length; i++) {
                if(this.seats[i].red == 2) {
                    return this.seats[i - 1].kolona;
                }
            }
            return this.seats.length;
        },
        lastRowIsFull() {
            if(this.seats.length != 0 && this.seats.length == (this.rows * this.columns))
                return true;
            else
                return false;
        },
        lastSeat() {
            if(this.seats.length == 0)
                return undefined;
            return this.seats[this.seats.length - 1];
        }
    },
    methods: {
        calculateColor(i) {

            let seat = this.seats[i];

            if(!seat.isReserved && !seat.isActive) {
                if(seat.isSelected)
                    return 'yellow';
                else {
                    return 'red lighten-4';
                }
            } else if(seat.isReserved || !seat.isActive) {
                if (seat.segmentClass == "FIRST_CLASS")
                    return 'deep-purple lighten-4';
                else if (seat.segmentClass == "BUSINESS_CLASS")
                    return 'blue lighten-4';
                else if (seat.segmentClass == "ECONOMIC_CLASS")
                    return "grey lighten-1"
            } else {
                if(seat.isSelected)
                    return 'green';
                else if (seat.segmentClass == "FIRST_CLASS")
                    return 'deep-purple lighten-1';
                else if (seat.segmentClass == "BUSINESS_CLASS")
                    return 'blue lighten-1';
                else if (seat.segmentClass == "ECONOMIC_CLASS")
                    return "grey darken-3"
            }
        },
        clickOnSeat(i) {
            if(!this.seats[i].isReserved && this.seats[i].isActive) {
                if(this.selectedSeatsCount == 0 && !this.seats[i].isSelected)
                {
                    this.selectedSeatsCount = 1;
                    this.seats[i].isSelected = true;
                    this.selectedSeatId = this.seats[i].id;
                }
                else if(this.selectedSeatsCount == 1 && this.seats[i].isSelected) {
                    this.selectedSeatsCount = 0;
                    this.seats[i].isSelected = false;
                    this.selectedSeatId = 0;
                }
            }
        },
        compare(a, b) {

            const redA = a.red;
            const redB = b.red;
            const kolonaA = a.kolona;
            const kolonaB = b.kolona;

            let comparison = 0;
            if (redA > redB) {
                comparison = 1;
            } else if (redA < redB) {
                comparison = -1;
            } else if(kolonaA > kolonaB) {
                comparison = 1;
            } else if(kolonaA < kolonaB) {
                comparison = -1;
            }

            return comparison;
        },
        submitChanges() {

            var requestBody = {
                seatId: this.selectedSeatId,
                discount: this.discount
            }

            var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};


            this.$axios.post('http://localhost:8080/api/flights/quickFlightReservation', requestBody, yourConfig)
                .then((response) => {
                    this.$swal("Success", "Quick flight reservation created.", 'success');
                }).catch((error) => {
                    this.$swal("Error", error.response.data.message, 'error');
                });
        },
        closeThisDialog() {
            this.seats = [];
            this.showThisDialog = false;
            this.selectedSeatsCount = 0;
            this.selectedSeatId = 0;
            this.$emit('isClosed', this.showThisDialog);
        }
    },
    beforeUpdate() {
        if(this.showDialog) {

            if(this.receivedSeats.length != 0 && this.seats.length == 0) {
                
                var temp = [...this.receivedSeats];
                this.seats = temp.sort(this.compare);

                this.showThisDialog = this.showDialog;
            }

        } else {
            this.closeThisDialog();
        }
    }
}
</script>



<style scoped>

table {
  margin: 20px auto;
  background: #fafafa;
  box-sizing: border-box;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.2);
}

table thead {
  border-right: 0;
  background: #fff;
}

table thead tr th {
  padding: 10px;
}

table tbody {
  border-left: 1px solid #ccc;
}

table tbody tr {
  border-bottom: 1px solid #ccc;
}

table tbody tr td {
  padding: 10px;
  border-right: 1px solid #ccc;
}
</style>
<template>
<v-layout row justify-center>
<v-dialog v-model="showDialog"  persistent max-width="800px">
    <v-card>

        <v-toolbar dark color="primary">
            <v-btn icon dark @click="showDialog = false">
                <v-icon>close</v-icon>
            </v-btn>
            <v-toolbar-title>Edit Seats Layout</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
                <v-btn dark flat @click="submitChanges()">Save</v-btn>
            </v-toolbar-items>
        </v-toolbar>

        <v-card-text>
            <v-container grid-list-md>

                <v-layout row>
                    <v-item-group multiple :key="cmpkey">
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
                    <v-layout align-space-between justify-start column ma-3>
                        <v-btn raised @click="addNewSeatRow()"><v-icon left>library_add</v-icon>Add Row</v-btn>
                        <v-btn raised @click="activateSelectedSeats()"><v-icon left>touch_app</v-icon>Activate</v-btn>
                        <v-btn raised @click="deleteSelectedSeats()"><v-icon left>block</v-icon>Deactivate</v-btn>
                        <v-btn raised @click="assignFirstClass()">Assign First Class</v-btn>
                        <v-btn raised @click="assignBusinessClass()">Assign Business Class</v-btn>
                        <v-btn raised @click="assignEconomicClass()">Assign Economic Class</v-btn>
                    </v-layout>
                </v-layout>

            </v-container>
        </v-card-text>
    </v-card>
</v-dialog>
</v-layout>
</template>

<script>

var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

export default {

    props: {
        showDialog: Boolean,
        receivedSeats: Array,
        flight: Number
    },

    data() {
        return {
            seats: [],
            cmpkey: 0,
        }
    },
    computed: {
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
            this.seats[i].isSelected = !this.seats[i].isSelected;
            this.cmpkey += 1;                    // TODO: FIX
        },
        addNewSeatRow() {

            var maxColumns = this.lastSeat.kolona;
            
            if(this.lastSeat.red == 40) {
                this.$swal('Error', "Maximum number of rows reached.", 'error');
                return;
            }

            for(var i = 0; i < maxColumns; i++) {
                var newSeatRow = this.lastSeat.red;
                var newSeatColumn = this.lastSeat.kolona + 1;
                if(this.lastRowIsFull) {
                    newSeatRow = newSeatRow + 1;
                    newSeatColumn = 1;
                }

                var newSeat = {
                    red: newSeatRow,
                    kolona: newSeatColumn,
                    segmentClass: 'ECONOMIC_CLASS',
                    isReserved: false,
                    isSelected: false,
                    isActive: true
                };

                this.seats.push(newSeat);
            }
        },
        deleteSelectedSeats() {
            for(var i = 0; i < this.seats.length; i++) {
                if(this.seats[i].isSelected) {
                    this.seats[i].isActive = false;
                    this.seats[i].isSelected = false;
                }
            }
            this.cmpkey += 1;                    // TODO: FIX
        },
        activateSelectedSeats() {
            for(var i = 0; i < this.seats.length; i++) {
                if(this.seats[i].isSelected) {
                    this.seats[i].isActive = true;
                    this.seats[i].isSelected = false;
                }
            }
            this.cmpkey += 1;                    // TODO: FIX
        },
        assignFirstClass() {
            for(var i = 0; i < this.seats.length; i++) {
                if(this.seats[i].isSelected) {
                    this.seats[i].segmentClass = "FIRST_CLASS";
                    this.seats[i].isSelected = false;
                }
            }
            this.cmpkey += 1;                    // TODO: FIX
        },
        assignBusinessClass() {
            for(var i = 0; i < this.seats.length; i++) {
                if(this.seats[i].isSelected) {
                    this.seats[i].segmentClass = "BUSINESS_CLASS";
                    this.seats[i].isSelected = false;
                }
            }
            this.cmpkey += 1;                    // TODO: FIX
        },
        assignEconomicClass() {
            for(var i = 0; i < this.seats.length; i++) {
                if(this.seats[i].isSelected) {
                    this.seats[i].segmentClass = "ECONOMIC_CLASS";
                    this.seats[i].isSelected = false;
                }
            }
            this.cmpkey += 1;                    // TODO: FIX
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
                updatedSeatsList: this.seats,
                flightId: this.flight
            }

            this.$axios.post('http://localhost:8080/api/flights/updateFlightSeats', requestBody, yourConfig)
                .then((response) => {
                    this.$swal("Success", "Updated seating layout", 'success');
                }).catch((error) => {
                    this.$swal("Error", error.response.data.message, 'error');
                });
        }
    },
    beforeUpdate() {
        if(this.seats.length == 0 && this.receivedSeats.length != 0) {

            var temp = this.receivedSeats.sort(this.compare);   // SORTIRAJ
            this.seats = [...temp];
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
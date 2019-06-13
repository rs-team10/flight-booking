<template>
<div v-if="show">
    <v-btn color="primary" @click="goBack">Back to search</v-btn>
    <v-layout row fill-height>

        <!-- TRIP SUMMARY -->
        <v-flex xs3 md3>
            <v-card class="cardcontent">
                <v-card-title>
                    <span class="headline">Trip Summary</span>
                </v-card-title>
                <v-card-text>
                    <v-container grid-list-md>
                        <v-layout wrap>
                            <v-flex>
                                <h2>Flight details</h2>
                                <br>
                                <h3>{{ flight.departureDate }}</h3>
                                <h3>Flight number: {{ flight.flightNumber }}</h3>
                                <p>From: {{ flight.departureAirport }} ({{ flight.departureCode }})</p>
                                <p>To: {{ flight.destinationAirport }} ({{ flight.destinationCode }})</p>
                                <p>Airline: {{ flight.airline }}</p>
                                <h3>{{ flight.departureTime + ' - ' + flight.arrivalTime}}</h3>
                                <p>{{ flight.flightDuration }}, {{ flight.transitCount }} stop</p>
                                <p>Distance: {{ flight.flightDistance }}km</p>
                                <p>Stops: PLACEHOLDER</p>
                                <h2>Trip total: {{ flight.ticketPrice }}€</h2>
                                <br>
                                <hr>
                                <br>
                                <h2>Luggage details [PLACEHOLDER]</h2>
                                <br>
                                <p>Hand luggage: 1 (max 8kg)</p>
                                <p>Checked bags: 1 (max 25kg)</p>
                                <br>
                                <hr>
                                <br>
                                <h2>Additional services [PLACEHOLDER]</h2>
                                <br>
                                <p>Food: Available</p>
                                <p>Soft drinks: Available</p>
                                <p>Alcohol: Not Available</p>
                                <p>Newspapers: Available</p>
                                <p>Multimedia: Available</p>
                                <p>WiFi: Not Available</p>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-card-text>
            </v-card>
        </v-flex>

        <!-- STEPPER -->
        <v-flex xs9 md9>
            <v-stepper v-model="currentStep" vertical>

                <!-- STEP 1 - SELECT SEATS -->
                <v-stepper-step step="1">
                    Seat selection
                </v-stepper-step>

                <v-stepper-content step="1">

                    <v-card class="mb-5">
                        <v-card-text>
                            <v-container grid-list-md>

                                <v-layout row align-start justify-center>
                                    
                                    <!-- SEATS -->
                                    <v-item-group multiple>
                                        <table>
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

                                    <!-- LEGEND -->
                                    <v-layout column align-start justify-start fill-height>

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

                    <v-btn color="primary" @click="proceedToStep2()" :disabled="isSeatSelectionDisabled">Continue</v-btn>
                </v-stepper-content>


                <!-- STEP 2 - INVITE FRIENDS -->
                <v-stepper-step step="2">
                    Friend invitation
                </v-stepper-step>

                <v-stepper-content step="2">

                    <v-layout column ma-4>
                        <v-text-field
                            v-model="search"
                            append-icon="search"
                            label="Search friends list"
                            single-line
                        ></v-text-field>

                        <v-data-table
                            :headers="headers"
                            :items="users"
                            :search="search"
                            v-bind:pagination.sync="pagination">
                            <template v-slot:items="props">
                                <td>{{ props.item.firstName }}</td>
                                <td>{{ props.item.lastName }}</td>
                                <td>{{ props.item.email }}</td>
                                <td>              
                                    <v-checkbox class="ma-2" v-model="selectedFriendsIds" label="Invite" :value="props.item.id" :disabled="isCheckboxDisabled"></v-checkbox>
                                </td>
                            </template>
                            <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                                Your search for "{{ search }}" found no results.
                            </v-alert>
                        </v-data-table>
                    </v-layout>


                    <v-btn color="primary" @click="proceedToStep3()">Continue</v-btn>
                    <v-btn flat @click="changeStep(1)">Back</v-btn>
                    <v-btn flat @click="clearFriendsSelection()">Clear selection</v-btn>
                </v-stepper-content>
                

                <!-- STEP 3 - INFO -->
                <v-stepper-step step="3">
                    Traveller info
                </v-stepper-step>

                <v-stepper-content step="3" :key="infoKey">

                    <div>
                        <v-stepper v-model="infoStepper">
                        <v-stepper-header>
                            <template v-for="n in passengerInfo.length">
                            <v-stepper-step
                                :key="`${n}-step`"
                                :complete="infoStepper > n"
                                :step="n"
                                editable
                            >
                                Passenger {{ n }}
                            </v-stepper-step>

                            <v-divider
                                v-if="n !== passengerInfo.length"
                                :key="n"
                            ></v-divider>
                            </template>
                        </v-stepper-header>

                        <v-stepper-items>

                            <!--CONTENT-->
                            <v-stepper-content
                                v-for="n in passengerInfo.length"
                                :key="`${n}-content`"
                                :step="n"
                            >

                                <v-flex xs12 sm6>
                                    <v-text-field
                                        v-model="passengerInfo[n - 1].firstName"
                                        label="Name"
                                        
                                    ></v-text-field>
                                </v-flex>

                                <v-flex xs12 sm6>
                                    <v-text-field
                                        v-model="passengerInfo[n - 1].lastName"
                                        label="Surname"
                                        
                                    ></v-text-field>
                                </v-flex>

                                <v-flex xs12 sm6>
                                    <v-text-field
                                        v-model="passengerInfo[n - 1].passportNumber"
                                        label="Passport number"
                                        
                                    ></v-text-field>
                                </v-flex>

                                <v-btn
                                    @click="nextInfoStep(n)"
                                >
                                    Next passenger
                                </v-btn>

                            </v-stepper-content>
                        </v-stepper-items>
                        </v-stepper>
                    </div>

                    <v-btn color="primary" @click="proceedToStep4()">Continue</v-btn>
                    <v-btn flat @click="changeStep(2)">Back</v-btn>
                </v-stepper-content>


                <!-- STEP 4 - CONFIRM FLIGHT RESERVATION -->
                <v-stepper-step step="4">
                    Overview and confirmation of flight reservation
                </v-stepper-step>

                <v-stepper-content step="4">
                    <v-layout xs12 sm6 md6 lg6 row wrap>
                        <v-flex v-for="item in reservationDataItems" :key="item.id">
                            <v-card flat>
                                <v-card-text>
                                    <h3 class="mb-0">{{ item.seatClass }} Seat {{ item.seatRow }}{{ item.seatColumnLetter }}</h3>
                                    <div>Passenger: {{ item.lastName }}, {{ item.firstName }} </div>
                                    <div>Passport number: {{ item.passportNumber }}</div>
                                    <h3 class="mb-0">Ticket price: {{ item.ticketPrice }}€</h3>
                                </v-card-text>
                            </v-card>
                        </v-flex>
                    </v-layout>

                    <v-btn color="primary" @click="confirmReservation()">Confirm reservation</v-btn>
                    <v-btn flat @click="changeStep(3)">Back</v-btn>
                </v-stepper-content>


                <!-- STEP 5 - CONTINUE or FINISH -->
                <v-stepper-step step="5">
                    Hotel and Rent-a-Car reservation
                </v-stepper-step>

                <v-stepper-content step="5">
                    Do you want to book a hotel or rent-a-car service?
                    <v-btn color="primary" @click="continueToHotelReservation()">Book a hotel</v-btn>
                    <v-btn color="primary" @click="continueToRentACarReservation()">Book a vehicle</v-btn>
                    <v-btn flat @click="redirectToHomePage()">No</v-btn>
                </v-stepper-content>

            </v-stepper>
        </v-flex>
    </v-layout>
</div>
</template>

<script>



export default {
    props: {
        show: Boolean,
        flight: Object,
        receivedSeats: Array,
        passengerCountSearch: Number
    },
    watch: {
      steps (val) {
        if (this.infoStepper > val) {
          this.infoStepper = val
        }
      }
    },
    data() {
        return {
            currentStep: 1,

            selectedSeatsCount: 0,
            seats: [],

            infoStepper: 1,
            steps: this.passengerCountSearch,
            infoKey: 1,

            passengerInfo: [],

            search: '',
            headers: [
                {
                    text: "First Name",
                    align: 'left',
                    sortable: true,
                    value: 'firstName'
                },
                {
                    text: "Last Name",
                    align: 'left',
                    sortable: true,
                    value: 'lastName'
                },
                {
                    text: "Email",
                    align: 'left',
                    sortable: true,
                    value: 'email'
                },
                {
                    text: "Actions",
                    align: 'center',
                    sortable: false,
                    value: 'actions',
                    width: 70
                }
            ],
            pagination: {'sortBy': 'firstName', 'descending': false, 'rowsPerPage': 5},
            users: [],
            selectedFriendsIds: [],

            reservationDataItems: [],

            flightReservation: undefined,
            groupReservationId: undefined
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
        lastSeat() {
            if(this.seats.length == 0)
                return undefined;
            return this.seats[this.seats.length - 1];
        },
        isSeatSelectionDisabled() {
            if(this.selectedSeatsCount == 0)
                return true;
            else
                return false;
        },
        isCheckboxDisabled() {
            if((this.selectedSeatsCount - 1) > this.selectedFriendsIds.length)
                return false;
            else
                return true; 
        }

    },
    methods: {
        proceedToStep2() {

                this.selectedFriendsIds = [];

                this.passengerInfo = [];

                for(var i = 0; i < this.selectedSeatsCount; i++) {

                    var newInfoObject = {
                        firstName: "",
                        lastName: "",
                        passportNumber: ""
                    };

                    this.passengerInfo.push(newInfoObject);
                }

                if(this.selectedSeatsCount == 1) {
                    proceedToStep3();
                } else {
                    
                    var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

                    this.$axios.get('http://localhost:8080/api/getAllFriendsAccepted/', yourConfig).then((response) => {
                        this.users = response.data;
                        this.currentStep = 2;
                    }).catch((error) => {
                        this.$swal("Error", error.response.data.message, 'error');
                    });
                }
        },
        clearFriendsSelection() {
            this.selectedFriendsIds = [];
        },
        proceedToStep3() {

            // TODO: Popuniti polja sa podacima sa korisnikom i pozvanim prijateljima i disable-ovati polja za unos imena i prezimena
            
            var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

            this.$axios.get('http://localhost:8080/api/currentUser/', yourConfig)
                .then((response) => {
                    
                    this.currentUserId = response.data.id;

                    this.passengerInfo[0].firstName = response.data.firstName;
                    this.passengerInfo[0].lastName = response.data.lastName;
                    
                    if(this.selectedSeatsCount > 1) {
                
                        var index = 1;

                        for(var i = 0; i < this.users.length; i++) {
                            for(var j = 0; j < this.selectedFriendsIds.length; j++) {
                                if(this.users[i].id == this.selectedFriendsIds[j]) {
                                    this.passengerInfo[index].firstName = this.users[i].firstName;
                                    this.passengerInfo[index].lastName = this.users[i].lastName;
                                    index++;
                                }
                            }
                        }
                    }
                    
                    this.infoKey++;
                    this.currentStep = 3;

                }).catch((error) => {
                    console.log("errorcina")
                    this.$swal("Error", error.response.data.message, 'error');
                });
        },
        proceedToStep4() {

            var hasEmptyField = false;

            for(var i = 0; i < this.passengerInfo.length; i++) {
                if(this.passengerInfo[i].firstName == "" || this.passengerInfo[i].lastName == "" || this.passengerInfo[i].passportNumber == "" || isNaN(this.passengerInfo[i].passportNumber))
                    hasEmptyField = true;
            }

            if(!hasEmptyField) {

                // Prodji kroz sva sedista i izdvoj samo odabrana sedista
                var reservedSeats = [];
                var reservedSeatIds = [];
                for(var i = 0; i < this.seats.length; i++) {
                    if(this.seats[i].isSelected) {
                        reservedSeats.push(this.seats[i]);
                        reservedSeatIds.push(this.seats[i].id);
                    }
                }

                // Pozvani prijatelji
                var userIds = [this.currentUserId];
                for(i = 0; i < this.selectedFriendsIds.length; i++) {
                    userIds.push(this.selectedFriendsIds[i]);
                }
                for(i = userIds.length - 1; i < reservedSeatIds.length; i++) {
                    userIds.push(this.currentUserId);
                }

                // Informacije o putnicima
                var seatReservationList = [];
                for(i = 0; i < reservedSeatIds.length; i++) {
                    var seatReservation = {
                        seatId: reservedSeatIds[i],
                        userId: userIds[i],
                        firstName: this.passengerInfo[i].firstName,
                        lastName: this.passengerInfo[i].lastName,
                        passportNumber: this.passengerInfo[i].passportNumber
                    };
                    seatReservationList.push(seatReservation);
                }

                // Kreiraj objekat za slanje
                var flightReservation = {
                    flightId: this.flight.id,
                    seatReservationDTOList: seatReservationList
                };

                this.flightReservation = flightReservation;


                // TODO: Prikazati ih na interfejsu

                for(i = 0; i < seatReservationList.length; i++) {
                    
                    var item = seatReservationList[i];
                    console.log(item);
                    var reservationDataItem = {
                        id: i,
                        firstName : item.firstName,
                        lastName: item.lastName,
                        passportNumber: item.passportNumber,
                        seatRow: reservedSeats[i].red,
                        seatColumnLetter: String.fromCharCode(64 + reservedSeats[i].kolona),
                        seatClass: "Economy class",
                        ticketPrice: "PLACEHOLDER"
                    }

                    if(reservedSeats[i].segmentClass == "ECONOMIC_CLASS")
                        reservationDataItem.seatClass = "Economy class";
                    else if(reservedSeats[i].segmentClass == "BUSINESS_CLASS")
                        reservationDataItem.seatClass = "Business class";
                    else if(reservedSeats[i].segmentClass == "FIRST_CLASS")
                        reservationDataItem.seatClass = "First class";

                    this.reservationDataItems.push(reservationDataItem);
                }

                this.currentStep = 4;
            } else {
                this.$swal("Can't proceed to next step", "Please enter the data for each passenger.", 'warning');
            }
        },
        changeStep(i) {
            if(this.selectedSeatsCount == 1 && i == 2) {
                this.currentStep = 1;
                this.selectedFriendsIds = [];
            }
            else
                this.currentStep = i;
        },
        goBack() {

            // TODO: Ocistiti sve sto je izabrano

            this.currentStep = 1;

            this.seats = [];
            this.selectedSeatsCount = 0;

            this.infoStepper = 1;
            this.passengerInfo = [];

            this.$emit("goBack");
        },
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
                if(this.seats[i].isSelected) {
                    this.seats[i].isSelected = false;
                    this.selectedSeatsCount--;
                } else if(this.selectedSeatsCount < this.passengerCountSearch) {
                    this.seats[i].isSelected = true;
                    this.selectedSeatsCount++;
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
        nextInfoStep (n) {
            if (n === this.passengerInfo.length) {
                this.infoStepper = 1;
            } else {
                this.infoStepper = n + 1;
            }
        },
        confirmReservation() {

            var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

            this.$axios.post('http://localhost:8080/api/reserveFlight', this.flightReservation, yourConfig)
                .then((response) => {
                    this.groupReservationId = response.data;
                    this.$swal.fire({
                            title: 'Success', 
                            html: 'Flight reservation successfull',
                            type: 'success',
                            showConfirmButton: false,
                            timer: 2000,
                            allowOutsideClick: false
                        }).then((result) => {
                            if (result.dismiss === this.$swal.DismissReason.timer) {
                                this.currentStep = 5;
                            }
                        });
                }).catch((error) => {
                    this.$swal("Error", "Unsuccessfull reservation. The selected seats are probably already reserved. Please select other seats.", 'error');
                    // TODO 5 : Ako ne uspe rezervacija, vratiti ga na korak 1 da izabere druga sedista

                    this.currentStep = 1;
                });
        },
        continueToHotelReservation() {
            
            // KATARINA

            // TODO: Korisnik nastavlja sa rezervacijom smestaja destinaciji za koju je rezervisao let
            //       Na kraju tih rezervacija, potrebno je poslati mail korisniku da je rezervisao let + nesto dodatno (hotel, rentacar ili oba)


        },
        continueToRentACarReservation() {

            // FILIP

            // TODO: Korisnik nastavlja sa rezervacijom vozila na destinaciji za koju je rezervisao let
            //       Na kraju tih rezervacija, potrebno je poslati mail korisniku da je rezervisao let + nesto dodatno (hotel, rentacar ili oba)


        },
        redirectToHomePage() {

            // TODO 1: Korisnik je rezervisao samo let, i sada mu treba poslati mail o potvrdi rezervacije leta

            this.$swal.fire({
                title: 'Payment confirmation',
                text: "If you proceed your credit card will be charged.",
                showCancelButton: true,
                confirmButtonText: 'Pay now',
                showLoaderOnConfirm: true,
                preConfirm: () => {

                    var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

                    return this.$axios.post("http://localhost:8080/api/sendEmails/" + this.groupReservationId, yourConfig)
                        .then(response => {
                            if(!response) {
                                throw new Error(response.statusText)
                            }
                            return response
                        }).catch(error => {
                            this.$swal.showValidationMessage(
                                "Request failed: " + error
                            )
                        })
                },
                allowOutsideClick: () => !this.$swal.isLoading()
            }).then((result_1) => {
                if(result_1) {
                    this.$swal.fire({
                        title: "Confirmation e-mail sent",
                        type: "success",
                        timer: 2000,
                        showConfirmButton: false,
                        allowOutsideClick: false
                    }).then((result) => {
                         if (result.dismiss === this.$swal.DismissReason.timer) {
                    
                            // TODO 2: Nakon toga redirect na pocetnu stranicu.
                            this.$router.replace({ path: '/' });
                        }
                    })
                }
            });
        }
    },
    created() {

        this.passengerInfo = [];
        for(var i = 0; i < this.passengerCountSearch; i++) {

            var newInfoObject = {
                firstName: "",
                lastName: "",
                passportNumber: ""
            };

            this.passengerInfo.push(newInfoObject);
        }
    },
    beforeUpdate() {

        if(this.receivedSeats.length != 0) {

            var temp = this.receivedSeats.sort(this.compare);   // SORTIRAJ SEDISTA
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

.cardcontent
{
    min-height: 100%;
}
</style>
<template>
    <div id="airline-flights" class="ma-3">
        <v-flex>
            <v-toolbar>
                <v-toolbar-title>Flights</v-toolbar-title>
                <v-spacer></v-spacer>
                <!-- DIJALOG ZA UNOS LETA -->
                <!-- BEGIN -->
                <v-dialog v-model="showFlightDialog" max-width="500px">
                    
                    <template v-slot:activator="{on}">
                        <v-btn v-on="on">New Flight</v-btn>
                    </template>

                    <v-card>
                        <v-card-title>
                            <span class="headline">Add new flight</span>
                        </v-card-title>

                        <v-card-text>
                            <v-container grid-list-md>
                                <v-layout wrap>

                                    <v-flex xs12 md12>
                                        <h2 class="text-xs-left mb-4">Flight Data</h2>
                                    </v-flex>

                                    <v-flex>
                                        <v-text-field
                                            v-model="flightNumber"
                                            label="Flight Number"
                                            prepend-icon="public"
                                            :error-messages="flightNumberErrors"
                                            @input="$v.flightNumber.$touch()"
                                            @blur="$v.flightNumber.$touch()"
                                            >
                                        </v-text-field>
                                    </v-flex>

                                    <v-flex>
                                        <v-autocomplete
                                            v-model="departure"
                                            :items="businessLocations"
                                            label="From"
                                            return-object
                                            :item-text="selectionItemText"
                                            prepend-icon="flight_takeoff"
                                            solo
                                            >
                                        </v-autocomplete>
                                    </v-flex>

                                    <v-flex>
                                        <v-autocomplete
                                            v-model="destination"
                                            :items="businessLocations"
                                            label="To"
                                            return-object
                                            :item-text="selectionItemText"
                                            prepend-icon="flight_land"
                                            solo
                                            >
                                        </v-autocomplete>
                                    </v-flex>

                                    <v-flex>
                                        <v-combobox
                                            v-model="transitDestinations"
                                            :items="computedBusinessLocations"
                                            label="Via"
                                            solo
                                            chips
                                            clearable
                                            return-object
                                            multiple
                                            prepend-icon="flight"
                                            :item-text="selectionItemText"
                                        >
                                            <template v-slot:selection="data">
                                                <v-chip
                                                    :selected="data.selected"
                                                    close
                                                    @input="removeTransitDestination(data.item)"
                                                >
                                                    {{ data.item.airportCode + ' - ' + data.item.name }}
                                                </v-chip>
                                            </template>
                                        </v-combobox>
                                    </v-flex>

                                    <v-flex xs12 md6>
                                        <v-menu
                                            v-model="showDepartureDateDialog"
                                            :close-on-content-click="false"
                                            :nudge-right="40"
                                            lazy
                                            transition="scale-transition"
                                            offset-y
                                            full-width
                                            min-width="290px"
                                        >
                                            <template v-slot:activator="{ on }">
                                                <v-text-field
                                                    v-model="departureDate"
                                                    label="Departure date"
                                                    prepend-icon="event"
                                                    readonly
                                                    v-on="on"
                                                ></v-text-field>
                                            </template>
                                            <v-date-picker
                                                v-model="departureDate"
                                                @input="showDepartureDateDialog = false"
                                                :min="minimalDepartureDate"
                                            ></v-date-picker>
                                        </v-menu>
                                    </v-flex>

                                    <v-flex xs12 md6> 
                                        <v-menu
                                            ref="departureTimeMenu"
                                            v-model="showDepartureTimeMenu"
                                            :close-on-content-click="false"
                                            :nudge-right="40"
                                            :return-value.sync="departureTime"
                                            lazy
                                            transition="scale-transition"
                                            offset-y
                                            full-width
                                            max-width="290px"
                                            min-width="290px"
                                        >
                                            <template v-slot:activator="{ on }">
                                                <v-text-field
                                                    v-model="departureTime"
                                                    label="Departure Time"
                                                    prepend-icon="access_time"
                                                    readonly
                                                    v-on="on"
                                                ></v-text-field>
                                            </template>
                                            <v-time-picker
                                                v-if="showDepartureTimeMenu"
                                                v-model="departureTime"
                                                full-width
                                                @click:minute="$refs.departureTimeMenu.save(departureTime)"
                                            ></v-time-picker>
                                        </v-menu>
                                    </v-flex>

                                    <v-flex xs12 md6>
                                        <v-menu
                                            v-model="showArrivalDateDialog"
                                            :close-on-content-click="false"
                                            :nudge-right="40"
                                            lazy
                                            transition="scale-transition"
                                            offset-y
                                            full-width
                                            min-width="290px"
                                        >
                                            <template v-slot:activator="{ on }">
                                                <v-text-field
                                                    v-model="arrivalDate"
                                                    label="Arrival date"
                                                    prepend-icon="event"
                                                    readonly
                                                    v-on="on"
                                                ></v-text-field>
                                            </template>
                                            <v-date-picker
                                                v-model="arrivalDate"
                                                @input="showArrivalDateDialog = false"
                                                :min="minimalArrivalDate"
                                            ></v-date-picker>
                                        </v-menu>
                                    </v-flex>

                                    <v-flex xs12 md6> 
                                        <v-menu
                                            ref="arrivalTimeMenu"
                                            v-model="showArrivalTimeMenu"
                                            :close-on-content-click="false"
                                            :nudge-right="40"
                                            :return-value.sync="arrivalTime"
                                            lazy
                                            transition="scale-transition"
                                            offset-y
                                            full-width
                                            max-width="290px"
                                            min-width="290px"
                                        >
                                            <template v-slot:activator="{ on }">
                                                <v-text-field
                                                    v-model="arrivalTime"
                                                    label="Arrival Time"
                                                    prepend-icon="access_time"
                                                    readonly
                                                    v-on="on"
                                                ></v-text-field>
                                            </template>
                                            <v-time-picker
                                                v-if="showArrivalTimeMenu"
                                                v-model="arrivalTime"
                                                full-width
                                                @click:minute="$refs.arrivalTimeMenu.save(arrivalTime)"
                                            ></v-time-picker>
                                        </v-menu>
                                    </v-flex>

                                    <v-flex>
                                        <v-text-field 
                                            label="Ticket Price"
                                            prepend-icon="euro_symbol"
                                            type="number"
                                            required
                                            :error-messages="ticketPriceErrors"
                                            v-model="ticketPrice"
                                            @input="$v.ticketPrice.$touch()"
                                            @blur="$v.ticketPrice.$touch()">
                                        </v-text-field>
                                    </v-flex>

                                    
                                    <v-flex xs12 md6>
                                        <v-text-field 
                                            label="Travel Distance (km)"
                                            prepend-icon="swap_calls"
                                            v-model="distance"
                                            readonly>
                                        </v-text-field>
                                    </v-flex>

                                    <v-flex xs12 md6>
                                        <v-text-field 
                                            label="Flight Duration (min)"
                                            prepend-icon="timelapse"
                                            v-model="duration"
                                            readonly>
                                        </v-text-field>
                                    </v-flex>

                                    <v-flex xs12 md12>
                                        <h2 class="text-xs-left my-4">Seat Management</h2>
                                    </v-flex>

                                    <v-flex xs12 md6>
                                        <v-text-field 
                                            type="number"
                                            label="Rows"
                                            prepend-icon="chevron_right"
                                            v-model="rowsCount"
                                            required
                                            :error-messages="rowsCountErrors"
                                            @input="$v.rowsCount.$touch()"
                                            @blur="$v.rowsCount.$touch()"
                                        >
                                        </v-text-field>
                                    </v-flex>

                                    <v-flex xs12 md6>
                                        <v-text-field
                                            type="number"
                                            label="Columns"
                                            prepend-icon="view_column"
                                            v-model="columnsCount"
                                            required
                                            :error-messages="columnsCountErrors"
                                            @input="$v.columnsCount.$touch()"
                                            @blur="$v.columnsCount.$touch()"
                                        >
                                        </v-text-field>
                                    </v-flex>

                                    <v-flex>
                                        <v-text-field
                                            type="number"
                                            label="First Class Seats"
                                            prepend-icon="airline_seat_legroom_extra"
                                            v-model="firstClassSeatsCount"
                                            :error-messages="firstClassSeatsCountErrors"
                                            @input="$v.firstClassSeatsCount.$touch()"
                                            @blur="$v.firstClassSeatsCount.$touch()"
                                        >
                                        </v-text-field>
                                    </v-flex>

                                    <v-flex>
                                        <v-text-field
                                            type="number"
                                            label="Business Class Seats"
                                            prepend-icon="airline_seat_legroom_normal"
                                            v-model="businessClassSeatsCount"
                                            :error-messages="businessClassSeatsCountErrors"
                                            @input="$v.businessClassSeatsCount.$touch()"
                                            @blur="$v.businessClassSeatsCount.$touch()"
                                        >
                                        </v-text-field>
                                    </v-flex>

                                    <v-flex>
                                        <v-text-field
                                            type="number"
                                            label="Economic Class Seats"
                                            prepend-icon="airline_seat_legroom_reduced"
                                            v-model="ecoClassSeatsCount"
                                            :error-messages="ecoClassSeatsCountErrors"
                                            @input="$v.ecoClassSeatsCount.$touch()"
                                            @blur="$v.ecoClassSeatsCount.$touch()"
                                        >
                                        </v-text-field>
                                    </v-flex>

                                </v-layout>
                            </v-container>
                        </v-card-text>

                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn flat @click="close">Close</v-btn>
                            <v-btn flat @click="save">Save</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
                <!-- DIJALOG ZA UNOS LETA -->
                <!-- END -->
            </v-toolbar>




            <edit-seats 
                :showDialog="showEditSeatsLayoutDialog"
                :receivedSeats="seats"
                :flight="idToEditSeats"
            />





            <v-data-table
                :headers="headers"
                :items="flights"
                :servicePagination.sync="flightsPagination"
                class="elevation-1">

                <template v-slot:items="props">
                    <td>{{ props.item.flightNumber }}</td>
                    <td>{{ props.item.departure }}</td>
                    <td>{{ props.item.destination }}</td>
                    <td>{{ props.item.transitDestinations.length }}</td>
                    <td>{{ props.item.departureDate }}</td>
                    <td>{{ props.item.arrivalDate }}</td>
                    <td>{{ props.item.ticketPrice }}</td>
                    <td>{{ props.item.distance }}</td>
                    <td>{{ props.item.duration }}</td>

                    <td class="justify-center layout px-0">
                        <v-icon small class="mr-2" @click="editSeatsLayout(props.item)">
                            airline_seat_legroom_normal
                        </v-icon> 
                        <v-icon small class="mr-2" @click="editFlight(props.item)">
                            edit
                        </v-icon>
                        <v-icon small class="mr-2" @click="deleteFlight(props.item)">
                            delete
                        </v-icon>   
                    </td>
                </template>

            </v-data-table>

            <div class="text-xs-center pt-2">
                <v-pagination v-model="flightsPagination.page" :length="flightsPages"></v-pagination>
            </div>
        </v-flex>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, numeric, between } from 'vuelidate/lib/validators'

import EditSeatsLayout from "@/components/airline/EditSeatsLayout.vue"

const haversine = require('haversine');

var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

const flightNumberFormat = (value, vm) => {
    var regex = /^[A-Z]{2}\d{3,4}$/g;
    return regex.test(value);
}

export default {

    name: "flights",
    mixins: [validationMixin],
    components: {
        'edit-seats': EditSeatsLayout,
    },
    data() {
        return {
            headers : [
                { text: 'Flight Number', align: 'left', sortable: true, value: 'flightNumber'},
                { text: 'From', align: 'left', sortable: true, value: 'departure'},
                { text: 'To', align: 'left', sortable: true, value: 'destination'},
                { text: 'Transit count', align: 'left', sortable: true, value: 'transitCount'},
                { text: 'Departure Date & Time', align: 'left', sortable: true, value: 'departureDateTime'},
                { text: 'Arrival Date & Time', align: 'left', sortable: true, value: 'arrivalDateTime'},
                { text: 'Ticket Price', align: 'left', sortable: true, value: 'ticketPrice'},
                { text: 'Distance (km)', align: 'left', sortable: true, value: 'distance'},
                { text: 'Duration (min)', align: 'left', sortable: true, value: 'duration'},
            ],
            flightsPagination: {},
            flightForSeats: undefined,

            flights: [],
            businessLocations: [],

            // show/hide dialog
            showFlightDialog: false,
            showEditSeatsLayoutDialog: false,
            seats: [],
            idToEditSeats: undefined,

            // flight data
            flightNumber: null,
            departure: null,
            destination: null,
            transitDestinations: [],
            ticketPrice: null,

            // departure dialog
            showDepartureDateDialog: false,
            departureDate: null,
            showDepartureTimeMenu: false,
            departureTime: null,

            // arrival dialog
            showArrivalDateDialog: false,
            arrivalDate: null,
            showArrivalTimeMenu: false,
            arrivalTime: null,

            // seats
            rowsCount: 0,
            columnsCount: 0,
            firstClassSeatsCount: 0,
            businessClassSeatsCount: 0,
            ecoClassSeatsCount: 0
        }
    },
    validations: {
        flightNumber: { required, flightNumberFormat },
        ticketPrice: { required, numeric, between: between(5, 99999) },
        rowsCount: { required, numeric, between: between(1, 40) },
        columnsCount: { required, numeric, between: between(2, 10) },
        firstClassSeatsCount: { required, numeric, between: between(0, 400) },
        businessClassSeatsCount: { required, numeric, between: between(0, 400) },
        ecoClassSeatsCount: { required, numeric, between: between(0, 400) }
    },
    computed: {

        // ============= validation ===================
        flightNumberErrors () {
            const errors = []
            if (!this.$v.flightNumber.$dirty) return errors
            !this.$v.flightNumber.required && errors.push('Flight Number is required.')
            !this.$v.flightNumber.flightNumberFormat && errors.push('Flight Number invalid')
            return errors
        },
        ticketPriceErrors () {
            const errors = []
            if (!this.$v.ticketPrice.$dirty) return errors
            !this.$v.ticketPrice.numeric && errors.push('Ticket price value invalid.')
            !this.$v.ticketPrice.required && errors.push('Ticket price is required.')
            !this.$v.ticketPrice.between && errors.push('Ticket price value out of specified range.')
            return errors
        },
        rowsCountErrors () {
            const errors = []
            if (!this.$v.rowsCount.$dirty) return errors
            !this.$v.rowsCount.numeric && errors.push('Rows count value invalid.')
            !this.$v.rowsCount.required && errors.push('Rows count is required.')
            !this.$v.rowsCount.between && errors.push('Rows count value out of specified range.')
            return errors
        },
        columnsCountErrors () {
            const errors = []
            if (!this.$v.columnsCount.$dirty) return errors
            !this.$v.columnsCount.numeric && errors.push('Columns count value invalid.')
            !this.$v.columnsCount.required && errors.push('Columns count is required.')
            !this.$v.columnsCount.between && errors.push('Columns count value out of specified range.')
            return errors
        },
        firstClassSeatsCountErrors () {
            const errors = []
            if (!this.$v.firstClassSeatsCount.$dirty) return errors
            !this.$v.firstClassSeatsCount.numeric && errors.push('First Class Seats count value invalid.')
            !this.$v.firstClassSeatsCount.required && errors.push('First Class Seats count is required.')
            !this.$v.firstClassSeatsCount.between && errors.push('First Class Seats count value out of specified range.')
            return errors
        },
        businessClassSeatsCountErrors () {
            const errors = []
            if (!this.$v.businessClassSeatsCount.$dirty) return errors
            !this.$v.businessClassSeatsCount.numeric && errors.push('Business Class Seats count value invalid.')
            !this.$v.businessClassSeatsCount.required && errors.push('Business Class Seats count is required.')
            !this.$v.businessClassSeatsCount.between && errors.push('Business Class Seats count value out of specified range.')
            return errors
        },
        ecoClassSeatsCountErrors () {
            const errors = []
            if (!this.$v.ecoClassSeatsCount.$dirty) return errors
            !this.$v.ecoClassSeatsCount.numeric && errors.push('Economic Class Seats count value invalid.')
            !this.$v.ecoClassSeatsCount.required && errors.push('Economic Class Seats count is required.')
            !this.$v.ecoClassSeatsCount.between && errors.push('Economic Class Seats count value out of specified range.')
            return errors
        },

        // ================ properties ================

        flightsPages() {

        },
        computedBusinessLocations: function() {
            var result = this.businessLocations;
            if(this.departure)
                result = result.filter(element => element.airportCode != this.departure.airportCode);
            if(this.destination)
                result = result.filter(element => element.airportCode != this.destination.airportCode);

            return result;
        },
        minimalDepartureDate() {
            var today = new Date();
            var tomorrow = new Date();
            tomorrow.setDate(today.getDate() + 1);

            return tomorrow.toISOString().substr(0, 10);
        },
        minimalArrivalDate() {
            return this.departureDate;
        },
        distance: function() {
            
            if(this.departure && this.destination) {

                var departureLatLng = {
                    latitude: this.departure.location.latitude,
                    longitude: this.departure.location.longitude
                };

                var destinationLatLng = {
                    latitude: this.destination.location.latitude,
                    longitude: this.destination.location.longitude
                };

                if(this.transitDestinations.length == 0) {
                    return Math.round(haversine(departureLatLng, destinationLatLng));
                } else {
                    
                    var distanceSum = 0;

                    distanceSum += haversine(departureLatLng, {
                        latitude: this.transitDestinations[0].location.latitude,
                        longitude: this.transitDestinations[0].location.longitude
                    });

                    for (var i = 1; i <= this.transitDestinations.length - 2; i++) { 
                        distanceSum += haversine({
                            latitude: this.transitDestinations[i-1].location.latitude,
                            longitude: this.transitDestinations[i-1].location.longitude
                        },{
                            latitude: this.transitDestinations[i].location.latitude,
                            longitude: this.transitDestinations[i].location.longitude
                        })
                    }

                    distanceSum += haversine({
                        latitude: this.transitDestinations[this.transitDestinations.length - 1].location.latitude,
                        longitude: this.transitDestinations[this.transitDestinations.length - 1].location.longitude
                    }, destinationLatLng);

                    return Math.round(distanceSum);
                }
            };
            
            return 0;
        },
        duration: function() {
            
            var start = this.$moment(this.departureDate + ' ' + this.departureTime, "YYYY-MM-DD HH:mm");
            var end = this.$moment(this.arrivalDate + ' ' + this.arrivalTime, "YYYY-MM-DD HH:mm");
            var diff = end.diff(start, 'minutes') >= 0 ? end.diff(start, 'minutes') : 0;

            return diff;            
        },
        totalSeatsCount: function() {
            return this.columnsCount * this.rowsCount;
        },
        actualSeatsCount: function() {
            return parseInt(this.firstClassSeatsCount) + parseInt(this.businessClassSeatsCount) + parseInt(this.ecoClassSeatsCount);
        }
    },
    methods: {
        selectionItemText: item => item.airportCode + ' - ' + item.name,

        removeTransitDestination(item) {
            this.transitDestinations.splice(this.transitDestinations.indexOf(item), 1);
            this.transitDestinations = [...this.transitDestinations];
        },

        close() {
            this.$v.$reset();
            this.showFlightDialog = false;

            this.flightNumber = null,
            this.departure = null,
            this.destination = null,
            this.transitDestinations = [],
            this.ticketPrice = null,

            this.showDepartureDateDialog = false,
            this.departureDate = null,
            this.showDepartureTimeMenu = false,
            this.departureTime = null,

            this.showArrivalDateDialog = false,
            this.arrivalDate = null,
            this.showArrivalTimeMenu = false,
            this.arrivalTime = null,

            this.rowsCount = 0,
            this.columnsCount = 0,
            this.firstClassSeatsCount = 0,
            this.businessClassSeatsCount = 0,
            this.ecoClassSeatsCount = 0
        },
        save() {
            this.$v.$touch();
            if(!this.$v.$invalid) {

                // ========================================================== Provera FROM, TO i VIA

                var codeArray = [];

                if(this.departure) {
                    codeArray.push(this.departure.airportCode);
                } else {
                    this.$swal("Error", "Invalid departure", 'warning');
                    return;
                }

                if(this.destination) {
                    codeArray.push(this.destination.airportCode);
                } else {
                    this.$swal("Error", "Invalid destination", 'warning');
                    return;
                }

                for (var i = 0; i <= this.transitDestinations.length - 1; i++)
                    codeArray.push(this.transitDestinations[i].airportCode);

                if(codeArray.length !== new Set(codeArray).size) {
                    this.$swal("Error", "Invalid transit data", 'warning');
                    return;
                }

                // ========================================================== Provera polaznog i krajnjeg datuma i vremena

                if(this.duration <= 0) {
                    this.$swal("Error", "Invalid dates and times", 'warning');
                    return;
                }

                // ========================================================== Provera broja sedista
                if(this.totalSeatsCount > this.actualSeatsCount) {
                    this.$swal("Error", "Invalid number of seats: not all seats have a class", 'warning');
                    return;
                }

                if(this.totalSeatsCount < this.actualSeatsCount) {
                    this.$swal("Error", "Invalid number of seats: number of seats exceeds the given column/row count", 'warning');
                    return;
                }

                var newFlightToSend = {
                    flightNumber : this.flightNumber,
                    departure : this.departure.name,
                    destination : this.destination.name,
                    transitDestinations : [],
                    ticketPrice : this.ticketPrice,
                    departureDate : this.departureDate + ' ' + this.departureTime,
                    arrivalDate : this.arrivalDate + ' ' + this.arrivalTime,
                    duration : this.duration,
                    distance : this.distance,
                    rowsCount : this.rowsCount,
                    columnsCount : this.columnsCount,
                    firstClassSeatsCount : this.firstClassSeatsCount,
                    businessClassSeatsCount : this.businessClassSeatsCount,
                    ecoClassSeatsCount : this.ecoClassSeatsCount
                };

                for (var i = 0; i < this.transitDestinations.length; i++) { 
                    newFlightToSend.transitDestinations.push(this.transitDestinations[i].name);
                }

                this.$axios.post('http://localhost:8080/api/flights/addFlight/', newFlightToSend, yourConfig)
                    .then((response) => {
                        this.$swal('Successfull', 'New flight created successfully.', 'success');
                        this.flights.push(newFlightToSend);
                        this.close();
                    }).catch((error) => {
                        this.$swal("Error", error.response.data.message, 'error');
                    });
            }
        },
        editFlight(flight) {
           // TODO: Izmena izabranog leta i cuvanje na serveru [OPCIONA FUNKCIONALNOST]
        
        },
        deleteFlight(flight) {
            // TODO : Logicko brisanje leta (deaktivacija)
        },
        editSeatsLayout(flight) {

            this.$axios.post('http://localhost:8080/api/flights/getFlightSeats', flight, yourConfig)
                .then((response) => {
                    this.seats = response.data;
                    this.idToEditSeats = flight.id;
                    this.showEditSeatsLayoutDialog = true;
                }).catch((error) => {
                    this.$swal("Error", error.response.data.message, 'error');
                });
        }
    },
    created() {

        this.$axios.get('http://localhost:8080/api/flights/getFlights', yourConfig)
            .then((response) => {

                this.flights = response.data;

                // TODO: PREMESTITI OVO KAD SE OTVORI DIJALOG
                this.$axios.get('http://localhost:8080/api/airlines/getBusinessLocations', yourConfig)
                    .then((response) => {
                        this.businessLocations = response.data;
                    }).catch((error) => {
                        this.$swal("Error", error.response.data.message, 'error');
                    });

            }).catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });
    }
}
</script>

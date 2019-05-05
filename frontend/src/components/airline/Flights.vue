<template>
    <div id="airline-flights" class="ma-3">
        <v-flex>
            <v-toolbar>
                <v-toolbar-title>Flights</v-toolbar-title>
                <v-spacer></v-spacer>

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
                                        <v-autocomplete
                                            v-model="newFlight.departure"
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
                                            v-model="newFlight.destination"
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
                                            v-model="newFlight.ticketPrice"
                                            @input="$v.newFlight.ticketPrice.$touch()"
                                            @blur="$v.newFlight.ticketPrice.$touch()">
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
            </v-toolbar>

            <v-data-table
                :headers="headers"
                :items="flights"
                :servicePagination.sync="flightsPagination"
                class="elevation-1">

                <template v-slot:items="props">
                    <td></td>

                    <td class="justify-center layout px-0">
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


const haversine = require('haversine');

const MOCK_ID = 1;

var yourConfig = {
    headers: { Authorization: "Bearer " + localStorage.getItem("token") }
};

export default {

    name: "flights",
    mixins: [validationMixin],

    data() {
        return {
            headers : [
                { text: 'Flight Number', align: 'left', sortable: true, value: 'flightNumber'},
                { text: 'From', align: 'left', sortable: true, value: 'departure'},
                { text: 'To', align: 'left', sortable: true, value: 'destination'},
            ],
            flightsPagination: {},

            showFlightDialog: false,
            flights: [],
            businessLocations: [],

            newFlight: {},

            transitDestinations: [],
            
            

            showDepartureDateDialog: false,
            departureDate: null,
            showDepartureTimeMenu: false,
            departureTime: null,

            showArrivalDateDialog: false,
            arrivalDate: null,
            showArrivalTimeMenu: false,
            arrivalTime: null,

            rowsCount: 0,
            columnsCount: 0,
            firstClassSeatsCount: 0,
            businessClassSeatsCount: 0,
            ecoClassSeatsCount: 0

        }
    },
    validations: {
        newFlight: {
            ticketPrice: { required, numeric, between: between(5, 99999) }
        },
        rowsCount: { required, numeric, between: between(1, 40) },
        columnsCount: { required, numeric, between: between(2, 10) },
        firstClassSeatsCount: { required, numeric, between: between(0, 400) },
        businessClassSeatsCount: { required, numeric, between: between(0, 400) },
        ecoClassSeatsCount: { required, numeric, between: between(0, 400) }
    },
    computed: {

        // ============= validation ===================

        ticketPriceErrors () {
            const errors = []
            if (!this.$v.newFlight.ticketPrice.$dirty) return errors
            !this.$v.newFlight.ticketPrice.numeric && errors.push('Ticket price value invalid.')
            !this.$v.newFlight.ticketPrice.required && errors.push('Ticket price is required.')
            !this.$v.newFlight.ticketPrice.between && errors.push('Ticket price value out of specified range.')
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


        // ============================================

        flightsPages() {

        },
        computedBusinessLocations: function() {

            var result = this.businessLocations;

            if(this.newFlight.departure)
                result = result.filter(element => element.id != this.newFlight.departure.id);
            
            if(this.newFlight.destination)
                result = result.filter(element => element.id != this.newFlight.destination.id);

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
            
            if(this.newFlight.departure && this.newFlight.destination) {

                var departureLatLng = {
                    latitude: this.newFlight.departure.location.latitude,
                    longitude: this.newFlight.departure.location.longitude
                };

                var destinationLatLng = {
                    latitude: this.newFlight.destination.location.latitude,
                    longitude: this.newFlight.destination.location.longitude
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

            this.newFlight = {},
            this.transitDestinations = [],

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

                // ===== Provera FROM, TO i VIA

                var idArray = [];

                if(this.newFlight.departure) {
                    idArray.push(this.newFlight.departure.id);
                } else {
                    this.$swal("Error", "Invalid departure", 'warning');
                    return;
                }

                if(this.newFlight.destination) {
                    idArray.push(this.newFlight.destination.id);
                } else {
                    this.$swal("Error", "Invalid destination", 'warning');
                    return;
                }

                for (var i = 0; i <= this.transitDestinations.length - 1; i++)
                    idArray.push(this.transitDestinations[i].id);

                if(idArray.length !== new Set(idArray).size) {
                    this.$swal("Error", "Invalid transit data", 'warning');
                    return;
                }

                // ===== Provera polaznog i krajnjeg datuma i vremena

                if(this.duration <= 0) {
                    this.$swal("Error", "Invalid dates and times", 'warning');
                    return;
                }

                // ===== Provera broja sedista
                if(this.totalSeatsCount > this.actualSeatsCount) {
                    this.$swal("Error", "Invalid number of seats: not all seats have a class", 'warning');
                    return;
                }

                if(this.totalSeatsCount < this.actualSeatsCount) {
                    this.$swal("Error", "Invalid number of seats: number of seats exceeds the given column/row count", 'warning');
                    return;
                }


                // TODO : POST NA SERVER
                
            }
        },
        editFlight(flight) {
            // TODO : PUT
        },
        deleteFlight(flight) {
            // TODO : DELETE
        },

    },
    created() {

        this.$axios.get('http://localhost:8080/api/airlines/' + MOCK_ID, yourConfig).then((response) => {
            this.businessLocations = response.data.businessLocations;
        }).catch((error) => {
            this.$swal("Error", error.response.data.message, 'error');
        });
    }
    
}
</script>

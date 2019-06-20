<template>
    <div>
        <div v-if="!showReservationStepper">
            <v-layout row-wrap mx-4 mt-4>
                <!-- TYPE -->
                <v-flex xs4 md2>
                    <v-select
                        v-model="searchType"
                        :items="searchTypeItems"
                        prepend-icon="compare_arrows"
                        label="Search Type"
                        
                    ></v-select>
                </v-flex>
                <!-- CLASS -->
                <v-flex xs4 md2 mx-2>
                    <v-select
                        v-model="searchClass"
                        :items="searchClassItems"
                        prepend-icon="airline_seat_legroom_normal"
                        label="Flight Class"
                        
                    ></v-select>
                </v-flex>
                <!-- PASSENGER COUNT -->
                <v-flex xs4 md2>
                    <v-layout row>
                    <v-icon @click="decrementPassengersCount" color="black" left>remove</v-icon>
                    <v-text-field v-model="passengersCount" label="Passengers count" append-icon="people" type="number" readonly></v-text-field>
                    <v-icon @click="incrementPassengersCount" color="black" right>add</v-icon>
                    </v-layout>
                </v-flex>
            </v-layout>

            <!-- ONE WAY SEARCH -->
            <div v-if='searchType === "One-way"'>
                <v-layout row-wrap mx-4>
                    <!-- DEPARTURE -->
                    <v-flex xs3 mr-2>
                        <v-autocomplete
                            label="From"
                            prepend-icon="flight_takeoff"
                            v-model="oneWaySearch.departure"
                            :items="availableDestinations"
                            return-object
                            :item-text="selectionItemText"
                            solo
                            >
                        </v-autocomplete>
                    </v-flex>
                    <!-- SWAP -->
                    <v-flex xs>
                        <v-btn flat icon color="grey" large @click="swap(oneWaySearch)">
                            <v-icon>compare_arrows</v-icon>
                        </v-btn>
                    </v-flex>
                    <!-- DESTINATION -->
                    <v-flex xs3 mr-2>
                        <v-autocomplete
                            label="To"
                            prepend-icon="flight_land"
                            v-model="oneWaySearch.destination"
                            :items="availableDestinations"
                            return-object
                            :item-text="selectionItemText"
                            solo
                            >
                        </v-autocomplete>
                    </v-flex>
                    <!-- DEPARTURE DATE -->
                    <v-flex xs3 ml-2>
                        <v-menu
                            v-model="oneWaySearch.showMenu"
                            :close-on-content-click="false"
                            lazy
                            transition="scale-transition"
                            offset-y full-width max-width="290px"
                            min-width="290px">
                            <template v-slot:activator="{ on }">
                                <v-text-field
                                    label="Departure date"
                                    prepend-icon="event"
                                    readonly
                                    v-on="on"
                                    v-model="oneWaySearch.date">
                                </v-text-field>
                            </template>
                            <v-date-picker
                                v-model="oneWaySearch.date"
                                no-title
                                scrollable
                                @input="oneWaySearch.showMenu = false"
                                :min="minimalDepartureDate">
                            </v-date-picker>
                        </v-menu>
                    </v-flex>
                    <v-flex xs4><v-btn color="primary" @click="performOneWaySearch">Search</v-btn></v-flex>
                </v-layout>
            </div>

            <!-- ROUND TRIP SEARCH -->
            <div v-else-if='searchType === "Round-trip"'>
                <v-layout row-wrap mx-4>
                    <!-- DEPARTURE -->
                    <v-flex xs3 mr-2>
                        <v-autocomplete
                            label="From"
                            prepend-icon="flight_takeoff"
                            v-model="roundTripSearch.departure"
                            :items="availableDestinations"
                            return-object
                            :item-text="selectionItemText"
                            solo
                            >
                        </v-autocomplete>
                    </v-flex>
                    <!-- SWAP -->
                    <v-flex xs>
                        <v-btn flat icon color="grey" large @click="swap(roundTripSearch)">
                            <v-icon>compare_arrows</v-icon>
                        </v-btn>
                    </v-flex>
                    <!-- DESTINATION -->
                    <v-flex xs3 mr-2>
                        <v-autocomplete
                            label="To"
                            prepend-icon="flight_land"
                            v-model="roundTripSearch.destination"
                            :items="availableDestinations"
                            return-object
                            :item-text="selectionItemText"
                            solo
                            >
                        </v-autocomplete>
                    </v-flex>
                    <!-- DEPARTURE DATE -->
                    <v-flex xs3 ml-2>
                        <v-menu
                            v-model="roundTripSearch.showDepartureDateMenu"
                            :close-on-content-click="false"
                            lazy
                            transition="scale-transition"
                            offset-y full-width max-width="290px"
                            min-width="290px">
                            <template v-slot:activator="{ on }">
                                <v-text-field
                                    label="Departure date"
                                    prepend-icon="event"
                                    readonly
                                    v-on="on"
                                    v-model="roundTripSearch.departureDate">
                                </v-text-field>
                            </template>
                            <v-date-picker
                                v-model="roundTripSearch.departureDate"
                                no-title
                                scrollable
                                @input="roundTripSearch.showDepartureDateMenu = false"
                                :min="minimalDepartureDate">
                            </v-date-picker>
                        </v-menu>
                    </v-flex>
                    <!-- RETURN DATE -->
                    <v-flex xs3 ml-2>
                        <v-menu
                            v-model="roundTripSearch.showReturnDateMenu"
                            :close-on-content-click="false"
                            lazy
                            transition="scale-transition"
                            offset-y full-width max-width="290px"
                            min-width="290px">
                            <template v-slot:activator="{ on }">
                                <v-text-field
                                    label="Return date"
                                    prepend-icon="event"
                                    readonly
                                    v-on="on"
                                    v-model="roundTripSearch.returnDate">
                                </v-text-field>
                            </template>
                            <v-date-picker
                                v-model="roundTripSearch.returnDate"
                                no-title
                                scrollable
                                @input="roundTripSearch.showReturnDateMenu = false"
                                :min="minimalReturnDate">
                            </v-date-picker>
                        </v-menu>
                    </v-flex>
                    <v-flex xs4><v-btn color="primary" @click="performRoundTripSearch">Search</v-btn></v-flex>
                </v-layout>
            </div>

            <!-- MULTI CITY SEARCH -->
            <div v-else-if='searchType === "Multi-city"'>
                <div v-for="(route, index) in multiCitySearch.routes" :key="index">
                    <v-layout row-wrap mx-4>
                        <!-- DEPARTURE -->
                        <v-flex xs4 mr-3>
                            <v-autocomplete
                                label="From"
                                prepend-icon="flight_takeoff"
                                v-model="route.departure"
                                :items="availableDestinations"
                                return-object
                                :item-text="selectionItemText"
                                solo
                            >
                            </v-autocomplete>
                        </v-flex>
                        <!-- DESTINATION -->
                        <v-flex xs4>
                            <v-autocomplete
                                label="From"
                                prepend-icon="flight_takeoff"
                                v-model="route.destination"
                                :items="availableDestinations"
                                return-object
                                :item-text="selectionItemText"
                                solo
                            >
                            </v-autocomplete>
                        </v-flex>
                        <!-- DATE -->
                        <v-flex xs3 ml-2>
                        <v-menu
                            v-model="route.showMenu"
                            :close-on-content-click="false"
                            lazy
                            transition="scale-transition"
                            offset-y full-width max-width="290px"
                            min-width="290px">
                                <template v-slot:activator="{ on }">
                                    <v-text-field
                                        label="Departure date"
                                        prepend-icon="event"
                                        readonly
                                        v-on="on"
                                        v-model="route.date">
                                    </v-text-field>
                                </template>
                                <v-date-picker
                                    v-model="route.date"
                                    no-title
                                    scrollable
                                    @input="route.showMenu = false"
                                    :min="minimalDepartureDate">
                                </v-date-picker>
                            </v-menu>
                        </v-flex>
                    </v-layout>
                </div>
                <v-layout row align-start mx-4>
                    <v-btn @click="addRow">Add</v-btn>
                    <v-btn @click="removeRow">Remove</v-btn>
                    <v-btn color="primary" @click="performMultiCitySearch">Search</v-btn>
                </v-layout>
            </div>

            <!-- FILTER AND RESULTS -->
            <div>
                <v-flex xs12 sm12 md12>
                <v-layout row justify-start>

                    <!-- FILTER -->
                    <v-flex xs2 sm2 md2>
                    <v-layout column ma-4>

                        <!-- AIRLINE -->
                        <v-flex xs12 sm6>
                            <v-select
                                v-model="filterOptions.airlines"
                                :items="searchResultAirlines"
                                label="Airlines"
                                multiple
                                chips
                                style="width: 200px"
                                >
                            </v-select>
                        </v-flex>

                        <!-- STOPS -->
                        <v-flex xs3 mr-2>
                            <div>Stops:</div>
                            <v-radio-group v-model="filterOptions.stopsCount" :mandatory="false">
                                <v-radio label="Direct" value="direct"></v-radio>
                                <v-radio label="1 stop" value="1"></v-radio>
                                <v-radio label="2+ stops" value="2+"></v-radio>
                            </v-radio-group>
                        </v-flex>

                        <!-- PRICE RANGE -->
                        <v-flex xs3 mr-2>
                            <div>Price Range:</div>
                            <v-layout column>
                                
                                <v-flex mx-2 mt-4 style="width: 270px">
                                    <v-range-slider
                                        v-model="filterOptions.priceRange"
                                        :max="filterOptions.maxPrice"
                                        :min="filterOptions.minPrice"
                                        hide-details
                                        always-dirty
                                        thumb-label="always"
                                    ></v-range-slider>
                                </v-flex>
                            </v-layout>
                        </v-flex>

                        <!-- CLASS -->
                        <v-flex xs12 sm4 md4>
                            <div>Class:</div>
                            <v-checkbox v-model="filterOptions.selectedClasses" label="Economy" value="economy" hide-details></v-checkbox>
                            <v-checkbox v-model="filterOptions.selectedClasses" label="Business" value="business" hide-details></v-checkbox>
                            <v-checkbox v-model="filterOptions.selectedClasses" label="First" value="first" hide-details></v-checkbox>
                        </v-flex>

                        <v-flex xs4 mx-1 mt-2><v-btn @click="filterData">Apply Filter</v-btn></v-flex>
                        <v-flex xs4 mx-1><v-btn @click="resetFilter">Reset Filter</v-btn></v-flex>
                    </v-layout>
                    </v-flex>
                    

                    <!-- RESULTS -->
                    <v-flex xs10 sm10 md10 v-if="searchResults.length > 0">
                    <v-item-group>

                        <!-- RESULT LIST -->
                        <v-layout column>
                            <v-list>
                                <v-flex
                                    v-for="flight in this.searchResults"
                                    :key="flight.id"
                                    class="d-inline align-center">
                                    <v-item width="100%">
                                        <v-card flat>
                                                <v-layout row wrap ma-3>
                                                    <v-flex xs12 md12>
                                                        <v-card>
                                                            <v-layout>
                                                                
                                                                <v-flex xs4 md4 align-self-center mx-2>
                                                                    <v-img :src="image" max-height="90px" max-width="120px"></v-img>
                                                                </v-flex>
                                                                
                                                                <v-flex xs5 md5>
                                                                    <v-card-title primary-title>
                                                                        <div>
                                                                            <div class="headline">{{ flight.departureTime + ' - ' + flight.arrivalTime }}</div>
                                                                            <div class="pl-0">
                                                                                <span class="grey--text text--darken-2 ">{{ flight.airline }}</span>
                                                                            </div>
                                                                        </div>
                                                                    </v-card-title>
                                                                </v-flex>

                                                                <v-flex xs5 md5>
                                                                    <v-card-title primary-title>
                                                                        <div>
                                                                            <div class="d-flex">
                                                                                <span class="grey--text text--darken-2 ">{{ transitLabelContent(flight) }}</span>
                                                                            </div>  
                                                                        </div>
                                                                    </v-card-title>
                                                                </v-flex>

                                                                <v-flex xs5 md5>
                                                                    <v-card-title primary-title>
                                                                        <div>
                                                                            <div class="headline">{{ flight.departureCode + ' - ' + flight.destinationCode }}</div>
                                                                            <div class="d-flex">
                                                                                <span class="grey--text text--darken-2 ">{{ flight.flightDuration + ' minutes'}}</span>
                                                                            </div>  
                                                                        </div>
                                                                    </v-card-title>
                                                                </v-flex>

                                                                <v-flex xs5 md5>
                                                                    <v-card-title primary-title>
                                                                        <div>
                                                                            <div class="headline">{{ "from " + flight.ticketPrice + "€" }}</div> 
                                                                        </div>
                                                                    </v-card-title>
                                                                </v-flex>

                                                                <v-flex>
                                                                    <v-card-actions>
                                                                        <v-spacer></v-spacer>
                                                                        <v-layout row>
                                                                            <v-btn flat @click.stop="doShowDeal(flight)">View Deal</v-btn>
                                                                            <v-btn flat @click.stop="flightSelected(flight)" v-if="isRegisteredUser">
                                                                                Reserve
                                                                                <v-icon right>input</v-icon>
                                                                            </v-btn>
                                                                        </v-layout>
                                                                    </v-card-actions>
                                                                </v-flex>
                                                            </v-layout>
                                                        </v-card>
                                                    </v-flex>
                                                </v-layout>
                                        </v-card>
                                    </v-item>
                                </v-flex>
                            </v-list>
                        </v-layout>

                        <!-- PAGE -->
                        <v-layout justify-center>
                            <v-btn block flat v-if="this.page > 0" @click="previousPage"><v-icon>arrow_backward</v-icon></v-btn>
                            <v-btn block flat disabled v-else><v-icon>arrow_backward</v-icon></v-btn>
                            <v-btn block flat @click="nextPage"><v-icon>arrow_forward</v-icon></v-btn>
                        </v-layout>
                    </v-item-group>
                    </v-flex>

                </v-layout>
                </v-flex>
            </div>

            <!-- VIEW DEAL -->
            <v-dialog v-model="showDeal" max-width="400px">
                <v-card>
                    <v-card-title>
                        <span class="headline">Trip Summary</span>
                    </v-card-title>
                    <v-card-text>
                        <v-container grid-list-md>
                            <v-layout wrap>
                                <v-flex>
                                    <h2>{{ selectedFlight.departureDate }}</h2>
                                    <p></p>
                                    <h3>Flight number: {{ selectedFlight.flightNumber }}</h3>
                                    <p></p>
                                    <p>From: {{ selectedFlight.departureAirport }} ({{ selectedFlight.departureCode }})</p>
                                    <p>To: {{ selectedFlight.destinationAirport }} ({{ selectedFlight.destinationCode }})</p>
                                    <p>Airline: {{ selectedFlight.airline }}</p>
                                    <h3>{{ selectedFlight.departureTime + ' - ' + selectedFlight.arrivalTime}}</h3>
                                    <p>{{ selectedFlight.flightDuration }}, {{ selectedFlight.transitCount }} stop</p>
                                    <p>Distance: {{ selectedFlight.flightDistance }}km</p>
                                    <p>Stops:</p>
                                    <p v-for="t in selectedFlight.transitDestinations" :key="t.id">{{ t.name + ', ' + t.airportName + ' (' + t.airportCode + ')'}}</p>
                                    <p>Passenger count: {{ passengersCount }}</p>
                                    <p>Class: {{ searchClass }}</p>
                                    <h2>Single ticket price: {{ selectedFlight.ticketPrice }}€</h2>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn flat @click="showDeal = false">Close</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </div>





        <flight-reservation
            :show="showReservationStepper"
            :flight="selectedFlight"
            :passengerCountSearch="passengersCount"
            @goBack="showReservationStepper = false"
        />

    </div>

</template>


<script>
var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

import FlightReservation from "@/components/flightsSearch/FlightReservation.vue"

export default {
    components: {
        "flight-reservation": FlightReservation
    },
    computed: {
        minimalDepartureDate() {
            var today = new Date();
            return today.toISOString().substr(0, 10);
        },
        minimalReturnDate() {
            if(this.roundTripSearch.departureDate) {
                var departureDate_ms = Date.parse(this.roundTripSearch.departureDate);
                var returnDate = new Date();
                returnDate.setTime(departureDate_ms + 86400000);
                return returnDate.toISOString().substr(0, 10);
            } else {
                return new Date().toISOString().substr(0, 10);
            }
        },
        isRegisteredUser(){
            if(localStorage.getItem('role') == "ROLE_REGISTERED_USER")
                return true
            return false
        }
    },
    data() {
        return {
            
            // ============================================================
            // SEARCH PARAMETERS
            // ============================================================

            searchType: "One-way",
            searchTypeItems: ["One-way", "Round-trip", "Multi-city"],
            searchClass: "Economy",
            searchClassItems: ["Economy", "Business", "First"],
            
            showPassengersCountMenu: false,
            passengersCount: 1,

            oneWaySearch: {
                showMenu: false,
                departure: { id: undefined },
                destination: { id: undefined },
                date: undefined,
                filterEnabled: false
            },
            roundTripSearch: {
                departure: "",
                destination: "",
                departureDate: "",
                showDepartureDateMenu: false,
                returnDate: "",
                showReturnDateMenu: false
            },
            multiCitySearch: {
                routes: [
                    {
                        showMenu: false,
                        departure: "",
                        destination: "",
                        date: ""
                    },
                    {
                        showMenu: false,
                        departure: "",
                        destination: "",
                        date: ""
                    }
                ]
            },

            // ============================================================
            // SEARCH RESULTS
            // ============================================================

            searchResults: [],
            page: 0,
            size: 5,
            image: "https://carwad.net/sites/default/files/aeroplane-logo-102302-3436257.jpg",
            showDeal: false,
            selectedFlight: {},

            // ============================================================
            // FILTER PARAMETERS
            // ============================================================
            searchResultAirlines: [],
            filterOptions: {
                airlines: [],
                stopsCount: [],
                minPrice: 0,
                maxPrice: 2000,
                priceRange: [0, 2000],
                selectedClasses: [],
            },

            // ============================================================
            // AUTOCOMPLETE DATA
            // ============================================================

            availableDestinations: [],

            // ============================================================
            // RESERVATION
            // ============================================================

            showReservationStepper: false,
        }
    },
    methods: {
        // ============================================================
        // SEARCH PARAMETERS
        // ============================================================
        selectionItemText: item => item.name + ' (' + item.airportCode + ')',
        incrementPassengersCount() {
            if(this.passengersCount < 20)
                this.passengersCount++;
        },
        decrementPassengersCount() {
            if(this.passengersCount > 1)
                this.passengersCount--;
        },
        swap(searchType) {
            [searchType.departure, searchType.destination] = [searchType.destination, searchType.departure];
        },
        // ============================================================
        // ONE WAY SEARCH
        // ============================================================

        performOneWaySearch() {

            // TODO: config za token (Promeniti u POST i slati objekat)

            if(this.oneWaySearch.departure.id && this.oneWaySearch.destination.id && this.oneWaySearch.date) {

                if(!this.oneWaySearch.filterEnabled) {
                    
                    // SEARCH

                    this.$axios.get('http://localhost:8080/api/flightSearch/oneWaySearch', {
                            params: {
                                page: this.page,
                                size: this.size,
                                departureId: this.oneWaySearch.departure.id,
                                destinationId: this.oneWaySearch.destination.id,
                                departureDate: this.oneWaySearch.date,
                                passengerCount: this.passengersCount,
                                flightClassString: this.searchClass
                            }
                        }).then((response) => {
                            if(response.data.length > 0) {
                                this.searchResults = response.data;
                            } else {
                                this.page -= 1;
                                this.searchResults = [];
                                this.$swal.fire({
                                    title: 'No results', 
                                    html: 'Search with given parameters returned 0 results.',
                                    type: 'info',
                                    showConfirmButton: false,
                                    timer: 1500,
                                    allowOutsideClick: true
                                });
                            }
                        }).catch((error) => {
                            this.$swal("Error", error.response.data.message, 'error');
                        });
                } else {

                    // FILTER

                    var stopsCount = 2;


                    if(this.filterOptions.stopsCount == "direct") {
                        stopsCount = 0;
                        console.log(stopsCount);
                    } else if(this.filterOptions.stopsCount == "1") {
                        stopsCount = 1;
                        console.log(stopsCount);
                    } else if(this.filterOptions.stopsCount == "2+") {
                        stopsCount = 2;
                        console.log(stopsCount);
                    }

                    var leftBound = this.filterOptions.priceRange[0];
                    var rightBound = this.filterOptions.priceRange[1];

                    var objectToSend = {
                        departureId: this.oneWaySearch.departure.id,
                        destinationId: this.oneWaySearch.destination.id,
                        departureDate: this.oneWaySearch.date,
                        passengerCount: this.passengersCount,
                        flightClassString: this.searchClass,
                        airlines: this.filterOptions.airlines,
                        stopsCount: stopsCount,
                        minPrice: leftBound,
                        maxPrice: rightBound
                    }

                    yourConfig.params = {
                        page: this.page,
                        size: this.size
                    }

                    this.$axios.post('http://localhost:8080/api/flightSearch/oneWayFilterSearch', objectToSend, yourConfig)
                        .then((response) => {
                            if(response.data.length > 0) {
                                this.searchResults = response.data;
                            } else {
                                this.page -= 1;
                                this.searchResults = [];
                                this.$swal.fire({
                                    title: 'No results', 
                                    html: 'Search with given parameters returned 0 results.',
                                    type: 'info',
                                    showConfirmButton: false,
                                    timer: 1500,
                                    allowOutsideClick: true
                                });
                            }
                        }).catch((error) => {
                            this.$swal("Error", error.response.data.message, 'error');
                        });
                }

            } else {
                this.$swal("Error", "Please fill out all fields.", 'error');
                return;
            }
        },

        // ============================================================
        // ROUND TRIP SEARCH
        // ============================================================

        performRoundTripSearch() {
            // TODO: Posalji GET na server i popuni listu rezultata
        },

        // ============================================================
        // MULTI CITY SEARCH
        // ============================================================

        addRow() {
            if(this.multiCitySearch.routes.length < 7)
                this.multiCitySearch.routes.push( {from: "", to: ""} );
            else
                return;
        },
        removeRow() {
            if(this.multiCitySearch.routes.length > 2)
                this.multiCitySearch.routes.splice(-1, 1);
        },
        performMultiCitySearch() {
            // TODO: Posalji GET na server i popuni listu rezultata
        },

        // ============================================================
        // PAGINATION
        // ============================================================
        nextPage() {
            
            // TODO: Ovo radi samo za 1. pretragu - uraditi i za ostale

            this.page += 1;

            if(this.oneWaySearch.departure.id && this.oneWaySearch.destination.id && this.oneWaySearch.date) {

                this.$axios.get('http://localhost:8080/api/flightSearch/oneWaySearch', {
                        params: {
                            page: this.page,
                            size: this.size,
                            departureId: this.oneWaySearch.departure.id,
                            destinationId: this.oneWaySearch.destination.id,
                            departureDate: this.oneWaySearch.date,
                            passengerCount: this.passengersCount,
                            flightClassString: this.searchClass
                        }
                    }).then((response) => {
                        
                        if(response.data.length > 0) {
                            this.searchResults = response.data;
                        } else {
                            this.page -= 1;
                            this.$swal({
                                title: 'No results', 
                                html: 'There are no more flight that satisfy the given search criteria.',
                                type: 'info',
                                showConfirmButton: false,
                                timer: 1500,
                                allowOutsideClick: true
                            });
                        }

                    }).catch((error) => {
                        this.$swal("Error", error.response.data.message, 'error');
                    });
            } else {
                this.$swal("Error", "Please fill out all fields.", 'error');
                return;
            }
        },
        previousPage() {
            this.page -= 1;

            if(this.searchType == "One-way")
                this.performOneWaySearch();
            else if(this.searchType == "Round-trip")
                this.performRoundTripSearch();
            else if(this.searchType == "Multi-city")
                this.performMultiCitySearch();
        },

        // ============================================================
        // SEARCH RESULTS
        // ============================================================

        flightSelected(flight) {
           this.selectedFlight = flight;
           this.showReservationStepper = true;
        },
        transitLabelContent(flight) {
            if(flight.transitCount == 0)
                return "direct";
            else if(flight.transitCount == 1)
                return "1 stop";
            else
                return flight.transitCount + " stops";
        },
        doShowDeal(flight) {
            this.selectedFlight = flight;
            this.showDeal = true;
        },

        // ============================================================
        // FILTER
        // ============================================================
        resetFilter() {

            this.filterOptions = {
                airlines: [],
                stopsCount: [],
                minPrice: 0,
                maxPrice: 2000,
                priceRange: [0, 2000],
                selectedClasses: [],
            };

            this.filterOptions.airlines = this.searchResultAirlines;

            if(this.searchType == "One-way") {
                this.oneWaySearch.filterEnabled = false;
                this.performOneWaySearch();
            }
            else if(this.searchType == "Round-trip") {
                this.roundTripSearch.filterEnabled = false;
                this.performRoundTripSearch();
            }
            else if(this.searchType == "Multi-city") {
                this.multiCitySearch.filterEnabled = false;
                this.performMultiCitySearch();
            }
        },
        filterData() {
            
            if(this.searchType == "One-way") {
                this.oneWaySearch.filterEnabled = true;
                this.performOneWaySearch();
            }
            else if(this.searchType == "Round-trip") {
                this.roundTripSearch.filterEnabled = true;
                this.performRoundTripSearch();
            }
            else if(this.searchType == "Multi-city") {
                this.multiCitySearch.filterEnabled = true;
                this.performMultiCitySearch();
            }
        }
    },
    created() {

        this.$axios.get('http://localhost:8080/api/flightSearch/getAllDestinations', yourConfig)
            .then((response) => {
                this.availableDestinations = response.data;
            }).catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });

        // TOOO: OVO IZMESTITI na kad se klikne search da get-uje samo one aviokompanije koje imaju let na datim parametrima
        this.$axios.get('http://localhost:8080/api/flightSearch/getAirlineNames', yourConfig)
            .then((response) => {
                this.searchResultAirlines = response.data;
                this.filterOptions.airlines = this.searchResultAirlines;
            }).catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });
    }
}
</script>

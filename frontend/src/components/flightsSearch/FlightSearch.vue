<template>
    <div>
        <v-layout row-wrap mx-4 mt-4>
            <!-- TYPE -->
            <v-flex xs12 sm6 d-flex>
                <v-select
                    v-model="searchType"
                    :items="searchTypeItems"
                    label="Type"
                    solo
                ></v-select>
            </v-flex>
            <!-- CLASS -->
            <v-flex xs12 sm6 d-flex>
                <v-select
                    v-model="searchClass"
                    :items="searchClassItems"
                    label="Flight Class"
                    solo
                ></v-select>
            </v-flex>
            <!-- PASSENGER COUNT -->
            <v-flex xs12 sm6 d-flex>
                <v-menu
                    v-model="showPassengersCountMenu"
                    :close-on-content-click="false"
                    :nudge-width="200"
                    offset-x
                    >

                    <template v-slot:activator="{ on }" >
                        <v-btn v-on="on">Passenger count: {{ passengersCount }}</v-btn>
                    </template>

                    <v-card>
                        <v-card-title>
                            <v-layout column>
                                <v-layout pt-2 row wrap justify-center>
                                    <!-- MINUS -->
                                    <v-flex xs1 ma-2 d-inline-block>
                                        <v-btn flat icon v-if="this.passengersCount > 1" @click="--passengersCount">
                                            <v-icon>indeterminate_check_box</v-icon>
                                        </v-btn>
                                        <v-btn v-else disabled flat icon>
                                            <v-icon>indeterminate_check_box</v-icon>
                                        </v-btn>
                                    </v-flex>
                                     <!-- COUNT -->
                                    <v-flex xs1 ma-2 d-inline-block justify-center align-center>
                                        <div> {{ this.passengersCount }} </div>
                                    </v-flex>
                                     <!-- PLUS -->
                                    <v-flex xs1 ma-2 d-inline-block>
                                        <v-btn flat icon v-if="this.passengersCount < 20" @click="++passengersCount">
                                            <v-icon>add_box</v-icon>
                                        </v-btn>
                                        <v-btn v-else disabled flat icon>
                                            <v-icon>add_box</v-icon>
                                        </v-btn>
                                    </v-flex>
                                </v-layout>
                            </v-layout>
                        </v-card-title>
                    </v-card>
                </v-menu>
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
                <v-flex xs4><v-btn @click="performOneWaySearch">Search</v-btn></v-flex>
            </v-layout>
        </div>

        <!-- ROUND TRIP SEARCH -->
        <div v-else-if='searchType === "Round-trip"'>
            <v-layout row-wrap ma-4>
                <!-- DEPARTURE -->
                <v-flex xs3 mr-2>
                    <v-text-field
                        label="From"
                        prepend-icon="flight_takeoff"
                        v-model="roundTripSearch.departure">
                    </v-text-field>
                </v-flex>
                <!-- SWAP -->
                <v-flex xs>
                    <v-btn flat icon color="grey" large @click="swap(roundTripSearch)">
                        <v-icon>compare_arrows</v-icon>
                    </v-btn>
                </v-flex>
                <!-- DESTINATION -->
                <v-flex xs3 mr-2>
                    <v-text-field
                        label="To"
                        prepend-icon="flight_land"
                        v-model="roundTripSearch.destination">
                    </v-text-field>
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
                <v-flex xs4><v-btn @click="performRoundTripSearch">Search</v-btn></v-flex>
            </v-layout>
        </div>

        <!-- MULTI CITY SEARCH -->
        <div v-else-if='searchType === "Multi-city"'>
            <div v-for="(route, index) in multiCitySearch.routes" :key="index">
                <v-layout row-wrap justify-start align-center mt-2 mx-4>
                    <v-flex xs4 mr-3>
                        <v-text-field label="Departure" prepend-icon="flight_takeoff" v-model="route.departure"></v-text-field>
                    </v-flex>
                    <v-flex xs4>
                        <v-text-field label="Destination" prepend-icon="flight_land" v-model="route.destination"></v-text-field>
                    </v-flex>
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
            <v-flex xs2 ma-2>
                <v-btn small @click="addRow">Add</v-btn>
            </v-flex>
            <v-flex xs2 ma-2>
                <v-btn small @click="removeRow">Remove</v-btn>
            </v-flex>
            <v-flex xs4 ma-2><v-btn @click="performMultiCitySearch">Search</v-btn></v-flex>
        </div>
        
        <div v-if="searchResults.length > 0">
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
                            style="width: 270px"
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

                    <v-flex xs4><v-btn @click="filterData" ma-4>Apply Filter</v-btn></v-flex>
                    <v-flex xs4><v-btn @click="resetFilter" ma-4>Reset Filter</v-btn></v-flex>
                </v-layout>
                </v-flex>
                

                <!-- RESULTS -->
                <v-flex xs10 sm10 md10>
                <v-item-group>

                    <!-- RESULT LIST -->
                    <v-layout column>
                        <v-list class="scroll-y pt-0" style="height: 700px">
                            <v-flex
                                v-for="flight in this.searchResults"
                                :key="flight.id"
                                class="d-inline align-center">
                                <v-item width="100%">
                                    <div style="margin: auto;">
                                        <v-card flat>
                                            <v-container fluid>
                                                <v-layout row wrap>
                                                    <v-flex xs12 md12>
                                                        <v-card>
                                                            <v-layout>
                                                                <!--
                                                                <v-flex xs4 md4>
                                                                    <v-avatar class="ma-2" :tile="false" :size="80">
                                                                        <v-img :src="image" height="100%" max-height="170px" max-width="200px"></v-img>
                                                                    </v-avatar>
                                                                </v-flex>
                                                                -->
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
                                                                                <span class="grey--text text--darken-2 ">{{ flight.flightDuration }}</span>
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
                                                                            <v-btn flat @click="flightSelected(flight)">
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
                                            </v-container>
                                        </v-card>
                                    </div>
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
                                <h2>Tuesday, 2019-05-21</h2>
                                <p></p>
                                <h3>Flight number: LX1419</h3>
                                <p></p>
                                <p>From: Nikola Tesla (BEG)</p>
                                <p>To: Zurich Airport (ZRH)</p>
                                <p>Airline: Air France</p>
                                <h3>15:25 - 20:10</h3>
                                <p>4h 45min, 1 stop</p>
                                <p>Distance: 1878km</p>
                                <p>Stops: Heatrow Airport (LHR)</p>
                                <p>Passenger count: {{ passengersCount }}</p>
                                <p>Class: {{ searchClass }}</p>
                                <h2>Trip total: 81€</h2>
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
</template>


<script>
export default {
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
        }
    },
    data() {
        return {
            searchType: "One-way",
            searchTypeItems: ["One-way", "Round-trip", "Multi-city"],
            searchClass: "Economy",
            searchClassItems: ["Economy", "Business", "First"],
            
            showPassengersCountMenu: false,
            passengersCount: 1,

            oneWaySearch: {
                showMenu: false,
                departure: "",
                destination: "",
                date: ""
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


            // ==================================================== SEARCH

            searchResults: [],
            page: 0,
            size: 5,
            rating: 4.5,         // TODO: CHANGE
            image: "https://beebom.com/wp-content/uploads/2018/12/Lufthansa-Logo.jpg",
            showDeal: false,


            // ==================================================== FILTER
            searchResultAirlines: ["Air France", "Air Serbia", "American Airlines", "Etihad", "Lufthansa", "RyanAir", "SWISS Airlines", "WizzAir"],     // TODO: CHANGE
            filterOptions: {
                airlines: [],
                stopsCount: [],
                minPrice: 0,
                maxPrice: 1000,
                priceRange: [0, 1000],
                selectedClasses: [],
            },

            // ==================================================== AUTOCOMPLETE

            availableDestinations: ["Beijing", "Belgrade", "Dubai", "London", "Los Angeles", "Zurich"]                                                   // TODO: CHANGE
        }
    },
    methods: {
        swap(searchType) {
            [searchType.departure, searchType.destination] = [searchType.destination, searchType.departure];
        },
        addRow() {
            if(this.multiCitySearch.routes.length < 7) {
                this.multiCitySearch.routes.push( {from: "", to: ""} );
            } else {
                return;
            }
        },
        removeRow() {
            if(this.multiCitySearch.routes.length > 2) {
                this.multiCitySearch.routes.splice(-1, 1);
            }
        },
        performOneWaySearch() {
            // TODO: Posalji GET na server i popuni listu rezultata

            this.searchResults = [

                { 
                    airline: "SWISS Airlines",
                    departure: "Belgrade",
                    departureCode: "BEG",
                    destination: "Zurich",
                    destinationCode: "ZRH",
                    departureTime: "14:40",
                    arrivalTime: "16:30",
                    transitCount: 0,
                    flightDuration: "1h 50m",
                    ticketPrice: 96
                },
                { 
                    airline: "Air Serbia",
                    departure: "Belgrade",
                    departureCode: "BEG",
                    destination: "Zurich",
                    destinationCode: "ZRH",
                    departureTime: "18:05",
                    arrivalTime: "19:55",
                    transitCount: 0,
                    flightDuration: "1h 50m",
                    ticketPrice: 98
                },
                { 
                    airline: "Air Serbia",
                    departure: "Belgrade",
                    departureCode: "BEG",
                    destination: "Zurich",
                    destinationCode: "ZRH",
                    departureTime: "07:20",
                    arrivalTime: "09:10",
                    transitCount: 0,
                    flightDuration: "1h 50m",
                    ticketPrice: 81
                },
                { 
                    airline: "Air Serbia",
                    departure: "Belgrade",
                    departureCode: "BEG",
                    destination: "Zurich",
                    destinationCode: "ZRH",
                    departureTime: "12:40",
                    arrivalTime: "14:30",
                    transitCount: 0,
                    flightDuration: "1h 50m",
                    ticketPrice: 81
                },
                { 
                    airline: "Air France",
                    departure: "Belgrade",
                    departureCode: "BEG",
                    destination: "Zurich",
                    destinationCode: "ZRH",
                    departureTime: "15:25",
                    arrivalTime: "20:10",
                    transitCount: 1,
                    flightDuration: "4h 45m",
                    ticketPrice: 81
                },
            ]
        },
        performRoundTripSearch() {
            // TODO: Posalji GET na server i popuni listu rezultata
        },
        performMultiCitySearch() {
            // TODO: Posalji GET na server i popuni listu rezultata
        },
        nextPage(){
            this.page += 1;
            this.$axios.get('http://localhost:8080/api/flights/searchFlights', {
                params: {
                    page: this.page,
                    size: this.size,
                    hotelName: this.hotelName,              // TODO: CHANGE
                    hotelLocation: this.hotelLocation       // TODO: CHANGE
            }})
            .then(response => {
                if(response.data.length > 0) {
                    this.searchResults = response.data;  
                } else {
                    this.page -= 1;
                    this.empty = true; 
                    setTimeout(() => {
                        this.empty = false;
                    }, 3000)      
                }
            })
        },
        previousPage(){
            this.page -= 1;                             // TODO: CHANGE (Videti koji tip pretrage je u pitanju)
            this.fetchFlights();
        },
        flightSelected(flight){
            this.selectedFlight = flight;

            this.$axios.get('http://localhost:8080/api/hotels/getFlight/' + this.selectedFlight.id)
            .then(response => {
                console.log(response.data) 
                this.searchResults = response.data
                //this.e6 = 2;
            })
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
            this.showDeal = true;
            // TODO: Dodati prave detalje
        },
        resetFilter() {
            
            this.filterOptions = {
                airlines: [],
                stopsCount: [],
                minPrice: 0,
                maxPrice: 1000,
                priceRange: [0, 1000],
                selectedClasses: [],
            },

            this.performOneWaySearch();
        },
        filterData() {

            // TODO: Uraditi filter na backendu

            var results = this.searchResults;
            var filteredByAirline = [];

            if(this.filterOptions.airlines.length == 0) {
                filteredByAirline = results;
            } else {
                for(var i = 0; i < this.filterOptions.airlines.length; i++) {
                    for(var j = 0; j < results.length; j++) {
                        if(results[j].airline.includes(this.filterOptions.airlines[i].toString())) {
                            filteredByAirline.push(results[j]);
                        }
                    }
                }
            }

            var filteredByTransitCount = [];
            var stopsCount = 0;

            if(this.filterOptions.stopsCount[0] == "direct")
                stopsCount = 0;
            else if(this.filterOptions.stopsCount[0] == "1")
                stopsCount = 1;
            else if(this.filterOptions.stopsCount[0] == "2+")
                stopsCount = 2;

            if(this.filterOptions.stopsCount.length == 0) {
                filteredByTransitCount = filteredByAirline;
            } else {
                for(var i = 0; i < filteredByAirline.length; i++) {
                    if(stopsCount != 2) {
                        if(filteredByAirline[i].transitCount == stopsCount) {
                            filteredByTransitCount.push(filteredByAirline[i]);
                        }
                    } else {
                        if(filteredByAirline[i].transitCount >= stopsCount) {
                            filteredByTransitCount.push(filteredByAirline[i]);
                        }
                    }

                }
            }

            var filteredByPriceRange = [];

            var leftBound = this.filterOptions.priceRange[0];
            var rightBound = this.filterOptions.priceRange[1];

            for(var i = 0; i < filteredByTransitCount.length; i++) {
                if(leftBound <= filteredByTransitCount[i].ticketPrice && filteredByTransitCount[i].ticketPrice <= rightBound) {
                    filteredByPriceRange.push(filteredByTransitCount[i]);
                }
            }

            if(filteredByPriceRange.length)
                this.searchResults = filteredByPriceRange;
        }
    }
}
</script>

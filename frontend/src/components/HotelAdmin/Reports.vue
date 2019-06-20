<template>
    <div id="izvestaji">
        <v-layout row>
        <v-flex xs3 md3 sm3>
            <v-layout column>
                <v-card class="ma-3" id="hotelFeedback">
                    <v-card-title class="headline text-xs-left font-weight-light indigo--text">Average hotel feedback</v-card-title>
                    <v-divider></v-divider>
                    <v-card-text>
                        <v-layout row>
                            <span class="mr-2 display-1 indigo--text font-weight-regular">{{report.averageFeedback}}</span>
                            <v-rating
                                :value="report.averageFeedback"
                                color="indigo lighten-1"
                                dense
                                large
                                half-increments
                                readonly
                                class="pb-1"
                            ></v-rating>
                            <span class="mt-3 ml-1 grey--text text--darken-2 font-weight-light caption">{{report.numberOfFeedbacks}} reviews</span>
                        </v-layout>
                    </v-card-text>
                </v-card>

                <v-card class="ma-3" id="roomsFeedback">
                    <v-item-group>
                        <v-layout column>
                            <!-- style="height: 630px" -->
                            <v-list class="scroll-y" style="height: 1325px">
                                <v-flex
                                    v-for="roomType in this.report.roomTypes"
                                    :key="roomType.id"
                                    class="d-inline align-center">
                                <v-item width="100%">
                                    <div style="margin: auto;">
                                        <v-card flat>
                                        <v-card-text>
                                            <v-layout row>
                                                <span class="mb-1 headline indigo--text font-weight-light">{{roomType.type}}</span>
                                                <v-spacer></v-spacer>
                                                <v-rating
                                                    :value="roomType.averageFeedback"
                                                    color="indigo lighten-1"
                                                    dense
                                                    half-increments
                                                    readonly
                                                ></v-rating>
                                                <!--<span class="mt-2 ml-1 grey--text text--darken-2 font-weight-light caption">{{numberOfRoomReviews(roomType.id)}} reviews</span>-->
                                            </v-layout>
                                        </v-card-text>
                                        <v-divider></v-divider>
                                        </v-card>
                                    </div>
                                </v-item>
                                </v-flex>
                            </v-list>
                        </v-layout>
                    </v-item-group>   
                </v-card>
            </v-layout>
        </v-flex>

        <v-flex xs5 md5 sm5 class="mr-2">
            <v-layout class="mt-3" column>
                <v-flex id="dailyVisits">
                    <v-card
                        class="mt-4"
                        max-width="760px"
                        v-if="canRender">
                        <v-sheet
                        class="v-sheet--offset mx-auto"
                        color="indigo lighten-4"
                        elevation="12"
                        max-width="calc(100% - 32px)">
                            <bar-chart 
                                class="pt-2 temp"
                                :chart-data="dailyReport">
                            </bar-chart>
                        </v-sheet>

                        <v-card-text class="py-0">
                        <v-layout row>
                        <div class="title indigo--text font-weight-regular mt-2">Daily visits</div>
                        <v-spacer></v-spacer>
                        <v-flex xs5 sm5 md5>
                            <v-menu
                            v-model="dailyDateMenu"
                            :close-on-content-click="true"
                            :nudge-right="40"
                            lazy
                            transition="scale-transition"
                            offset-y
                            full-width
                            min-width="290px"
                            >
                            <template v-slot:activator="{ on }">
                                <v-text-field
                                    v-model="dailyDate"
                                    append-icon="event"
                                    v-on="on"
                                    class="my-0 py-0">
                                </v-text-field>
                            </template>
                            <v-date-picker v-model="dailyDate" @change="getDailyVisits()" @input="dailyDateMenu=false" color="indigo lighten-1"></v-date-picker>
                            </v-menu>
                        </v-flex>
                        </v-layout>
                        </v-card-text>
                    </v-card>
                </v-flex>

                <v-flex id="weeklyVisits">
                    <v-card
                        class="mt-5"
                        max-width="760px"
                        v-if="canRender">
                        <v-sheet
                        class="v-sheet--offset mx-auto"
                        color="indigo lighten-4"
                        elevation="12"
                        max-width="calc(100% - 32px)">
                            <bar-chart 
                                class="pt-2 temp"
                                :chart-data="weeklyReport">
                            </bar-chart>
                        </v-sheet>

                        <v-card-text class="py-0">
                        <v-layout row>
                        <div class="title indigo--text font-weight-regular mt-2">Weekly visits</div>
                        <v-spacer></v-spacer>
                        <v-flex xs5 sm5 md5>
                            <v-menu
                            v-model="weeklyDateMenu"
                            :close-on-content-click="true"
                            :nudge-right="40"
                            lazy
                            transition="scale-transition"
                            offset-y
                            full-width
                            min-width="290px"
                            >
                            <template v-slot:activator="{ on }">
                                <v-text-field
                                    v-model="weeklyDate"
                                    append-icon="event"
                                    v-on="on"
                                    class="my-0 py-0">
                                </v-text-field>
                            </template>
                            <v-date-picker v-model="weeklyDate" 
                                    @change="getWeeklyVisits()" @input="weeklyDateMenu=false" color="indigo lighten-1" :allowed-dates="allowedDates"></v-date-picker>
                            </v-menu>
                        </v-flex>
                        </v-layout>
                        </v-card-text>
                    </v-card>
                </v-flex>

                <v-flex id="monthlyVisits">
                    <v-card
                        class="my-5"
                        max-width="760px"
                        v-if="canRender">
                        <v-sheet
                        class="v-sheet--offset mx-auto"
                        color="indigo lighten-4"
                        elevation="12"
                        max-width="calc(100% - 32px)">
                            <bar-chart 
                                class="pt-2 temp"
                                :chart-data="yearlyReport">
                            </bar-chart>
                        </v-sheet>

                        <v-card-text class="py-0">
                        <v-layout row>
                        <div class="title indigo--text font-weight-regular mt-2">Monthly visits</div>
                        <v-spacer></v-spacer>
                        <v-flex xs3 sm3 md3>
                            <v-select
                            :items="years"
                            v-model="year"
                            class="my-0 py-0"
                            @change="getMonthlyVisits()"
                            ></v-select>
                        </v-flex>
                        </v-layout>
                        </v-card-text>
                    </v-card>
                </v-flex>
            </v-layout>                
        </v-flex>

        <v-flex xs4 sm4 md4>
        <v-card class="mt-3 mr-3" id="hotelIncome">
            <v-card-title class="headline text-xs-left font-weight-light indigo--text">Hotel income</v-card-title>
            <v-divider></v-divider>
            <v-card-text>
                <v-layout row>
                <v-menu
                v-model="dateFromMenu"
                :close-on-content-click="true"
                :nudge-right="40"
                lazy
                transition="scale-transition"
                offset-y
                full-width
                min-width="290px"
                >
                <template v-slot:activator="{ on }">
                <v-flex xs6 md6>
                    <v-text-field
                        v-model="dateFrom"
                        append-icon="event"
                        label="From"
                        class="mx-3"
                        max-width="30px"
                        v-on="on">
                    </v-text-field>
                </v-flex>
                </template>
                <v-date-picker v-model="dateFrom" @input="dateFromMenu=false" color="indigo lighten-1" :max="maxDate"></v-date-picker>
                </v-menu>
                
                <!-- datum do -->
                <v-menu
                v-model="dateToMenu"
                :close-on-content-click="true"
                :nudge-right="40"
                lazy
                transition="scale-transition"
                offset-y
                full-width
                min-width="290px"
                >
                <template v-slot:activator="{ on }">
                <v-flex xs6 md6>
                    <v-text-field
                        v-model="dateTo"
                        append-icon="event"
                        label="To"
                        class="mx-3"
                        v-on="on">
                    </v-text-field>
                </v-flex>
                </template>
                <v-date-picker v-model="dateTo" @input="dateToMenu=false" color="indigo lighten-1" :max="maxDate"></v-date-picker>
                </v-menu>

                <v-btn class="mt-2" outline block flat color="indigo lighten-3" @click="getIncomeReport">Show</v-btn>
                </v-layout>
            </v-card-text>

            <div v-if="showIncome">
            <v-divider></v-divider>
            <v-card-text v-if="showIncome">
                <v-layout column>
                    <v-layout row class="title font-weight-light indigo--text">
                        <span>From:</span>
                        <v-spacer></v-spacer>
                        <span>{{ showDateFrom }}</span>
                    </v-layout><br>

                    <v-layout row class="title font-weight-light indigo--text">
                        <span>To:</span>
                        <v-spacer></v-spacer>
                        <span>{{ showDateTo }}</span>
                    </v-layout><br>

                    <v-divider light></v-divider><br>

                    <v-layout row class="headline font-weight-medium indigo--text text-uppercase">
                        <span>Total income:</span>
                        <v-spacer></v-spacer>
                        <span>{{ income }}€</span>
                    </v-layout>
                </v-layout>
            </v-card-text>
            </div>
        </v-card>
        </v-flex>

        </v-layout>
    </div>
</template>

<script>
import Chart from './Chart.vue'

export default {
    components: {
        'bar-chart' : Chart
    },

    props: ['selectedHotel'],

    data(){
        return{
            report: {},

            dateFromMenu: false,
            dateFrom: new Date().toISOString().substr(0, 10),
            dateToMenu: false,
            dateTo: new Date().toISOString().substr(0, 10),
            maxDate: new Date().toISOString().substr(0, 10),

            income: '',
            canRender: false,
            showIncome: false,
            showDateFrom: '',
            showDateTo: '',

            dailyReport: {},
            dailyDate: new Date().toISOString().substr(0, 10),
            dailyDateMenu: false,

            weeklyReport: {},
            weeklyDate: new Date().toISOString().substr(0, 10),
            weeklyDateMenu: false,

            yearlyReport: {},
            year: '',   

            months: { 0 : 'JAN', 1 : 'FEB', 2 : 'MAR', 3 : 'APR', 4 : 'MAY', 5 : 'JUN', 6 : 'JUL', 7 : 'AUG', 8 : 'SEP', 9 : 'OCT', 10 : 'NOV', 11 : 'DEC' }
        }
    },

    methods:{
        getIncomeReport(){
            this.$axios
                .get('/api/hotels/getIncomeReport/', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    },
                    params: {
                        adminUsername : localStorage.getItem("username"),
                        dateFrom : this.dateFrom,
                        dateTo: this.dateTo
                    }
                }).then(response => {
                    this.income = response.data;
                    this.showDateFrom = this.dateFrom;
                    this.showDateTo = this.dateTo;
                    this.showIncome = true;
                })
        },
        getMonthlyVisits(){
            this.$axios
                .get('/api/hotels/monthlyReport/', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    },
                    params: {
                        adminUsername : localStorage.getItem("username"),
                        numberOfYears : new Date().getFullYear() - this.year
                    }
                }).then(response => {
                    this.yearlyReport = {
                        labels : (Array.from(Object.keys(response.data), x=>this.months[new Date(parseInt(x)).getMonth()])),
                        datasets : [
                            {
                            backgroundColor: '#5c6bc0',
                            hoverBackgroundColor: '#7986cb',
                            borderWidth: 1,
                            pointBorderColor: '#249EBF',
                            data: Object.values(response.data)
                            }
                        ]
                    }
                    this.year = new Date(parseInt((Object.keys(response.data))[0])).getFullYear()
                })
        },
        getWeeklyVisits(){
            this.$axios
                .get('/api/hotels/weeklyReport/', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    },
                    params: {
                        adminUsername : localStorage.getItem("username"),
                        dateFrom : this.weeklyDate
                    }
                }).then(response => {
                    this.weeklyReport = {
                        labels : (Array.from(Object.keys(response.data), x=>new Date(parseInt(x)).toLocaleString(undefined, {month : '2-digit', day: '2-digit'}).substr(0, 5).replace('-', '/'))),
                        datasets : [
                            {
                            backgroundColor: '#5c6bc0',
                            hoverBackgroundColor: '#7986cb',
                            borderWidth: 1,
                            pointBorderColor: '#249EBF',
                            data: Object.values(response.data)
                            }
                        ]
                    }
                })
        },
        getDailyVisits(){
            this.$axios
                .get('/api/hotels/dailyReport/', {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    },
                    params: {
                        adminUsername : localStorage.getItem("username"),
                        dateFrom : this.dailyDate
                    }
                }).then(response => {
                    this.dailyReport = {
                        labels : (Array.from(Object.keys(response.data), x=>new Date(parseInt(x)).toLocaleString(undefined, {month : '2-digit', day: '2-digit'}).substr(0, 5).replace('-', '/'))),
                        datasets : [
                            {
                            backgroundColor: '#5c6bc0',
                            hoverBackgroundColor: '#7986cb',
                            borderWidth: 1,
                            pointBorderColor: '#249EBF',
                            data: Object.values(response.data)
                            }
                        ]
                    }
                })
        },
        allowedDates: val => new Date(val).getDay() == 1
    },
    computed: {
        years () {
            const year = new Date().getFullYear()
            return Array.from({length: year - 1969}, (value, index) => 1970 + index)
        }
    },
    created(){
        var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

        this.$axios
            .get('/api/hotels/getReport/' + localStorage.getItem('username'), yourConfig)
            .then(response => {
                this.report = response.data;
                this.dailyReport.labels = Array.from(Object.keys(this.report.dailyReports), x=>new Date(parseInt(x)).toLocaleString(undefined, {month : '2-digit', day: '2-digit'}).substr(0, 5));
                this.dailyReport.datasets = [
					{
                        backgroundColor: '#5c6bc0',
                        hoverBackgroundColor: '#7986cb',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: Object.values(this.report.dailyReports)
					}
                ]

                this.weeklyReport.labels = (Array.from(Object.keys(this.report.weeklyReports), x=>new Date(parseInt(x)).toLocaleString(undefined, {month : '2-digit', day: '2-digit'}).substr(0, 5).replace('-', '/')));
                this.weeklyReport.datasets = [
                    {
                        backgroundColor: '#5c6bc0',
                        hoverBackgroundColor: '#7986cb',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
                        data: Object.values(this.report.weeklyReports)
                    }
                ]

                this.yearlyReport.labels = (Array.from(Object.keys(this.report.yearlyReport), x=>this.months[new Date(parseInt(x)).getMonth()]));
                this.yearlyReport.datasets = [
                    {
                        backgroundColor: '#5c6bc0',
                        hoverBackgroundColor: '#7986cb',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
                        data: Object.values(this.report.yearlyReport)
                    }
                ],
                this.year = new Date(parseInt((Object.keys(this.report.yearlyReport))[0])).getFullYear()

                this.canRender = true;
            })
    }
}
</script>

<style scoped>
  .v-sheet--offset {
    top: -24px;
    position: relative;
  }
</style>

<template>
    <v-card>
        <v-toolbar flat>   
            <v-icon>report</v-icon> 
            <v-toolbar-title>Reports</v-toolbar-title>      
        </v-toolbar>
        <v-flex v-if="canRender"
            id="scroll-target"
            style="max-height: 500px"
            class="scroll-y">
            <v-layout column>
                <!--
                <div class="title black--text font-weight-big mt-3">Daily report</div>
                <bar-chart 
                    class="pt-2 temp"
                    :chart-data="dailyReport">
                </bar-chart>
                <v-card-text class="py-0">
                    <v-layout row>
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
                                min-width="260px">
                                <template v-slot:activator="{ on }">
                                    <v-text-field
                                        v-model="dailyDate"
                                        append-icon="event"
                                        v-on="on"
                                        class="my-0 py-0">
                                    </v-text-field>
                                </template>
                                <v-date-picker 
                                    v-model="dailyDate" 
                                    @change="getDailyReport()" 
                                    @input="dailyDateMenu=false">
                                </v-date-picker>
                            </v-menu>
                        </v-flex>
                    </v-layout>
                </v-card-text>

                <v-divider/>
                -->

                <div class="title black--text font-weight-big mt-3">Weekly report</div>
                <bar-chart 
                    class="pt-2 temp"
                    :chart-data="weeklyReport">
                </bar-chart>
                <v-card-text class="py-0">
                        <v-layout row>
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
                                min-width="260px">
                                <template v-slot:activator="{ on }">
                                    <v-text-field
                                        v-model="weeklyDate"
                                        append-icon="event"
                                        v-on="on"
                                        class="my-0 py-0"
                                        width="40px">
                                    </v-text-field>
                                </template>
                                <v-date-picker 
                                    v-model="weeklyDate" 
                                    @change="getWeeklyReport()" 
                                    @input="weeklyDateMenu=false" 
                                    :allowed-dates="allowedDates">
                                </v-date-picker>
                            </v-menu>
                        </v-flex>
                        </v-layout>
                        </v-card-text>
           
                <v-divider/>
                <v-spacer/>

                <div class="title black--text font-weight-big mt-3">Yearly report</div>
                <bar-chart 
                    class="pt-2 temp"
                    :chart-data="yearlyReport">
                </bar-chart> 
                
                <v-card-text class="py-0">
                <v-layout row>
                <v-spacer></v-spacer>
                <v-flex xs3 sm3 md3>
                    <v-select
                    :items="years"
                    v-model="year"
                    class="my-0 py-0"
                    @change="getMonthlyReport()"
                    ></v-select>
                </v-flex>
                </v-layout>
                </v-card-text>

                <v-divider/>
                <v-spacer/>

                <div class="title black--text font-weight-big mt-3">Rent a car income</div>
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
                            min-width="290px">
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
                            <v-date-picker v-model="dateFrom" @input="dateFromMenu=false" :max="maxDate"></v-date-picker>
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
                            min-width="290px">
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
                            <v-date-picker v-model="dateTo" @input="dateToMenu=false" :max="maxDate"></v-date-picker>
                        </v-menu>

                        <v-btn class="mt-2" outline block flat color="black lighten-3" @click="getIncomeReport">Show</v-btn>
                    </v-layout>
                </v-card-text>
                <v-flex v-if="showIncome">
                    <v-card-text v-if="showIncome">
                        <v-layout column>
                            <v-layout row class="title font-weight-light black--text">
                                <span>From:</span>
                                <v-spacer></v-spacer>
                                <span>{{ showDateFrom }}</span>
                            </v-layout><br>

                            <v-layout row class="title font-weight-light black--text">
                                <span>To:</span>
                                <v-spacer></v-spacer>
                                <span>{{ showDateTo }}</span>
                            </v-layout><br>

                            <v-divider light></v-divider><br>

                            <v-layout row class="headline font-weight-medium black--text text-uppercase">
                                <span>Total income:</span>
                                <v-spacer></v-spacer>
                                <span>{{ income }}€</span>
                            </v-layout>
                        </v-layout>
                    </v-card-text>
                </v-flex>
            </v-layout>
        </v-flex>
    </v-card>
</template>


<script>

import Chart from './Chart.vue'


export default {

    components: {
        'bar-chart' : Chart
    },

    props: ['rentACarId'],
    
    data: () => ({

        dialog: false,

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

        
    }),
    methods : {

        getIncomeReport(){
            this.$axios
                .get('http://localhost:8080/api/rentACarIncomeReport/', {
                    params: {
                        rentACarId : this.rentACarId,
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
        getMonthlyReport(){
            this.$axios
                .get('http://localhost:8080/api/rentACarMonthlyReport/', {
                    params: {
                        rentACarId : this.rentACarId,
                        numberOfYears : new Date().getFullYear() - this.year
                    }
                }).then(response => {
                    this.yearlyReport = {
                        labels : (Array.from(Object.keys(response.data), x=>this.months[new Date(parseInt(x)).getMonth()])),
                        datasets : [
                            {
                                backgroundColor: '#A4A4A4',
                                hoverBackgroundColor: '#424242',
                                borderWidth: 1,
                                pointBorderColor: '#F4FA58',
                            data: Object.values(response.data)
                            }
                        ]
                    }
                    this.year = new Date(parseInt((Object.keys(response.data))[0])).getFullYear()
                })
        },
        getWeeklyReport(){
            this.$axios
                .get('http://localhost:8080/api/rentACarWeeklyReport/', {
                    params: {
                        rentACarId : this.rentACarId,
                        dateFrom : this.weeklyDate
                    }
                }).then(response => {
                    this.weeklyReport = {
                        labels : (Array.from(Object.keys(response.data), x=>new Date(parseInt(x)).toLocaleString(undefined, {month : '2-digit', day: '2-digit'}).substr(0, 5).replace('-', '/'))),
                        datasets : [
                            {
                                backgroundColor: '#A4A4A4',
                                hoverBackgroundColor: '#424242',
                                borderWidth: 1,
                                pointBorderColor: '#F4FA58',
                                data: Object.values(response.data)
                            }
                        ]
                    }
                })
        },
        getDailyReport(){
            this.$axios
                .get('http://localhost:8080/api/rentACarDailyReport/', {
                    params: {
                        rentACarId : this.rentACarId,
                        dateFrom : this.dailyDate
                    }
                }).then(response => {
                    console.log(response.data)
                    this.dailyReport = {
                        labels : (Array.from(Object.keys(response.data), x=>new Date(parseInt(x)).toLocaleString(undefined, {month : '2-digit', day: '2-digit'}).substr(0, 5).replace('-', '/'))),
                        datasets : [
                            {
                                backgroundColor: '#A4A4A4',
                                hoverBackgroundColor: '#424242',
                                borderWidth: 1,
                                pointBorderColor: '#F4FA58',
                                data: Object.values(response.data)
                            }
                        ]
                    }
                })
        },

        allowedDates: val => new Date(val).getDay() == 1
    },
    computed: {
        years(){
            const year = new Date().getFullYear()
            return Array.from({length: year - 1969}, (value, index) => 1970 + index)
        }
    },
    created(){
        this.$axios
            .get('http://localhost:8080/api/rentACarReport/' + this.rentACarId)
            .then(response => {
                
                this.report = response.data;

                /*
                this.dailyReport.labels = Array.from(Object.keys(this.report.dailyReports), x=>new Date(parseInt(x)).toLocaleString(undefined, {month : '2-digit', day: '2-digit'}).substr(0, 5));
                this.dailyReport.datasets = [
					{
                        backgroundColor: '#A4A4A4',
                        hoverBackgroundColor: '#424242',
						borderWidth: 1,
						pointBorderColor: '#F4FA58',
						data: Object.values(this.report.dailyReports)
					}
                ]
                */

                this.weeklyReport.labels = (Array.from(Object.keys(this.report.weeklyReports), x=>new Date(parseInt(x)).toLocaleString(undefined, {month : '2-digit', day: '2-digit'}).substr(0, 5).replace('-', '/')));
                this.weeklyReport.datasets = [
                    {
                        backgroundColor: '#A4A4A4',
                        hoverBackgroundColor: '#424242',
						borderWidth: 1,
						pointBorderColor: '#F4FA58',
                        data: Object.values(this.report.weeklyReports)
                    }
                ]

                this.yearlyReport.labels = (Array.from(Object.keys(this.report.yearlyReport), x=>this.months[new Date(parseInt(x)).getMonth()]));
                this.yearlyReport.datasets = [
                    {
                        backgroundColor: '#A4A4A4',
                        hoverBackgroundColor: '#424242',
						borderWidth: 1,
						pointBorderColor: '#F4FA58',
                        data: Object.values(this.report.yearlyReport)
                    }
                ],
                this.year = new Date(parseInt((Object.keys(this.report.yearlyReport))[0])).getFullYear()

                this.canRender = true;

            })
    }
}

</script>


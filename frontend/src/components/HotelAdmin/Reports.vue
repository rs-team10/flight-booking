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
                            <v-list class="scroll-y" style="height: 630px">
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
                                                <span class="mt-2 ml-1 grey--text text--darken-2 font-weight-light caption">{{numberOfRoomReviews(roomType.id)}} reviews</span>
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

        <v-flex xs5 md5 sm5>
            <v-layout class="mt-4" column>
                <v-flex id="dailyVisits">
                    <v-card
                        class="mt-3"
                        max-width="760px">
                        <v-sheet
                        class="v-sheet--offset mx-auto"
                        color="indigo lighten-1"
                        elevation="12"
                        max-width="calc(100% - 32px)">
                            <v-sparkline
                                :labels="(Array.from(Object.keys(this.report.dailyReports), x=>x.substr(5, 5).replace('-', '/'))).sort()"
                                :value="Object.values(this.report.dailyReports)"
                                color="white"
                                line-width="2"
                                padding="16"
                            ></v-sparkline>
                        </v-sheet>

                        <v-card-text class="pt-0">
                        <div class="title indigo--text font-weight-regular mb-2">Daily visits</div>
                        </v-card-text>
                    </v-card>
                </v-flex>

                <v-flex id="weeklyVisits">
                    <v-card
                        class="mt-5"
                        max-width="760px">
                        <v-sheet
                        class="v-sheet--offset mx-auto"
                        color="indigo lighten-1"
                        elevation="12"
                        max-width="calc(100% - 32px)"
                        >
                            <v-sparkline
                                :labels="labels"
                                :value="weeklyValues"
                                color="white"
                                line-width="2"
                                padding="16"
                            ></v-sparkline>
                        </v-sheet>

                        <v-card-text class="pt-0">
                        <div class="title indigo--text font-weight-regular mb-2">Weekly visits</div>

                        </v-card-text>
                    </v-card>
                </v-flex>

                <v-flex id="monthlyVisits">
                    <v-card
                        class="mt-5"
                        max-width="760px">
                        <v-sheet
                        class="v-sheet--offset mx-auto"
                        color="indigo lighten-1"
                        elevation="12"
                        max-width="calc(100% - 32px)">
                            <v-sparkline
                                :labels="monthlyLabels"
                                :value="value"
                                color="white"
                                line-width="2"
                                padding="16"
                            ></v-sparkline>
                        </v-sheet>

                        <v-card-text class="pt-0">
                        <div class="title indigo--text font-weight-regular mb-2">Monthly visits</div>
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
                <v-date-picker v-model="dateFrom" @input="dateFromMenu=false" color="indigo lighten-1"></v-date-picker>
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
                <v-date-picker v-model="dateTo" @input="dateToMenu=false" color="indigo lighten-1"></v-date-picker>
                </v-menu>
                </v-layout>
            </v-card-text>
        </v-card>
        </v-flex>

        </v-layout>
    </div>
</template>

<script>
export default {
    props: ['selectedHotel'],

    data(){
        return{
            report: {},
            labels: ['12am','3am','6am','9am','12pm','3pm','6pm','9pm'],
            weeklyValues: [200,675,410,390,310,460,250,240],
            value: [200,675,410,390,310,460,250,240, 200, 200, 200, 200],
            monthlyLabels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],

            dateFromMenu: false,
            dateFrom: new Date().toISOString().substr(0, 10),
            dateToMenu: false,
            dateTo: new Date().toISOString().substr(0, 10),
        }
    },

    methods:{
        numberOfRoomReviews(roomTypeId){
            return 0;
        }
    },
    created(){
        this.$axios
            .get('http://localhost:8080/api/hotels/getReport/' + this.selectedHotel.id)
            .then(response => {
                this.report = response.data;
                var keys = Object.keys(this.report.dailyReports)
                var temp = Array.from(Object.keys(this.report.dailyReports), x=>x.substr(8, 2))
                console.log(temp)
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

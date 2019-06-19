<template>
<div>
    <v-toolbar color="white" flat dense>
        <v-spacer></v-spacer>
        <v-btn icon @click="$emit('closeProfileDialog')"><v-icon>close</v-icon></v-btn>
    </v-toolbar>

    <v-card-text>
        <v-layout row wrap>
            
            <v-flex xs6 sm6 md6>
            <v-card flat height="300px">

                <v-card-text>
                    <span class="display-2 font-weight-light">{{selectedHotel.name}}</span><br>
                    <v-icon big class="mr-1 mt-3">location_on</v-icon>
                    <span class="blue--text text--darken-2 title">{{ selectedHotel.location.formattedAddress }}</span>
                    <div class="d-flex mt-2">
                    <v-rating
                        :value="selectedHotel.averageFeedback"
                        color="amber"
                        half-increments
                        dense
                        readonly
                        size="35"
                    ></v-rating>
                    </div>
                </v-card-text>

                <v-card-actions class="indigo--text font-weight-light title ml-2 pt-0">
                    <!--{{ selectedHotel.description }}-->
                    {{ lorem }}
                </v-card-actions>
            </v-card>
            </v-flex>

            <v-flex xs6 sm6 md6>
                <v-card flat>
                <v-toolbar flat>
                    <v-toolbar-title class="text-uppercase indigo--text font-weight-light">Additional services</v-toolbar-title>
                </v-toolbar>
                </v-card>
                <v-layout column style="height: 300px">
                <v-flex style="overflow: auto">
                    <v-data-table
                        :items="selectedHotel.additionalServicesPriceList.priceListItems"
                        item-key="name"
                        hide-actions
                        hide-headers
                        no-data-text 
                        >
                        <template v-slot:items="props">
                            <td class="text-xs-left font-weight-bold indigo--text">{{ props.item.name }}</td>
                            <td class="text-xs-left font-weight-bold indigo--text">{{ props.item.price }}€</td>
                        </template>

                    </v-data-table>
                </v-flex>
                </v-layout>
            </v-flex>

            <v-flex xs12 sm12 md12>
                <v-card flat>
                <v-toolbar flat>
                    <v-toolbar-title class="text-uppercase indigo--text font-weight-light">Room types</v-toolbar-title>
                </v-toolbar>
                </v-card>
                <v-layout column style="height: 300px">
                <v-flex style="overflow: auto">
                    <v-data-table
                        :headers="roomsHeaders"
                        :items="selectedHotel.roomTypes"
                        hide-actions
                        no-data-text>

                        <template v-slot:items="props">
                            <td>{{ props.item.type }}</td>
                            <td>{{ props.item.pricePerNight }}</td>
                            <td>{{ props.item.capacity }}</td>
                            <td>{{ props.item.singleBedCount }}</td>
                            <td>{{ props.item.doubleBedCount }}</td>
                            <td>{{ props.item.hasTV }}</td>
                            <td>{{ props.item.hasBalcony }}</td>
                            <td>{{ props.item.squareFootage }}</td>
                            <td>{{ props.item.averageFeedback }}</td>
                        </template>
                    </v-data-table>
                </v-flex>
                </v-layout>
            </v-flex>

            </v-layout>

    </v-card-text>
</div>
</template>

<script>
export default {
    props: ['selectedHotel'],

    data(){
        return{
                        lorem: 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'
,
            roomsHeaders: [
                { text: 'Type', align: 'left', sortable: false, value: 'name'},
                { text: 'Price per night (€)', value:'price', sortable: false},
                { text: 'Capacity', value: 'discount', sortable: false},
                { text: 'Single beds', value: 'singleBeds', sortable: false },
                { text: 'Double beds', value: 'doubleBeds', sortable: false },
                { text: 'Has TV', value: 'hasTV', sortable: false },
                { text: 'Has Balcony', value: 'hasBalcony', sortable: false },
                { text: 'Square ft.', value: 'squareFootage', sortable: false },
                { text: 'Average Feedback', value: 'avgFdbk', sortable: false}
            ],
            lorem: "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        }
    }
}
</script>

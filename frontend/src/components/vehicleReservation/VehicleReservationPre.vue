<template>
    <v-card>
        <v-container>
            
            <v-layout row>
                <v-flex xs4>
                    <v-layout column>
                     
                        <v-img
                            :aspect-ratio="16/9"
                            :src="vehicle.image"
                        >
                        </v-img>
                        <v-layout row>
                        <v-divider vertical/>
                        <v-list>
                            <v-list-tile>
                                <v-list-tile-content>
                                <v-list-tile-title>{{vehicle.manufacturer}}</v-list-tile-title>
                                <v-list-tile-sub-title>Manufacturer</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>
                            

                            <v-list-tile>
                                <v-list-tile-content>
                                <v-list-tile-title>{{vehicle.model}}</v-list-tile-title>
                                <v-list-tile-sub-title>Model</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>

                        <v-list-tile>
                                <v-list-tile-content>
                                <v-list-tile-title>{{vehicle.year}}</v-list-tile-title>
                                <v-list-tile-sub-title>Year</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>

                            <v-list-tile>
                                <v-list-tile-content>
                                <v-list-tile-title>{{vehicle.fuel}}</v-list-tile-title>
                                <v-list-tile-sub-title>Fuel</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>
                        </v-list>
                        <v-spacer />
                        <v-divider vertical/>
                        <v-list>
                            <v-list-tile>
                                <v-list-tile-content>
                                <v-list-tile-title>{{vehicle.engine}}</v-list-tile-title>
                                <v-list-tile-sub-title>Engine</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>
                            <v-list-tile>
                                <v-list-tile-content>
                                <v-list-tile-title>{{vehicle.transmission}}</v-list-tile-title>
                                <v-list-tile-sub-title>Transmission</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>
                            <v-list-tile>
                                <v-list-tile-content>
                                <v-list-tile-title>{{vehicle.seatsCount}}</v-list-tile-title>
                                <v-list-tile-sub-title>Seats count</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>
                            <v-list-tile>
                                <v-list-tile-content>
                                <v-list-tile-title>{{vehicle.airCondition}}</v-list-tile-title>
                                <v-list-tile-sub-title>Aircondition</v-list-tile-sub-title>
                                </v-list-tile-content>
                            </v-list-tile>
                        </v-list>

                        </v-layout>
                    </v-layout>
                </v-flex>
                <v-spacer />
                <component :is='component'>
                </component>
            </v-layout>
        </v-container>
    </v-card>
</template>




<script>
import VehiclePriceList from "@/components/rentACarComp/VehiclePriceList.vue"
export default {



    props: ['vehicle'],
    components:{
        'vehiclePriceList':VehiclePriceList
    },


    data:()=>({

        component : 'vehiclePriceList',


    })
}
</script>


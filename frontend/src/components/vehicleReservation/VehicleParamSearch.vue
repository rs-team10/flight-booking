<template>


    <v-flex xs10 offset-xs1>    
                                <v-form
                                    ref="form"
                                >
                                <v-layout row>
                                    <v-flex xs3>
                                        <v-text-field
                                            v-model.lazy="searchVehicle.manufacturer"
                                            :counter="10"
                                            label="Manufacturer"

                                        >
                                        </v-text-field>
                                    </v-flex>


                                    <v-divider 
                                    class="mx-2"
                                    vertical
                                    dark>
                                    </v-divider>


                                    <v-flex xs3>
                                        <v-text-field
                                            v-model.lazy="searchVehicle.model"
                                            label="Model"
                                        >
                                        </v-text-field>
                                    </v-flex>


                                    <v-divider 
                                    class="mx-2"
                                    vertical
                                    dark>
                                    </v-divider>


                                    <v-flex xs10>

                                        <v-layout
                                        row
                                        wrap
                                        >
                                            <v-flex>
                                                <v-range-slider
                                                    :max="5.0"
                                                    :min="0.9"
                                                    :step="0.1"
                                                    v-model.lazy ="searchVehicle.engine"
                                                    label="Engine"
                                                ></v-range-slider>
                                            </v-flex>
                                            <v-flex
                                                shrink
                                                style="width: 60px"
                                            >
                                                <v-text-field
                                                    class = "pl-3"
                                                    hide-details
                                                    single-line
                                                    type="number"
                                                    v-model.lazy ="searchVehicle.engine[0]"
                                                    readonly
                                                ></v-text-field>
                                            </v-flex>
                                            <v-flex
                                                shrink
                                                style="width: 60px"
                                            >
                                                <v-text-field
                                                    class = "pl-3"
                                                    hide-details
                                                    single-line
                                                    type="number"
                                                    v-model.lazy ="searchVehicle.engine[1]"
                                                    readonly
                                                ></v-text-field>
                                            </v-flex>
                                        </v-layout>
                                    </v-flex>

                                    

                                </v-layout>


                                <v-layout row >
                                    <v-flex xs4>
                                        <v-select
                                            :items="items"
                                            label="Select fuel"
                                            v-model="searchVehicle.fuel"
                                        ></v-select>
                                    </v-flex>

                                    <v-divider 
                                        class="mx-2"
                                        vertical
                                        dark>
                                    </v-divider>
                                        
                                    <v-flex xs8>
    
                                        <v-layout
                                        row
                                        wrap
                                        >
                                            <v-flex>
                                                <v-slider
                                                    :max="8"
                                                    :min="1"
                                                    :step="1"
                                                    v-model="searchVehicle.seatsCount"
                                                    label="Seats count"
                                                ></v-slider>
                                            </v-flex>
                                            
                                            <v-flex
                                                shrink
                                                style="width: 60px"
                                            >
                                                <v-text-field
                                                    class = "pl-2"
                                                    hide-details
                                                    single-line
                                                    v-model="searchVehicle.seatsCount"
                                                    readonly
                                                ></v-text-field>
                                            </v-flex>
                                        </v-layout>
                                    </v-flex>

                                    <v-divider 
                                        class="mx-3"
                                        vertical
                                        dark>
                                    </v-divider>

                                    <v-flex xs2>
                                        <v-switch
                                            v-model="searchVehicle.transmission"
                                            :label="`Automatic`"
                                        ></v-switch>
                                    </v-flex>

                                    <v-divider 
                                        class="mx-2"
                                        vertical
                                        dark>
                                    </v-divider>

                                    <v-flex xs2>
                                        <v-switch
                                            v-model="searchVehicle.airCondition"
                                            :label="`Aircondition`"
                                        ></v-switch>
                                    </v-flex>

                                    
                                </v-layout>
                                <v-layout row>

                                    <v-flex xs12>
                                        <v-layout
                                        row
                                        wrap
                                        >
                                            <v-flex>
                                                <v-range-slider
                                                    v-model.lazy="searchVehicle.year"
                                                    :max="2019"
                                                    :min="1900"
                                                    :step="1"
                                                    label="Year"
                                                ></v-range-slider>
                                            </v-flex>
                                            <v-flex
                                                shrink
                                                style="width: 70px"
                                            >
                                                <v-text-field
                                                    hide-details
                                                    single-line
                                                    class = "pl-3"
                                                    type="number"
                                                    v-model.lazy="searchVehicle.year[0]"
                                                    readonly
                                                ></v-text-field>
                                            </v-flex>
                                            <v-flex
                                                shrink
                                                style="width: 70px"
                                            >
                                                <v-text-field
                                                    hide-details
                                                    single-line
                                                    class = "pl-3"
                                                    type="number"
                                                    v-model.lazy="searchVehicle.year[1]"
                                                    readonly
                                                ></v-text-field>
                                            </v-flex>
                                        </v-layout>
                                    </v-flex>


                                    

                                </v-layout>
                                    
                                <v-layout row>



                                    <v-flex xs12>
                                        <v-layout
                                        row
                                        wrap
                                        >
                                            <v-flex>
                                                <v-range-slider
                                                    :max="1000"
                                                    :min="0"
                                                    :step="10"
                                                    v-model.lazy="searchVehicle.dailyRentalPrice"
                                                    label="Rental price range"
                                                ></v-range-slider>
                                            </v-flex>
                                            <v-flex
                                                shrink
                                                style="width: 80px"
                                            >
                                                <v-text-field
                                                    class = "pl-2"
                                                    hide-details
                                                    single-line
                                                    type="number"
                                                    v-model="searchVehicle.dailyRentalPrice[0]"
                                                    readonly
                                                ></v-text-field>
                                            </v-flex>
                                            <v-flex
                                                shrink
                                                style="width: 80px"
                                            >
                                                <v-text-field
                                                    class = "pl-2"
                                                    hide-details
                                                    single-line
                                                    type="number"
                                                    v-model="searchVehicle.dailyRentalPrice[1]"
                                                    readonly
                                                ></v-text-field>
                                            </v-flex>
                                            
                                            
                                        </v-layout>
                                    </v-flex>


                                </v-layout>

                                <v-layout row>



                                    <v-flex xs4 offset-xs4>
                                        <v-text-field
                                            v-model.lazy ='searchVehicle.city'
                                            label="City"

                                        >
                                        </v-text-field>
                                    </v-flex>


                                </v-layout>


                                    <v-card-actions>
                                        <v-spacer></v-spacer>
                                        <v-btn @click='submit' >submit</v-btn>
                                        <v-btn  color ='error' @click="cancel" >cancel</v-btn>
                                    </v-card-actions>
                                    
                                </v-form>
                            </v-flex>

</template>

<script>

    export default {
        data: () => ({
            searchVehicle : {

                manufacturer            : '',
                model                   : '',
                year                    : [1900, 2019],
                fuel                    : '',
                engine                  : [0.9, 5.0],
                transmission            : '',
                seatsCount              : '',
                airCondition            : '',
                dailyRentalPrice        : [0, 1000],
                dateFrom                : "",           // ovo ce iz propertija da se skupi
                dateTo                  : "", 
                city                    : ""

            },

            items: ['Gasoline' ,'Diesel', 'Liquified Petroleum', 
                    'Compressed Natural Gas', 'Ethanol', 'Bio-diesel'],
        }),
         methods : {
            cancel(){
                this.searchVehicle =  {
                    manufacturer            : '',
                    model                   : '',
                    year                    : [0, 2019],
                    fuel                    : '',
                    engine                  : [0.9, 5.0],
                    transmission            : '',
                    seatsCount              : '',
                    airCondition            : '',
                    dailyRentalPrice        : [0, 1000], 
                    dateFrom                : "",
                    dateTo                  : "", 
                    mainRentACar            : '',
                    city                    : ""
                };
                this.$emit('cancelSearch');

            },

            submit: function(){
                this.$emit('searchVehicle', this.searchVehicle);
            }
        }
    }

</script>
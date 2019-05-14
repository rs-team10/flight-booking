<template>
<v-flex>

        <v-toolbar tabs flat>
            <v-tabs
            v-model="tabs"
            fixed-tabs
            color="transparent"
            >
            <v-tabs-slider></v-tabs-slider>

                <v-tab href="#mobile-tabs-5-1" class="primary--text">
                    <v-icon>account_balance</v-icon>
                </v-tab>

                <v-tab href="#mobile-tabs-5-2" class="primary--text">
                    <v-icon>directions_car</v-icon>
                </v-tab>


            </v-tabs>
        </v-toolbar>

        <v-container>

                    <v-tabs-items v-model="tabs" class="white elevation-1">
                        <v-tab-item :value="'mobile-tabs-5-1'">
                 
                            <!-- component -->
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
                                                    v-model="searchVehicle.dailyRentalPrice"
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

                                    
                                        
                                    <!-- pitaj da l treba ovako?
                                    
                                    <v-flex xs4>
                                       
                                       <v-menu
                                        ref="menu1"
                                        v-model="menu1"
                                        :close-on-content-click="false"
                                        :nudge-right="40"
                                        lazy
                                        transition="scale-transition"
                                        offset-y
                                        full-width
                                        max-width="290px"
                                        min-width="290px"
                                        >
                                        <template v-slot:activator="{ on }">
                                            <v-text-field
                                            v-model="dateFormatted"
                                            label="Date"
                                            hint="MM/DD/YYYY format"
                                            persistent-hint
                                            prepend-icon="event"
                                            @blur="date = parseDate(dateFormatted)"
                                            v-on="on"
                                            ></v-text-field>
                                        </template>
                                        <v-date-picker v-model="date" no-title @input="menu1 = false"></v-date-picker>
                                        </v-menu>
                                        <p>Date in ISO format: <strong>{{ date }}</strong></p>
                                    </v-flex>

                                    <v-divider 
                                        class="mx-3"
                                        vertical
                                        dark>
                                    </v-divider>

                                    <v-flex xs4>
                                        <v-menu
                                        v-model="menu2"
                                        :close-on-content-click="false"
                                        :nudge-right="40"
                                        lazy
                                        transition="scale-transition"
                                        offset-y
                                        full-width
                                        max-width="290px"
                                        min-width="290px"
                                        >
                                        <template v-slot:activator="{ on }">
                                            <v-text-field
                                            v-model="computedDateFormatted"
                                            label="Date (read only text field)"
                                            hint="MM/DD/YYYY format"
                                            persistent-hint
                                            prepend-icon="event"
                                            readonly
                                            v-on="on"
                                            ></v-text-field>
                                        </template>
                                        <v-date-picker v-model="date" no-title @input="menu2 = false"></v-date-picker>
                                        </v-menu>
                                        <p>Date in ISO format: <strong>{{ date }}</strong></p>

                                    </v-flex>
                                     -->

                                </v-layout>


                                    <v-card-actions>
                                        <v-spacer></v-spacer>
                                        <v-btn  >submit</v-btn>
                                        <v-btn  color ='error' @click="cancel" >cancel</v-btn>
                                    </v-card-actions>
                                    
                                </v-form>
                            </v-flex>

                            <v-card>
                                
                            </v-card>
               
                        </v-tab-item>
                        
                        <v-tab-item :value="'mobile-tabs-5-2'">
                                
                                <v-card>
                                    <p> Nes da pise </p>
                                </v-card>

                            </v-tab-item>
                    </v-tabs-items>

                </v-container> 
                <v-container grid-list-md>
                    <v-tabs-items v-model="tabs" class="white elevation-1">
                            <v-tab-item :value="'mobile-tabs-5-1'">
                                
                                <v-data-iterator
                                :items="vehicles"
                                :rows-per-page-items="rowsPerPageItems"
                                :pagination.sync="pagination"
                                content-tag="v-layout"
                                row
                                wrap
                                >
                                <template v-slot:item="props">
                                    <v-flex
                                    xs12
                                    sm6
                                    md4
                                    lg3
                                    >
                                        <v-hover>
                                            <v-card
                                            slot-scope="{ hover }"
                                            class="mx-auto"
                                            color="grey lighten-4"
                                            max-width="600"
                                            >
                                            
                                            <v-img
                                                :aspect-ratio="16/9"
                                                :src="props.item.image"
                                            >
                                                <v-expand-transition>
                                                    <div
                                                        v-if="hover"
                                                        class="d-flex transition-fast-in-fast-out black darken-2 v-card--reveal display-1 white--text"
                                                        style="height: 100%;"
                                                    >
                                                        ${{props.item.dailyRentalPrice}}/day                                   
                                                    </div>
                                                </v-expand-transition>
                                                
                                            </v-img>
                                            

                                            <v-card-text
                                                class="pt-4"
                                                style="position: relative;"
                                            >
                                                <div class="font-weight-light grey--text title mb-2">For the perfect day</div>
                                                <h3 class="display-1 font-weight-light blue--text mb-2">{{props.item.manufacturer}} {{props.item.model}}</h3>
                                                <div class="font-weight-light title mb-2">
                                                    Vehicle owner: <br/>{{props.item.mainRentACar}}
                                                </div>
                                            </v-card-text>
                                            </v-card>
                                        </v-hover>
                                    </v-flex>
                                </template>
                                </v-data-iterator>

                            </v-tab-item>

                            <v-tab-item :value="'mobile-tabs-5-2'">
                                
                                <v-card>
                                    <p> Nes da pise </p>
                                </v-card>

                            </v-tab-item>
                    </v-tabs-items>
                </v-container>

                   
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
            dailyRentalPrice        : [0, 1000], // kako se pravi dinamicki query xD
            dateFrom                : "",       // ovo ce iz propertija da se skupi - pitaj kako baza poredi datume
            dateTo                  : "", 
            mainRentACar            : ''
            

        },



        tabs : null,

        rowsPerPageItems: [4, 8, 12],
        pagination: {
            rowsPerPage: 4
        },
        vehicles: [
            {
                id                  : 1,
                manufacturer        : 'BMW',
                model               : '520',
                year                : '2018',
                fuel                : 'Gasoline',
                engine              : 5.0,
                transmission        : 'True',
                seatsCount          : 5,
                airCondition        : 'True',
                dailyRentalPrice    : 380,
                image               : require('@/assets/vehicles/bmw.jpg'),
                mainRentACar        : 'My rent-a-car service'
            },
            {
                id                  : 2,
                manufacturer        : 'Mercedes',
                model               : '520',
                year                : '2018',
                fuel                : 'Gasoline',
                engine              : 5.0,
                transmission        : 'True',
                seatsCount          : 5,
                airCondition        : 'True',
                dailyRentalPrice    : 250,
                image               : require('@/assets/vehicles/mercedes.jpg'),
                mainRentACar        : 'My rent-a-car service'
            },
            {
                id                  : 3,
                manufacturer        : 'Renault',
                model               : 'Laguna',
                year                : '2018',
                fuel                : 'Gasoline',
                engine              : 5.0,
                transmission        : 'True',
                seatsCount          : 5,
                airCondition        : 'True',
                dailyRentalPrice    : 280,
                image               : require('@/assets/vehicles/laguna.jpg'),
                mainRentACar        : 'My rent-a-car service'
            },{
                id                  : 4,
                manufacturer        : 'BMW',
                model               : '520',
                year                : '2018',
                fuel                : 'Gasoline',
                engine              : 5.0,
                transmission        : 'True',
                seatsCount          : 5,
                airCondition        : 'True',
                dailyRentalPrice    : 380,
                image               : require('@/assets/vehicles/bmw.jpg'),
                mainRentACar        : 'My rent-a-car service'
            },
            {
                id                  : 5,
                manufacturer        : 'Mercedes',
                model               : '520',
                year                : '2018',
                fuel                : 'Gasoline',
                engine              : 5.0,
                transmission        : 'True',
                seatsCount          : 5,
                airCondition        : 'True',
                dailyRentalPrice    : 250,
                image               : require('@/assets/vehicles/mercedes.jpg'),
                mainRentACar        : 'My rent-a-car service'
            },
            {
                id                  : 6,
                manufacturer        : 'Renault',
                model               : 'Laguna',
                year                : '2018',
                fuel                : 'Gasoline',
                engine              : 5.0,
                transmission        : 'True',
                seatsCount          : 5,
                airCondition        : 'True',
                dailyRentalPrice    : 280,
                image               : require('@/assets/vehicles/laguna.jpg'),
                mainRentACar        : 'My rent-a-car service'
            },{
                id                  : 7,
                manufacturer        : 'BMW',
                model               : '520',
                year                : '2018',
                fuel                : 'Gasoline',
                engine              : 5.0,
                transmission        : 'True',
                seatsCount          : 5,
                airCondition        : 'True',
                dailyRentalPrice    : 380,
                image               : require('@/assets/vehicles/bmw.jpg'),
                mainRentACar        : 'My rent-a-car service'
            },
            {
                id                  : 8,
                manufacturer        : 'Mercedes',
                model               : '520',
                year                : '2018',
                fuel                : 'Gasoline',
                engine              : 5.0,
                transmission        : 'True',
                seatsCount          : 5,
                airCondition        : 'True',
                dailyRentalPrice    : 250,
                image               : require('@/assets/vehicles/mercedes.jpg'),
                mainRentACar        : 'My rent-a-car service'
            },
            {
                id                  : 9,
                manufacturer        : 'Renault',
                model               : 'Laguna',
                year                : '2018',
                fuel                : 'Gasoline',
                engine              : 5.0,
                transmission        : 'True',
                seatsCount          : 5,
                airCondition        : 'True',
                dailyRentalPrice    : 280,
                image               : require('@/assets/vehicles/laguna.jpg'),
                mainRentACar        : 'My rent-a-car service'
            }
            
        ],
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
                mainRentACar            : ''
            };

        },
    }
  }
</script>


<style scoped>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: .5;
  position: absolute;
  width: 100%;
}
</style>
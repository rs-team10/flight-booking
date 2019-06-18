<template>
    <div id="edit-Vehicle">
        <h1>Edit {{selectedVehicle.manufacturer}} {{selectedVehicle.model}}</h1>

        <div id="edit-form">
            <form>
                <v-text-field
                    v-model.lazy="selectedVehicle.manufacturer"
                    :error-messages="manufacturerErrors"
                    :counter="10"
                    label="Manufacturer"
                    required
                    @input="$v.selectedVehicle.manufacturer.$touch()"
                    @blur="$v.selectedVehicle.manufacturer.$touch()"
                >
                </v-text-field>

                <v-text-field
                    v-model.lazy="selectedVehicle.model"
                    :error-messages="modelErrors"
                    label="Model"
                    required
                    @input="$v.selectedVehicle.model.$touch()"
                    @blur="$v.selectedVehicle.model.$touch()"
                >
                </v-text-field>


                <v-layout
                row
                wrap
                >
                    <v-flex>
                        <v-slider
                            v-model.lazy="selectedVehicle.year"
                            :error-messages="yearErrors"
                            :max="2019"
                            :min="1900"
                            :step="1"
                            label="Year"
                        ></v-slider>
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
                            v-model="selectedVehicle.year"
                            readonly
                        ></v-text-field>
                    </v-flex>
                </v-layout>
          
                <v-select
                    v-model="selectedVehicle.fuel"
                    :error-messages="fuelErrors"
                    :items="items"
                    :rules="rules"
                    label="Select fuel"
     
                    @input="$v.selectedVehicle.fuel.$touch()"
                    @blur="$v.selectedVehicle.fuel.$touch()"
                ></v-select>
                
                <v-layout
                row
                wrap
                >
                    <v-flex>
                        <v-slider
                            v-model.lazy="selectedVehicle.engine"
                            :error-messages="engineErrors"
                            :max="5.0"
                            :min="0.9"
                            :step="0.1"
                            label="Engine"
                        ></v-slider>
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
                            v-model="selectedVehicle.engine"
                            readonly
                        ></v-text-field>
                    </v-flex>
                </v-layout>

                <v-switch
                    v-model="selectedVehicle.transmission"
                    :error-messages="transmissionErrors"
                    :label="`Automatic: ${selectedVehicle.transmission.toString()}`"
                ></v-switch>

                <v-layout
                row
                wrap
                >
                    <v-flex>
                        <v-slider
                            v-model.lazy="selectedVehicle.seatsCount"
                            :error-messages="seatsCountErrors"
                            :max="8"
                            :min="1"
                            :step="1"
                            label="Seats count"
                        ></v-slider>
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
                            v-model="selectedVehicle.seatsCount"
                            readonly
                        ></v-text-field>
                    </v-flex>
                </v-layout>
                
                <v-switch
                    v-model.lazy="selectedVehicle.airCondition"
                    :error-messages="airConditionErrors"
                    :label="`Aircondition: ${selectedVehicle.airCondition.toString()}`"
                ></v-switch>

                <v-text-field
                    v-model.lazy="selectedVehicle.dailyRentalPrice"
                    :error-messages="dailyRentalPriceErrors"
                    label="Daily rental price"
                    required
                    type="number"
                    @input="$v.selectedVehicle.dailyRentalPrice.$touch()"
                    @blur="$v.selectedVehicle.dailyRentalPrice.$touch()"
                >
                </v-text-field>


        
                <v-btn @click="submit">submit</v-btn>
            </form>
        </div>

    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, maxLength, numeric } from 'vuelidate/lib/validators'

var yourConfig = {headers: { Authorization: "Bearer " + localStorage.getItem("token")}}
export default {
    
    props: ['selectedVehicle'],
    data(){
        
        return {
            
            items: ['Gasoline' ,'Diesel', 'Liquified Petroleum', 
                'Compressed Natural Gas', 'Ethanol', 'Bio-diesel'],
            rules: [
                    v => !!v || 'The value is required'
                    ]
        }
    },

    mixins: [validationMixin],
    validations: {
        selectedVehicle: {
            manufacturer        : { required, 
                                    maxLength: maxLength(10) 
                                    },
            model               : { required },
            year                : { required },
            fuel                : { required },
            engine              : { required },
            transmission        : { required },
            seatsCount          : { required },
            airCondition        : { required },
            dailyRentalPrice    : { required, numeric }
                                   
        }
    },
    computed: {
        manufacturerErrors () {
            const errors = []
            if (!this.$v.selectedVehicle.manufacturer.$dirty) return errors
            !this.$v.selectedVehicle.manufacturer.maxLength && errors.push('Manufacturer must be at most 10 characters long')
            !this.$v.selectedVehicle.manufacturer.required && errors.push('Manufacturer is required.')
            return errors
        },
        modelErrors() {
            const errors = []
            if (!this.$v.selectedVehicle.model.$dirty) return errors
            !this.$v.selectedVehicle.model.required && errors.push('Model is required.')
            return errors
        },
        yearErrors() {
            const errors = []
            if (!this.$v.selectedVehicle.year.$dirty) return errors
            !this.$v.selectedVehicle.year.required && errors.push('Year is required.')
            return errors
        },
        fuelErrors(){
            const errors = []
            if (!this.$v.selectedVehicle.fuel.$dirty) return errors
            !this.$v.selectedVehicle.fuel.required && errors.push('Fuel is required.')
            return errors
        },
        engineErrors(){
            const errors = []
            if (!this.$v.selectedVehicle.engine.$dirty) return errors
            !this.$v.selectedVehicle.engine.required && errors.push('Engine is required.')
            return errors
        },
        transmissionErrors(){
            const errors = []
            if (!this.$v.selectedVehicle.transmission.$dirty) return errors
            !this.$v.selectedVehicle.transmission.required && errors.push('Transmission is required.')
            return errors
        },
        seatsCountErrors(){
            const errors = []
            if (!this.$v.selectedVehicle.seatsCount.$dirty) return errors
            !this.$v.selectedVehicle.seatsCount.required && errors.push('Seats count is required.')
            return errors
        },
        airConditionErrors(){
            const errors = []
            if (!this.$v.selectedVehicle.airCondition.$dirty) return errors
            !this.$v.selectedVehicle.airCondition.required && errors.push('Aircondition is required.')
            return errors
        },
        dailyRentalPriceErrors() {
            const errors = []
            if (!this.$v.selectedVehicle.dailyRentalPrice.$dirty) return errors
            !this.$v.selectedVehicle.dailyRentalPrice.required && errors.push('Daily rental price is required.')
            !this.$v.selectedVehicle.dailyRentalPrice.numeric && errors.push('Daily rental price must be positive number.')
            return errors
        }
    },
    methods:{
        submit () {
            this.$v.$touch();

            if(!this.$v.$invalid){
                if(confirm('Are you sure you want to edit this vehicle?'))
                    this.editVehicle();
            }
        },
        editVehicle: function(){
            this.$axios
            .post('http://localhost:8080/api/vehicles', this.selectedVehicle, yourConfig)
            .then(response => {
                alert(response.data);
            }).catch(function(error) {
                alert(error.response.data.message);
            });
        }
    }
}
</script>

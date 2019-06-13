<template>
    <div id="add-Vehicle">
        <h1>Add new vehicle</h1>

        <div id="add-form">
            <v-form
                ref="form"
            >
                <v-text-field
                    v-model.lazy="vehicle.manufacturer"
                    :error-messages="manufacturerErrors"
                    :counter="10"
                    label="Manufacturer"
                    required
                    @input="$v.vehicle.manufacturer.$touch()"
                    @blur="$v.vehicle.manufacturer.$touch()"
                >
                </v-text-field>

                <v-text-field
                    v-model.lazy="vehicle.model"
                    :error-messages="modelErrors"
                    label="Model"
                    required
                    @input="$v.vehicle.model.$touch()"
                    @blur="$v.vehicle.model.$touch()"
                >
                </v-text-field>


                <v-layout
                row
                wrap
                >
                    <v-flex>
                        <v-slider
                            v-model.lazy="vehicle.year"
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
                            v-model="vehicle.year"
                            readonly
                        ></v-text-field>
                    </v-flex>
                </v-layout>
          
                <v-select
                    v-model="vehicle.fuel"
                    :error-messages="fuelErrors"
                    :items="items"
                    :rules="rules"
                    label="Select fuel"
     
                    @input="$v.vehicle.fuel.$touch()"
                    @blur="$v.vehicle.fuel.$touch()"
                ></v-select>
                
                <v-layout
                row
                wrap
                >
                    <v-flex>
                        <v-slider
                            v-model.lazy="vehicle.engine"
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
                            v-model="vehicle.engine"
                            readonly
                        ></v-text-field>
                    </v-flex>
                </v-layout>

                <v-switch
                    v-model="vehicle.transmission"
                    :error-messages="transmissionErrors"
                    :label="`Automatic: ${vehicle.transmission.toString()}`"
                ></v-switch>

                <v-layout
                row
                wrap
                >
                    <v-flex>
                        <v-slider
                            v-model.lazy="vehicle.seatsCount"
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
                            v-model="vehicle.seatsCount"
                            readonly
                        ></v-text-field>
                    </v-flex>
                </v-layout>
                
                <v-switch
                    v-model="vehicle.airCondition"
                    :error-messages="airConditionErrors"
                    :label="`Aircondition: ${vehicle.airCondition.toString()}`"
                ></v-switch>

                <v-text-field
                    v-model.lazy="vehicle.dailyRentalPrice"
                    :error-messages="dailyRentalPriceErrors"
                    label="Daily rental price"
                    required
                    type="number"
                    @input="$v.vehicle.dailyRentalPrice.$touch()"
                    @blur="$v.vehicle.dailyRentalPrice.$touch()"
                >
                </v-text-field>


                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn  @click="submit">submit</v-btn>
                    <v-btn  color ='error' @click="cancel">Cancel</v-btn>
                </v-card-actions>
                
            </v-form>
        </div>

    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, maxLength, numeric } from 'vuelidate/lib/validators'

export default {
    props: ['myBranch'],
    mixins: [validationMixin],

    validations: {
        vehicle: {
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
      
    data(){
        return {
            vehicle: {
                manufacturer        : '',
                model               : '',
                year                : '',
                fuel                : '',
                engine              : '',
                transmission        : '',
                seatsCount          : '',
                airCondition        : '',
                dailyRentalPrice    : ''
            },
            items: ['Gasoline' ,'Diesel', 'Liquified Petroleum', 
                'Compressed Natural Gas', 'Ethanol', 'Bio-diesel'],
            rules: [
                    v => !!v || 'The value is required'
                    ]
        }
    },
    computed: {
        manufacturerErrors () {
            const errors = []
            if (!this.$v.vehicle.manufacturer.$dirty) return errors
            !this.$v.vehicle.manufacturer.maxLength && errors.push('Manufacturer must be at most 10 characters long')
            !this.$v.vehicle.manufacturer.required && errors.push('Manufacturer is required.')
            return errors
        },
        modelErrors() {
            const errors = []
            if (!this.$v.vehicle.model.$dirty) return errors
            !this.$v.vehicle.model.required && errors.push('Model is required.')
            return errors
        },
        yearErrors() {
            const errors = []
            if (!this.$v.vehicle.year.$dirty) return errors
            !this.$v.vehicle.year.required && errors.push('Year is required.')
            return errors
        },
        fuelErrors(){
            const errors = []
            if (!this.$v.vehicle.fuel.$dirty) return errors
            !this.$v.vehicle.fuel.required && errors.push('Fuel is required.')
            return errors
        },
        engineErrors(){
            const errors = []
            if (!this.$v.vehicle.engine.$dirty) return errors
            !this.$v.vehicle.engine.required && errors.push('Engine is required.')
            return errors
        },
        transmissionErrors(){
            const errors = []
            if (!this.$v.vehicle.transmission.$dirty) return errors
            !this.$v.vehicle.transmission.required && errors.push('Transmission is required.')
            return errors
        },
        seatsCountErrors(){
            const errors = []
            if (!this.$v.vehicle.seatsCount.$dirty) return errors
            !this.$v.vehicle.seatsCount.required && errors.push('Seats count is required.')
            return errors
        },
        airConditionErrors(){
            const errors = []
            if (!this.$v.vehicle.airCondition.$dirty) return errors
            !this.$v.vehicle.airCondition.required && errors.push('Aircondition is required.')
            return errors
        },
        dailyRentalPriceErrors() {
            const errors = []
            if (!this.$v.vehicle.dailyRentalPrice.$dirty) return errors
            !this.$v.vehicle.dailyRentalPrice.required && errors.push('Daily rental price is required.')
            !this.$v.vehicle.dailyRentalPrice.numeric && errors.push('Daily rental price must be positive number.')
            return errors
        }
    },

    methods:{
        submit () {
            this.$v.$touch();

            if(!this.$v.$invalid){
                this.addVehicle();
            }
        },
        resetValidation () {
            this.$refs.form.resetValidation()
        },
        cancel(){
            this.vehicle =  {
                manufacturer        : '',
                model               : '',
                year                : '',
                fuel                : '',
                engine              : '',
                transmission        : '',
                seatsCount          : '',
                airCondition        : '',
                dailyRentalPrice    : ''
            };
            this.resetValidation();
            this.$v.$reset();

        },
        addVehicle: function(){
            this.$axios
            .post('http://localhost:8080/api/vehicles/'+this.myBranch, this.vehicle)
            .then(response => {
                alert(response.data);
                this.cancel();
            }).catch(error => {
                alert(error.response.data.message);
            });
        }
    }
}
</script>

<!-- System admin -->
<template>
    <div id="add-airline">
        <h1>Register new airline</h1>
        <div>
            <v-alert
                :value="success"
                type="success"
                transition="scale-transition">
            Airline: <b>{{ airline.name }}</b> registered successfully.
            </v-alert>

            <v-alert
                :value="error"
                type="error">
                {{this.error}}
            </v-alert>

        </div>
        <div id="add-form">
            <v-flex xs12 sm6 offset-sm3>
                <form>
                    <v-text-field
                        v-model.lazy="airline.name"
                        :error-messages="nameErrors"
                        label="Name"
                        required>
                    </v-text-field>

                    <v-text-field
                        v-model.lazy="airline.location.street"
                        :error-messages="addressErrors"
                        label="Address"
                        required>
                    </v-text-field>
                    
                    <!--
                        Potrebno dodavanje administratora
                    -->
                    <v-select
                        :items="placeholders"
                        label="Airline Administrator">
                    </v-select>
                    
                    <v-btn @click="submit">submit</v-btn>
                </form>
            </v-flex>
        </div>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required } from 'vuelidate/lib/validators'

export default {
    mixins: [validationMixin],

    validations: {
        airline: {
            name: { required },
            location: {
                street: { required }
            }
        }
    },
      
    data(){
        return {
            airline: {
                name: '',
                location: {
                    street: ''
                },
                description: ''
            },
            placeholders: ["placeholder1", "placeholder2", "placeholder3"],
            success: false,
            error: false
        }
    },
    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.airline.name.$dirty) return errors
            !this.$v.airline.name.required && errors.push('Name is required.')
            return errors
        },
        addressErrors() {
            const errors = []
            if (!this.$v.airline.location.street.$dirty) return errors
            !this.$v.airline.location.street.required && errors.push('Address is required.')
            return errors
        }
    },

    methods:{
        submit () {
            this.$v.$touch();

            if(!this.$v.$invalid){
                this.addAirline();
            }
        },
        addAirline: function(){
            this.$axios
            .post('http://localhost:8081/api/airlines/registerAirline', this.airline)
            .then(response => {
                console.log(response);
                this.success = true;
            }).catch(error => {
                console.log(error.response.data);
                this.error = error.response.data;
            });
        }
    }
}
</script>

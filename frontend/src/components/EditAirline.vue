<template>
    <v-container grid-list>
        <v-subheader>Edit airline profile</v-subheader>
        <v-layout row wrap>
            <div>
                <v-alert
                    :value="submitted"
                    type="success"
                    transition="scale-transition"
                >
                Airline profile edited successfully.
                </v-alert>
            </div>
            <v-flex xs12 md4>
                <form v-if="!submitted">

                    <v-text-field
                        v-model="airline.name"
                        :error-messages="nameErrors"
                        label="Airline Name"
                        required
                        @input="$v.airline.name.$touch()"
                        @blur="$v.airline.name.$touch()"
                    ></v-text-field>

                    <v-textarea
                        v-model="airline.description"
                        :error-messages="descriptionErrors"
                        auto-grow
                        box
                        label="Airline Promo Description"
                        required
                        @input="$v.airline.description.$touch()"
                        @blur="$v.airline.description.$touch()"
                    ></v-textarea>

                    <v-text-field
                        v-model="airline.address"
                        :error-messages="addressErrors"
                        label="Airline Address"
                        required
                        @input="$v.airline.address.$touch()"
                        @blur="$v.airline.address.$touch()"
                    ></v-text-field>

                    <v-btn @click="editAirline">submit</v-btn>

                </form>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, minLength } from 'vuelidate/lib/validators'

export default {
    mixins: [validationMixin],

    data () {
        return {
            airline: {
                id: 1,                            // TODO: This is hardcoded. Change!
                name: '',
                description: '',
                address: ''
            },
            submitted: false
        }
    },

    validations: {
        airline: {
            name: { required, minLength: minLength(6) },
            description: { required, minLength: minLength(60) },
            address: { required }
        }
    },

    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.airline.name.$dirty) return errors
            !this.$v.airline.name.minLength && errors.push('Name must be at least 6 characters long')
            !this.$v.airline.name.required && errors.push('Name is required.')
            return errors
        },
        descriptionErrors () {
            const errors = []
            if (!this.$v.airline.description.$dirty) return errors
            !this.$v.airline.description.minLength && errors.push('Description must be at least 60 characters long')
            !this.$v.airline.description.required && errors.push('Description is required.')
            return errors
        },
        addressErrors () {
            const errors = []
            if (!this.$v.airline.address.$dirty) return errors
            !this.$v.airline.address.required && errors.push('Address is required.')
            return errors
        },
    },

    methods: {
        editAirline: function() {

            this.$v.$touch()

            if(!this.$v.$invalid) {
                this.$axios.put('http://localhost:8081/api/airlines/', this.airline).then((data) => {
                    this.submitted = true;
                });
            }
        }
    },

    created() {
        this.$axios.get('http://localhost:8081/api/airlines/' + this.airline.id).then((response) => {
            this.airline = response.data;
        }).catch(function(error) {
                alert(error.response.data.message);
            });
    }
}
</script>
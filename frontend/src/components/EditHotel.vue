<template>
    <div id="edit-hotel">
        <h1>Edit {{selectedHotel.name}}</h1>
        <div>
            <v-alert
                :value="success"
                type="success"
                transition="scale-transition"
            >
            Hotel: <b>{{ selectedHotel.name }}</b> updated successfully.
            </v-alert>

            <v-alert
                :value="error"
                type="error"
                >
                {{this.error}}
            </v-alert>

        </div>
        <div id="edit-form">
            <v-flex xs12 sm6 offset-sm3>
                <form>
                    <v-text-field
                        v-model.lazy="selectedHotel.name"
                        :error-messages="nameErrors"
                        label="Name"
                        required>
                    </v-text-field>

                    <v-text-field
                        v-model.lazy="selectedHotel.location.street"
                        :error-messages="addressErrors"
                        label="Address"
                        required>
                    </v-text-field>

                    <v-textarea
                        v-model.lazy.trim="selectedHotel.description"
                        label="Description">
                    </v-textarea>

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
    props: ['selectedHotel'],

    mixins: [validationMixin],

    validations: {
        selectedHotel: {
            name: { required },
            location: {
                street : { required }
            }
        }
    },
    data(){
        return {
            success: false,
            error: false
        }
    },
    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.selectedHotel.name.$dirty) return errors
            !this.$v.selectedHotel.name.required && errors.push('Name is required.')
            return errors
        },
        addressErrors() {
            const errors = []
            if (!this.$v.selectedHotel.location.street.$dirty) return errors
            !this.$v.selectedHotel.location.street.required && errors.push('Address is required.')
            return errors
        }
    },

    methods:{
        submit () {
            this.$v.$touch();

            if(!this.$v.$invalid){
                this.editHotel();
            }
        },
        editHotel: function(){
            this.$axios
            .put('http://localhost:8081/api/hotels/updateHotel', this.selectedHotel)
            .then(response => {
                this.success = true;
            }).catch(error => {
                this.error = error.response.data;
            });
        }
    }
}
</script>
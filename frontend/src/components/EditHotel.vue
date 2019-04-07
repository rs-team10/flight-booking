<template>
    <div id="edit-hotel">
        <h1>Edit {{selectedHotel.name}}</h1>

        <div id="edit-form">
            <v-flex xs12 sm6 offset-sm3>
                <form>
                    <v-text-field
                        v-model.lazy="selectedHotel.name"
                        :error-messages="nameErrors"
                        label="Name"
                        required
                        @input="$v.selectedHotel.name.$touch()"
                        @blur="$v.selectedHotel.name.$touch()">
                    </v-text-field>

                <v-text-field
                        v-model.lazy="selectedHotel.address"
                        :error-messages="addressErrors"
                        label="Address"
                        required
                        @input="$v.selectedHotel.address.$touch()"
                        @blur="$v.selectedHotel.address.$touch()">
                    </v-text-field>

                    <v-textarea
                        v-model.lazy.trim="selectedHotel.description"
                        :error-messages="descriptionErrors"
                        label="Description"
                        required
                        @input="$v.selectedHotel.description.$touch()"
                        @blur="$v.selectedHotel.description.$touch()">
                    </v-textarea>

                    <v-btn @click="submit">submit</v-btn>
                </form>
            </v-flex>
        </div>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, maxLength } from 'vuelidate/lib/validators'

export default {
    props: ['selectedHotel'],

    mixins: [validationMixin],

    validations: {
        selectedHotel: {
            name: { required, maxLength: maxLength(10) },
            address: { required },
            description: { required }
        }
    },

    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.selectedHotel.name.$dirty) return errors
            !this.$v.selectedHotel.name.maxLength && errors.push('Name must be at most 10 characters long')
            !this.$v.selectedHotel.name.required && errors.push('Name is required.')
            return errors
        },
        addressErrors() {
            const errors = []
            if (!this.$v.selectedHotel.address.$dirty) return errors
            !this.$v.selectedHotel.address.required && errors.push('Address is required.')
            return errors
        },
        descriptionErrors() {
            const errors = []
            if (!this.$v.selectedHotel.description.$dirty) return errors
            !this.$v.selectedHotel.description.required && errors.push('Description is required.')
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
            .put('http://localhost:8081/api/hotels/' + this.selectedHotel.id, 
                this.selectedHotel)
            .then(response => {
                alert("Hotel is edited");
            }).catch(error => {
                alert(error.response.data.message);
            });
        }
    }
}
</script>
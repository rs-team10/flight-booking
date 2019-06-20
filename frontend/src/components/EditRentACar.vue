<template>
    <div id="edit-rentACar">
        <h1>Edit {{selectedRentACar.name}}</h1>

        <div id="edit-form">
            <form>
                <v-text-field
                    v-model.lazy="selectedRentACar.name"
                    :error-messages="nameErrors"
                    label="Name"
                    required
                    @input="$v.selectedRentACar.name.$touch()"
                    @blur="$v.selectedRentACar.name.$touch()">
                </v-text-field>
                <!--
                <v-text-field
                    v-model.lazy="selectedRentACar.address"
                    :error-messages="addressErrors"
                    label="Address"
                    required
                    @input="$v.selectedRentACar.address.$touch()"
                    @blur="$v.selectedRentACar.address.$touch()">
                </v-text-field>
                -->
                <v-textarea
                    v-model.lazy.trim="selectedRentACar.description"
                    :error-messages="descriptionErrors"
                    label="description"
                    required
                    @input="$v.selectedRentACar.description.$touch()"
                    @blur="$v.selectedRentACar.description.$touch()">
                </v-textarea>

                <v-btn @click="submit">submit</v-btn>
            </form>
        </div>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, maxLength } from 'vuelidate/lib/validators'

export default {
    props: ['selectedRentACar'],

    mixins: [validationMixin],

    validations: {
        selectedRentACar: {
            name: { required, maxLength: maxLength(10) },
            //address: { required },
            description: { required }
        }
    },

    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.selectedRentACar.name.$dirty) return errors
            !this.$v.selectedRentACar.name.maxLength && errors.push('Name must be at most 10 characters long')
            !this.$v.selectedRentACar.name.required && errors.push('Name is required.')
            return errors
        },
        /*
        addressErrors() {
            const errors = []
            if (!this.$v.selectedRentACar.address.$dirty) return errors
            !this.$v.selectedRentACar.address.required && errors.push('Address is required.')
            return errors
        },
        */
        descriptionErrors() {
            const errors = []
            if (!this.$v.selectedRentACar.description.$dirty) return errors
            !this.$v.selectedRentACar.description.required && errors.push('Description is required.')
            return errors
        }
    },

    methods:{
        submit () {
            this.$v.$touch();

            if(!this.$v.$invalid){
                this.editRentACar();
            }
        },
        editRentACar: function(){
            this.$axios
            .put('/api/rentACars/', 
                this.selectedRentACar)
            .then(function(response){
                alert("Rent-a-car with name "+ response.data.name+" is edited");
            }).catch(function(error) {
                alert(error.response.data.message);
            });
        }
    }
}
</script>
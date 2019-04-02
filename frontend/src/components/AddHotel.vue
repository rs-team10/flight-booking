<template>
    <div id="add-hotel">
        <h1>Add new hotel</h1>

        <div id="add-form">
            <form>
                <v-text-field
                    v-model.lazy="hotel.name"
                    :error-messages="nameErrors"
                    :counter="10"
                    label="Name"
                    required
                    @input="$v.hotel.name.$touch()"
                    @blur="$v.hotel.name.$touch()">
                </v-text-field>

                <v-text-field
                    v-model.lazy="hotel.address"
                    :error-messages="addressErrors"
                    label="Address"
                    required
                    @input="$v.hotel.address.$touch()"
                    @blur="$v.hotel.address.$touch()">
                </v-text-field>

                <v-textarea
                    v-model.lazy.trim="hotel.description"
                    :error-messages="descriptionErrors"
                    label="Description"
                    required
                    @input="$v.hotel.description.$touch()"
                    @blur="$v.hotel.description.$touch()">
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
    mixins: [validationMixin],

    validations: {
        hotel: {
            name: { required, maxLength: maxLength(10) },
            address: { required },
            description: { required }
        }
    },
      
    data(){
        return {
            hotel: {
                name: '',
                address: '',
                description: ''
            }
        }
    },
    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.hotel.name.$dirty) return errors
            !this.$v.hotel.name.maxLength && errors.push('Name must be at most 10 characters long')
            !this.$v.hotel.name.required && errors.push('Name is required.')
            return errors
        },
        addressErrors() {
            const errors = []
            if (!this.$v.hotel.address.$dirty) return errors
            !this.$v.hotel.address.required && errors.push('Address is required.')
            return errors
        },
        descriptionErrors() {
            const errors = []
            if (!this.$v.hotel.description.$dirty) return errors
            !this.$v.hotel.description.required && errors.push('Description is required.')
            return errors
        }
    },

    methods:{
        submit () {
            this.$v.$touch();

            if(!this.$v.$invalid){
                this.addHotel();
            }
        },
        addHotel: function(){
            this.$axios
            .post('http://localhost:8081/api/hotels', this.hotel)
            .then(function(response){
                console.log(response);
                alert("Hotel is added");
            }).catch(function(error) {
                alert(error.response.data.message);
            });
        }
    }
}
</script>

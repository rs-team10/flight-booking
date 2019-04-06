<!-- System admin -->
<template>
    <div id="add-hotel">
        <h1>Register new hotel</h1>

        <div id="add-form">
            <form>
                <v-text-field
                    v-model.lazy="hotel.name"
                    :error-messages="nameErrors"
                    label="Name"
                    required>
                    <!--
                    @input="$v.hotel.name.$touch()"
                    @blur="$v.hotel.name.$touch()"
                    -->
                </v-text-field>

                <v-text-field
                    v-model.lazy="hotel.address"
                    :error-messages="addressErrors"
                    label="Address"
                    required>
                    <!--
                    @input="$v.hotel.address.$touch()"
                    @blur="$v.hotel.address.$touch()">
                    -->
                </v-text-field>

                <!-- <v-textarea
                    v-model.lazy.trim="hotel.description"
                    :error-messages="descriptionErrors"
                    label="Description"
                    required
                    @input="$v.hotel.description.$touch()"
                    @blur="$v.hotel.description.$touch()">
                </v-textarea> -->

                <v-flex xs12 sm6 d-flex>
                    <v-select
                    :items="placeholders"
                    label="Hotel Administrator"
                    ></v-select>
                </v-flex>
        
                <v-btn @click="submit">submit</v-btn>
            </form>
        </div>

    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required } from 'vuelidate/lib/validators'

export default {
    mixins: [validationMixin],

    validations: {
        hotel: {
            name: { required },
            address: { required }
        }
    },
      
    data(){
        return {
            hotel: {
                name: '',
                address: '',
                description: ''
            },
            placeholders: ["placeholder1", "placeholder2", "placeholder3"]
        }
    },
    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.hotel.name.$dirty) return errors
            !this.$v.hotel.name.required && errors.push('Name is required.')
            return errors
        },
        addressErrors() {
            const errors = []
            if (!this.$v.hotel.address.$dirty) return errors
            !this.$v.hotel.address.required && errors.push('Address is required.')
            return errors
        }
        // descriptionErrors() {
        //     const errors = []
        //     if (!this.$v.hotel.description.$dirty) return errors
        //     !this.$v.hotel.description.required && errors.push('Description is required.')
        //     return errors
        // }
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
            .post('http://localhost:8081/api/hotels/registerHotel', this.hotel)
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

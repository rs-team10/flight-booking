<!-- System admin -->
<template>
    <div id="add-rentacar">
        <h1>Register new rent-a-car service</h1>
        <div>
            <v-alert
                :value="success"
                type="success"
                transition="scale-transition"
            >
            Rent-a-car service: <b>{{ rentacar.name }}</b> registered successfully.
            </v-alert>

            <v-alert
                :value="error"
                type="error"
                >
                {{this.error}}
            </v-alert>

        </div>
        <div id="add-form">
            <v-flex xs12 sm6 offset-sm3>
                <form>
                    <v-text-field
                        v-model.lazy="rentacar.name"
                        :error-messages="nameErrors"
                        label="Name"
                        required>
                    </v-text-field>

                    <v-text-field
                        v-model.lazy="rentacar.location.street"
                        :error-messages="addressErrors"
                        label="Address"
                        required>
                    </v-text-field>
                    
                    <!--
                        Potrebno dodavanje administratora
                    -->
                    <v-select
                        :items="placeholders"
                        label="Rent-a-car Administrator">
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
        rentacar: {
            name: { required },
            location: {
                street: { required }
            }
        }
    },
      
    data(){
        return {
            rentacar: {
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
            if (!this.$v.rentacar.name.$dirty) return errors
            !this.$v.rentacar.name.required && errors.push('Name is required.')
            return errors
        },
        addressErrors() {
            const errors = []
            if (!this.$v.rentacar.location.street.$dirty) return errors
            !this.$v.rentacar.location.street.required && errors.push('Address is required.')
            return errors
        }
    },

    methods:{
        submit () {
            this.$v.$touch();

            if(!this.$v.$invalid){
                this.addRentACar();
                
            }
        },
        addRentACar: function(){
            alert(localStorage.getItem("token"));
            var yourConfig = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            }

            this.$axios.interceptors.request.use(request => {
            console.log('Starting Request', request)
            return request
            })

            this.$axios
            .post('http://localhost:8080/api/rentACars', this.rentacar, yourConfig)
            .then(response => {

                this.success = true;
            }).catch(error => {

                this.error = error.response.data;
            });
        }
    }
}
</script>

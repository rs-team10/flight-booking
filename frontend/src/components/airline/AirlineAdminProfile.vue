<template>
    <div>
        <v-flex xs6 sm6 md6 offset-sm3>
        <v-card color="grey lighten-5" class="mt-4">
            <v-toolbar flat color="grey lighten-4">
                <v-toolbar-title class="indigo--text text-uppercase">
                    <span class="font-weight-light">My profile</span>
                </v-toolbar-title>    
            </v-toolbar>

            <v-card-text>
                <v-text-field
                    v-model="user.username"
                    label="Username"
                    required
                    >
                </v-text-field>

                <v-text-field
                    v-model="user.password"
                    label="New password"
                    :append-icon="showPassword ? 'visibility' : 'visibility_off'"
                    :type="showPassword ? 'text' : 'password'"
                    >
                </v-text-field>

                <!-- <v-text-field
                    v-model="user.passwordConfirmation"
                    label="New password confirmation"
                    :append-icon="showPasswordConfirmation ? 'visibility' : 'visibility_off'"
                    :type="showPasswordConfirmation ? 'text' : 'password'"
                    :error-messages="passwordConfirmationErrors"
                    counter
                    @click:append="showPasswordConfirmation = !showPasswordConfirmation"
                    @input="$v.user.passwordConfirmation.$touch()"
                    @blur="$v.user.passwordConfirmation.$touch()">
                </v-text-field> -->

                <v-text-field
                    v-model="user.firstName"
                    label="First name"
                    >
                </v-text-field>

                <v-text-field
                    v-model="user.lastName"
                    label="Last name"
                    >
                </v-text-field>

                <v-text-field
                    v-model="user.email"
                    :error-messages="emailErrors"
                    label="Email"
                    required
                    >
                </v-text-field>

                <v-flex xs6 sm6 md6 offset-sm3>
                    <v-btn block flat outline color="indigo" @click="editAirlineAdmin">Submit</v-btn>
                </v-flex>
            </v-card-text>
        </v-card>
        </v-flex>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, email, minLength, sameAs } from 'vuelidate/lib/validators'

export default{
    mixins: [validationMixin],
    validations: {
        user: {
            username: { required },
            email: { required, email }
        }
    },
    data(){
        return{
            user: {},
            showPassword: false,
            showPasswordConfirmation: false,
        }
    },
    computed:{
        usernameErrors () {
            const errors = []
            if (!this.$v.user.username.$dirty) return errors
            !this.$v.user.username.required && errors.push('Username is required.')
            return errors
        },
        // passwordErrors () {
        //     const errors = []
        //     if (!this.$v.user.password.$dirty) return errors
        //     !this.$v.user.password.minLength && errors.push('Password must be at least 6 characters long')
        //     return errors
        // },
        emailErrors () {
            const errors = []
            if (!this.$v.user.email.$dirty) return errors
            !this.$v.user.email.email && errors.push('E-mail invalid.')
            !this.$v.user.email.required && errors.push('Email is required.')
            return errors
        }
    },
    methods:{
        editAirlineAdmin(){
            this.$v.$touch();
            if(!this.$v.$invalid){
                var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

                this.$axios.put('http://localhost:8080/api/users/updateAirlineAdmin', this.user, yourConfig)
                .then(response => {
                    this.$swal("Success", "User profile updated successfully", "success");
                }).catch(error => {
                    this.$swal("Error", error.response.data.message, "error");
                })
            }
        }
    },
    created(){
        var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

        this.$axios
        .get('http://localhost:8080/api/users/currentAirlineAdmin', yourConfig)
        .then((response) => {
            this.user = response.data;
            
        }).catch((error) => {
            this.$swal("Error", "User not logged in", 'error');
        });
    }
}

</script>
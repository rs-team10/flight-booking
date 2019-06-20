<template>
    <div>
        <v-flex xs6 sm6 md6 offset-sm3>
        <v-card color="grey lighten-5" class="mt-4">
            <v-toolbar flat color="grey lighten-4">
                <v-toolbar-title class="black--text text-uppercase">
                    <span class="font-weight-light">My profile</span>
                </v-toolbar-title>    
            </v-toolbar>

            <v-card-text>
                <v-text-field
                    v-model="user.username"
                    :error-messages="usernameErrors"
                    label="Username"
                    @input="$v.user.username.$touch()"
                    @blur="$v.user.username.$touch()">
                </v-text-field>

                <v-text-field
                    v-model="user.password"
                    label="New password"
                    :append-icon="showPassword ? 'visibility' : 'visibility_off'"
                    :type="showPassword ? 'text' : 'password'"
                    :error-messages="passwordErrors"
                    counter
                    @click:append="showPassword = !showPassword"
                    @input="$v.user.password.$touch()"
                    @blur="$v.user.password.$touch()">
                </v-text-field>

                <v-text-field
                    v-model="user.firstName"
                    label="First name"
                    @input="$v.user.firstName.$touch()"
                    @blur="$v.user.firstName.$touch()">
                </v-text-field>

                <v-text-field
                    v-model="user.lastName"
                    label="Last name"
                    @input="$v.user.lastName.$touch()"
                    @blur="$v.user.lastName.$touch()">
                </v-text-field>

                <v-text-field
                    v-model="user.email"
                    :error-messages="emailErrors"
                    label="Email"
                    required
                    @input="$v.user.email.$touch()"
                    @blur="$v.user.email.$touch()">
                </v-text-field>

                <v-flex xs6 sm6 md6 offset-sm3>
                    <v-btn block flat outline color="black" @click="editRentACarAdmin">Submit</v-btn>
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
            username: { required, minLength: minLength(5) },
            password: { minLength: minLength(6) },
            
            email: { required, email }
        }
    },
    data(){
        return{
            user: {
                password: ''
              
            },
            showPassword: false,
            showPasswordConfirmation: false,
        }
    },
    computed:{
        usernameErrors () {
            const errors = []
            if (!this.$v.user.username.$dirty) return errors
            !this.$v.user.username.minLength && errors.push('Username must be at least 5 characters long')
            !this.$v.user.username.required && errors.push('Username is required.')
            return errors
        },
        passwordErrors () {
            const errors = []
            if (!this.$v.user.password.$dirty) return errors
            !this.$v.user.password.minLength && errors.push('Password must be at least 6 characters long')
            return errors
        },
        passwordConfirmationErrors () {
            const errors = []
            if (!this.$v.user.passwordConfirmation.$dirty) return errors
            !this.$v.user.passwordConfirmation.sameAs && errors.push('Passwords do not match.')
            return errors
        },
        emailErrors () {
            const errors = []
            if (!this.$v.user.email.$dirty) return errors
            !this.$v.user.email.email && errors.push('E-mail invalid.')
            !this.$v.user.email.required && errors.push('Email is required.')
            return errors
        }
    },
    methods:{
        editRentACarAdmin(){
            this.$v.$touch();
            if(!this.$v.$invalid){
                var yourConfig = {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                };

                //delete this.user.passwordConfirmation;
                this.$axios.put('/api/users/rentACarAdmin', this.user, yourConfig)
                .then(() => {
                    this.$swal("Success", "User profile updated successfully", "success");
                }).catch(error => {
                    this.$swal("Error", error.response.data.message, "error");
                })
            }
        }
    },
    created(){
        var yourConfig = {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        };

        this.$axios
        .get('/api/users/currentRentACarAdmin', yourConfig)
        .then((response) => {
            this.user = response.data;
            this.user.password = '';
            //this.user.passwordConfirmation = '';
        }).catch(() => {
            this.$swal("Error", "User not logged in", 'error');
        });
    }
}

</script>
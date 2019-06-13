<template>
    <v-layout row wrap>
        <v-flex xs12 md8>
            <h2>Edit profile</h2>
            <form>
                
                <v-text-field
                    v-model="user.username"
                    :error-messages="usernameErrors"
                    label="Username"
                    required
                    @input="$v.user.username.$touch()"
                    @blur="$v.user.username.$touch()"
                ></v-text-field>

                <v-text-field
                    v-model="user.password"
                    :append-icon="showPassword ? 'visibility' : 'visibility_off'"
                    :type="showPassword ? 'text' : 'password'"
                    :error-messages="passwordErrors"
                    label="New password"
                    required
                    counter
                    @click:append="showPassword = !showPassword"
                    @input="$v.user.password.$touch()"
                    @blur="$v.user.password.$touch()"
                ></v-text-field>

                <v-text-field
                    v-model="user.passwordConfirmation"
                    :append-icon="showPasswordConfirmation ? 'visibility' : 'visibility_off'"
                    :type="showPasswordConfirmation ? 'text' : 'password'"
                    :error-messages="passwordConfirmationErrors"
                    label="New password confirmation"
                    required
                    counter
                    @click:append="showPasswordConfirmation = !showPasswordConfirmation"
                    @input="$v.user.passwordConfirmation.$touch()"
                    @blur="$v.user.passwordConfirmation.$touch()"
                ></v-text-field>

                <v-text-field
                    v-model="user.firstName"
                    :error-messages="firstNameErrors"
                    label="First Name"
                    required
                    @input="$v.user.firstName.$touch()"
                    @blur="$v.user.firstName.$touch()"
                ></v-text-field>

                <v-text-field
                    v-model="user.lastName"
                    :error-messages="lastNameErrors"
                    label="Last Name"
                    required
                    @input="$v.user.lastName.$touch()"
                    @blur="$v.user.lastName.$touch()"
                ></v-text-field>

                <v-text-field
                    v-model="user.email"
                    :error-messages="emailErrors"
                    label="E-Mail"
                    required
                    @input="$v.user.email.$touch()"
                    @blur="$v.user.email.$touch()"
                ></v-text-field>

                <v-text-field
                    v-model="user.phone"
                    :error-messages="phoneErrors"
                    label="Phone number"
                    required
                    @input="$v.user.phone.$touch()"
                    @blur="$v.user.phone.$touch()"
                ></v-text-field>

                <GmapAutocomplete
                    class="custom-input-field"
                    @place_changed="getAddressData">
                </GmapAutocomplete>
                <!--
                <vuetify-google-autocomplete
                    id="map"
                    classname="form-control"
                    label="Address"
                    v-model="user.address"
                    v-on:placechanged="getAddressData"
                >
                </vuetify-google-autocomplete>
                -->
                <v-btn @click="editUser">submit</v-btn>
            </form>
        </v-flex>
    </v-layout>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, email, minLength, sameAs, numeric} from 'vuelidate/lib/validators'

export default {
    mixins: [validationMixin],

    data () {
        return {
            user: {
                password: '',
                passwordConfirmation: ''
            },
            showPassword: false,
            showPasswordConfirmation: false,
        }
    },

    validations: {
        user: {
            username: { required, minLength: minLength(5) },
            password: { required, minLength: minLength(6) },
            passwordConfirmation: { required, minLength: minLength(6), sameAs: sameAs('password') },
            firstName: { required },
            lastName: { required },
            email: { required, email },
            phone: { numeric },
            address: { }
        }
    },

    computed: {
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
            !this.$v.user.password.required && errors.push('Password is required.')
            return errors
        },
        passwordConfirmationErrors () {
            const errors = []
            if (!this.$v.user.passwordConfirmation.$dirty) return errors
            !this.$v.user.passwordConfirmation.sameAs && errors.push('Passwords do not match.')
            !this.$v.user.passwordConfirmation.required && errors.push('Password confirmation is required.')
            return errors
        },
        firstNameErrors () {
            const errors = []
            if (!this.$v.user.firstName.$dirty) return errors
            !this.$v.user.firstName.required && errors.push('First Name is required.')
            return errors
        },
        lastNameErrors () {
            const errors = []
            if (!this.$v.user.lastName.$dirty) return errors
            !this.$v.user.lastName.required && errors.push('Last Name is required.')
            return errors
        },
        emailErrors () {
            const errors = []
            if (!this.$v.user.email.$dirty) return errors
            !this.$v.user.email.email && errors.push('E-mail invalid.')
            !this.$v.user.email.required && errors.push('Email is required.')
            return errors
        },
        phoneErrors () {
            const errors = []
            if (!this.$v.user.phone.$dirty) return errors
            !this.$v.user.phone.numeric && errors.push('Phone number invalid.')
            return errors
        },
        addressErrors () {
            const errors = [];
            return errors
        }
    },

    methods: {
        editUser: function() {
            this.$v.$touch();
            if(!this.$v.$invalid) {
                delete this.user.passwordConfirmation;

                var yourConfig = {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                };
                
                this.$axios.put('http://localhost:8080/api/registeredUsers/', this.user, yourConfig).then((response) => {                    
                    
                    this.$swal('Success', 'User profile edited successfuly', 'success');

                    // ToDo: fix this mess below

                    this.$axios
                        .post('http://localhost:8080/auth/login', this.user)
                        .then(response => {
                            if(response.data.accessToken == undefined){
                                this.$swal('Login unsuccessful', "Invalid credentials", 'error');
                            }
                            else{
                                localStorage.setItem("token", response.data.accessToken);
                                localStorage.setItem("username", this.user.username);
                                localStorage.setItem("role", response.data.role);
                                //this.$swal('Success', 'Login successful', 'success');
                            }
                        }).catch(error => {
                            this.$swal('Login unsuccessful', "Invalid credentials", 'error');
                        });
                }).catch((error) => {
                    this.$swal("Error", error.response.data.message, 'error');
                });
            }
        },
        getAddressData: function (place) {
            if(place)
                this.user.address = place.formatted_address;
        }
    },
    created() {

        var yourConfig = {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        };

        this.$axios.get('http://localhost:8080/api/currentUser/', yourConfig).then((response) => {
            this.user = response.data;
        }).catch((error) => {
            this.$swal("Error", error.response.data.message, 'error');
        });
    }
}
</script>

<style scoped>

.custom-input-field {
    position: relative;
    padding: 5px 0;
    border: 0;
    outline: 0;
    margin-top: 10px;
    border-bottom: 1px solid gray;
    background-color: transparent;
    font-family: inherit;
    font-size: inherit;
    width: 100%;
}

.custom-input-field:focus {
  border-bottom: 2px solid #5C6BC0;
}

</style>
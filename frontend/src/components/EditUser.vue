<template>
    <v-container grid-list>
        <v-subheader>Edit user profile</v-subheader>
        <v-layout row wrap>
            <div>
                <v-alert
                    :value="submitted"
                    type="success"
                    transition="scale-transition"
                >
                User profile edited successfully.
                </v-alert>
            </div>
            <v-flex xs12 md4>
                <form v-if="!submitted">

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
                        label="Password"
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
                        label="Password confirmation"
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

                    <v-text-field
                        v-model="user.address"
                        :error-messages="addressErrors"
                        label="Address"
                        required
                        @input="$v.user.address.$touch()"
                        @blur="$v.user.address.$touch()"
                    ></v-text-field>

                     <v-btn @click="editUser">submit</v-btn>
                </form>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, email, minLength, sameAs, numeric} from 'vuelidate/lib/validators'

export default {
    mixins: [validationMixin],

    data () {
        return {
            user: {
                id: 1,                            // TODO: This is hardcoded. Change!
                passwordConfirmation: ''
            },
            showPassword: false,
            showPasswordConfirmation: false,
            submitted: false
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
            !this.$v.user.email.required && errors.push('Username is required.')
            return errors
        },
        phoneErrors () {
            const errors = []
            if (!this.$v.user.phone.$dirty) return errors
            !this.$v.user.phone.numeric && errors.push('Phone number invalid.')
            return errors
        },
        addressErrors () {
            const errors = []
            return errors
        }
    },

    methods: {
        editUser: function() {

            this.$v.$touch()

            if(!this.$v.$invalid) {
                delete this.user.passwordConfirmation;
                this.$axios.put('http://localhost:8081/api/registeredUsers/' + this.user.id, this.user).then((data) => {
                    this.submitted = true;
                });
            }
        }
    },
    created() {
        this.$axios.get('http://localhost:8081/api/registeredUsers/' + this.user.id).then((response) => {
            this.user = response.data;
        }).catch(function(error) {
                alert(error.response.data.message);
            });;
    }
}
</script>
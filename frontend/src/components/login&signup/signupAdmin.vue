<template>
    <div id="add-user">
        <h1> Register new user</h1>
        <div>
            <v-alert
                :value="success"
                type="success"
                transition="scale-transition"
            >
            Admin registered successfully.
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
                        v-model.lazy="registeredUser.username"
                        :error-messages="usernameErrors"
                        label="Username"
                        required
                        @input="$v.registeredUser.username.$touch()"
                        @blur="$v.registeredUser.username.$touch()"
                    ></v-text-field>

                    <v-text-field
                        v-model.lazy="registeredUser.password"
                        :append-icon="showPassword ? 'visibility' : 'visibility_off'"
                        :type="showPassword ? 'text' : 'password'"
                        :error-messages="passwordErrors"
                        label="Password"
                        required
                        counter
                        @click:append="showPassword = !showPassword"
                        @input="$v.registeredUser.password.$touch()"
                        @blur="$v.registeredUser.password.$touch()"
                    ></v-text-field>

                    <v-text-field
                        v-model.lazy="registeredUser.passwordConfirmation"
                        :append-icon="showPasswordConfirmation ? 'visibility' : 'visibility_off'"
                        :type="showPasswordConfirmation ? 'text' : 'password'"
                        :error-messages="passwordConfirmationErrors"
                        label="Password confirmation"
                        required
                        counter
                        @click:append="showPasswordConfirmation = !showPasswordConfirmation"
                        @input="$v.registeredUser.passwordConfirmation.$touch()"
                        @blur="$v.registeredUser.passwordConfirmation.$touch()"
                    ></v-text-field>

                    <v-text-field
                        v-model.lazy="registeredUser.firstName"
                        :error-messages="firstNameErrors"
                        label="First Name"
                        required
                        @input="$v.registeredUser.firstName.$touch()"
                        @blur="$v.registeredUser.firstName.$touch()"
                    ></v-text-field>

                    <v-text-field
                        v-model.lazy="registeredUser.lastName"
                        :error-messages="lastNameErrors"
                        label="Last Name"
                        required
                        @input="$v.registeredUser.lastName.$touch()"
                        @blur="$v.registeredUser.lastName.$touch()"
                    ></v-text-field>

                    <v-text-field
                        v-model.lazy="registeredUser.email"
                        :error-messages="emailErrors"
                        label="E-Mail"
                        required
                        @input="$v.registeredUser.email.$touch()"
                        @blur="$v.registeredUser.email.$touch()"
                    ></v-text-field>

                    <v-text-field
                        v-model.lazy="registeredUser.phone"
                        :error-messages="phoneErrors"
                        label="Phone number"
                        required
                        @input="$v.registeredUser.phone.$touch()"
                        @blur="$v.registeredUser.phone.$touch()"
                    ></v-text-field>

                    <v-select
                    v-model="registeredUser.authority"
                    :error-messages="authorityErrors"
                    :items="items"
                    :rules="rules"
                    label="Select authority"
     
                    @input="$v.registeredUser.authority.$touch()"
                    @blur="$v.registeredUser.authority.$touch()"
                ></v-select>

                     <v-btn @click="submit">submit</v-btn>
                </form>
            </v-flex>
        </div>

    </div>
    
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, email, minLength, sameAs, numeric} from 'vuelidate/lib/validators'

export default {
    mixins: [validationMixin],

    data () {
        return {
            registeredUser: {
                username: '',
                password: '',
                passwordConfirmation: '',
                firstName: '',
                lastName: '',
                email: '',
                phone: '',
                authority: ''
            },
            savePassword: '',
            showPassword: false,
            showPasswordConfirmation: false,
            success: false,
            error: false,
            items: ["SYSTEM_ADMIN", "AIRLINE_ADMIN", 
            "HOTEL_ADMIN", "RENT_A_CAR_ADMIN", "REGISTERED_USER"],
            rules: [
                    v => !!v || 'The value is required'
                    ]
        }
    },

    validations: {
        registeredUser: {
            username: { required, minLength: minLength(5) },
            password: { required, minLength: minLength(6) },
            passwordConfirmation: { required, minLength: minLength(6), sameAs: sameAs('password') },
            firstName: { required },
            lastName: { required },
            email: { required, email },
            phone: { numeric },
            authority: {required}
        }
    },

    computed: {
        usernameErrors () {
            const errors = []
            if (!this.$v.registeredUser.username.$dirty) return errors
            !this.$v.registeredUser.username.minLength && errors.push('Username must be at least 5 characters long')
            !this.$v.registeredUser.username.required && errors.push('Username is required.')
            return errors
        },
        passwordErrors () {
            const errors = []
            if (!this.$v.registeredUser.password.$dirty) return errors
            !this.$v.registeredUser.password.minLength && errors.push('Password must be at least 6 characters long')
            !this.$v.registeredUser.password.required && errors.push('Password is required.')
            return errors
        },
        passwordConfirmationErrors () {
            const errors = []
            if (!this.$v.registeredUser.passwordConfirmation.$dirty) return errors
            !this.$v.registeredUser.passwordConfirmation.sameAs && errors.push('Passwords do not match.')
            !this.$v.registeredUser.passwordConfirmation.required && errors.push('Password confirmation is required.')
            return errors
        },
        firstNameErrors () {
            const errors = []
            if (!this.$v.registeredUser.firstName.$dirty) return errors
            !this.$v.registeredUser.firstName.required && errors.push('First Name is required.')
            return errors
        },
        lastNameErrors () {
            const errors = []
            if (!this.$v.registeredUser.lastName.$dirty) return errors
            !this.$v.registeredUser.lastName.required && errors.push('Last Name is required.')
            return errors
        },
        emailErrors () {
            const errors = []
            if (!this.$v.registeredUser.email.$dirty) return errors
            !this.$v.registeredUser.email.email && errors.push('E-mail invalid.')
            !this.$v.registeredUser.email.required && errors.push('Username is required.')
            return errors
        },
        phoneErrors () {
            const errors = []
            if (!this.$v.registeredUser.phone.$dirty) return errors
            !this.$v.registeredUser.phone.numeric && errors.push('Phone number invalid.')
            return errors
        },
        addressErrors () {
            const errors = []
            return errors
        },
        authorityErrors(){
            const errors = []
            if (!this.$v.registeredUser.authority.$dirty) return errors
            !this.$v.registeredUser.authority.required && errors.push('Authority is required.')
            return errors
        }
    },

    methods: {
        submit: function() {

            this.$v.$touch()

            if(!this.$v.$invalid) {
                this.addUser();
            }
        },

        addUser: function(){
            this.savePassword = this.registeredUser.passwordConfirmation;
            delete this.registeredUser.passwordConfirmation;
            this.$axios
            .post('/api/registeredUsers', this.registeredUser)
            .then(response => {
                alert(response.data.username)
                this.success = true;
                this.registeredUser.passwordConfirmation = this.savePassword;
            }).catch(error => {
                this.registeredUser.passwordConfirmation = this.savePassword;
                this.error = error.response.data;
            });
        }
    }
}
</script>
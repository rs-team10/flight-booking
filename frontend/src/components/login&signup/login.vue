<template>
    <div id="add-user">
        <h1> Login</h1>
        <div>
            <v-alert
                :value="success"
                type="success"
                transition="scale-transition"
            >
            Login DONE!.
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
                        v-model.lazy="user.username"
                        :error-messages="usernameErrors"
                        label="Username"
                        required
                        @input="$v.user.username.$touch()"
                        @blur="$v.user.username.$touch()"
                    ></v-text-field>

                    <v-text-field
                        v-model.lazy="user.password"
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
            user: {
                username: '',
                password: '',
  
            },
            showPassword: false,
            success: false,
            error: false
        }
    },

    validations: {
        user: {
            username: { required },
            password: { required }
        }
    },

    computed: {
        usernameErrors () {
            const errors = []
            if (!this.$v.user.username.$dirty) return errors
            !this.$v.user.username.required && errors.push('Username is required.')
            return errors
        },
        passwordErrors () {
            const errors = []
            if (!this.$v.user.password.$dirty) return errors
            !this.$v.user.password.required && errors.push('Password is required.')
            return errors
        }
    },

    methods: {
        submit: function() {

            this.$v.$touch()

            if(!this.$v.$invalid) {
                this.logIn();
            }
        },

        logIn: function(){
           
            this.$axios
            .post('http://localhost:8081/auth/login', this.user)
            .then(response => {
  
                localStorage.setItem("accessToken", response.data.accessToken);
                localStorage.setItem("role", response.data.role); 
                localStorage.setItem("expiresIn", response.data.expiresIn);
                
                
                localStorage.setItem("token", {
                                                accessToken : response.data.accessToken,
                                                role        : response.data.role,
                                                expiresIn   : response.data.expiresIn
                                                });
            //za slucaj da zatreba, ne znam da li server
            //to zna iz tokena...
            }).catch(error => {
                this.error = error.response.data;
            });
        }
    }
}
</script>
<template>
    <div id="edit-user">
        <h2>Edit user profile</h2>
        <form v-if="!submitted">
            <label>Username</label>
            <input type="text" v-model="user.username" :class="{ 'error': $v.user.username.$invalid, 'valid': !$v.user.username.$invalid }"
                    @input="$v.user.username.$touch" @blur="$v.user.username.$touch" required />
            <div v-if="$v.user.username.$invalid">
                <p class="error-message">Username must contain at least {{ $v.user.username.$params.minLength.min }} characters.</p>
            </div>

            <label>Password</label>
            <input type="password" v-model="user.password" :class="{ 'error': $v.user.password.$invalid, 'valid': !$v.user.password.$invalid }" 
                    @input="$v.user.password.$touch" @blur="$v.user.password.$touch" required />
            <div v-if="$v.user.password.$invalid">
                <p class="error-message">Password must contain at least {{ $v.user.password.$params.minLength.min }} characteres.</p>
            </div>

            <!-- THIS NEEDS TO BE FIXED -->
            <label>Confirm password</label>
            <input type="password" v-model="passwordConfirmation" :class="{ 'error': $v.passwordConfirmation.$invalid , 'valid': !$v.passwordConfirmation.$invalid  }" 
                    @input="$v.passwordConfirmation.$touch" @blur="$v.passwordConfirmation.$touch" required />
            <div v-if="!$v.passwordConfirmation.sameAsPassword">
                <p class="error-message">Passwords do not match.</p>
            </div>
            
            <label>First Name</label>
            <input type="text" v-model="user.firstName" :class="{ 'error': $v.user.firstName.$invalid, 'valid': !$v.user.firstName.$invalid }"
                    @input="$v.user.firstName.$touch" @blur="$v.user.firstName.$touch" required />

            <label>Last Name</label>
            <input type="text" v-model="user.lastName" :class="{ 'error': $v.user.lastName.$invalid, 'valid': !$v.user.lastName.$invalid }"
                    @input="$v.user.lastName.$touch" @blur="$v.user.lastName.$touch" required />

            <label>Email</label>
            <input type="text" v-model="user.email" :class="{ 'error': $v.user.email.$invalid, 'valid': !$v.user.email.$invalid }"
                    @input="$v.user.email.$touch" @blur="$v.user.email.$touch" required />
            <div v-if="$v.user.email.$dirty">
                <p class="error-message" v-if="!$v.user.email.email">Please enter a valid email address.</p>
                <p class="error-message" v-if="!$v.user.email.required">Email must not be empty.</p>
            </div>

            <label>Phone</label>
            <input type="text" v-model="user.phone" :class="{ 'error': $v.user.phone.$invalid, 'valid': !$v.user.phone.$invalid }"
                    @input="$v.user.phone.$touch" @blur="$v.user.phone.$touch" />

            <label>Address</label>
            <input type="text" v-model="user.address" :class="{ 'error': $v.user.address.$invalid, 'valid': !$v.user.address.$invalid }"
                    @input="$v.user.address.$touch" @blur="$v.user.address.$touch"/>

            <hr />
            <button v-on:click.prevent="editUser" :disabled="$v.$invalid">Confirm</button>
        </form>
        <div v-if="submitted">
            <h3>User profile edited successfully</h3>
        </div>
    </div>
</template>

<script>
import { required, email, minLength, sameAs, numeric} from 'vuelidate/lib/validators'

export default {
    data () {
        return {
            user: {
                id: 1                            // TODO: This is hardcoded. Change!
            },
            passwordConfirmation: '',
            submitted: false
        }
    },
    validations: {
        user: {
            username: {
                required,
                minLength: minLength(5)
            },
            password: {
                required,
                minLength: minLength(6)
            },
            firstName: {
                required
            },
            lastName: {
                required
            },
            email: {
                required,
                email
            },
            phone: {
                numeric
            },
            address: {

            }
        },
        passwordConfirmation: {
            required,
            minLength: minLength(6),
            sameAsPassword: sameAs("passwordConfirmation")
        },
    },
    methods: {
        editUser: function() {
            if(!this.$v.$invalid) {
                this.$axios.put('http://localhost:8081/api/registeredUsers/' + this.user.id, this.user).then((data) => {
                    this.submitted = true;
                });
            }
        }
    },
    created() {
        this.$axios.get('http://localhost:8081/api/registeredUsers/' + this.user.id).then((response) => {
            this.user = response.data;
            this.passwordConfirmation = '';
        }).catch(function(error) {
                alert(error.response.data.message);
            });;
    }
}
</script>

<style scoped>
#edit-user *{
    box-sizing: border-box;
}
#edit-user{
    margin: 20px auto;
    max-width: 300px;
}
label{
    display: block;
    margin: 20px 0 10px;
}
input, button {
    display: block;
    width: 100%;
    padding: 8px;
}
h3{
    margin-top: 10px;
}
.error {
  border-color: red;
  background: #FDD;
}
.error:focus {
  outline-color: #F99;
}
.valid {
  border-color: #5A5;
  background: #EFE;
}
.valid:focus {
  outline-color: #8E8;
}
p {
    font-size: 11px;
}
</style>
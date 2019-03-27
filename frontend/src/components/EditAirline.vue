<template>
    <div id="edit-airline">
        <h2>Edit airline profile</h2>
        <form v-if="!submitted">
            <label>Airline name</label>
            <input type="text" v-model="airline.name" :class="{ 'error': $v.airline.name.$invalid, 'valid': !$v.airline.name.$invalid }"
                    @input="$v.airline.name.$touch" @blur="$v.airline.name.$touch" required />
            <div v-if="$v.airline.name.$invalid">
                <p class="error-message">Airline name must contain at least {{ $v.airline.name.$params.minLength.min }} characters.</p>
            </div>

            <label>Airline promo description</label>
            <textarea v-model.trim="airline.description" :class="{ 'error': $v.airline.description.$invalid, 'valid': !$v.airline.description.$invalid }"
                    @input="$v.airline.description.$touch" @blur="$v.airline.description.$touch" required>
            </textarea>
            <div v-if="$v.airline.description.$invalid">
                <p class="error-message">Airline description too short.</p>
            </div>

            <label>Airline address</label>
            <input type="text" v-model="airline.address" :class="{ 'error': $v.airline.address.$invalid, 'valid': !$v.airline.address.$invalid }"
                    @input="$v.airline.address.$touch" @blur="$v.airline.address.$touch" required />
            <div v-if="$v.airline.address.$invalid">
                <p class="error-message">Airline address cannot be empty.</p>
            </div>

            <hr />
            <button v-on:click.prevent="editAirline" :disabled="$v.$invalid">Confirm</button>
        </form>
        <div v-if="submitted">
            <h3>Airline profile edited successfully</h3>
        </div>
    </div>
</template>

<script>
import { required, minLength, } from 'vuelidate/lib/validators'

export default {
    data () {
        return {
            airline: {
                id: 1,                            // TODO: This is hardcoded. Change!
                name: '',
                description: '',
                address: ''
            },
            submitted: false
        }
    },
    validations: {
        airline: {
            name: {
                required,
                minLength: minLength(6)
            },
            description: {
                required,
                minLength: minLength(60)
            },
            address: {
                required
            }
        }
    },
    methods: {
        editAirline: function() {
            if(!this.$v.$invalid) {
                this.$axios.put('http://localhost:8081/api/airlines/' + this.airline.id, this.airline).then((data) => {
                    console.log(data);
                    this.submitted = true;
                });
            }
        }
    },
    created() {
        this.$axios.get('http://localhost:8081/api/airlines/' + this.airline.id).then((response) => {
            this.airline = response.data;
        }).catch(function(error) {
                alert(error.response.data.message);
            });
    }
}
</script>

<style scoped>
#edit-airline *{
    box-sizing: border-box;
}
#edit-airline{
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
textarea {
    display: block;
    width: 100%;
    height: 250px;
    padding: 8px;
    resize: none;
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
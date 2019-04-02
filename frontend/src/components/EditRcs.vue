<!-- HTML -->
<template>
    <div id = "editRcs">
        <h2>Edit rent-a-car service</h2>
        <form v-if ='!submitted'>
            
            <label>Name</label>
           <input type="text" v-model="rcs.name" 
                :class="{ 'error': $v.rcs.name.$invalid, 'valid': !$v.rcs.name.$invalid }"
                    @input="$v.rcs.name.$touch" @blur="$v.rcs.name.$touch" required />
            <div v-if="$v.rcs.name.$invalid">
                <p class="error-message">Rent-a-car service name must contain at least 
                    {{ $v.rcs.name.$params.minLength.min }} characters.</p>
            </div>



            <label>Description</label>
            <input type="text" v-model="rcs.description" 
                :class="{ 'error': $v.rcs.description.$invalid, 'valid': !$v.rcs.description.$invalid }"
                    @input="$v.rcs.description.$touch" @blur="$v.rcs.description.$touch" required />
            <div v-if="$v.rcs.description.$invalid">
                <p class="error-message">Rent-a-car service name must contain at least {{ $v.rcs.description.$params.minLength.min }} characters.</p>
            </div>



            <label>Address</label>
            <input type="text" v-model="rcs.address" 
                :class="{ 'error': $v.rcs.address.$invalid, 'valid': !$v.rcs.address.$invalid }"
                    @input="$v.rcs.description.$touch" @blur="$v.rcs.description.$touch" required />
            <div v-if="$v.rcs.description.$invalid">
                <p class="error-message">Rent-a-car service address cannot be empty.</p>
            </div>

             <hr/>
            <button v-on:click.prevent="editRcs" :disabled="$v.$invalid">Confirm</button>
        </form>
    </div>    
</template>

<!-- SCRIPT -->
<script>

import { required, minLength} from "vuelidate/lib/validators"

export default{
    data(){
        return{
            rcs: {
                id : 1,
            },
            submitted : false
        }
    },

    validations: {
        rcs: {
            name: {
                required,
                minLength: minLength(6)
            },
            description: {
                required,
                minLength: minLength(20)
            },
            address: {
                required
            }
        }
    },

    methods: {
        //TODO

    }

}
</script>

<!-- CSS -->
<style scoped>
    
    #editRcs *{
    box-sizing: border-box;
    }
    #editRcs{
        margin: 20px auto;
        max-width: 600px;
    }

    label{
    display: block;
    margin: 20px 0 10px;
    }
    input, button {
        margin :auto;
        display: block;
        width: 50%;
        padding: 8px;
        border-radius: 10px;
        
        
    }

    input:focus{
        border-color: yellow;
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

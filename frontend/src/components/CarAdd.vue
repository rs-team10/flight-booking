<!-- HTML -->
<template>
    <div id = "carAdd">
        <h2>Add car</h2>

        <form v-if = '!submitted'>

            <label>Name:</label>
            <input type="text" v-model="car.name"
            :class="{ 'error': $v.car.name.$invalid, 'valid': !$v.car.name.$invalid }"
                    @input="$v.car.name.$touch" @blur="$v.car.name.$touch" required />
            <div v-if="$v.car.name.$invalid">
                <p class="error-message">Car name cannot be empty.</p>
            </div>

            <label>Brand:</label>
            <input type="text" v-model="car.brand"
            :class="{ 'error': $v.car.brand.$invalid, 'valid': !$v.car.brand.$invalid }"
                    @input="$v.car.brand.$touch" @blur="$v.car.brand.$touch" required />
            <div v-if="$v.car.brand.$invalid">
                <p class="error-message">Car bran cannot be empty.</p>
            </div>

            <label>Model:</label>
            <input type="text" v-model="car.model"
            :class="{ 'error': $v.car.model.$invalid, 'valid': !$v.car.model.$invalid }"
                    @input="$v.car.model.$touch" @blur="$v.car.model.$touch" required />
            <div v-if="$v.car.model.$invalid">
               <p class="error-message">Car model address cannot be empty.</p>
            </div>
        
            <label>Year:</label>
            <input type="number" min="1900" max="2020" v-model="car.year"
            :class="{ 'error': $v.car.year.$invalid, 'valid': !$v.car.year.$invalid }"
                    @input="$v.car.year.$touch" @blur="$v.car.year.$touch" required />
            <div v-if="$v.car.year.$invalid">
                <p class="error-message">Car year cannot be empty.</p>
            </div>

            <label>Seats number:</label>
            <input type="number" min="1" max="7" v-model="car.seatsNumber"
            :class="{ 'error': $v.car.seatsNumber.$invalid, 'valid': !$v.car.seatsNumber.$invalid }"
                    @input="$v.car.seatsNumber.$touch" @blur="$v.car.seatsNumber.$touch" required />
            <div v-if="$v.car.seatsNumber.$invalid">
                <p class="error-message">Car seats number cannot be empty.</p>
            </div>

            <label>Type:</label>
            <input type="text" v-model="car.type"
            :class="{ 'error': $v.car.type.$invalid, 'valid': !$v.car.type.$invalid }"
                    @input="$v.car.type.$touch" @blur="$v.car.type.$touch" required />
            <div v-if="$v.car.type.$invalid">
                <p class="error-message">Car seats number cannot be empty.</p>
            </div>

            <label>Average:</label>
            <input type="number" min="1" max="10" v-model="car.average"
            :class="{ 'error': $v.car.average.$invalid, 'valid': !$v.car.average.$invalid }"
                    @input="$v.car.average.$touch" @blur="$v.car.average.$touch" required />
            <div v-if="$v.car.average.$invalid">
                <p class="error-message">Car average cannot be empty.</p>
            </div>

            

            <hr/>
            <button v-on:click.prevent="addCar" :disabled="$v.$invalid">confirm</button>
            
        </form>
    </div>    


</template>

<!-- SCRIPT -->
<script>

import { required} from "vuelidate/lib/validators"

export default{

data(){
        return{
            car : {
                    id : 1,
                    name : "",
                    brand : "",
                    model : "",
                    year : null,
                    seatsNumber : null,
                    type :"",
                    average : null
                },
                submitted : false
        }
    },

    validations: {
        car: {
            name: {
                required,
            },
            brand: {
                required,
            },
            model: {
                required
            },
            year: {
                required
            },
            seatsNumber: {
                required
            },
            type: {
                required
            },
            average: {
                required
            }
        }
    },

    methods: {
        addCar:function(){
            //TODO NE OVAKO DA RADIS PROVERU!


            var invalid = false;
            if(this.car.year > 2020 || this.car.year < 1990){
                invalid = true;
                alert("Invalid year range (must be in 1990 - 2020)");
            }if(this.car.seatsNumber > 9 || this.car.seatsNumber < 1){
                invalid = true;
                alert("Invalid year range (must be in 1 - 8)");
            }if(this.car.average > 10 || this.car.average < 1){
                invalid = true;
                alert("Invalid year range (must be in 1 - 10)");
            }if(invalid){
                return 1;
            }

            

            return 0;
        },

        dataAccept:function(){
            return 1;
        }

    }

}
</script>

<!-- CSS -->
<style scoped>
    
    #carAdd *{
    box-sizing: border-box;
    }
    #carAdd{
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

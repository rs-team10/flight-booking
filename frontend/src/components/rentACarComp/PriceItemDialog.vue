
<template>
    <v-card>
        <v-container>
            <h1 class="text-uppercase grey--text">Price list item</h1>
            <v-form 
                ref="myForm"
                v-model="valid"
                lazy-validation
            >
                <v-layout column>
                    <v-text-field
                        v-model="priceListItem.name"
                        :rules="nameRules"
                        :counter="20"
                        label="Name"
                        
                    ></v-text-field>
                    <v-text-field
                        v-model="priceListItem.price"
                        :rules="priceRules"
                        :counter="3"
                        type="number"
                        label="Price"
                    ></v-text-field>

                    <v-layout
                        row
                        wrap
                    >
                        <v-flex>
                            <v-slider
                                v-model="priceListItem.discount"
                                :max="100"
                                :min="0"
                                :step="1"
                                label="Discount"
                            ></v-slider>
                        </v-flex>
                        <v-flex
                            shrink
                            style="width: 60px"
                        >
                            <v-text-field
                                class = "pl-3"
                                hide-details
                                single-line
                                type="number"
                                v-model="priceListItem.discount"
                                readonly
                            ></v-text-field>
                        </v-flex>

                        <v-textarea
                            v-model="priceListItem.description"
                            label="Description"
                            placeholder="Price list item descsriptin"
                        ></v-textarea>

                    </v-layout>

                </v-layout>
                <v-card-actions v-if="priceListItem.id===0">
                    <v-spacer></v-spacer>
                    <v-btn  @click="submit">submit</v-btn>
                    <v-btn  color ='error' @click="cancel">Cancel</v-btn>
                </v-card-actions>
                <v-card-actions v-else>
                    <v-spacer></v-spacer>
                    <v-btn  @click="submit">submit</v-btn>
                </v-card-actions>
            </v-form>
        </v-container>
    </v-card>
</template>


<script>

var yourConfig = {headers: { Authorization: "Bearer " + localStorage.getItem("token")}}
export default {
    props:['priceListItem', 'rentACarId'],

    
    data:() => ({

        valid : true,

        nameRules: [
            v => !!v || 'Name is required',
            v => (v && v.length <= 20) || 'Name must be less than 20 characters'
        ],
        priceRules: [
            v => !!v || 'Price is required',
            v => (v && 0<=v && v <=100) || '0 < price < 100'
        ],


    }),
    methods:{

        cancel:function(){

            this.$refs.myForm.reset()

        },
        submit:function(){
            if(this.priceListItem.id == 0){
                this.$axios
                .post('/api/createPriceListItem/'+ this.rentACarId, this.priceListItem, yourConfig)
                .then(response => {
                    this.$swal("Yoohoo!", response.data, 'success');
                }).catch(() => {
                    this.$swal("Error", "Something went wrong.", 'error');
                });
            }else{
                this.$axios
                .put('/api/updatePriceListItem', this.priceListItem, yourConfig)
                .then(response => {
                    this.$swal("Yoohoo!", response.data, 'success');
                }).catch(() => {
                    this.$swal("Error", "Something went wrong.", 'error');
                });
            }
        }
    }
}
</script>


<!--


import { required, maxLength } from 'vuelidate/lib/validators'

validations: {
        priceListItem: {
            name        : { required,
                            maxLength: maxLength(20)
                        },
            price       : { required }                   
        }
    },


,
    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.priceListItem.name.$dirty) return errors
            !this.$v.priceListItem.name.maxLength && errors.push('Name must be at most 20 characters long')
            !this.$v.priceListItem.name.required && errors.push('Name is required.')
            return errors
        },
        priceErrors () {
            const errors = []
            if (!this.$v.priceListItem.price.$dirty) return errors
            !this.$v.priceListItem.price.required && errors.push('Price is required.')
            return errors
        },
    }



-->
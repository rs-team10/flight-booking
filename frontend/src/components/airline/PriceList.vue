<template>
    <div id="airline-pricelist" class="ma-3">
        <v-flex>
            <v-toolbar>
                <v-toolbar-title>Price List</v-toolbar-title>
                <v-spacer></v-spacer>

                <v-dialog v-model="showDialog" persistent max-width="400px">
                    
                    <template v-slot:activator="{ on }">
                        <v-btn v-on="on">New Price List Item</v-btn>
                    </template>

                    <v-card>
                        <v-card-title>
                            <span class="headline">Add Price List Item</span>
                        </v-card-title>

                        <v-card-text>
                            <v-container grid-list-md>
                                <v-layout wrap>
                                    
                                    <v-flex>
                                        <v-text-field
                                            label="Item Name"
                                            v-model="newPriceListItem.name"
                                            :error-messages="nameErrors">
                                        </v-text-field>
                                    </v-flex>

                                    <v-flex>
                                        <v-text-field
                                            label="Price"
                                            v-model="newPriceListItem.price"
                                            type="number"
                                            :error-messages="priceErrors">
                                        </v-text-field>
                                    </v-flex>

                                    <v-flex>
                                        <v-text-field
                                            label="Description"
                                            v-model="newPriceListItem.description"
                                            :error-messages="descriptionErrors">
                                        </v-text-field>
                                    </v-flex>

                                </v-layout>
                            </v-container>
                        </v-card-text>

                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn flat @click="close">Close</v-btn>
                            <v-btn flat @click="save">Save</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-toolbar>

            <v-list
                three-line
                class="scroll-y"
                style="height: 200px">
                <template v-for="priceListItem in this.priceListItems">
                    <v-list-tile :key="priceListItem.id" class="ma-2">
                        <v-list-tile-content>
                            <v-list-tile-title class="font-weight-bold">{{ priceListItem.name }}</v-list-tile-title>
                            <v-list-tile-sub-title class="font-weight-bold">{{ "Price: " + priceListItem.price }}</v-list-tile-sub-title>
                            <v-list-tile-sub-title class="font-weight-bold">{{ "Description: " + priceListItem.description }}</v-list-tile-sub-title>
                        </v-list-tile-content>
                        <v-icon
                            class="mr-1"
                            @click="deletePriceListItem(priceListItem)">
                            delete
                        </v-icon>
                        <v-icon
                            class="mr-1"
                            @click="editPriceListItem(priceListItem)">
                            edit
                        </v-icon>
                    </v-list-tile>
                </template>
            </v-list>
        </v-flex>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, minLength, maxLength } from 'vuelidate/lib/validators'

export default {

    mixins: [validationMixin],

    validations: {
        newPriceListItem: {
            name: { required },
            price: { required },
            description: { required },
        }
    },

    data() {
        return {
            showDialog: false,
            priceListItems: [],
            newPriceListItem: {}
        }
    },

    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.newPriceListItem.name.$dirty) return errors
            !this.$v.newPriceListItem.name.required && errors.push('Name is required.')
            return errors
        },
        priceErrors () {
            const errors = []
            if (!this.$v.newPriceListItem.price.$dirty) return errors
            !this.$v.newPriceListItem.price.required && errors.push('Price is required.')
            return errors
        },
        descriptionErrors () {
            const errors = []
            if (!this.$v.newPriceListItem.description.$dirty) return errors
            !this.$v.newPriceListItem.description.required && errors.push('Description is required.')
            return errors
        },
    },

    methods: {
        close() {
            this.$v.newPriceListItem.$reset();
            this.showDialog = false;
            this.newPriceListItem = {}
        },
        save() {
            this.$v.newPriceListItem.$touch();
            if(!this.$v.newPriceListItem.$invalid) {

                var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

                this.$axios.put('/api/airlines/addPriceListItem/', this.newPriceListItem, yourConfig)
                    .then((data) => {
                        this.$swal('Success', 'Item added successfuly', 'success');
                        this.priceListItems.push(this.newPriceListItem);
                        this.close();
                    }).catch((error) => {
                        this.$swal("Error", error.response.data.message, 'error');
                    });
            }
        },
        deletePriceListItem: function(priceListItem) {
            
            this.$swal({
                title: 'Are you sure?',
                text: 'You can\'t revert your action',
                type: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes',
                cancelButtonText: 'No',
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                showCloseButton: true,
            }).then((result) => {

                if(result.value) { 
                    const index = this.priceListItems.indexOf(priceListItem);
                    this.priceListItems.splice(index, 1);

                    var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

                    this.$axios.put('/api/airlines/removePriceListItem/', priceListItem, yourConfig)
                        .then((data) => {
                            this.$swal('Success', 'Item removed successfuly', 'success');
                        }).catch((error) => {
                            this.$swal("Error", error.response.data.message, 'error');
                        });
                }
            });
        },
        editPriceListItem(priceListItem) {
            // TODO: Izmena izabranog item-a i cuvanje na serveru [OPCIONA FUNKCIONALNOST]
        }
    },
    created() {
        
        var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

        this.$axios.get('/api/airlines/getPriceListItems', yourConfig)
        .then((response) => {
            this.priceListItems = response.data;
        }).catch((error) => {
            this.$swal("Error", error.response.data.message, 'error');
        });
    }
}

</script>

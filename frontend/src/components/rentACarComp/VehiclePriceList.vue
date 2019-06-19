<template>
    
    <v-card>
        <v-dialog v-model="dialog" max-width="500px"> 
            <component 
                :is='component'
                :priceListItem = 'priceListItem'
                :rentACarId = 'rentACarId'
            >
            </component>
        </v-dialog>

        <v-toolbar flat color="gray">


          <v-toolbar-title class="text-uppercase grey--text">Price list</v-toolbar-title>

          <v-spacer></v-spacer>

        </v-toolbar>
        <v-list
          two-line
        >
        <template v-for="item in this.priceList.priceListItems">
          <v-list-tile :key="item.id">
              
                <v-list-tile-content>

                    <v-list-tile-title>{{item.name}} €{{item.price}}</v-list-tile-title>
                    <v-list-tile-sub-title class="text--primary">Discount: {{item.discount}}%</v-list-tile-sub-title>
                    <v-list-tile-sub-title>{{item.description}}</v-list-tile-sub-title>

                </v-list-tile-content>
                <v-list-tile-action v-if = 'adminsPage'>
                    <v-layout row>
                    <v-icon
                            small
                            class="mr-2"
                            @click="editItem(item)"
                            >
                            edit
                        </v-icon>
                        <v-icon
                            small
                            @click="deleteItem(item)"
                            color = "error"
                            >
                            delete
                        </v-icon>
                    </v-layout>
                </v-list-tile-action>
            
          </v-list-tile>
        </template>

        </v-list>
        <v-layout column  align-center v-if = 'adminsPage'>
            <v-btn fab small dark color="primary" @click="addItem" flat><v-icon dark>add</v-icon></v-btn>
        </v-layout>

    </v-card>
    
</template>


<script>
import PriceItemDialog from "@/components/rentACarComp/PriceItemDialog.vue"

var yourConfig = {headers: { Authorization: "Bearer " + localStorage.getItem("token")}}
export default {
    props:['priceList', 'rentACarId'],

    components:{
        'priceItemDialog': PriceItemDialog
    },
    data:() => ({
        component : 'priceItemDialog',
        dialog : null,
        priceListItem : {
            id          : 0,
            name        : "",
            description : "",	
            price       : 0,
            discount    : 0
        },
        priceListTEST: [
            {
                id          : 1,
                name        : "Driver",
                description : "If you have low driver experience this is recommended.",	
                price       : 50,
                discount    : 10
            },
            {
                id          : 2,
                name        : "Fuel",
                description : "We can tank vehicle after return.",	
                price       : 30,
                discount    : 5
            },
            {
                id          : 3,
                name        : "Insurance",
                description : "Insurance for you and passangers.",	
                price       : 10,
                discount    : 5
            },
            {
                id          : 4,
                name        : "Something elese",
                description : "Not needed but you can choose.",	
                price       : 30,
                discount    : 5
            },
            {
                id          : 5,
                name        : "Something else",
                description : "Not needed but you can choose.",	
                price       : 30,
                discount    : 5
            }

        ]

    }),
    methods:{
        editItem:function(item){
            this.priceListItem = item;
            this.dialog= true;

        },
        deleteItem:function(item){
            this.priceListItem = item

            this.$axios
            .delete('http://localhost:8080/api/deletePriceListItem/'+ this.priceListItem.id, yourConfig)
            .then(response => {
                this.$swal("Yoohoo!", response.data, 'success');
            }).catch(() => {
                this.$swal("Error", "Something went wrong.", 'error');
            });
            
        },
        addItem:function(){

            this.priceListItem = {
                id          : 0,
                name        : "",
                description : "",	
                price       : "",
                discount    : ""
            };

            this.dialog= true;
        }
    },
    computed: {
        adminsPage(){
            return localStorage.getItem("rentACarId") == this.rentACarId
        }
    }
}
</script>

<template>
    
    <v-card>
        <v-dialog v-model="dialog" max-width="500px"> 
            <component 
                :is='component'
                :priceListItem = 'priceListItem'
            >
            </component>
        </v-dialog>

         <v-toolbar flat>   
            <v-icon>update</v-icon> 
            <v-toolbar-title>Quick reservations</v-toolbar-title>      
        </v-toolbar>
        <v-list
          two-line
        >
        <template v-for="item in this.quickReservationsTEST">
          <v-list-tile :key="item.id">
              
                <v-list-tile-content>

                    <v-list-tile-title>{{item.manufacturer}} {{item.model}} €{{item.totalPrice}}</v-list-tile-title>
                    <v-list-tile-sub-title class="text--primary">Discount: {{item.discount}}%</v-list-tile-sub-title>
                    <v-list-tile-sub-title>{{item.dateFrom}} - {{item.dateTo}}</v-list-tile-sub-title>

                </v-list-tile-content>
                <v-list-tile-action>
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
        <v-layout column  align-center>
            <v-btn fab small dark color="primary" @click="addItem" flat><v-icon dark>add</v-icon></v-btn>
        </v-layout>

    </v-card>
    
</template>


<script>
import PriceItemDialog from "@/components/rentACarComp/PriceItemDialog.vue"
export default {
    props:['quickReservations'],

    components:{
        'priceItemDialog': PriceItemDialog
    },
    data:() => ({
        component : 'priceItemDialog', //neki drugi dialog

        dialog : null,

        quickReservation : {
            id: 1,
            dateFrom: '2019-03-11',
            dateTo: '2019-04-11',
            additionalServices: "neka lista",
            vehicleId: 1,
            manufacturer: "BMW",
            model: "520",
            totalPrice: 10000,
            discount: 3
        },
        quickReservationsTEST: [
            {
                id: 1,
                dateFrom: '2019-03-11',
                dateTo: '2019-04-11',
                additionalServices: "neka lista",
                vehicleId: 1,
                manufacturer: "BMW",
                model: "520",
                totalPrice: 10000,
                discount: 3
	
            },
            {
                id: 1,
                dateFrom: '2019-03-11',
                dateTo: '2019-04-11',
                additionalServices: "neka lista",
                vehicleId: 1,
                manufacturer: "BMW",
                model: "520",
                totalPrice: 10000,
                discount: 3
            },
            {
                id: 1,
                dateFrom: '2019-03-11',
                dateTo: '2019-04-11',
                additionalServices: "neka lista",
                vehicleId: 1,
                manufacturer: "BMW",
                model: "520",
                totalPrice: 10000,
                discount: 3
            },
            {
                id: 1,
                dateFrom: '2019-03-11',
                dateTo: '2019-04-11',
                additionalServices: "neka lista",
                vehicleId: 1,
                manufacturer: "BMW",
                model: "520",
                totalPrice: 10000,
                discount: 3
            },
            {
                id: 1,
                dateFrom: '2019-03-11',
                dateTo: '2019-04-11',
                additionalServices: "neka lista",
                vehicleId: 1,
                manufacturer: "BMW",
                model: "520",
                totalPrice: 10000,
                discount: 3
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
    }
}
</script>

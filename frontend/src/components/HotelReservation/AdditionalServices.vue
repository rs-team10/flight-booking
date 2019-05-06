<template>
    <div id="additionalServices">
        <v-layout row align-center justify-space-around fill-height>
        <v-flex xs4 sm4 md4>    
            <v-card>
                <v-toolbar color="indigo" class="elevation-10">
                    <v-toolbar-title class="text--white">Additional services</v-toolbar-title>
                </v-toolbar>
            </v-card>
            <v-layout column style="height: 500px">
                <v-flex style="overflow: auto">
                    <v-data-table
                        v-model="selected"
                        :items="temp"
                        class="elevation-3 mytable"
                        item-key="name"
                        hide-actions
                        hide-headers
                        >
                        <template v-slot:items="props">
                            <tr @click="props.expanded = !props.expanded">
                                <td>
                                <v-checkbox
                                    v-model="props.selected"
                                    primary
                                    hide-details
                                    color="indigo"
                                ></v-checkbox>
                                </td>
                                <td class="text-xs-left font-weight-bold">{{ props.item.name }}</td>
                                <td class="text-xs-left font-weight-bold">{{ props.item.pricePerNight }}€</td>
                            </tr>
                        </template>
                        <template v-slot:expand="props">
                            <v-card flat>
                                <v-card-text>{{ props.item.description }}</v-card-text>
                            </v-card>
                        </template>
                    </v-data-table>
                </v-flex>
            </v-layout>
            </v-flex>

            <v-flex xs6 sm6 md6>
                    <v-card>
                        <v-toolbar>
                            <v-toolbar-title class="text-uppercase grey--text">
                                <span class="font-weight-light">Flight</span>
                                <span>Booking</span>
                            </v-toolbar-title>
                                <v-spacer></v-spacer>   
                                <span class="title text-uppercase grey--text font-weight-light">Reservation</span>
                        </v-toolbar>
                        <v-card-text>
                            <v-layout column>
                                <v-layout row class="title font-weight-light indigo--text">
                                    <span>Room price:</span>
                                    <v-spacer></v-spacer>
                                    <span>{{ reservation.totalPrice }}€</span>
                                </v-layout><br>

                                <v-layout row class="title font-weight-light indigo--text">
                                    <span>Additional services:</span>
                                    <v-spacer></v-spacer>
                                    <span>{{ additionalServicesPrice }}€</span>
                                </v-layout><br>

                                <v-divider light></v-divider><br>

                                <v-layout row class="headline font-weight-medium indigo--text text-uppercase">
                                    <span>Total</span>
                                    <v-spacer></v-spacer>
                                    <span>{{ totalPrice }}€</span>
                                </v-layout>
                            </v-layout>
                        </v-card-text>
                    </v-card>
            </v-flex>
            </v-layout>

        <v-btn color="primary" >Continue</v-btn>

    </div>
</template>

<script>
export default {
    props: ['days', 'reservation'],

    data(){
        return{
            temp: [
                { name: "WiFi",  pricePerNight: 5, description: "OPIS" },
                { name: "Parking",  pricePerNight: 10, description: "OPIS"},
                { name: "Underground parking",  pricePerNight: 10, description: "OPIS"},
                { name: "City tour arrangements",  pricePerNight: 10, description: "OPIS"},
                { name: "Smoking area club",  pricePerNight: 10, description: "OPIS"},
                { name: "Phone service",  pricePerNight: 10, description: "OPIS"},
                { name: "Spa",  pricePerNight: 10, description: "OPIS"},
                { name: "Gym",  pricePerNight: 10, description: "OPIS"},
                { name: "Dry cleaning",  pricePerNight: 10, description: "OPIS"},
                { name: "Laundry service",  pricePerNight: 10, description: "OPIS"}
            ],
            selected: []
        }
    },
    computed: {
        additionalServicesPrice(){
            var total = 0;
            for(var i = 0; i < this.selected.length; i++){  
                total += this.selected[i].pricePerNight * this.days;
            }
            return total;
        },
        totalPrice(){
            return this.additionalServicesPrice + this.reservation.totalPrice;
        }
    }
    
}
</script>

<style scoped>

/* .mytable table tr {
    background-color: #E8EAF6;
    border-bottom: none;
} */
</style>



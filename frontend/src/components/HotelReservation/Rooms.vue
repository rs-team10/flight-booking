<template>
    <div id="rooms">
        <v-layout row>

        <v-flex xs8 sm8 md8>
            <v-item-group>
                <v-layout column>
                    <v-list class="scroll-y pt-0" style="height: 700px">
                    <v-flex
                        v-for="room in this.selectedHotel.roomTypes"
                        :key="room.type"
                        class="d-inline align-center">
                    <v-item v-if="proveraSoba(room)" width="100%">
                        <div style="margin: auto;">
                            <v-card flat>
                            <v-container fluid class="pt-0">
                                <v-layout row wrap justify-center>
                                <v-flex>
                                    <v-card color="grey lighten-4">
                                    <v-layout>
                                        <v-flex xs3 md3>
                                        <v-img
                                            :src="image"
                                            height="100%"
                                            max-height="216px"
                                            max-width="372px"
                                            class="elevation-4"
                                        ></v-img>
                                        </v-flex>
                                        <v-flex xs5 md5 >
                                            <v-card-text class="indigo--text font-weight-light subheading">
                                                <v-flex id="description">
                                                    {{ room.description }} <!--650 karaktera opis da ima max -->
                                                </v-flex>
                                            </v-card-text>
                                        </v-flex>
                                        <v-flex xs4 md4>
                                            <v-layout row >
                                                <v-flex xs5>
                                                <v-layout column align left class="mt-1 ml-2 indigo--text font-weight-regular subheading">
                                                    <div class="my-2"><v-icon color="indigo">person</v-icon> {{room.capacity}}</div>
                                                    <v-divider></v-divider>
                                                    <div class="my-2">Single beds: {{room.singleBedCount}}</div>
                                                    <v-divider></v-divider>
                                                    <div class="my-2">Double beds: {{room.doubleBedCount}}</div>
                                                    <v-divider></v-divider>
                                                    <div class="my-2">Balcony: 
                                                        <v-icon v-if="room.hasBalcony" color="indigo">done</v-icon>
                                                        <v-icon v-else color="indigo">clear</v-icon>
                                                    </div>
                                                    <v-divider></v-divider>
                                                    <div class="my-2"><v-icon color="indigo" v-if="room.hasTV">tv</v-icon></div>
                                                </v-layout>
                                                </v-flex>

                                                <v-layout column align right>
                                                    <v-card height="216px" color="indigo lighten-3" class="white--text">
                                                        <v-card-title primary-title>
                                                            <div>
                                                                <div class="text-xs-center headline font-weight-light">Price for {{ days }} nights:</div> 
                                                                <div class="mt-1 text-xs-center display-1">{{ totalPrice(room.pricePerNight) }}€</div>
                                                            </div>
                                                        </v-card-title>
                                                        <v-card-actions>
                                                            <v-layout row align right class="mt-3">

                                                                <v-select
                                                                    v-model.lazy="room.numberOfRooms"
                                                                    :items="Array.from(rooms.filter(x => x.roomType.type == room.type), (x, index) => index+1)"                                                                
                                                                    label="Number of rooms"
                                                                    flat
                                                                    solo
                                                                    hide-details
                                                                    background-color="indigo lighten-4"
                                                                >
                                                                </v-select>   
                                                            </v-layout>
                                                        </v-card-actions>
                                                        </v-card>    
                                            </v-layout>
                                            </v-layout>
                                        </v-flex>                           
                                    </v-layout>
                                        <v-card-actions class="pa-3">
                                            <span class="headline indigo--text">{{ room.type }}</span>
                                            <v-spacer></v-spacer>
                                            <v-rating
                                                :value="averageFeedback"
                                                color="indigo"
                                                dense
                                                half-increments
                                                readonly
                                            ></v-rating>
                                        </v-card-actions>
                                    </v-card>
                                </v-flex>
                                </v-layout>
                            </v-container>
                            </v-card>
                        </div>
                    </v-item>
                    </v-flex>
                    </v-list>
                </v-layout>   
            </v-item-group>
        </v-flex>

        <v-flex xs4 sm4 md4>
            <v-card>
                <v-toolbar color="indigo lighten-3" class="elevation-10">
                    <v-toolbar-title class="text-uppercase white--text font-weight-light">Additional services</v-toolbar-title>
                </v-toolbar>
            </v-card>
            <v-layout column style="height: 650px">
                <v-flex style="overflow: auto">
                    <v-data-table v-if="canRender"
                        v-model="selected"
                        :items="selectedHotel.additionalServicesPriceList.priceListItems"
                        class="elevation-3"
                        item-key="name"
                        hide-actions
                        hide-headers
                        :headers-length="headersLength"
                        no-data-text="This hotel does not offer any additional services"
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
                                <td class="text-xs-left font-weight-bold indigo--text">{{ props.item.name }}</td>
                                <td class="text-xs-left font-weight-bold indigo--text">{{ props.item.price }}€</td>
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

        </v-layout>

        <v-layout row class="mr-5">
            <v-spacer></v-spacer>
            <v-btn  flat outline color="indigo" @click="reserveHotelRoom">Reserve</v-btn>
            <v-btn flat @click="goBack">Back</v-btn>
        </v-layout>
    </div>
</template>

<script>
export default {
    props: ['canRender', 'selectedHotel', 'days', 'checkInDate', 'checkOutDate', 'rooms', 'priceRange'],

    data(){
        return {
            enabled: false,
            averageFeedback: 3.46,
            image: "https://i.pinimg.com/originals/1b/c3/5f/1bc35faccb8be639c3326ec41fb20506.jpg",

            error: false,
            reservation: {
                listOfRooms: [],
                totalPrice: 0
            },
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
            selected: [],
            headersLength: 3
        }
    },
    methods: {
        reserveHotelRoom(){
            //saljemo broj soba kog tipa hoce i za koji period
            //saljemo additional services sve standard
            //nazad selektujemo iz baze jednu po jednu sobu
            

            //{ tipSobe: brojSoba }
            var lista = [];

            for(var i = 0; i < this.selectedHotel.roomTypes.length; i++){
                var roomType = this.selectedHotel.roomTypes[i];
                if(roomType.numberOfRooms != 0 && roomType.numberOfRooms != undefined){
                    var objekat = { "roomType" : roomType, "numberOfRooms" : roomType.numberOfRooms};
                    lista.push(objekat);
                }
            }
            this.reservation.listOfRooms = lista;

            //ukupna cena
            this.reservation.totalPrice = this.calculateTotalPrice(lista);

            //dodatne usluge
            this.reservation.additionalServices = this.selected

            console.log(this.reservation)
            this.$axios
            .post('http://localhost:8080/api/reservations/reserveRoom/' + this.checkInDate, + '/' + this.checkOutDate, this.reservation)
            .then(response => {
                this.$swal("Reservation successful", "", "success");
            }).catch(error => {
                console.log(error.response.data)
            });

        },

        reserve(){
            var lista = [];

            for(var i = 0; i < this.selectedHotel.roomTypes.length; i++){
                var roomType = this.selectedHotel.roomTypes[i];
                if(roomType.numberOfRooms != 0 && roomType.numberOfRooms != undefined){
                    var objekat = { "roomType" : roomType, "numberOfRooms" : roomType.numberOfRooms};
                    lista.push(objekat);
                }
            }
            this.$axios
                .post('http://localhost:8080/api/rooms/getRooms/' + this.checkInDate + '/' + this.checkOutDate, lista)
                .then(response => {
                    if(response.data.length == 0){
                        this.$swal("At least one room must be chosen.", "", "info");
                        return;
                    }
                    this.reservation.listOfRooms = response.data;
                    this.reservation.totalPrice = this.calculateTotalPrice(this.reservation.listOfRooms);
                    this.$emit('continueReservation', this.reservation);
                }).catch(error => {
                    //nema dovoljno soba tog tipa   
                    this.$swal(error.response.data, "", "info");
                })
            //Treba proveriti da li broj gostiju prevazilazi broj kreveta, da li su dostupne sobe
        },
        totalPrice(price){
            return this.days * price;
        },
        // calculateTotalPrice(listOfRooms){
        //     var total = 0;
        //     for(var i = 0; i < listOfRooms.length; i++)
        //         total += this.totalPrice(listOfRooms[i].roomType.pricePerNight);
        //     return total;
        // },
        calculateTotalPrice(roomTypes){
            var total = 0;

            for(var i = 0; i < roomTypes.length; i++){
                total += (parseInt(roomTypes[i].roomType.pricePerNight) * parseInt(this.days)) * parseInt(roomTypes[i].numberOfRooms);
            }
            total += parseInt(this.additionalServicesPrice);
            return total;
        },
        proveraSoba(room){
            if(room.pricePerNight > this.priceRange[0] && room.pricePerNight < this.priceRange[1])
                return true;
            return false;
        },
        goBack(){
            this.$emit('goBack');
        }
    },
    computed: {
        additionalServicesPrice(){
            var total = 0;
            for(var i = 0; i < this.selected.length; i++){  
                total += this.selected[i].price * this.days;
            }
            return total;
        }
    },
}
</script>

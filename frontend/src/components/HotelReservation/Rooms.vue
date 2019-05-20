<template>
    <div id="rooms">
        <v-item-group>
            <v-layout column>
                <v-list class="scroll-y" style="height: 700px">
                <v-flex
                    v-for="room in this.selectedHotel.roomTypes"
                    :key="room.type"
                    class="d-inline align-center">
                <v-item v-if="proveraSoba(room)" width="100%">
                    <div style="margin: auto;">
                        <v-card flat>
                        <v-container fluid>
                            <v-layout row wrap justify-center>
                            <v-flex xs8>
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

        <v-layout row class="mr-5">
            <v-spacer></v-spacer>
            <v-btn  flat outline color="indigo" @click="reserve">Reserve</v-btn>
            <v-btn flat @click="goBack">Back</v-btn>
        </v-layout>
    </div>
</template>

<script>
export default {
    props: ['selectedHotel', 'days', 'checkInDate', 'checkOutDate', 'rooms', 'priceRange'],

    data(){
        return {
            enabled: false,
            averageFeedback: 3.46,
            image: "https://i.pinimg.com/originals/1b/c3/5f/1bc35faccb8be639c3326ec41fb20506.jpg",

            error: false,
            reservation: {
                listOfRooms: [],
                totalPrice: 0
            }
        }
    },
    methods: {
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
        calculateTotalPrice(listOfRooms){
            var total = 0;
            for(var i = 0; i < listOfRooms.length; i++)
                total += this.totalPrice(listOfRooms[i].roomType.pricePerNight);
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
    }
}
</script>

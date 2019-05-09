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
                            <v-flex xs10>
                                <v-card>
                                <v-layout>
                                    <v-flex xs3 md3>
                                    <v-img
                                        :src="image"
                                        height="100%"
                                        max-height="216px"
                                        max-width="372px"
                                    ></v-img>
                                    </v-flex>
                                    <v-flex xs4 md4 >
                                        <v-card-text>
                                            <v-flex id="description">
                                                {{ room.description }} <!--650 karaktera opis da ima max -->
                                            </v-flex>
                                        </v-card-text>
                                    </v-flex>
                                    <v-divider light vertical></v-divider>      
                                    <v-flex xs5 md5>
                                        <v-layout row >
                                            <v-flex xs5>
                                            <v-layout column align left>
                                                Ovde ce biti sta sve ima u sobi, TV, kupatilo, zabranjeno pusenje,
                                                kapacitet, cena po nocenju, single, double beds...sta god
                                            </v-layout>
                                            </v-flex>

                                            <v-layout column align right>
                                                <v-card height="216px" color="indigo lighten-2" class="white--text">
                                                    <v-card-title primary-title>
                                                        <div>
                                                            <div class="headline">Price for {{ days }} nights: <br> {{ totalPrice(room.pricePerNight) }}</div>
                                                        </div>
                                                    </v-card-title>
                                                    <v-card-actions>
                                                        <v-layout row align right class="mt-3">

                                                            <v-autocomplete
                                                                v-model.lazy="room.numberOfRooms"
                                                                :items="Array.from(rooms.filter(x => x.roomType.type == room.type), (x, index) => index+1 )"                                                                label="Number of rooms"
                                                                flat
                                                                solo
                                                                hide-details
                                                                background-color="indigo lighten-3"
                                                            >
                                                            </v-autocomplete>   

                                                        </v-layout>
                                                    </v-card-actions>
                                                    </v-card>    
                                        </v-layout>
                                        </v-layout>
                                    </v-flex>                           
                                </v-layout>
                                <v-divider light></v-divider>
                                    <v-card-actions class="pa-3">
                                        <span class="headline">{{ room.type }}</span>
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

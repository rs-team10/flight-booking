<template>
    <div id="rooms">
        <v-item-group>
            <v-layout column >
                <v-list class="scroll-y" style="height: 700px">
                <v-flex
                    v-for="room in this.selectedHotel.roomTypes"
                    :key="room.type"
                    class="d-inline align-center">
                <v-item width="100%">
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
                                                            <v-text-field 
                                                                v-model.lazy="room.numberOfRooms"
                                                                label="Number of rooms"
                                                                solo
                                                                flat
                                                                type="number"
                                                                background-color="indigo lighten-3"
                                                                min="0"

                                                            ></v-text-field>
                                                            <!-- <v-checkbox 
                                                                color="white" 
                                                                v-model="room.active" 
                                                                label="Reserve"
                                                                class=" ml-2">
                                                            </v-checkbox>  -->
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
        <v-btn color="primary" @click="reserve">Reserve</v-btn>

    </div>
</template>

<script>
export default {
    props: ['selectedHotel', 'days'],

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
            listOfRooms: []
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
                .post('http://localhost:8080/api/hotels/getRooms', lista)
                .then(response => {
                    // for(var i = 0; i < response.data.length; i++){
                    //     this.listOfRooms.push(response.data[i]);
                    //     totalRoomPrice += response.data[i].roomType.pricePerNight * this.days;
                    // }
                    // this.error = false;
                    this.reservation.listOfRooms = response.data;
                    this.reservation.totalPrice = this.calculateTotalPrice(this.reservation.listOfRooms);
                    this.$emit('continueReservation', this.reservation);
                }).catch(error => {
                    //nema dovoljno soba tog tipa   
                    //this.$swal("", "Unfortunately, " + roomType.numberOfRooms.bold() + " " + roomType.type.bold().toUpperCase() + " rooms are not currently available.", "info");
                    this.error = true;
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
        }
    },
    mounted(){
        console.log(this.selectedHotel)
    }
}
</script>

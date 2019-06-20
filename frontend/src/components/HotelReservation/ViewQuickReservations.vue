<template>
    <div id="quickRoomReservation">
        <v-btn color="indigo" flat @click="goBack">
            <v-icon left>arrow_back</v-icon>
            Regular reservation 
        </v-btn>
        <v-item-group>    
            <v-layout column>
                <v-list class="scroll-y" style="height: 700px">
                <v-flex
                    v-for="reservation in this.quickRoomReservations"
                    :key="reservation.id"
                    class="d-inline align-center">
                <v-item width="100%">
                    <div style="margin: auto;">
                        <v-card flat>
                        <v-container fluid>
                            <v-layout row wrap justify-center>
                            <v-flex xs8 sm8 md8>
                                <v-card color="grey lighten-4">
                                <v-layout>
                                    <v-flex xs3 sm3 md3>
                                    <v-img
                                        :src="image"
                                        height="100%"
                                        max-height="216px"
                                        max-width="372px"
                                        class="elevation-4"
                                    ></v-img>
                                    </v-flex>
                                    <v-flex xs5 sm5 md5>
                                        <v-card-text class="indigo--text font-weight-light subheading">
                                            <v-flex id="description">
                                                {{ reservation.room.roomType.description}}
                                            </v-flex>
                                        </v-card-text>
                                    </v-flex>
                                    <v-flex xs4 sm4 md4>
                                        <v-layout row >
                                                <v-layout column align left class="mt-1 ml-2 indigo--text font-weight-regular subheading">
                                                    <div class="my-2"><v-icon color="indigo">person</v-icon> {{reservation.room.roomType.capacity}}</div>
                                                    <v-divider></v-divider>
                                                    <div class="my-2">Single beds: {{reservation.room.roomType.singleBedCount}}</div>
                                                    <v-divider></v-divider>
                                                    <div class="my-2">Double beds: {{reservation.room.roomType.doubleBedCount}}</div>
                                                    <v-divider></v-divider>
                                                    <div class="my-2">Balcony: 
                                                        <v-icon v-if="reservation.room.roomType.hasBalcony" color="indigo">done</v-icon>
                                                        <v-icon v-else color="indigo">clear</v-icon>
                                                    </div>
                                                    <v-divider></v-divider>
                                                    <div class="my-2"><v-icon color="indigo" v-if="reservation.room.roomType.hasTV">tv</v-icon></div>
                                                </v-layout>

                                            <v-layout column align right>
                                                <v-card height="216px" color="indigo lighten-4" class="white--text">
                                                    <v-card-title primary-title>
                                                        <div>
                                                            <div class="text-xs-center headline font-weight-light"><s>Standard price: {{reservation.totalPrice}}€</s></div> 
                                                            <div class="mt-1 indigo--text font-weight-light text-xs-center display-1">New price: {{calculateDiscountedPrice(reservation)}}€</div>
                                                        </div>
                                                    </v-card-title>

                                                    <v-card-text>
                                                        
                                                            <v-btn outline color="indigo" flat block @click="reserveQuickReservation(reservation.id)">
                                                                Reserve 
                                                                <v-icon right>done_outline</v-icon>
                                                            </v-btn>
                                                        
                                                    </v-card-text>
                                                    
                                                </v-card>    
                                            </v-layout>
                                        </v-layout>
                                    </v-flex>                           
                                </v-layout>
                                    <v-card-actions class="pa-3">
                                        <span class="headline indigo--text">{{ reservation.room.roomType.type}}</span>                                        
                                        <v-spacer></v-spacer>
                                        <v-rating
                                            :value="reservation.room.roomType.averageFeedback"
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

    </div>
</template>

<script>
export default {
    props: ['hotelId', 'checkInDate', 'checkOutDate'],

    data(){
        return{
            quickRoomReservations: [],
            image: "https://i.pinimg.com/originals/1b/c3/5f/1bc35faccb8be639c3326ec41fb20506.jpg",
        }
    },
    methods:{
        goBack(){
          this.$emit('goBackToRegularReservation')  
        },
        calculateDiscountedPrice(reservation){
            return Math.floor(reservation.totalPrice - (reservation.totalPrice * (reservation.discount / 100)))
        },
        reserveQuickReservation(resId){
            //potrebno prikazati i dodatne usluge

            this.$axios 
            .post('/api/reservations/quickReserveRoom', {}, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                },
                params: {
                    quickReservationId: parseInt(resId),
                    groupReservationId: parseInt(localStorage.getItem('groupResId'))
                }
            }).then(response => {
                //ako je rezervacija uspesna
                localStorage.removeItem('groupResId')
                localStorage.removeItem('arrivalDate')
                localStorage.removeItem('guests')
                //idi na pocetnu stranicu
                this.$swal("Reservation successful", "Pack your bags, you're ready to go!", "success")
                    .then(() => this.$router.push('/'))
                
            }).catch(error => {
                this.$swal("Reservation error", "Sorry, room has already been reserved", "error")
                    .then(() => this.$router.push('/hotelReservation'))
            })
        }
    },
    created(){
        this.$axios
            .get('/api/hotels/quickRoomReservations', {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                },
                params: {
                    hotelId: this.hotelId, 
                    checkInDate: this.checkInDate,
                    checkOutDate: this.checkOutDate
                }
            }).then(response => {
                if(response.data.length > 0){
                    this.quickRoomReservations = response.data;
                }else{
                    this.$swal("No discounts", "There are no discounted rooms while you are on vacation :(", "warning")
                        .then(() => this.$router.push('/hotelReservation'))
                }
            })
    }
}
</script>

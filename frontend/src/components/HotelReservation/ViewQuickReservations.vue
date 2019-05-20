<template>
    <div id="quickRoomReservation">
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
                                            </v-flex>
                                        </v-card-text>
                                    </v-flex>
                                    <v-flex xs5 md5>
                                        <v-layout row >
                                            <v-flex xs5>
                                                
                                            </v-flex>

                                            <v-layout column align right>
                                                <v-card height="216px" color="indigo lighten-4" class="white--text">
                                                    <v-card-title primary-title>
                                                        <div>
                                                        </div>
                                                    </v-card-title>
                                                    <v-card-actions>
                                                        <v-layout row align right class="mt-3">

                                                        </v-layout>
                                                    </v-card-actions>
                                                </v-card>    
                                            </v-layout>
                                        </v-layout>
                                    </v-flex>                           
                                </v-layout>
                                <v-divider light></v-divider>
                                    <v-card-actions class="pa-3">
                                        <span class="headline">{{ reservation.room.roomType.type}}</span>                                        
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
    created(){
        console.log(this.hotelId)
        this.$axios
            .get('http://localhost:8080/api/hotels/quickRoomReservations', {
                params: {
                    hotelId: this.hotelId, 
                    checkInDate: this.checkInDate,
                    checkOutDate: this.checkOutDate
                }
            }).then(response => {
                if(response.data.length > 0){
                    this.quickRoomReservations = response.data;
                    console.log(this.quickRoomReservations)

                }else{
                    //obavesti da nema qrr dok je on tamo
                }
            })
    }
}
</script>

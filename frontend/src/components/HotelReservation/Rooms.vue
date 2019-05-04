<template>
    <div id="rooms">
        <v-item-group>
            <v-layout column >
                <v-list class="scroll-y" style="height: 700px">
                <v-flex
                    v-for="room in this.temp"
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
                                        :src="room.image"
                                        height="100%"
                                        max-height="216px"
                                        max-width="372px"
                                    ></v-img>
                                    </v-flex>
                                    <v-flex xs4 md4 >
                                        <v-card-text>
                                            <v-flex id="description">
                                                {{ lorem }} <!--650 karaktera opis da ima max -->
                                            </v-flex>
                                        </v-card-text>
                                    </v-flex>
                                    <v-divider light vertical></v-divider>      
                                    <v-flex xs3 md3>
                                    </v-flex>                           
                                </v-layout>
                                <v-divider light></v-divider>
                                    <v-card-actions class="pa-3">
                                        <span class="headline">{{ room.type }}</span>
                                        <v-spacer></v-spacer>
                                        <v-rating
                                            :value="room.averageFeedback"
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
    data(){
        return {
            temp: [
                { type: "Room1", singleBedCount: 1, pricePerNight: 10, capacity: 2, averageFeedback: 3.46, image: "https://dynaimage.cdn.cnn.com/cnn/q_auto,w_380,c_fill,g_auto,h_214,ar_16:9/http%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F160726150143-us-beautiful-hotels-17-bellagio-2.jpg"},
                { type: "Room2", singleBedCount: 1, pricePerNight: 10, capacity: 2, averageFeedback: 3.46, image: "https://d27k8xmh3cuzik.cloudfront.net/wp-content/uploads/2018/04/FotoJetkb6592covernuwara.jpg"},
                { type: "Room3", singleBedCount: 1, pricePerNight: 10, capacity: 2, averageFeedback: 3.46, image: "https://i.dailymail.co.uk/i/pix/2014/11/14/1415989082721_wps_4_The_Jade_Hotel_exterior_j.jpg"},
                { type: "Room4", singleBedCount: 1, pricePerNight: 10, capacity: 2, averageFeedback: 3.46, image: "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fcdn-image.travelandleisure.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F1600x1000%2Fpublic%2F1546553575%2Fritz-carlton-bal-harbour-miami-florida-HOTELBATHS0119.jpg%3Fitok%3DBoGkpR6S&q=85"},
                { type: "Room5", singleBedCount: 1, pricePerNight: 10, capacity: 2, averageFeedback: 3.46, image: "https://3.bp.blogspot.com/--2G5-blY_NE/U8LBLJLDwDI/AAAAAAAAIrQ/wQtHbEhbzNI/s1600/luxury-hotel-HD-Wallpapers.jpg"},
                { type: "Room6", singleBedCount: 1, pricePerNight: 10, capacity: 2, averageFeedback: 3.46, image: "https://media.cntraveler.com/photos/53da828b6dec627b149eeee6/4:3/w_420,c_limit/fairmont-kea-lani-hawaii-maui.jpg"}
            ],
            lorem: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."       
        }
    }
}
</script>

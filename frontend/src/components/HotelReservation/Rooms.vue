<template>
    <div id="rooms">
        <v-layout row>
        <v-flex xs8 sm8 md8>
            <v-item-group>
                <v-layout column>
                    <v-list class="scroll-y pt-0" style="height: 700px; background: #FAFAFA;">
                    <v-flex
                        v-for="room in this.selectedHotel.roomTypes"
                        :key="room.type"
                        class="d-inline align-center">
                    <v-item v-if="proveraSoba(room)" width="100%">
                        <div style="margin: auto;">
                            <v-card flat color="grey lighten-5">
                            <v-container fluid class="pt-0">
                                <v-layout row wrap justify-center>
                                <v-flex>
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
                                                    {{ room.description }} <!--650 karaktera opis da ima max -->
                                                </v-flex>
                                            </v-card-text>
                                        </v-flex>
                                        <v-flex xs4 sm4 md4>
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
                                                    <v-card flat height="216px" color="indigo lighten-3" class="white--text">
                                                        <v-card-title primary-title>
                                                            <div>
                                                                <div class="text-xs-center headline font-weight-light">Price for {{ days }} nights:</div> 
                                                                <div class="mt-1 text-xs-center display-1">{{ totalPrice(room.pricePerNight) }}€</div>
                                                            </div>
                                                        </v-card-title>
                                                        <v-card-actions>
                                                            <!-- <v-layout row align right class="mt-3">

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
                                                            </v-layout> -->

                                                            <v-btn @click="openRoomsDialog(room)" flat dark outline block>Choose rooms</v-btn>
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
        </v-flex>

        <v-flex xs4 sm4 md4 class="ml-3">
            <v-card flat>
                <v-toolbar tile color="indigo lighten-3" flat >
                    <v-toolbar-title class="text-uppercase white--text font-weight-light">Additional services</v-toolbar-title>
                </v-toolbar>
            </v-card>
            <v-layout column style="height: 650px">
                <v-flex style="overflow: auto">
                    <v-data-table v-if="canRender"
                        v-model="selected"
                        :items="selectedHotel.additionalServicesPriceList.priceListItems"
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

        <v-dialog v-model="confirmResDialog" width="550px">
            <v-card>
                <v-toolbar>
                    <v-toolbar-title class="text-uppercase grey--text">
                        <span class="font-weight-light">Flight</span>
                        <span>Booking</span>
                    </v-toolbar-title>
                        <v-spacer></v-spacer>   
                        <span class="title text-uppercase grey--text font-weight-light">Hotel Reservation</span>
                </v-toolbar>
                <v-card-text>
                    <v-layout column>
                        <v-layout row class="title font-weight-light indigo--text">
                            <span>Room price:</span>
                            <v-spacer></v-spacer>
                            <span>{{ totalRoomsPrice(reservation.listOfRooms) }}€</span>
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
                            <span>{{ reservation.totalPrice }}€</span>
                        </v-layout>
                    </v-layout>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn flat>Cancel</v-btn>
                    <v-btn outline color="success" flat @click="confirmReservation">
                        Confirm reservation 
                        <v-icon right>done_outline</v-icon>
                    </v-btn>
                
                </v-card-actions>
            </v-card>
        </v-dialog>

        <v-dialog v-model="chooseRoomsDialog" max-width="300px">
            <v-card height="500px">
            <v-layout column style="height: 500px">
            <v-flex style="overflow: auto">
                <v-data-table
                    v-model="selectedRooms"
                    :items="roomsToShow"
                    :headers="roomHeaders"
                    :headers-length="headersLength"
                    item-key="roomNumber"
                    hide-actions>
                    <template v-slot:items="props">
                        <td>{{ props.item.roomNumber }}</td>
                        <td>{{ props.item.floor }}</td>
                        <td>
                            <v-checkbox
                                v-model="props.selected"
                                hide-details   
                                color="indigo">
                            </v-checkbox>
                        </td>
                    </template>
                </v-data-table>
            </v-flex>
            </v-layout>
            </v-card>
        </v-dialog>

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
            headersLength: 3,
            roomsHeadersLength: 2,
            
            chooseRoomsDialog: false,
            roomsToShow: [],
            selectedRooms: [],
            roomHeaders: [
                { text: 'Room number', align: 'left', sortable: false, value: 'roomNumber'},
                { text: 'Floor', align: 'left', sortable: false, value: 'floor'},
                { text: '', align: 'left', sortable: false, value: 'check'},
            ],

            guests: localStorage.getItem('guests'),
            confirmResDialog: false
        }
    },
    methods: {
        openRoomsDialog(roomType){
            this.roomsToShow = this.rooms.filter(x => x.roomType.type == roomType.type)
            this.chooseRoomsDialog = true
        },

        reserveHotelRoom(){
            //provera da li broj kreveta prevazilazi broj gostiju
            var numberOfBeds = 0;
            for(var i = 0; i < this.selectedRooms.length; i++)
                numberOfBeds += this.selectedRooms[i].roomType.capacity;
            
            if(numberOfBeds == 0){
                this.$swal("No rooms selected", "", "warning");
                return;
            }else if(numberOfBeds > this.guests){
                this.$swal("We're sorry", "You want to reserve more beds than you need to. Please try again", "warning")
                return;
            }else if(numberOfBeds < this.guests){
                this.$swal("We're sorry", "You're gonna need more beds'. Please try again", "warning")
                return;
            }

            this.reservation.listOfRooms = this.selectedRooms;
            this.reservation.dateFrom = this.checkInDate
            this.reservation.dateTo = this.checkOutDate
            this.reservation.totalPrice = this.calculateTotalPrice(this.reservation.listOfRooms)
            this.reservation.additionalServices = this.selected

            this.confirmResDialog = true
        },
        confirmReservation(){
            var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

            this.$axios
            .post('http://localhost:8080/api/reservations/reserveRoom/' + localStorage.getItem('groupResId'), this.reservation, yourConfig)
            .then(response => {
                this.confirmResDialog = false;

                this.$swal({
                    title: "Reservation successful",
                    text: 'Do you want to continue to vehicle reservation?',
                    type: 'success',
                    showCancelButton: true,
                    confirmButtonText: 'Yes, continue',
                    cancelButtonText: "No, I don't"
                }).then(result => {
                    if(result.value){
                        this.$router.push('/vehicleReservation')
                    }
                    else{
                        //ocisti local storage
                        localStorage.removeItem('groupResId')
                        localStorage.removeItem('arrivalDate')
                        localStorage.removeItem('guests')
                        //idi na pocetnu stranicu
                        this.$router.push('/')
                    }
                })
            }).catch(error => {
                this.$swal("Please try again", "One or more rooms have been reserved in the meantime", "error")
            });
        },
        
        calculateTotalPrice(listOfRooms){
            var total = 0;
            for(var i = 0; i < listOfRooms.length; i++)
                total += listOfRooms[i].roomType.pricePerNight * this.days
            total += this.additionalServicesPrice
            return total;
        },
        totalRoomsPrice(listOfRooms){
            var total = 0;
            for(var i = 0; i < listOfRooms.length; i++)
                total += listOfRooms[i].roomType.pricePerNight * this.days
            return total;
        },
        totalPrice(price){
            return this.days * price;
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
        }, 

        
    },
}
</script>

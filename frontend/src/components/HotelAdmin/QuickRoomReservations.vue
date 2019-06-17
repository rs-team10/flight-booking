<template>
    <div id="quickRoomReservation">
        <v-layout class="d-flex">
            <v-flex id="tabela" xs6 md6 class="ml-4">
                <v-btn icon flat @click="goBack">
                    <v-icon>
                        arrow_back
                    </v-icon>
                </v-btn>
                <v-toolbar dense color="indigo lighten-4" class="mt-2">
                    <v-toolbar-title class="text-uppercase font-weight-regular indigo--text">Quick room reservations</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn color="indigo" flat @click="saveData">
                        save
                        <v-icon right>save</v-icon>
                    </v-btn>
                </v-toolbar>
                <v-layout column style="height: 800px">
                <v-flex style="overflow: auto">
                    <v-data-table
                        :headers="headers"
                        :items="quickRoomReservations"
                        hide-actions
                        class="elevation-5">

                        <template v-slot:items="props">
                            <td>{{ props.item.room.roomType.type }}</td>
                            <td>{{ props.item.room.roomNumber }}</td>
                            <td>{{ props.item.dateFrom }}</td>
                            <td>{{ props.item.dateTo }}</td>
                            <td>{{ props.item.totalPrice }}</td>
                            <td>{{ calcDiscountedPrice(props.item.totalPrice, props.item.discount) }}</td>
                            <td>{{ props.item.discount }}%</td>
                            <td class="justify-center layout px-0">
                                <v-icon
                                    small
                                    @click="deleteItem(props.item)">
                                delete
                                </v-icon>
                            </td>
                        </template>
                    </v-data-table>
            </v-flex>
            </v-layout>
            </v-flex>
            
            <v-flex id="forma" class="mt-5 mx-3">
                <form class="pt-2">
                    <v-layout row id="dates">
                        <v-menu
                        v-model="dateFromMenu"
                        :close-on-content-click="true"
                        :nudge-right="40"
                        lazy
                        transition="scale-transition"
                        offset-y
                        full-width
                        min-width="290px"
                        >
                        <template v-slot:activator="{ on }">
                        <v-flex xs6 md6>
                            <v-text-field
                                v-model="dateFrom"
                                append-icon="event"
                                label="Date from"
                                class="mx-3"
                                max-width="30px"
                                v-on="on">
                            </v-text-field>
                        </v-flex>
                        </template>
                        <v-date-picker v-model="dateFrom" @input="dateFromMenu=false" color="indigo lighten-1"></v-date-picker>
                        </v-menu>
                        
                        <!-- datum do -->
                        <v-menu
                        v-model="dateToMenu"
                        :close-on-content-click="true"
                        :nudge-right="40"
                        lazy
                        transition="scale-transition"
                        offset-y
                        full-width
                        min-width="290px"
                        >
                        <template v-slot:activator="{ on }">
                        <v-flex xs6 md6>
                            <v-text-field
                                v-model="dateTo"
                                append-icon="event"
                                label="Date to"
                                class="mx-3"
                                v-on="on">
                            </v-text-field>
                        </v-flex>
                        </template>
                        <v-date-picker v-model="dateTo" @input="dateToMenu=false" color="indigo lighten-1"></v-date-picker>
                        </v-menu>
                    </v-layout>

                    <v-slider
                        v-model="discount"
                        label="Discount"
                        :max="100"
                        :min="0"
                        :step="5"
                        thumb-label
                        thumb-color="indigo"
                        class="mx-3"
                    ></v-slider>

                    <v-dialog id="roomSelection" v-model="roomsDialog" max-width="300px">
                        <v-card height="500px">
                        <v-layout column style="height: 500px">
                        <v-flex style="overflow: auto">
                            <v-data-table
                                v-model="selectedRooms"
                                :items="rooms"
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

                    <v-layout row>
                        <v-flex xs5 md5 class="ml-3" id="roomTypes">
                            <v-toolbar flat dense color="indigo lighten-4">
                                <v-toolbar-title class="text-uppercase font-weight-regular indigo--text">Rooms</v-toolbar-title>
                            </v-toolbar>
                            <v-layout column style="height: 500px">
                            <v-flex style="overflow: auto">
                                <v-data-table
                                    :items="selectedHotel.roomTypes"
                                    hide-headers
                                    hide-actions
                                    :headers-length="headersLength"
                                    class="elevation-5">
                                <template v-slot:items="props">
                                    <td>{{ props.item.type }}</td>
                                    <td class="justify-center layout px-0">
                                        <v-icon color="indigo" @click="openRoomsDialog(props.item.type)">
                                            add
                                        </v-icon>
                                    </td>
                                </template>
                                </v-data-table>
                            </v-flex>
                            </v-layout>
                        </v-flex>

                        <v-flex xs7 md7 class="mx-3" id="additionalServices">
                            <v-toolbar flat dense color="indigo lighten-4">
                                <v-toolbar-title class="text-uppercase font-weight-regular indigo--text">Additional services</v-toolbar-title>
                            </v-toolbar>
                            <v-layout column style="height: 500px">
                            <v-flex style="overflow: auto">
                                <v-data-table
                                    v-model="selected"
                                    :items="selectedHotel.additionalServicesPriceList.priceListItems"
                                    hide-headers
                                    hide-actions
                                    item-key="name"
                                    class="elevation-5">
                                <template v-slot:items="props">
                                    <td>{{ props.item.name }}</td>
                                    <td>{{ props.item.price }}</td>
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
                        </v-flex>
                    </v-layout>

                    <v-layout row align right class="mr-2">
                        <v-btn outline color="indigo" flat @click="addQuickReservation">
                            Add quick reservation 
                            <v-icon right>add</v-icon>
                        </v-btn>
                    </v-layout>
                </form>
            </v-flex>
        </v-layout>

    </div>
</template>

<script>
export default {
    props: ['selectedHotel'],

    data() {
        return {
            dateFromMenu: false,
            dateFrom: new Date().toISOString().substr(0, 10),
            dateToMenu: false,
            dateTo: new Date().toISOString().substr(0, 10),
            headersLength: 2,
            roomsDialog: false,
            rooms: [],
            selected: [],
            selectedRooms: [],
            discount: 0,

            temp: [
                { roomType: "Single", roomNumber: 1, dateFrom: "nesto", dateTo: "nesto", oldPrice: 33, newPrice: 15}
            ],
            headers: [
                { text: 'Room type', align: 'left', sortable: false, value: 'roomType'},
                { text: 'Room number', align: 'left', sortable: false, value: 'roomNumber'},
                { text: 'Date from', align: 'left', sortable: false, value: 'dateFrom'},
                { text: 'Date to', align: 'left', sortable: false, value: 'dateTo'},
                { text: 'Total price', align: 'left', sortable: false, value: 'oldPrice'},
                { text: 'Discounted price', align: 'left', sortable: false, value: 'newPrice'},
                { text: 'Discount', align: 'left', sortable: false, value: 'discount'},
                { text: '', value: 'actions', sortable: false }
            ],
            roomHeaders: [
                { text: 'Room number', align: 'left', sortable: false, value: 'roomNumber'},
                { text: 'Floor', align: 'left', sortable: false, value: 'floor'},
                { text: '', align: 'left', sortable: false, value: 'check'},
            ],

            quickRoomReservations: []
        }
    },
    methods: {
        openRoomsDialog(roomType){
            this.rooms = this.selectedHotel.rooms.filter(x => x.roomType.type == roomType)
            this.roomsDialog = true
        },
        goBack(){
            this.$emit('goBack')
        },
        addQuickReservation(){
            
            var additionalServicesPrice = this.calculateAdditionalService();
            if(!this.validateData()){
                return;
            }
            //TODO: POTREBNO DODATI PROVERU DA LI BRZA REZERVACIJA VEC POSTOJI ZA SOBU
             

            for(var i = 0; i < this.selectedRooms.length; i++){
                var quickRoomReservation =  {    
                                        dateFrom : this.dateFrom,
                                        dateTo : this.dateTo,
                                        additionalServices : this.selected,
                                        discount: this.discount,
                                        totalPrice : additionalServicesPrice
                                        }
                var room = this.selectedRooms[i];
                quickRoomReservation.room = room;
                quickRoomReservation.totalPrice += this.calculateRoomPrice(room.roomType.pricePerNight);
                quickRoomReservation.totalPrice = Math.floor(quickRoomReservation.totalPrice)
                this.quickRoomReservations.push(quickRoomReservation)
            } 

            
        },
        calculateAdditionalService(){
            var total = 0;
            var days = (new Date(this.dateTo) - new Date(this.dateFrom)) / (1000*60*60*24);
            for(var i = 0; i < this.selected.length; i++){  
                total += this.selected[i].price * days;
            }
            return total;
        },
        calculateRoomPrice(roomPrice){
            var days = (new Date(this.dateTo) - new Date(this.dateFrom)) / (1000*60*60*24);
            return roomPrice * days;
        },
        saveData(){
            //this.selectedHotel.quickRoomReservations = this.quickRoomReservations;
            this.$axios
            .post('http://localhost:8080/api/hotels/quickRoomReservations/'+ this.selectedHotel.id, this.quickRoomReservations)
            .then(response => {
                this.$swal("Reservations added successfuly", "", "success");
            }).catch(error => {
                console.log(error)
            });
        },
        calcDiscountedPrice(totalPrice, discount){
            return Math.floor(totalPrice - (totalPrice * (discount / 100)));
        },
        validateData(){
            if((this.dateFrom == this.dateTo) || (this.dateTo < this.dateFrom)){
                this.$swal("Invalid check-in/check-out dates", "", "error");
                return false;
            }else if(this.discount == 0){
                this.$swal("Dicount can't be 0", "", "error")
                return false;
            }else if(this.selectedRooms.length == 0){
                this.$swal("At least 1 room must be selected", "", "error")
                return false;
            }
            return true;
        }
    },
    mounted(){
        this.$axios
            .get('http://localhost:8080/api/hotels/quickRoomReservations/' + this.selectedHotel.id)
            .then(response => {
                console.log(response.data)
                this.quickRoomReservations = response.data;
            })
    }
}
</script>


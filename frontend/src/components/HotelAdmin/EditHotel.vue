<template>
    <div id="edit-hotel" v-if="selectedHotel">  
        <div id="edit-form" class="mt-3">
            <form>
            <v-layout row>
            <v-flex xs4 sm4 md4>
                <v-layout column>
                    <v-card flat color="grey lighten-5">
                        <v-card-text>
                            <v-text-field
                                v-model.lazy="selectedHotel.name"
                                :error-messages="nameErrors"
                                label="Name"
                                required>
                            </v-text-field>

                            <v-textarea
                                v-model.lazy.trim="selectedHotel.description"
                                label="Description">
                            </v-textarea>

                            <v-text-field
                                v-model="selectedHotel.location.formattedAddress"
                                label="Current address"
                                disabled
                            ></v-text-field>

                            <GmapAutocomplete
                                class="custom-input-field mt-0"
                                @place_changed="getLocationData">
                            </GmapAutocomplete>
                            <GmapMap v-if="true"
                                :center="currentMapCenter"
                                :zoom="14"
                                map-type-id="terrain"
                                class="custom-map"
                                :options="{
                                    zoomControl: true,
                                    mapTypeControl: true,
                                    scaleControl: false,
                                    streetViewControl: false,
                                    rotateControl: false,
                                    fullscreenControl: false,
                                    disableDefaultUi: false
                                }">
                                <GmapMarker
                                    :position="currentMapCenter"
                                    :clickable="false"
                                    :draggable="false"/>
                            </GmapMap>
                        </v-card-text>
                    </v-card>
                </v-layout>
            </v-flex>

            <v-flex xs8 sm8 md8>
                <v-layout column>
                    <v-btn class="mx-3" color="indigo" outline flat @click="goToQuickReservations()">
                        Quick Room Reservations
                        <v-icon right>arrow_forward</v-icon> 
                    </v-btn>
                    <v-card flat color="grey lighten-5">
                        <v-card-text>
                            <!--
                                Mesto za tabelu sa cenovnikom tipova soba
                            -->
                            <div id="cenovnik-soba">
                                <v-toolbar color="indigo lighten-3" flat>
                                    <v-toolbar-title class="text-uppercase font-weight-light">Room types</v-toolbar-title>
                                    <v-spacer></v-spacer>

                                    <v-dialog v-model="roomDialog" max-width="500px">

                                        <template v-slot:activator="{on}">
                                            <v-btn color="indigo" dark class="mb-2" v-on="on">New room type</v-btn>
                                        </template>

                                        <v-card>
                                            <v-card-title>
                                                <span class="headline">{{ roomFormTitle }}</span>
                                            </v-card-title>
                                            
                                            <v-card-text>
                                                <v-container grid-list-md>
                                                    <v-layout wrap>
                                                        <v-flex xs12>
                                                            <v-text-field v-model="roomEditedItem.type" label="Type"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12 sm6 md6>
                                                            <v-text-field v-model="roomEditedItem.pricePerNight" label="Price per night"  type="number" min="1" oninput="validity.valid||(value='');"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12 sm6 md6>
                                                            <v-text-field v-model="roomEditedItem.capacity" label="Capacity" type="number" min="1" oninput="validity.valid||(value='');"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12 sm6 md6>
                                                            <v-text-field v-model="roomEditedItem.singleBedCount" label="Single beds" type="number" min="0" oninput="validity.valid||(value='');"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12 sm6 md6>
                                                            <v-text-field v-model="roomEditedItem.doubleBedCount" label="Double beds" type="number" min="0" oninput="validity.valid||(value='');"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12>
                                                            <v-textarea v-model="roomEditedItem.description" label="Description"></v-textarea>
                                                        </v-flex>
                                                        <v-flex>
                                                            <v-text-field v-model="roomEditedItem.squareFootage" label="Square footage" type="number" min="0" oninput="validity.valid||(value='');"></v-text-field>
                                                        </v-flex>
                                                        <v-flex>
                                                            <v-checkbox v-model="roomEditedItem.hasTV" label="TV"></v-checkbox>
                                                        </v-flex>
                                                        <v-flex>
                                                            <v-checkbox v-model="roomEditedItem.hasBalcony" label="Balcony"></v-checkbox>
                                                        </v-flex>
                
                                                    </v-layout>
                                                </v-container>
                                                                    
                                            </v-card-text>

                                            <v-card-actions>
                                                <v-spacer></v-spacer>
                                                <v-btn color="indigo darken-1" flat @click="roomClose">Cancel</v-btn>
                                                <v-btn color="indigo darken-1" flat @click="roomSave">Save</v-btn>
                                            </v-card-actions>
                                        </v-card>
                                    </v-dialog>

                                    <v-dialog v-model="specialPricesDialog" max-width="800px">
                                        <component 
                                            v-bind:is="specialPricesComp"
                                            v-bind:selectedRoomType="selectedRoomType"
                                        ></component>
                                    </v-dialog>

                                    <v-dialog v-model="editRoomsDialog" max-width="800px">
                                        <component 
                                            v-bind:is="editRoomsComp"
                                            v-bind:selectedRoomType="selectedRoomType"
                                            v-bind:hotelRooms="selectedHotel.rooms"
                                            v-bind:selectedHotel="selectedHotel"
                                        ></component>
                                    </v-dialog>


                                </v-toolbar>

                                <v-data-table
                                    :headers="roomsHeaders"
                                    :items="selectedHotel.roomTypes"
                                    hide-actions
                                    :servicePagination.sync="roomPagination"
                                    class="elevation-1">

                                    <template v-slot:items="props">
                                        <td>{{ props.item.type }}</td>
                                        <td>{{ props.item.pricePerNight }}</td>
                                        <td>{{ props.item.capacity }}</td>
                                        <td>{{ props.item.singleBedCount }}</td>
                                        <td>{{ props.item.doubleBedCount }}</td>
                                        <td>{{ props.item.hasTV }}</td>
                                        <td>{{ props.item.hasBalcony }}</td>
                                        <td>{{ props.item.squareFootage }}</td>
                                        <td>{{ props.item.averageFeedback }}</td>

                                        <td class="justify-center layout px-0">
                                        
                                        <v-layout row v-if="isReserved(props.item.type)">
                                            <v-icon disabled small class="mr-2" @click="roomEditItem(props.item)">
                                                edit
                                            </v-icon>
                                            <v-icon disabled small class="mr-2" @click="roomDeleteItem(props.item)">
                                                delete
                                            </v-icon>
                                        </v-layout>
                                        
                                        <v-layout row v-else>
                                            <v-icon small class="mr-2" @click="roomEditItem(props.item)">
                                                edit
                                            </v-icon>
                                            <v-icon small class="mr-2" @click="roomDeleteItem(props.item)">
                                                delete
                                            </v-icon>
                                        </v-layout>

                                        <v-tooltip bottom>
                                            <template v-slot:activator="{ on }">
                                                <v-icon
                                                    small
                                                    class="mr-2"
                                                    @click="specialRoomPrice(props.item)"
                                                    v-on="on">
                                                    star
                                                </v-icon>

                                            </template>
                                            <span>Special prices</span>
                                        </v-tooltip>
                                        <v-tooltip bottom>
                                            <template v-slot:activator="{ on }">
                                                <v-icon
                                                    small
                                                    class="mr-2"
                                                    @click="editRooms(props.item)"
                                                    v-on="on">
                                                    hotel
                                                </v-icon>
                                            </template>
                                            <span>Rooms</span>
                                        </v-tooltip>     
                                        </td>
                                    </template>

                                </v-data-table>
                                <div class="text-xs-center pt-2">
                                    <v-pagination v-model="roomPagination.page" :length="roomPages"></v-pagination>
                                </div>
                            </div>

                            <!--
                                Mesto za tabelu sa cenovnikom dodatnih usluga
                            -->
                            <div id="cenovnik-dodatnih" class="mt-2">
                                <v-toolbar color="indigo lighten-3" flat>
                                    <v-toolbar-title class="text-uppercase font-weight-light">Additional services</v-toolbar-title>
                                    <v-spacer></v-spacer>

                                    <v-dialog v-model="serviceDialog" max-width="500px">

                                        <template v-slot:activator="{on}">
                                            <v-btn color="indigo" dark class="mb-2" v-on="on">New service</v-btn>
                                        </template>

                                        <v-card>
                                            <v-card-title>
                                                <span class="headline">{{ serviceFormTitle }}</span>
                                            </v-card-title>
                                            
                                            <v-card-text>
                                                <v-container grid-list-md>
                                                    <v-layout wrap>
                                                        <v-flex xs12>
                                                        <v-text-field v-model="serviceEditedItem.name" label="Name"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12 sm6 md6>
                                                        <v-text-field v-model="serviceEditedItem.price" label="Price" type="number" min="1" oninput="validity.valid||(value='');"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12 sm6 md6>
                                                        <v-text-field v-model="serviceEditedItem.discount" label="Discount" type="number" min="0" oninput="validity.valid||(value='');"></v-text-field>
                                                        </v-flex>
                                                        <v-flex xs12>
                                                        <v-textarea v-model="serviceEditedItem.description" label="Description"></v-textarea>
                                                        </v-flex>
                                                    </v-layout>
                                                </v-container>
                                                                    
                                            </v-card-text>

                                            <v-card-actions>
                                                <v-spacer></v-spacer>
                                                <v-btn color="indigo darken-1" flat @click="serviceClose">Cancel</v-btn>
                                                <v-btn color="indigo darken-1" flat @click="serviceSave">Save</v-btn>
                                            </v-card-actions>
                                        </v-card>
                                    </v-dialog>
                                </v-toolbar>

                                <v-data-table
                                    :headers="servicesHeaders"
                                    :items="selectedHotel.additionalServicesPriceList.priceListItems"
                                    hide-actions
                                    :servicePagination.sync="servicePagination"
                                    class="elevation-1">

                                    <template v-slot:items="props">
                                        <td>{{ props.item.name }}</td>
                                        <td >{{ props.item.price }}</td>
                                        <td >{{ props.item.discount }}</td>
                                        <td class="justify-center layout px-0">
                                            <v-icon
                                                small
                                                class="mr-2"
                                                @click="editItem(props.item)">
                                            edit
                                            </v-icon>
                                            <v-icon
                                                small
                                                @click="deleteItem(props.item)">
                                            delete
                                            </v-icon>
                                        </td>
                                    </template>
                                </v-data-table>
                                <div class="text-xs-center pt-2">
                                    <v-pagination v-model="servicePagination.page" :length="servicePages"></v-pagination>
                                </div>
                            </div>

                        </v-card-text>
                    </v-card>
                </v-layout>
            </v-flex>
            </v-layout>

            <v-flex xs6 sm6 md6 offset-sm3>
            <v-btn block outline flat color="indigo" dark class="mt-2" @click="submit">submit</v-btn>
            </v-flex>
            </form>
        </div>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required } from 'vuelidate/lib/validators'
import SpecialRoomPrices from "./SpecialRoomPrices.vue"
import EditRooms from "./EditRooms.vue"

var yourConfig = {
    headers: { Authorization: "Bearer " + localStorage.getItem("token") }
}

export default {
    components: {
        "specialRoomPrices" : SpecialRoomPrices,
        "editRooms" : EditRooms
    },

    mixins: [validationMixin],

    validations: {
        selectedHotel: {
            name: { required },
            location: {
                street : { required }
            }
        }
    },
    data(){
        return {
            specialPricesComp: "specialRoomPrices",
            editRoomsComp: "editRooms",
            selectedRoomType: {},
            //---------------------------------
            rooms: [],

            //ZA SERVICE DATA TABLE
            //===========================================
            serviceDialog: false,
            servicePagination: {},
            servicesHeaders: [
                { text: 'Name', align: 'left', sortable: false, value: 'name'},
                { text: 'Price (€)', value:'price'},
                { text: 'Discount (%)', value: 'discount'},
                { text: '', value: 'name', sortable: false }
            ],
            serviceEditedIndex: -1,
            serviceEditedItem: {},
            serviceDefaultItem: {},
            //===========================================

            //ZA ROOM TYPES DATA TABLE
            //===========================================
            roomDialog: false,
            specialPricesDialog: false,
            editRoomsDialog: false,
            roomPagination: {},
            roomsHeaders: [
                //image
                { text: 'Type', align: 'left', sortable: false, value: 'name'},
                { text: 'Price per night (€)', value:'price'},
                { text: 'Capacity', value: 'discount'},
                { text: 'Single beds', value: 'singleBeds', sortable: false },
                { text: 'Double beds', value: 'doubleBeds', sortable: false },
                { text: 'Has TV', value: 'hasTV', sortable: false },
                { text: 'Has Balcony', value: 'hasBalcony', sortable: false },
                { text: 'Square ft.', value: 'squareFootage', sortable: true },
                { text: 'Average Feedback', value: 'avgFdbk'},
                { text: '', value: 'actions', sortable: false }
            ],
            roomEditedIndex: -1,
            roomEditedItem: {},
            roomDefaultItem: {}, 
            //===========================================
            selectedHotel: null
        }
    },
    computed: {
        nameErrors () {
            const errors = []
            if (!this.$v.selectedHotel.name.$dirty) return errors
            !this.$v.selectedHotel.name.required && errors.push('Name is required.')
            return errors
        },
        addressErrors() {
            const errors = []
            if (!this.$v.selectedHotel.location.street.$dirty) return errors
            !this.$v.selectedHotel.location.street.required && errors.push('Address is required.')
            return errors
        },
        serviceFormTitle(){
            return this.serviceEditedIndex === -1 ? 'New Service' : 'Edit Service'
        },
        roomFormTitle() {
            return this.roomEditedIndex === -1 ? 'New Room Type' : 'Edit Room Type'
        },
        servicePages () {
        if (this.servicePagination.rowsPerPage == null ||
          this.servicePagination.totalItems == null
        ) return 0
        return Math.ceil(this.servicePagination.totalItems / this.servicePagination.rowsPerPage)
        },
        roomPages () {
        if (this.roomPagination.rowsPerPage == null ||
          this.roomPagination.totalItems == null
        ) return 0
        return Math.ceil(this.roomPagination.totalItems / this.roomPagination.rowsPerPage)
        },
        currentMapCenter(){
            return {
                lat: this.selectedHotel.location.latitude,
                lng: this.selectedHotel.location.longitude
            }
        }
    },
    watch:{
        serviceDialog (val){
            val || this.serviceClose()
        },
        roomsDialog (val){
            val || this.roomClose()
        }
    },
    methods:{
        submit () {
            this.$v.$touch();

            if(!this.$v.$invalid){
                this.editHotel();
            }
        },
        editHotel(){
            this.$axios
            .put('http://localhost:8080/api/hotels/' + this.selectedHotel.id, this.selectedHotel, yourConfig)
            .then(() => {
                this.$swal("Hotel edited successfully", "", "success")
                    .then(() => this.$router.push('hotels'))
            //     this.success = true;
            //     setTimeout(() => {
            //         this.success = false
            // }, 3000)
            }).catch(error => {
                this.$swal("Error", "", "error")
            });
        },
        getLocationData(place) {
            if(place) {
                this.selectedHotel.location = this.extractLocationData(place);
                this.currentMapCenter = {
                    lat: this.selectedHotel.location.latitude,
                    lng: this.selectedHotel.location.longitude
                }
            }
        },
        extractLocationData(place) {
            var locationToReturn = {};

            locationToReturn.latitude = place.geometry.location.lat();
            locationToReturn.longitude = place.geometry.location.lng();
            locationToReturn.street = place.name;
            locationToReturn.formattedAddress = place.formatted_address;

            place.address_components.forEach(element => {
                if(element.types.includes('country'))
                    locationToReturn.country = element.long_name;
                else if(element.types.includes('locality'))
                    locationToReturn.city = element.long_name;
            });
            return locationToReturn;
        },
        //==============================SERVICES================================================
        editItem(item){
            this.serviceEditedIndex = this.selectedHotel.additionalServicesPriceList.priceListItems.indexOf(item)
            this.serviceEditedItem = Object.assign({}, item)
            this.serviceDialog = true
        },

        deleteItem(item){
            const index = this.selectedHotel.additionalServicesPriceList.priceListItems.indexOf(item)
            this.selectedHotel.additionalServicesPriceList.priceListItems.splice(index, 1)
        },
        serviceClose(){
            this.serviceDialog = false
            setTimeout(() => {
                this.serviceEditedItem = Object.assign({}, this.serviceDefaultItem)
                this.serviceEditedIndex = -1
            }, 300)
        },
        serviceSave(){
            if(this.serviceEditedIndex > -1){
                //ako menjamo neki selektovani item
                Object.assign(this.selectedHotel.additionalServicesPriceList.priceListItems[this.serviceEditedIndex], this.serviceEditedItem)
            }else{
                //ako nijedan item nije selektovan znaci da dodajemo novi
                this.selectedHotel.additionalServicesPriceList.priceListItems.push(this.serviceEditedItem)
            }
            this.serviceClose()
        },
        //=========================================================================================
        //====================ROOMS================================================================
        roomEditItem(item){
            this.roomEditedIndex = this.selectedHotel.roomTypes.indexOf(item)
            this.roomEditedItem = Object.assign({}, item)
            this.roomDialog = true
        },
        roomDeleteItem(item){
            const index = this.selectedHotel.roomTypes.indexOf(item)
            this.selectedHotel.roomTypes.splice(index, 1)
        },
        roomClose(){
            this.roomDialog = false
            setTimeout(() => {
                this.roomEditedItem = Object.assign({}, this.roomDefaultItem)
                this.roomEditedIndex = -1
            }, 300)
        },
        roomSave(){
            if(this.roomEditedIndex > -1){
                //ako menjamo neki selektovani item
                Object.assign(this.selectedHotel.roomTypes[this.roomEditedIndex], this.roomEditedItem)
            }else{
                //ako nijedan item nije selektovan znaci da dodajemo novi
                //this.roomEditedItem["specialRoomPrices"] = new Array();
                this.selectedHotel.roomTypes.push(this.roomEditedItem);
            }
            this.roomClose()
        },
        specialRoomPrice(item){
            this.selectedRoomType = item
            this.specialPricesDialog = true
        },
        //=========================================================================================
        editRooms(roomType){
            this.selectedRoomType = roomType
            this.editRoomsDialog = true
        },
        isReserved(type){
            var resultArray = this.selectedHotel.rooms.filter(x => x.roomType.type == type)
            for(var i = 0; i <  resultArray.length; i++){
                if(resultArray[i].reserved)
                    return true;
            }
            return false;
        },
        goToQuickReservations(){
            this.$emit('goToQuickReservations', this.selectedHotel)
        }
    },
    beforeCreate(){
        this.$axios
        .get('http://localhost:8080/api/hotels/hotelToEdit/' + localStorage.getItem('username'))
        .then(response => {
            this.selectedHotel = response.data;
        })
        
    }
}
</script>

<style scoped>
.custom-map {
    position: relative;
    padding: 5px 0;
    width: 100%;
    height: 400px;
}
.custom-input-field {
    position: relative;
    padding: 9px 0 9px 0px;
    margin: 3px 0 8px 0px;
    /*border: 2px solid #A0A0A0;*/
    outline: 0;
    background-color: transparent;
    font-family: 'Roboto', sans-serif;
    font-size: 15px;
    width: 100%;
}
</style>
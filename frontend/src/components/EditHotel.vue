<template>
    <div id="edit-hotel">
        <div id="alerts">
            <v-alert
                :value="success"
                type="success"
                transition="scale-transition"
            >
            Hotel: <b>{{ selectedHotel.name }}</b> updated successfully.
            </v-alert>

            <v-alert
                :value="error"
                dismissible
                type="error"
                >
                {{this.error}}
            </v-alert>

        </div>  

        <div id="edit-form" class="mt-3">
            <v-flex xs12 sm6 offset-sm3> 
                <h1 class="text-xs-center indigo--text">Edit {{selectedHotel.name}}</h1>
                <form>
                    <v-text-field
                        v-model.lazy="selectedHotel.name"
                        :error-messages="nameErrors"
                        label="Name"
                        required>
                    </v-text-field>

                    <v-text-field
                        v-model.lazy="selectedHotel.location.street"
                        :error-messages="addressErrors"
                        label="Address"
                        required>
                    </v-text-field>

                    <v-textarea
                        v-model.lazy.trim="selectedHotel.description"
                        label="Description">
                    </v-textarea>

                    <!--
                        Mesto za tabelu sa cenovnikom tipova soba
                    -->
                    <div id="cenovnik-soba">
                        <v-toolbar color="indigo lighten-2">
                            <v-toolbar-title>Room types</v-toolbar-title>
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
                                                    <v-checkbox v-model="roomEditedItem.hasTV" label="Has TV"></v-checkbox>
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
                                <td >{{ props.item.pricePerNight }}</td>
                                <td >{{ props.item.capacity }}</td>
                                <td >{{ props.item.singleBedCount }}</td>
                                <td >{{ props.item.doubleBedCount }}</td>
                                <td >{{ props.item.hasTV }}</td>
                                <td >{{ props.item.averageFeedback }}</td>

                                <td class="justify-center layout px-0">
                                <v-icon
                                    small
                                    class="mr-2"
                                    @click="roomEditItem(props.item)">
                                edit
                                </v-icon>
                                <v-icon
                                    small
                                    class="mr-2"
                                    @click="roomDeleteItem(props.item)">
                                delete
                                </v-icon>
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
                    <!-- ========================================================== -->

                    <!--
                        Mesto za tabelu sa cenovnikom dodatnih usluga
                    -->
                    <div id="cenovnik-dodatnih" class="mt-2">
                        <v-toolbar color="indigo lighten-2">
                            <v-toolbar-title>Additional services</v-toolbar-title>
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
                    <!-- ========================================================== -->
                    
                    <v-btn block color="indigo" dark class="mt-2" @click="submit">submit</v-btn>
                    
                </form>
            </v-flex>
        </div>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required } from 'vuelidate/lib/validators'
import SpecialRoomPrices from "@/components/SpecialRoomPrices.vue"
import EditRooms from "@/components/EditRooms.vue"

var yourConfig = {
    headers: { Authorization: "Bearer " + localStorage.getItem("token") }
}

export default {
    components: {
        "specialRoomPrices" : SpecialRoomPrices,
        "editRooms" : EditRooms
    },

    props: ['selectedHotel'],

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
                { text: 'Average Feedback', value: 'avgFdbk'},
                { text: '', value: 'name', sortable: false }
            ],
            roomEditedIndex: -1,
            roomEditedItem: {},
            roomDefaultItem: {}, 
            //===========================================

            success: false,
            error: false
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
                console.log(this.selectedHotel);
                this.editHotel();
            }
        },
        editHotel: function(){
            this.$axios
            .put('http://localhost:8080/api/hotels/' + this.selectedHotel.id, this.selectedHotel, yourConfig)
            .then(() => {
                this.success = true;
                setTimeout(() => {
                    this.success = false
            }, 3000)
            }).catch(error => {
                this.error = error.response.data;
            });
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
                console.log(this.selectedHotel);
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
        }
    },
    created(){
        console.log(this.selectedHotel);
    }
}
</script>
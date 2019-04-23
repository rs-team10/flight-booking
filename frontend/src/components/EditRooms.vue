<template>
    <v-card>
        <v-toolbar color="indigo lighten-2">
            <v-dialog v-model="addDialog" max-width="300px" persistent>
                <template v-slot:activator="{on}">
                    <v-btn absolute color="indigo" dark fab right small class="mt-3 mr-3" v-on="on">
                    <v-icon>add</v-icon>
                    </v-btn>
                </template>

                <v-card id="addRoom">
                    <v-card-title>
                        <span class="headline ">New room</span>
                    </v-card-title>

                    <v-card-text>
                        <v-container grid-list-md class="pt-0 pb-1">
                            <v-layout wrap column>
                                <v-flex xs4>
                                    <v-text-field 
                                        v-model="newRoom.floor" 
                                        label="Floor" 
                                        type="number">
                                    </v-text-field>
                                </v-flex>

                                <v-flex xs4>
                                    <v-text-field 
                                        v-model="newRoom.squareFootage" 
                                        label="Square footage" 
                                        type="number">
                                    </v-text-field>
                                </v-flex>

                                <v-flex xs4>
                                    <v-checkbox
                                        v-model="newRoom.hasBalcony"
                                        color="indigo"
                                        label="Balcony"
                                    ></v-checkbox>
                                </v-flex>

                            </v-layout>
                        </v-container>
                    </v-card-text>

                    <v-card-actions class="pt-1">
                        <v-spacer></v-spacer>
                        <v-btn color="indigo darken-1" flat @click="closeAddDialog">Cancel</v-btn>
                        <v-btn color="indigo darken-1" flat @click="saveRoom">Save</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-toolbar>

        <v-data-table
            :items="hotelRooms"
            :headers="headers"
            class="elevation-1"
            hide-actions>
            <template v-slot:items="props">
                <td>
                    <v-edit-dialog
                        :return-value.sync="props.item.floor"
                        large
                        lazy
                        persistent
                        >
                    {{ props.item.floor }}
                    <template v-slot:input>
                        <v-text-field
                        v-model="props.item.floor"
                        label="Edit"
                        single-line
                        type="number"
                        ></v-text-field>
                    </template>
                    </v-edit-dialog>
                </td>

                <td>
                    <v-edit-dialog
                        :return-value.sync="props.item.squareFootage"
                        large
                        lazy
                        persistent
                        >
                    {{ props.item.squareFootage }}
                    <template v-slot:input>
                        <v-text-field
                        v-model="props.item.squareFootage"
                        label="Edit"
                        single-line
                        type="number"
                        ></v-text-field>
                    </template>
                    </v-edit-dialog>
                </td>

                <td>
                    <v-checkbox
                    v-model="props.item.hasBalcony"
                    color="indigo"
                    hide-details
                ></v-checkbox>
                </td>

                <td class="justify-center layout px-0">
                    <v-icon size="20px" @click="deletePrice(props.item)">delete</v-icon>
                </td>
            </template>

        </v-data-table>
    </v-card>
</template>

<script>
export default {
    props: ['selectedRoomType', 'hotelRooms'],

    data(){
        return{
            addDialog: false,
            headers:[
                { text: "Floor", align: "left", sortable: false, value: "floor" },
                { text: "Square ft.", align: "left", sortable: false, value: "squareFt" },
                { text: "Has Balcony", align: "left", sortable: false, value: "hasBalcony" },
                { text: "", align: "right", sortable: false, value: "actions" }

            ],
            newRoom: {}
        }
    },
    methods: {
        closeAddDialog(){
            this.addDialog = false;
            this.newRoom = {}
        },
        saveRoom(){
            this.newRoom.roomType = Object.assign({}, this.selectedRoomType);
            console.log(this.newRoom);
            this.hotelRooms.push(this.newRoom);
            this.closeAddDialog();
        }
    }

}
</script>

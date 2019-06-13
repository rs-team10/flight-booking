<template>
    <v-card>
      <v-toolbar color="indigo lighten-2">

      <v-dialog v-model="addDialog" max-width="600" persistent>

          <template v-slot:activator="{on}">
            <v-btn absolute color="indigo" dark fab right small class="mt-3 mr-3" v-on="on">
              <v-icon>add</v-icon>
            </v-btn>
          </template>

        <v-card id="addSpecialPrice">
          <v-card-title>
            <span class="headline">New special price</span>
          </v-card-title>

          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs4>
                  <v-text-field 
                    v-model="newPrice" 
                    label="Special price" 
                    prepend-icon="attach_money"
                    type="number">
                  </v-text-field>
                </v-flex>

                <v-flex xs4>
                  <v-menu
                    v-model="addStartDateMenu"
                    :close-on-content-click="true"
                    :nudge-right="40"
                    lazy
                    transition="scale-transition"
                    offset-y
                    full-width
                    min-width="290px"
                  >
                    <template v-slot:activator="{ on }">
                      <v-text-field
                        v-model="addStartDate"
                        prepend-icon="event"
                        readonly
                        label="Start date"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker v-model="addStartDate" @input="startMenu = false" :min="minDate"></v-date-picker>
                  </v-menu>
                </v-flex>

                <v-flex xs4>
                  <v-menu
                    v-model="addEndDateMenu"
                    :close-on-content-click="true"
                    :nudge-right="40"
                    lazy
                    transition="scale-transition"
                    offset-y
                    full-width
                    min-width="290px"
                  >
                    <template v-slot:activator="{ on }">
                      <v-text-field
                        v-model="addEndDate"
                        prepend-icon="event"
                        readonly
                        label="End date"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker v-model="addEndDate" @input="endMenu = false" :min="minDate"></v-date-picker>
                  </v-menu>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" flat @click="closeAddDialog">Cancel</v-btn>
            <v-btn color="blue darken-1" flat @click="savePrice">Save</v-btn>
          </v-card-actions>

        </v-card>
      </v-dialog>
      </v-toolbar>
      <v-data-table
        :items="selectedRoomType.specialRoomPrices"
        :headers="headers"
        class="elevation-1"
        hide-actions
      >
        <template v-slot:items="props">
          <td>
            <v-edit-dialog
              :return-value.sync="props.item.price"
              large
              lazy
              persistent
              >
            {{ props.item.price }}
              <template v-slot:input>
                <v-text-field
                  v-model="props.item.price"
                  label="Edit"
                  single-line
                  type="number"
                ></v-text-field>
              </template>
            </v-edit-dialog>
            </td>

            <td>
              <v-edit-dialog
                :return-value.sync="props.item.validFrom"
                large
                lazy
                persistent>
              {{ props.item.validFrom }}
                <template v-slot:input>
                  <v-menu
                    v-model="startMenu"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    lazy
                    transition="scale-transition"
                    offset-y
                    full-width
                    min-width="290px">
                    <template v-slot:activator="{ on }">
                      <v-text-field
                        v-model="props.item.validFrom"
                        prepend-icon="event"
                        readonly
                        v-on="on"
                      ></v-text-field>
                    </template>
                      <v-date-picker 
                        v-model="props.item.validFrom" 
                        @input="startMenu = false"
                        :min="minDate">
                      </v-date-picker>
                    </v-menu>
                </template>
              </v-edit-dialog>
            </td>

            <td>
              <v-edit-dialog
                :return-value.sync="props.item.validTo"
                large
                lazy
                persistent>
                {{ props.item.validTo }}
                <template v-slot:input>
                  <v-menu
                    v-model="endMenu"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    lazy
                    transition="scale-transition"
                    offset-y
                    full-width
                    min-width="290px">
                    <template v-slot:activator="{ on }">
                      <v-text-field
                        v-model="props.item.validTo"
                        prepend-icon="event"
                        readonly
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker 
                      v-model="props.item.validTo" 
                      @input="endMenu = false"
                      :min="minDate">
                    </v-date-picker>
                  </v-menu>
                </template>
              </v-edit-dialog>
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
  props: ['selectedRoomType'],

  data() {
    return {
      startDate: new Date().toISOString().substr(0, 10),
      startMenu: false,
      endDate: new Date().toISOString().substr(0, 10),
      endMenu: false,
      minDate: new Date().toISOString().substr(0, 10),
      
      addDialog: false,

      newPrice: null,
      addStartDate: new Date().toISOString().substr(0, 10),
      addStartDateMenu: false,
      addEndDate: new Date().toISOString().substr(0, 10),
      addEndDateMenu: false,

      newSpecialPrice: {},
      
      headers: [
        { text: "Price", align: "left", sortable: false, value: "price"},
        { text: "Start date", align: "left", sortable: false, value: "startDate" },
        { text: "End date", align: "left", sortable: false, value: "endDate" },
        { text: "", align: "right", sortable: false, value: "actions" }

      ]
    }
  },
  methods:{
    savePrice(){
      this.newSpecialPrice = 
        { price: this.newPrice, 
          validFrom: this.addStartDate.substr(0, 10), 
          validTo: this.addEndDate.substr(0, 10)
        }
      this.selectedRoomType.specialRoomPrices.push(this.newSpecialPrice);
      this.closeAddDialog();
    },
    closeAddDialog(){
      this.addDialog = false;
      this.newPrice = null;
      this.addStartDate = new Date().toISOString().substr(0, 10);
      this.addEndDate = new Date().toISOString().substr(0, 10);
    },

    deletePrice(item){
      const index = this.selectedRoomType.specialRoomPrices.indexOf(item);
      this.selectedRoomType.specialRoomPrices.splice(index, 1);
    }
  }
};
</script>
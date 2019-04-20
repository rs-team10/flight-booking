<template>
    <v-card>
      <v-data-table
        :items="temps"
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
              @save="save"
              @cancel="cancel"
              @open="open"
              @close="close">
            {{ props.item.price }}
              <template v-slot:input>
                <v-text-field
                  v-model="props.item.price"
                  label="Edit"
                  single-line
                ></v-text-field>
              </template>
            </v-edit-dialog>
            </td>

            <td>
              <v-edit-dialog
                :return-value.sync="props.item.dateFrom"
                large
                lazy
                persistent
                @save="save"
                @cancel="cancel"
                @open="open"
                @close="close">
              {{ props.item.dateFrom }}
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
                        v-model="props.item.dateFrom"
                        prepend-icon="event"
                        readonly
                        v-on="on"
                      ></v-text-field>
                    </template>
                      <v-date-picker 
                        v-model="props.item.dateFrom" 
                        @input="startMenu = false"
                        :min="minDate">
                      </v-date-picker>
                    </v-menu>
                </template>
              </v-edit-dialog>
            </td>

            <td>
              <v-edit-dialog
                :return-value.sync="props.item.dateFrom"
                large
                lazy
                persistent
                @save="save"
                @cancel="cancel"
                @open="open"
                @close="close">
              {{ props.item.dateTo }}
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
                      v-model="props.item.dateTo"
                      prepend-icon="event"
                      readonly
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker 
                    v-model="props.item.dateTo" 
                    @input="endMenu = false"
                    :min="minDate">
                  </v-date-picker>
                </v-menu>
              </template>
              </v-edit-dialog>
            </td>
        </template>
      </v-data-table>

      <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" flat>Cancel</v-btn>
          <v-btn color="blue darken-1" flat>Save</v-btn>
      </v-card-actions>
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
      
      temps: [
        {
          price: 30,
          dateFrom: '2019-04-20',
          dateTo: '2019-04-25'
        }
      ],
      headers: [
        { text: "Price", align: "left", sortable: false, value: "price" },
        { text: "Start date", align: "left", sortable: false, value: "startDate" },
        { text: "End date", align: "left", sortable: false, value: "endDate" }

      ]
    }
  }
};
</script>
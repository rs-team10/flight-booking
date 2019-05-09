<template>

    <v-item-group multiple>
        <table border="1" :key="componentKey">
            <tr>
                <td></td>
                <td v-for="k in columns" :key="k">
                    {{ String.fromCharCode(64 + k) }}
                </td>
            </tr>
            <tr v-for="i in rows" :key="i">
                <td>{{ i }}</td>

                <td v-for="j in columns" :key="j">
                    <v-item>
                        <v-card
                            hover
                            :selected="seats[(i - 1) * columns + j - 1].isSelected"
                            :color="calculateColor(seats[(i - 1) * columns + j - 1])"
                            class="d-flex align-center"
                            dark
                            height="50"
                            width="50"
                            @click="clickOnSeat(seats[(i - 1) * columns + j - 1])"
                        >
                            <v-scroll-y-transition>
                                <div class="text-xs-center">
                                    {{ seats[(i - 1) * columns + j - 1].red + String.fromCharCode(64 + seats[(i - 1) * columns + j - 1].kolona) }}
                                </div>
                            </v-scroll-y-transition>
                        </v-card>
                    </v-item>
                </td>
            </tr>
        </table>

        <v-btn @click="addNewSeat()">Add</v-btn>
        <v-btn>Delete</v-btn>
        <v-btn>Assign Class</v-btn>
    </v-item-group>

</template>

<script>
export default {

    data() {
        return {
            componentKey: 0,
            rows: 6,
            columns: 5,
            seats: [
                {id: 1, red: 1, kolona: 1, segmentClass: 'FIRST_CLASS', isReserved: false, isSelected: false},
                {id: 2, red: 1, kolona: 2, segmentClass: 'FIRST_CLASS', isReserved: true, isSelected: false},
                {id: 3, red: 1, kolona: 3, segmentClass: 'FIRST_CLASS', isReserved: false, isSelected: false},
                {id: 4, red: 1, kolona: 4, segmentClass: 'FIRST_CLASS', isReserved: false, isSelected: false},
                {id: 5, red: 1, kolona: 5, segmentClass: 'FIRST_CLASS', isReserved: false, isSelected: false},
                {id: 6, red: 2, kolona: 1, segmentClass: 'BUSINESS_CLASS', isReserved: false, isSelected: false},
                {id: 7, red: 2, kolona: 2, segmentClass: 'BUSINESS_CLASS', isReserved: false, isSelected: false},
                {id: 8, red: 2, kolona: 3, segmentClass: 'BUSINESS_CLASS', isReserved: true, isSelected: false},
                {id: 9, red: 2, kolona: 4, segmentClass: 'BUSINESS_CLASS', isReserved: false, isSelected: false},
                {id: 10, red: 2, kolona: 5, segmentClass: 'BUSINESS_CLASS', isReserved: false, isSelected: false},
                {id: 11, red: 3, kolona: 1, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 12, red: 3, kolona: 2, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 13, red: 3, kolona: 3, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 14, red: 3, kolona: 4, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 15, red: 3, kolona: 5, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 16, red: 4, kolona: 1, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 17, red: 4, kolona: 2, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 18, red: 4, kolona: 3, segmentClass: 'ECONOMIC_CLASS', isReserved: true, isSelected: false},
                {id: 19, red: 4, kolona: 4, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 20, red: 4, kolona: 5, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 21, red: 5, kolona: 1, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 22, red: 5, kolona: 2, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 23, red: 5, kolona: 3, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 24, red: 5, kolona: 4, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 25, red: 5, kolona: 5, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 26, red: 6, kolona: 1, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 27, red: 6, kolona: 2, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 28, red: 6, kolona: 3, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 29, red: 6, kolona: 4, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
                {id: 30, red: 6, kolona: 5, segmentClass: 'ECONOMIC_CLASS', isReserved: false, isSelected: false},
            ],
            selectedSeats: []
        }
    },
    methods: {
        calculateColor(seat) {
            if(seat.isReserved) {
                if (seat.segmentClass == "FIRST_CLASS")
                    return 'deep-purple lighten-4';
                else if (seat.segmentClass == "BUSINESS_CLASS")
                    return 'blue lighten-4';
                else if (seat.segmentClass == "ECONOMIC_CLASS")
                    return "grey lighten-1"
            } else {
                if(seat.isSelected)
                    return 'green';
                else if (seat.segmentClass == "FIRST_CLASS")
                    return 'deep-purple lighten-1';
                else if (seat.segmentClass == "BUSINESS_CLASS")
                    return 'blue lighten-1';
                else if (seat.segmentClass == "ECONOMIC_CLASS")
                    return "grey darken-3"
            }
        },
        clickOnSeat(seat) {
            
            if(!seat.isReserved) {
                
                if(seat.isSelected) {
                    const index = this.selectedSeats.indexOf(seat);
                    this.selectedSeats.splice(index, 1);
                } else {
                    this.selectedSeats.push(seat);
                }

                seat.isSelected = !seat.isSelected;
            }
        },
        addNewSeat() {
            var newSeat = {
                red: this.seats[this.seats.length - 1].red + 1,
                kolona: this.seats[this.seats.length - 1].red + 1,
                segmentClass: 'ECONOMIC_CLASS',
                isReserved: false,
                isSelected: false
            };
            this.seats[0].isReserved = true;
            this.seats.push(newSeat);
            console.log(this.seats);
            vm.$forceUpdate();
            this.componentKey += 1;
        },
        deleteSelectedSeat() {

        },
        changeSelectedSeat() {

        }
    }
    
}
</script>

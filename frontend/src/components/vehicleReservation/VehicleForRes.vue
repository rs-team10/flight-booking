<template>
<v-card>
<v-dialog v-model="dialog" max-width="1000px"> 
    <component 
        :is='component'
        :overview = 'overview'
    >
    </component>
</v-dialog>
<v-data-iterator
    :items="vehicles"
    :rows-per-page-items="rowsPerPageItems"
    :pagination.sync="pagination"
    content-tag="v-layout"
    row
    wrap
    >

    <template v-slot:item="props">
        <v-flex
        xs12
        sm6
        md4
        lg3
        >
            <v-hover>
                <v-card
                slot-scope="{ hover }"
                class="mx-auto"
                color="grey lighten-4"
                max-width="600"
                >
                
                <v-img
                    :aspect-ratio="16/9"
                    :src="props.item.image"
                >
                    <v-expand-transition>
                        <div
                            v-if="hover"
                            class="d-flex transition-fast-in-fast-out black darken-2 v-card--reveal display-1 white--text"
                            style="height: 100%;"
                            @click="showPrev(props.item)"
                        >
                            €{{props.item.totalPrice}}                                  
                        </div>
                    </v-expand-transition>
                    
                </v-img>
                

                <v-card-text
                    class="pt-4"
                    style="position: relative;"
                >
                    <div class="font-weight-light grey--text title mb-2">For the perfect day</div>
                    <h3 class="display-1 font-weight-light blue--text mb-2">{{props.item.manufacturer}} {{props.item.model}}</h3>
                    <div class="font-weight-light title mb-2">
                        Vehicle owner: <br/>{{props.item.mainRentACar}}
                    </div>
                </v-card-text>
                </v-card>
            </v-hover>
        </v-flex>
    </template>
    </v-data-iterator>

</v-card>
</template>


<script>
import VehicleReservationPre from "@/components/vehicleReservation/VehicleReservationPre.vue"

    export default {
        components:{
            'vehicleReservationPre': VehicleReservationPre   
        },
        
        props: ['vehicles'],

        data: () => ({

            overview:{
                vehicle : '',
                rentACarId: '',
                rentACarName: '',
                branchOfficeId: '',
                country: "",
                city    : "",
                vehicleId: '',
                priceList: {
                    id: '',
                    priceListItems: []
                }
            },
            dialog : null,
            component: 'vehicleReservationPre',

            rowsPerPageItems: [4, 8, 12],
            pagination: {
                rowsPerPage: 4
            },

            
        }),
        methods:{
            showPrev:function(vehicle){
                
                this.$axios
                .get('http://localhost:8080/api/vehicleReservationPrew/'+vehicle.id)
                .then(respone =>{
                    var datas = respone.data
                    this.overview.rentACarId = datas.rentACarId;
                    this.overview.rentACarName = datas.rentACarName;
                    this.overview.branchOfficeId = datas.branchOfficeId;
                    this.overview.country = datas.country;
                    this.overview.city = datas.city;
                    this.overview.vehicleId = datas.vehicleId;
                    this.overview.priceList = datas.priceList;
                })
                

                this.overview.vehicle = vehicle
                this.dialog = true;
            }
        }
    
  }

</script>


<style scoped>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: .5;
  position: absolute;
  width: 100%;
}
</style>
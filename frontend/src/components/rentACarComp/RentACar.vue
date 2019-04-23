<!-- Rent a car homepage -->
<template>
    <div id="view-rentacar">

            <div id="main-form" >
                <v-flex d-flex xs12 md12>


                    <template>
                        <div>
                            <v-toolbar tabs>
                            <!--
                            <v-toolbar-side-icon></v-toolbar-side-icon>
                            za ovo takodje
                            -->
                            <v-toolbar-title>Rent a car service: <b>{{this.rentACarId}}</b></v-toolbar-title> <!-- Promeni u name kada preuzmes ceo sa backa-->

                            <v-spacer></v-spacer>
                            <!--
                            <v-btn icon>
                                <v-icon>search</v-icon>
                            </v-btn>

                            <v-btn icon>
                                <v-icon>more_vert</v-icon>
                            </v-btn>
                            Ako mi nesto pametno padne na pamet da dodam
                            -->
                            <template v-slot:extension>
                                <v-tabs
                                v-model="tabs"
                                fixed-tabs
                                color="transparent"
                                >
                                <v-tabs-slider></v-tabs-slider>
                                <v-tab href="#mobile-tabs-5-1" class="primary--text">
                                    <v-icon>info</v-icon>
                                </v-tab>

                                <v-tab href="#mobile-tabs-5-2" class="primary--text">
                                    <v-icon>account_balance</v-icon>
                                </v-tab>

                                </v-tabs>
                            </template>
                            </v-toolbar>

                            <v-tabs-items v-model="tabs" class="white elevation-1">
                                <v-tab-item :value="'mobile-tabs-5-1'">
                                    <v-card>
                                        <!-- Ovde ce da ide komponenta sa informacijama u zavisnosti od toga ko pristupa (ako je admin
                                        ovog rent a cara prikazace se adminska komponenta)-->
                    

                                        <component 
                                            v-bind:is="component1"
                                            :rentACarId="rentACarId"
                                        > 
                                        </component>

                                    </v-card>
                                </v-tab-item>
                                <v-tab-item :value="'mobile-tabs-5-2'">

                                    
                                    <v-card>
                                        <!--Ovde takodje na ovaj template mogu samo komponente da se menjaju, a linkovi da ostaju isti-->
                                        <component 
                                            v-bind:is="component2"
                                            :rentACarId="rentACarId"
                                        > 
                                        </component>
                                    </v-card>


                                </v-tab-item>
                            </v-tabs-items>
                        </div>
                    </template>


                </v-flex>
            </div>

    </div>
</template>




<script>
import ViewBranchOfficesA from "@/components/rentACarComp/ViewBranchOfficesA.vue"
import ViewRentACarA from "@/components/rentACarComp/ViewRentACarA.vue"

export default {
    props:{
        rentACarId:{
            type: String,
            default: '0'
        }
    },
    components: {
        'viewBranchOfficesA' : ViewBranchOfficesA,
        'viewRentACarA' : ViewRentACarA
    },
    data () {
      return {
        component1 : 'viewRentACarA',
        component2 : 'viewBranchOfficesA',
        tabs: null,
        text: 'Ovde ce trenutnom adminu da se pokazuju podaci o njegovom rent-a-car servisu'
      }
    }
}
</script>

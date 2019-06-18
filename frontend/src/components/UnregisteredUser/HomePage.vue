<template>
    <div id="testing">
        <v-flex xs6 sm6 md6 offset-sm3>
            <v-layout row>
                <v-flex xs3 sm3 md3>
                    <v-card class="elevation-5" color="grey lighten-4" height="260px">
                        <v-card-title class="text-xs-center display-1 text-uppercase blue--text text--lighten-2 font-weight-light">Airlines</v-card-title>
                        <v-card-text>
                            <v-flex xs6 sm6 md6 offset-sm3>
                            <v-img
                                src="https://img.icons8.com/color/96/000000/paper-plane.png"
                                aspect-ratio="1"
                                max-height="100px"
                                max-width="100px">
                            </v-img>
                            </v-flex>
                            <v-btn router to="/airlines" flat block color="blue lighten-2">view all</v-btn>
                        </v-card-text>
                    </v-card>
                </v-flex>

                <v-flex xs1 sm1 md1></v-flex>

                <v-flex xs3 sm3 md3 class="mx-5">
                    <v-card class="elevation-5" color="grey lighten-4" height="260px" >
                        <v-card-title class="text-xs-center display-1 text-uppercase indigo--text text--lighten-2 font-weight-light">Hotels</v-card-title>
                        <v-card-text>
                            <v-flex xs6 sm6 md6 offset-sm3>
                            <v-img
                                src="https://img.icons8.com/color/96/000000/bed.png "
                                aspect-ratio="1"
                                max-height="100px"
                                max-width="100px">
                            </v-img>
                            </v-flex>
                            <v-btn router to="/hotels" flat block color="indigo lighten-2">view all</v-btn>
                        </v-card-text>
                    </v-card>
                </v-flex>

                <v-flex xs1 sm1 md1></v-flex>

                <v-flex xs3 sm3 md3>
                    <v-card class="elevation-5" color="grey lighten-4" height="260px">
                        <v-card-title class="text-xs-center display-1 text-uppercase red--text text--lighten-2 font-weight-light">Rent-a-car</v-card-title>
                        <v-layout align-center justify-center column>
                        <v-card-text>
                            <v-flex xs6 sm6 md6 offset-sm3>
                            <v-img
                                src="https://img.icons8.com/color/96/000000/fiat-500.png"
                                aspect-ratio="1"
                                max-height="100px"
                                max-width="100px" 
                                >
                            </v-img>
                            </v-flex>
                            <v-btn router to="/rcs" flat block color="red lighten-2">view all</v-btn>
                        </v-card-text>
                        </v-layout>

                    </v-card>
                </v-flex>
            </v-layout>
        </v-flex>
    </div>
</template>

<script>
export default {
    data(){
        return{

        }
    },
    created(){
        console.log(localStorage.getItem('user'))
    }
}
</script>

<style scoped>
#testing {
    margin-top: 200px;
}
</style>

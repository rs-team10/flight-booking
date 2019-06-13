<template>
    <v-flex xs3 sm3 md3>
    <v-card class="mx-3 mt-2 scroll-y" style="height: 870px">
        <v-toolbar flat dense color="white">
            <v-toolbar-title class="indigo--text text-uppercase">
                <span class="font-weight-light">Discounts</span>
                <span>%</span>
            </v-toolbar-title>
            <v-spacer></v-spacer>
                <v-btn flat color="indigo" @click="lista = []">
                    clear 
                </v-btn>
                <v-btn icon  @click="saveDiscounts()">
                    <v-icon color="indigo">save</v-icon>
                </v-btn>
                <v-btn icon @click="openDialog()">
                    <v-icon color="indigo">add</v-icon>
                </v-btn>    
        </v-toolbar>
        
        <v-card-text class="pt-0">
            <v-dialog v-model="dialog" max-width="600px">
                <v-card>
                    <v-toolbar color="indigo lighten-2">
                        <v-toolbar-title>
                            <span class="white--text font-weight-light">Add discount</span>
                        </v-toolbar-title>
                    </v-toolbar>
                    
                    <v-card-text>
                    <v-layout row>
                        <v-text-field type='number' v-model="value.minPoints" label="Min points" :disabled="this.lista.length != 0" persistent-hint class="mr-3"></v-text-field>
                        <v-text-field type='number' v-model="value.maxPoints" label="Max points" persistent-hint></v-text-field>
                        <v-icon color="indigo" class="mx-3">arrow_forward</v-icon>
                        <v-text-field type='number' v-model="value.discount" label="Discount (%)" persistent-hint></v-text-field>
                        <v-btn @click="addDiscount" flat class="mt-3" color="indigo">Submit</v-btn>
                    </v-layout>
                    </v-card-text>
                </v-card>
            </v-dialog>
            
            <v-flex v-for="item in lista" :key="item.id" >
                <v-layout row class="mt-5">
                    <v-flex xs2 sm2 md2 class="title font-weight-light indigo--text mt-1">
                        <span class="display-1">{{ item.discount }}%</span>
                    </v-flex> 
                    <v-flex xs10 sm10 md10>
                    <v-range-slider
                        readonly
                        thumb-label="always"
                        thumb-color="indigo lighten-2"
                        color="indigo lighten-2"
                        :min="item.minPoints"
                        :max="item.maxPoints"
                        v-model="item.arr"
                        class="mx-4">
                    </v-range-slider>
                    </v-flex>
                </v-layout>
                <v-divider></v-divider>
            </v-flex>
        </v-card-text>
    </v-card>
    </v-flex>
</template>

<script>
export default{
    data(){
        return{
            lista: [],
            dialog: false,
            value: {},
            arr: []
        }
    },
    methods: {
        openDialog(){
            if(this.lista.length != 0)
                this.value.minPoints = parseInt(this.lista.slice(-1).pop().maxPoints) + 1;
            this.dialog = true;
        },
        addDiscount(){
            if(this.value.minPoints >= this.value.maxPoints){
                this.$swal("Max value must be biger than min value", "", "warning");
                return; 
            }else if(this.value.minPoints < 1 || this.value.maxPoints < 1){
                this.$swal("Values must be bigger than zero.", "", "warning");
                return;
            }

            this.arr = new Array(this.value.minPoints, this.value.maxPoints)
            this.value.arr = this.arr
            this.lista.push(this.value);
            
            this.value = {};
            this.arr = []
            this.dialog = false;
        },
        saveDiscounts(){
            var listToSave = [];
            for(var i = 0; i < this.lista.length; i++){
                listToSave.push(
                    { discount: this.lista[i].discount,
                      minPoints: this.lista[i].minPoints,
                      maxPoints: this.lista[i].maxPoints
                    })
            }
            this.$axios
            .post("http://localhost:8080/api/discounts", listToSave)
        }
    },
    mounted(){
        this.$axios
        .get("http://localhost:8080/api/discounts")
        .then(response => {
            this.lista = response.data;
            for(var i = 0; i < this.lista.length; i++){
                this.lista[i].arr = new Array(this.lista[i].minPoints, this.lista[i].maxPoints)
            }
        })
    }
}
</script>
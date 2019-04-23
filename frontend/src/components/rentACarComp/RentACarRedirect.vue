<template>
    <div>


    </div>
</template>



<script>

export default {

    data(){
        return {

            showError : false

        }
    },

    methods:{

        checkAdmin:function(){

            if(localStorage.getItem('role') == 'ROLE_RENT_A_CAR_ADMIN'){
                //TODO napravi da asdjflkajsdf
            }
        }

    }
    




}
</script>
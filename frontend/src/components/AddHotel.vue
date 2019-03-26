<template>
    <div id="add-hotel">
        <h1>U wanna add hotel</h1>

        <div id="add-form">
        <form>
            <label>Hotel name:</label>
            <input type="text" v-model.lazy="hotel.name" required />
            <label>Hotel address:</label>
            <input type="text" v-model.lazy="hotel.address" required />
            <label>Hotel description</label>
            <textarea v-model.lazy.trim="hotel.description"></textarea>
            <!--cenovnik
                konfiguracija soba
                administrator
            -->
            
            <hr />
            <button type="submit" v-on:click.prevent="addHotel">Add Hotel</button>
        </form>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return {
            hotel: {
                name: '',
                address: '',
                description: ''
            }
        }
    },
    methods:{
        addHotel: function(){
            this.$axios
            .post('http://localhost:8081/api/hotels', this.hotel)
            .then(function(response){
                console.log(response);
                alert("Hotel is added");
            }).catch(function(error) {
                alert(error.response.data.message);
            });;
            
            //treba da se vratis na prethodnu komponentu koja prikazuje sve, kao 
            //emit a da parent slusa
        }
    }
}
</script>

<style scoped>

#add-form *{
    box-sizing: border-box;
}
#add-form{
    margin: 20px auto;
    max-width: 600px;
    padding: 20px;
}
label{
    display: block;
    margin: 20px 0 10px;
}
input[type="text"], textarea, select{
    display: block;
    width: 100%;
    padding: 8px;
}
textarea{
    height:200px;
}
h3{
    margin-top: 10px;
}
hr{
    display: none;
}
button{
    display: center;
    margin: 20px 0;
    background: #FC756F;
    color: #fff;
    border: 0;
    padding: 14px;
    border-radius: 4px;
    font-size: 18px;
    cursor: pointer;
}

</style>

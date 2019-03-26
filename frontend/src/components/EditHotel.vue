<template>
    <div id="edit-hotel">
        <h1>U wanna edit hotel</h1>

        <div id="edit-form">
            <form>
                <label>Hotel name:</label>
                <input type="text" v-model.lazy="selectedHotel.name" disabled />
                <label>Hotel address:</label>
                <input type="text" 
                    v-model.lazy="selectedHotel.address" required />
                <label>Hotel description</label>
                <textarea 
                    v-model.lazy="selectedHotel.description"></textarea>
                <!--cenovnik
                    konfiguracija soba
                    administrator
                -->
                
                <hr />
            <button type="submit" v-on:click.prevent="editHotel">Edit Hotel</button>
        </form>
        </div>
    </div>
</template>

<script>
export default {
    props: ['selectedHotel'],
    data(){
        return {
            
        }
    },
    methods:{
        editHotel: function(){
            this.$axios
            .put('http://localhost:8081/api/hotels/' + this.selectedHotel.id, 
                this.selectedHotel)
            .then(function(response){
                console.log(response);
                alert("Hotel is edited");
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

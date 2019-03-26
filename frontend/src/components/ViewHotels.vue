<template>
  <div>
    <h1>U in hotel</h1> 
    <div class="list-type2">
        <ol>
            <li v-for="hotel in hotels" :key="hotel.id" v-on:click="hotelSelected(hotel)">
                <a href="#">
                    <b>Name: </b>{{hotel.name}} <br>
                    <b>Addres: </b>{{hotel.address}} <br>
                    <b>Description: </b>{{hotel.description}}
                </a>
            </li>
        </ol>
    </div>    
  </div>
</template>

<script>
// @ is an alias to /src


export default {
  name: 'hotels',
  data(){
      return{
          hotels: [],
          selectedHotel: ''
      }
  },
  methods:{
      editHotel: function(){
          alert("Hotel izmenjen!");
      },
      fetchHotels: function(){
          this.$axios
          .get('http://localhost:8081/api/hotels')
          .then(response => this.hotels = response.data)
      },
      hotelSelected: function(hotel){
          this.$emit('hotelSelected', hotel);
      }
  },
  mounted(){
      this.fetchHotels();
  }
}
</script>

<style scoped>
ul{
    list-style-type: none;
    padding: 0;
    border: 1px solid #ddd;
}
ul li {
  padding: 8px 16px;
  border-bottom: 1px solid #ddd;
}

.list-type2{
width:400px;
margin:0 auto;
}

.list-type2 ol{
counter-reset: li;
list-style: none;
*list-style: decimal;
font-size: 15px;
font-family: 'Raleway', sans-serif;
padding: 0;
margin-bottom: 4em;
}

.list-type2 ol ol{
margin: 0 0 0 2em;
}

.list-type2 a{
position: relative;
display: block;
padding: .4em .4em .4em 2em;
*padding: .4em;
margin: .5em 0;
background: #FC756F;
color: #444;
text-decoration: none;
transition: all .2s ease-in-out;
}

.list-type2 a:hover{
background: #d6d4d4;
text-decoration:none;
transform: scale(1.1);
}

.list-type2 a:before{
content: counter(li);
counter-increment: li;
position: absolute;
left: -1.3em;
top: 50%;
margin-top: -1.3em;
background:#FC756F;
height: 2em;
width: 2em;
line-height: 2em;
border: .3em solid #fff;
text-align: center;
font-weight: bold;
color:#FFF;
}
</style>
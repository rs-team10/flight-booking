<template>
  <body>
    
    <div id = "CarSarchInputParam">
        <form>
            <table id="csit">

                <tr>
                    <td><label>Name:</label></td>
                    <td><input type="text" v-model="car.name"/></td>

                    <td><label>Brand:</label></td>
                    <td><input type="text" v-model="car.brand"/></td>

                    <td><label>Model:</label></td>
                    <td><input type="text" v-model="car.model"/></td>

                </tr>

                <tr>
                    <td><label>Year:</label></td>
                    <td><input type="number" min="1900" max="2020" v-model="car.year"/></td>

                    <td><label>Seats number:</label></td>
                    <td><input type="number" min="1" max="7" v-model="car.seatsNumber"/></td>

                    <td><label>Type:</label></td>
                    <td><input type="text" v-model="car.type"/></td>

                </tr>

                <tr>
                    <td></td>
                    <td></td>
                    <td><label>Average:</label></td>
                    <td><input type="number" min="1" max="10" v-model="car.average"/></td>
                    <td></td>
                    <td></td>
                </tr>

            </table>
    

            <button id = "search">search</button>
            
            
        </form>
    </div>    


    <div id = "CarTable">
        <div class = "container">
            <div class = "table-responsive-vertical">
           
                <table id = "viewTable" class = "table table-condensensed table-bordered table-striped table-hover table-mc-blue">
                    <thead>
                        <tr> 
                            <th>Name</th> 
                            <th>Brand</th> 
                            <th>Model</th>
                            <th>Year</th>
                            <th>Seats number</th>
                            <th>Type</th>
                            <th>Average</th>
                        </tr>
                    </thead>
                    <tbody v-for ="car in cars" :key="car.id">
                        <tr>
                                <td>{{car.name}}</td>
                                <td>{{car.brand}}</td>
                                <td>{{car.model}}</td>
                                <td>{{car.year}}</td>
                                <td>{{car.seatsNumber}}</td>
                                <td>{{car.type}}</td>
                                <td>{{car.average}}</td>
                        </tr>  
                    </tbody>
                </table>
            </div>
        </div>
    </div>
  </body>
  
</template>


<script>
// @ is an alias to /src

export default {
  name: 'CarSearch',
  data(){
        return{
            car : {
                    id : 1,
                    name : "",
                    brand : "",
                    model : "",
                    year : null,
                    seatsNumber : null,
                    type :"",
                    average : null
                },
            cars : [],
        }
    },

    methods: {
        //TODO

    },
    created(){
        this.$axios.get('http://localhost:8081/api/cars').then(response => this.cars = response.data);
    }
}
</script>


<style scoped>

    body { 
      background: url("../assets/Garage.jpg") no-repeat center center fixed; 
      -webkit-background-size: cover;
      -moz-background-size: cover;
      -o-background-size: cover;
      background-size: cover;
      width: 100%;
      height: 100%;
    }

    #CarSarchInputParam *{
    box-sizing: border-box;
    }
    #CarSarchInputParam{
      padding-top: 2%;
      opacity : 0.85;
      margin: 10px auto;
      max-width: 700px;
    }

    #csit{
      color : white;
      margin : auto;
      width: 100%;
    }

    label{
    display: block;
    margin: 20px 0 10px;
    }
    input, button {
      margin :auto;
      display: block;
      width: 100%;
      padding: 8px;
      border-radius: 10px;
    }
    #search{
      margin : 25px auto;
    }

    #CarTable *{
    box-sizing: border-box;
    }
    #CarTable{
      padding-top: 2%;
      padding-bottom: 1%;
      margin: 10px auto;
      max-width: 700px;
    }



    .body{
      background:#e0e0e0;
    }

    .container{
      margin: 20px auto;
      max-width: 960px;
      
    }


    #viewTable{
      *border-collapse: collapse;
      border-spacing : 0;
      border-radius: 15px;
      width: 100%;
      max-width: 100%;
      margin-bottom: 20px;
      opacity:0.95;
      background-color: white
      
    }

    th:first-child {
      border-radius: 15px 0 0 0;
    }

    th:last-child {
      border-radius: 0 15px 0 0;
    }

    th:only-child{
      border-radius: 15px 15px 0 0;
    }

    td:first-child{
      border-radius: 5px 0 0 5px;
    }
    td:last-child{
      border-radius: 0 5px 5px 0;
    }

    th:only-child{
      border-radius: 5px 5px 5px 5px;
    }


    .table > thead{
      background-color: rgba(126, 126, 126, 0.5);
    }


    .table > thead > th,
    .table > tbody > th,
    .table > tfoot > th,
    .table > thead > tr,
    .table > tbody > tr,
    .table > tfoot > tr{
      text-align:left;
      vertical-align: top;
    }

    .table > thead > th{
      font-weight: 400;
      color:#757575;
      vertical-align:bottom;
      border-bottom: 1px solid rgba(0,0,0,0.12);
    }



    .table .table{
      background-color : rgba(19, 89, 158, 0.267);

    }


    .table-condensensed> thead > tr > th,
    .table-condensensed> tbody > tr > th,
    .table-condensensed> tfoot > tr > th,
    .table-condensensed> thead > tr > td,
    .table-condensensed> tbody > tr > td,
    .table-condensensed> tfoot > tr > td{
      padding : 20px;

    }


    .table-hover>tbody>tr:hover>td,
    .table-hover>tbody>tr:hover>th{
      background-color: rgba(0,0,0,0.12);
    }

    .table-striped.table-mc-blue > tbody > tr:nth-child(odd) > td,
    .table-striped.table-mc-blue > tbody > tr:nth-child(odd) > th{
      background-color: rgba(211, 211, 211, 0.418)
    }

    .table-hover.table-mc-blue>tbody>tr:hover>td,
    .table-hover.table-mc-blue>tbody>tr:hover>th{
      background-color: skyblue
    }

    label{
    display: block;
    margin: 20px 0 10px;
    }
    input, button {
      margin :auto;
      display: block;
      width: 100%;
      padding: 8px;
      border-radius: 10px;
    }
    #search{
      margin : 25px auto;
    }


</style>
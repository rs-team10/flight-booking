<template>

  <body>
    
    <div class = "carSearchInputParam">
        <carSearchInputParam/>
    </div>


    <div class = "carSSearchTable">
        <carTable/>
    </div>

    <button id="delete"> delete </button>
  </body>

</template>


<script>
// @ is an alias to /src
import carTable from '@/components/CarTable.vue'
import carSearchInputParam from '@/components/CarSearchInputParam.vue'

export default {
  name: 'CarSearch',
  components: {
    carSearchInputParam,
    carTable
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

    
    #delete {
        margin :auto;
        display: block;
        width: 100%;
        padding: 8px;
        padding-bottom: 1%;
        border-radius: 10px;
        max-width: 700px;
    }



</style>
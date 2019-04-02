<template>
  <div class="car-search">
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Car search"/>
  </div>
</template>

<script>
// @ is an alias to /src
import CarSearchComp from '@/components/CarSearchComp.vue'

export default class Car_sarch{
	private async  created(){
		const response = await axios.get()
	}
}



</script>
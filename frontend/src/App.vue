<template>
  <v-app>

    <Header ref="header" />

    <v-content>
      <router-view @logged="changeText"></router-view>
    </v-content>

  </v-app>
</template>

<script>
import Header from '@/components/Header'


export default {
  name: 'App',
  components: { Header },        
  data () {
    return {
      //
    }
  },
  methods: {
    changeText: function(){
      this.$refs.header.log();
    }
  },
  mounted(){
    console.log(localStorage.getItem("token"))
  }
}
</script>

<style>
body {
  font-family: 'Roboto', sans-serif;
}
</style>

<template>
  <div id="app">
    <div id="nav">
      <app-header></app-header>
    </div>
    <router-view/>
  </div>
</template>

<script>

// Imports
import Header from './components/Header.vue';

export default {
    components: {
        'app-header': Header
    },
    data () {
        return {
        }
    },
    methods: {
    }
}
</script>

<style>
body {
    margin: 0;
    font-family: 'Nunito', 'Helvetica';
}
</style>

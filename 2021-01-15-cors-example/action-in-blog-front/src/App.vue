<template>
    <img alt="Vue logo" src="./assets/logo.png">
    <CorsReuqest />
</template>

<script>
import CorsReuqest from './components/CorsReuqest.vue'

export default {
    name: 'App',
    components: {
        CorsReuqest
    }
}
</script>

<style>
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
}
</style>

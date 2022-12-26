<template>
  <KoreanInput/>
</template>

<script>
import KoreanInput from './components/KoreanInput.vue'

export default {
  name: 'App',
  components: {
    KoreanInput
  }
}
</script>

<style>
body {
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen',
  'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
  sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

*,
*::before,
*::after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html {
  margin: 1rem;
  box-sizing: border-box;
}
</style>
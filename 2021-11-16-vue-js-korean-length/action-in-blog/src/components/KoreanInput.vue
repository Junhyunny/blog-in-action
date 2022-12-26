<template>
  <div>
    <input @input="changeValue($event)"/>
    <div class="header">
      <h3>{{ value }}</h3>
      <h4>(길이:{{ value.length }})</h4>
    </div>
  </div>
</template>

<script>
export default {
  name: 'KoreanInput',
  data() {
    return {
      value: ''
    }
  },
  methods: {
    changeValue(event) {
      this.value = event.target.value;
    }
  }
}
</script>

<style scoped lang="css">
input{
  margin: 0.25rem 0.5rem;
}

.header {
  margin: 0.25rem 0.5rem;
  padding: 0.5rem;
  border: 1px lightgray solid;
}

.header h3 {
  border-bottom: 1px lightgray solid;
  height: 2.5rem;
  display: flex;
  align-items: center;
}
</style>
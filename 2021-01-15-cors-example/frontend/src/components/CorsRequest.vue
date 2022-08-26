<template>
  <div class="wrapper">
    <h1>Check CORS(Cross Origin Resource Sharing)</h1>
    <div class="message flex-center" :class="{error: isError}">
      <p>{{ response }}</p>
    </div>
    <div class="button-group flex-center">
      <div class="buttons flex-center">
        <button @click="requestError()">Error</button>
        <button @click="requestAnnotation()">Annotation</button>
      </div>
      <div class="buttons flex-center">
        <button @click="requestConfigure()">Configure</button>
        <button @click="requestFilter()">Filter</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      response: 'Waiting',
      isError: false
    }
  },
  methods: {
    requestError() {
      this.requestApi('http://localhost:8080/health')
    },
    requestAnnotation() {
      this.requestApi('http://localhost:8080/cors-health')
    },
    requestConfigure() {
      this.requestApi('http://localhost:8081/health')
    },
    requestFilter() {
      this.requestApi('http://localhost:8082/health')
    },
    requestApi(url) {
      axios.get(url)
          .then((res) => {
            this.response = res.data
            this.isError = false
          })
          .catch((error) => {
            this.response = error.message
            this.isError = true
          })
    }
  }
}
</script>

<style scoped>
.wrapper {
  width: 80%;
  margin: 0 auto;

  border: 1px solid gray;
  border-radius: 5px;
  box-shadow: 2px 2px 2px gray;
}

h1 {
  text-align: center;
}

.flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

.message {
  width: 80%;
  height: 2rem;
  margin: 0 auto;

  border: 1px solid gray;
  border-radius: 5px;

  text-align: center;
  font-size: 1rem;
}

.message.error {
  color: red;
}

.button-group {
  width: 80%;
  margin: 1rem auto;

  flex-direction: column;
  gap: 1rem;
}

.buttons {
  gap: 1rem;
}

.buttons > button {
  width: 5rem;
}
</style>
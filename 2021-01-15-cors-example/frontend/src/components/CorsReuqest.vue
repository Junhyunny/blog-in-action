<template>
    <div>
        <h1>Cross Origin Resource Sharing Test</h1>
        <div>
            <button @click="request1()">http://localhost:8081/api/cors/health</button>
            <button @click="request2()">http://localhost:8081/api/cors/health-cors-annotaion</button>
            <div>{{this.response}}</div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'CorsReuqest',
    data() {
        return {
            response: ''
        }
    },
    methods: {
        request1() {
            axios.get('http://localhost:8081/api/cors/health').then((res) => {
                this.response = res.data
            }).catch((error) => {
                this.response = error.message
                console.log('error message: ', error)
            })
        },
        request2() {
            axios.get('http://localhost:8081/api/cors/health-cors-annotaion').then((res) => {
                this.response = res.data
            }).catch((error) => {
                this.response = error.message
                console.log('error message: ', error)
            })
        }
    }
}
</script>

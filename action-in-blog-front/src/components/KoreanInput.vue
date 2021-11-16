<template>
    <div>
        <h3>{{this.value}}(길이:{{this.value.length}})</h3>
        <input @input="changeValue($event)"/>
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

<template>
    <div >
        <p>계산된 메시지 with true: "{{ computedMessage(true) }}"</p>
        <p>계산된 메시지 with false: "{{ computedMessage(false) }}"</p>
    </div>
</template>

<script>
export default {
    name: 'ParameterComputed',
    data() {
        return {
            message: '안녕하세요'
        }
    },
    computed: {
        computedMessage() {
            return (flag) => {
                if (flag) {
                    return this.message.split('').reverse().join('')
                }
                return this.message;
            }
        }
    }
}
</script>

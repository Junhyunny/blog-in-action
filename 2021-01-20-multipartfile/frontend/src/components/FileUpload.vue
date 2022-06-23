<template>
    <div>
        <h3>파일 업로드 결과: {{this.response === '' ? 'waiting' : this.response}}</h3>
        <div>
            <button @click="selectUploadFile()">이미지 선택</button>
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
        selectUploadFile() {
            var vue = this
            let elem = document.createElement('input')
            // 이미지 파일 업로드 / 동시에 여러 파일 업로드
            elem.id = 'image'
            elem.type = 'file'
            elem.accept = 'image/*'
            elem.multiple = true
            // 클릭
            elem.click();
            // 이벤트 감지
            elem.onchange = function() {
                const formData = new FormData()
                for (var index = 0; index < this.files.length; index++) {
                    formData.append('fileList', this.files[index])
                }
                axios.post('http://localhost:8081/api/file/upload/profile-img', formData, { headers: { 'Content-Type': 'multipart/form-data' } }).then(response => {
                    vue.response = response.data
                }).catch(error => {
                    vue.response = error.message
                })
            }
        }
    }
}
</script>

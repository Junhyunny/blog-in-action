<template>
    <div>
        <h3>파일 업로드 결과: {{this.response === '' ? 'waiting' : this.response}}</h3>
        <div>
            <button @click="uploadFileInDto()">Multipart in DTO Upload</button>
            <button @click="uploadFileListInDto()">Images List in DTO Upload</button>
            <button @click="uploadFileMapInDto()">Images Map in DTO Upload</button>
            <button @click="uploadFileMapListInDto()">Images Map-List in DTO Upload</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'FileUpload',
    data() {
        return {
            response: ''
        }
    },
    methods: {
        responseCallback(response) {
            this.response = response.data;
        },
        errorCallback(error) {
            this.response = error.message;
        },
        getImageSelectElement(multiple) {
            let elem = document.createElement('input');
            elem.id = 'image';
            elem.type = 'file';
            elem.accept = 'image/*';
            elem.multiple = multiple;
            return elem;
        },
        uploadFileInDto() {
            var context = this;
            let elem = this.getImageSelectElement(false);
            elem.click();
            elem.onchange = function() {
                const formData = new FormData();
                formData.append('file', this.files[0]);
                axios.post('http://localhost:8081/dto', formData, { headers: { 'Content-Type': 'multipart/form-data' } }).then(context.responseCallback).catch(context.errorCallback);
            }
        },
        uploadFileListInDto() {
            var context = this;
            let elem = this.getImageSelectElement(true);
            elem.click();
            elem.onchange = function() {
                const formData = new FormData();
                for (var index = 0; index < this.files.length; index++) {
                    formData.append('files', this.files[index]);
                }
                axios.post('http://localhost:8081/dto/multipart/list', formData, { headers: { 'Content-Type': 'multipart/form-data' } }).then(context.responseCallback).catch(context.errorCallback);
            }
        },
        uploadFileMapInDto() {
            var context = this;
            let elem = this.getImageSelectElement(true);
            elem.click();
            elem.onchange = function() {
                const formData = new FormData();
                for (var index = 0; index < this.files.length; index++) {
                    formData.append('files[' + index + ']', this.files[index]);
                }
                axios.post('http://localhost:8081/dto/multipart/map', formData, { headers: { 'Content-Type': 'multipart/form-data' } }).then(context.responseCallback).catch(context.errorCallback);
            }
        },
        uploadFileMapListInDto() {
            var context = this;
            let elem = this.getImageSelectElement(true);
            elem.click();
            elem.onchange = function() {
                const formData = new FormData();
                for (var index = 0; index < this.files.length; index++) {
                    formData.append('files[' + (index % 3) + ']', this.files[index]);
                }
                axios.post('http://localhost:8081/dto/multipart/map/list', formData, { headers: { 'Content-Type': 'multipart/form-data' } }).then(context.responseCallback).catch(context.errorCallback);
            }
        }
    }
}
</script>

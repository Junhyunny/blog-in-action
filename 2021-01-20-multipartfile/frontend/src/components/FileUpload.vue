<template>
  <div>
    <div>
      <p>이미지를 업로드하세요.</p>
      <button @click="$refs.fileRef.click">선택</button>
      <input type="file" @change="selectFile" multiple accept="image/*" ref="fileRef" hidden/>
    </div>
    <div>
      <div v-for="filePath in filePaths" :key="filePath">
        <img src="filePath" alt="이미지">
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      filePaths: []
    }
  },
  mounted() {
    this.fetchFilePaths()
  },
  methods: {
    async fetchFilePaths() {
      const response = await axios.get('/files')
      this.filePaths = response.data;
    },
    async selectFile(event) {
      const formData = new FormData()
      for (const file of event.target.files) {
        formData.append('files', file)
      }
      try {
        const response = await axios.post('/files', formData, {
          headers: {'Content-Type': 'multipart/form-data'}
        })
        alert(response.data)
      } catch (error) {
        alert(error.message)
      }
    },
  }
}
</script>

<template>
    <div class="pointer">
        <a @click="onPageChange(currentPage - 1)">&lt;</a>
        <a v-for="(paging, index) in pages" :key="index" @click="onPageChange(paging - 1)" :class="paging - 1 === currentPage ? 'currentPage' : ''"> {{paging}} </a>
        <a @click="onPageChange(currentPage + 1)">&gt;</a>
    </div>
</template>

<script>
export default {
    name: 'Pagination',
    props: ['currentPage', 'totalPages', 'pageChange'],
    data() {
        return {};
    },
    computed: {
        pages: function() {
            const list = [];
            for (let index = this.startPage; index <= this.endPage; index += 1) { list.push(index); }
            return list;
        },
        startPage() {
            return parseInt(this.currentPage / 5) * 5 + 1;
        },
        endPage() {
            let lastPage = parseInt(this.currentPage / 5) * 5 + 5;
            return lastPage <= this.totalPages ? lastPage : this.totalPages;
        }
    },
    methods: {
        onPageChange(val) {
            if (val < 0) {
                alert('첫 페이지입니다.');
                return;
            }
            if (val >= this.totalPages) {
                alert('마지막 페이지입니다.');
                return;
            }
            const param = {
                requestPage: val,
            };
            this.pageChange(param);
        }
    }
}
</script>

<style scoped>
.pointer a {
    cursor: pointer;
    margin: 5px;
}
.currentPage {
    background: #A3C010;
}
</style>
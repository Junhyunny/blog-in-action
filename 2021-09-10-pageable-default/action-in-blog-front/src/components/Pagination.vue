<template>
    <div>
        <a v-if="startPage!==1" @click="onPageChange(startPage-1)">&lt;</a>
        <a v-for="(page, index) in pages()" :key="index" @click="onPageChange(page)">{{page}}</a>
        <a v-if="totalPage>endPage" @click="onPageChange(endPage+1)">&gt;</a>
    </div>
</template>

<script>
export default {
    name: 'Pagination',
    props: ['currentPage', 'totalPage', 'pageChange'],
    data() {
        return {
            pages: function() {
                const list = [];
                for (let index = this.startPage; index <= this.endPage; index += 1) { list.push(index); }
                return list;
            },
        };
    },
    computed: {
        startPage() {
            return parseInt((this.currentPage - 1) / 5) * 5 + 1;
        },
        endPage() {
            return (parseInt((this.currentPage - 1) / 5) + 1) * 5 < this.totalPage ? (parseInt((this.currentPage - 1) / 5) + 1) * 5 : this.totalPage;
        }
    },
    methods: {
        onPageChange(val) {
            const param = {
                requestPage: val,
            };
            this.pageChange(param);
        }
    }
}
</script>

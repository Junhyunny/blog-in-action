<template>
    <input type="button" value="조회" @click="search()" />
    <Table 
        :headerList="headerList"
        :itemList="itemList"
        :itemKeyList="itemKeyList"
        :currentPage="getCurrentPage"
        :totalPage="getTotalPage"
        :pageChange="onPageChange"
    />
</template>

<script>
import Table from './components/Table.vue';
import { fetchList } from '@/api/post';

export default {
    name: 'App',
    components: {
        Table
    },
    data() {
        return {
            headerList: ["등록일시", "제목", "내용"],
            itemList: [],
            itemKeyList: ["createdAt", "contents", "title"],
            page: {
                page: 0,
                size: 10,
                sort: "createdAt,desc"
            }
        };
    },
    computed: {
        
    },
    methods: {
        search() {
            fetchList(this.page).then((response) => {
                console.log(response);
                this.itemList = response.data.elements;
            });
        },
        getCurrentPage() {
            return 
        },
        getTotalPage() {
            return 
        },
        onPageChange() {
            return 
        },
    }
}
</script>
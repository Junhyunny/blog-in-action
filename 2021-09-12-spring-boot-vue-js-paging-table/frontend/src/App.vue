<template>
    <input type="button" value="조회" @click="search()" />
    <select @change="onSortChange($event)">
        <option value="" disabled selected>정렬 기준</option>
        <option v-for="(header, index) in headerList" :value="itemKeyList[index]" :key="'select-' + index">{{headerList[index]}}</option>
    </select>
    <select @change="onSortDirectionChange($event)">
        <option value="" disabled selected>정렬 방향</option>
        <option value="asc">오름차순</option>
        <option value="desc">내림차순</option>
    </select>
    <Table 
        :headerList="headerList"
        :itemList="itemList"
        :itemKeyList="itemKeyList"
        :currentPage="page.page"
        :totalPages="totalPages"
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
            sortHeader: 'createdAt',
            sortDirection: 'desc',
            totalPages: 0,
            page: {
                page: 0,
                size: 10,
                sort: "createdAt,desc"
            }
        };
    },
    methods: {
        search() {
            fetchList(this.page).then((response) => {
                this.itemList = response.data.elements;
                this.totalPages = response.data.totalPages;
                this.page.page = response.data.currentPage;
            });
        },
        onPageChange(value) {
            this.page.page = value.requestPage;
            this.search();
        },
        onSortChange(event) {
            this.sortHeader = event.target.value;
            this.page.sort = this.sortHeader + ',' + this.sortDirection;
            this.search();
        },
        onSortDirectionChange(event) {
            this.sortDirection = event.target.value;
            this.page.sort = this.sortHeader + ',' + this.sortDirection;
            this.search();
        }
    }
}
</script>
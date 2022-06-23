<template>
    <div class="tableDiv">
        <table>
            <colgroup>
                <col v-for="(column, index) in headerList" :key="'size-' + index" />
            </colgroup>
            <thead>
                <tr>
                    <th v-for="(column, index) in headerList" :key="'header-' + index">{{headerList[index]}}</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index) in itemList" :key="'item-' + index">
                    <td v-for="(itemKey, subIndex) in itemKeyList" :key="'item-key-' + subIndex">
                        {{item[itemKey]}}
                    </td>
                </tr>
            </tbody>
        </table>
        <Pagination :currentPage="currentPage" :totalPages="totalPages" :pageChange="pageChange" />
    </div>
</template>

<script>
import Pagination from './Pagination';

export default {
    name: 'Table',
    props: ['headerList', 'itemList', 'itemKeyList', 'currentPage', 'totalPages', 'pageChange'],
    components: { Pagination }
}
</script>

<style scoped>
.tableDiv {
    border-radius: 5px;
    border: 1px solid #e0e1e3;
}

.tableDiv table th {
    font-size: 15px;
    padding: 5px 10px 5px 10px;
    color: #fff;
    background: #9d9fac;
    border: 1px solid #000;
}

.tableDiv table td {
    font-size: 15px;
    padding: 5px 10px 5px 10px;
    text-align: center;
    color: #2e2f30;
    border: 1px solid #000;
}
</style>
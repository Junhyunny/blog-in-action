<template>
    <div>
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
                    <td v-for="(itemKey, index) in itemKeyList" :key="'item-key-' + index">
                        {{item[itemkey]}}
                    </td>
                </tr>
            </tbody>
        </table>
        <Pagination :currentPage="currentPage" :totalPage="totalPage" :pageChange="pageChange"/>
    </div>
</template>

<script>
import Pagination from './Pagination';

export default {
    name: 'Table',
    props: ['headerList', 'itemList', 'itemKeyList', 'currentPage', 'totalPage', 'pageChange'],
    components: { Pagination },
    data() {
        return {}
    },
    methods: {

    }
}
</script>

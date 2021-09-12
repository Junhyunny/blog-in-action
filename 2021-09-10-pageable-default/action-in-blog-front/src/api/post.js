import request from '@/utils/request';

export function fetchList(query) {
    return request({
        url: '/posts',
        method: 'get',
        params: query
    });
}
import axios from "axios";
import HttpClient from "./HttpClient";

describe('test http client', () => {

    it('call axios get method not given bearer token', () => {

        const spyAxios = jest.spyOn(axios, 'get').mockResolvedValue({data: {}});

        HttpClient.get('/member/1', {});

        expect(spyAxios).toHaveBeenNthCalledWith(1, 'http://localhost:8081/member/1', {
            params: {}
        });
    });

    it('call axios get method given bearer token', () => {

        localStorage.setItem('token_type', 'bearer');
        localStorage.setItem('access_token', 'access_token');
        const spyAxios = jest.spyOn(axios, 'get').mockResolvedValue({});

        HttpClient.get('/member/1', {});

        expect(spyAxios).toHaveBeenNthCalledWith(1, 'http://localhost:8081/member/1', {
            headers: {
                Authorization: 'bearer access_token'
            },
            params: {}
        });
    });
});
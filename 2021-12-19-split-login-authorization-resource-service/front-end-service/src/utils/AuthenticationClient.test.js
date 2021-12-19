import axios from "axios";
import AuthenticationClient from "./AuthenticationClient";

describe('test authentication client', () => {

    const params = {
        username: 'Junhyunny',
        password: '123'
    };

    afterEach(() => {
        jest.restoreAllMocks();
    });

    it('call axios post with proper params method when authenticate', async () => {

        // setup
        const spyAxios = jest.spyOn(axios, 'post').mockResolvedValue({
            data: {}
        });

        // action
        await AuthenticationClient.authenticate(params);

        // assert
        expect(spyAxios).toHaveBeenNthCalledWith(1, 'http://localhost:8080/oauth/token', {}, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            auth: {
                username: 'CLIENT_ID',
                password: 'CLIENT_SECRET'
            },
            params: {
                ...params,
                grant_type: 'password'
            }
        });
    });

    it('get true as a result when succeed authentication', async () => {

        // setup
        jest.spyOn(axios, 'post').mockResolvedValue({
            data: {}
        });

        // action
        const result = await AuthenticationClient.authenticate(params);

        // assert
        expect(result).toEqual(true);
    });

    it('save access token and refresh token when succeed authentication', async () => {

        // setup
        jest.spyOn(axios, 'post').mockResolvedValue({
            data: {
                access_token: 'access_token',
                refresh_token: 'refresh_token',
                token_type: 'bearer'
            }
        });

        // action
        await AuthenticationClient.authenticate(params);

        // assert
        expect(localStorage.getItem('access_token')).toEqual('access_token');
        expect(localStorage.getItem('refresh_token')).toEqual('refresh_token');
        expect(localStorage.getItem('token_type')).toEqual('bearer');
    });

    it('get false as a result when fail authentication', async () => {

        // setup
        jest.spyOn(axios, 'post').mockRejectedValue({});

        // action
        const result = await AuthenticationClient.authenticate(params);

        // assert
        expect(result).toEqual(false);
    });
});
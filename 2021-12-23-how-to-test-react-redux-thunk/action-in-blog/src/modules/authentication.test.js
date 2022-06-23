import AuthenticationClient from "../utils/AuthenticationClient";
import * as authentication from "./authentication";

describe('test groups container', () => {

    it('call setAuthentication action of authentication module when succeed authentication', async () => {

        // setup
        const dispatch = jest.fn();
        jest.spyOn(AuthenticationClient, 'authenticate').mockResolvedValue(true);

        // act
        await authentication.authenticate({})(dispatch);

        // assert
        expect(dispatch).toHaveBeenNthCalledWith(1, authentication.setAuthentication(true));
    });

    it('call authenticationFailure action of authentication module when occur exception', async () => {

        // setup
        const dispatch = jest.fn();
        jest.spyOn(AuthenticationClient, 'authenticate').mockRejectedValue({});

        // act
        await authentication.authenticate({})(dispatch);

        // assert
        expect(dispatch).toHaveBeenNthCalledWith(1, authentication.authenticationFailure());
    });
});
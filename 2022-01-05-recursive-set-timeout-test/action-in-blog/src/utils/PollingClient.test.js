import axios from "axios";
import PollingClient from "./PollingClient";
import {waitFor} from "@testing-library/react";

describe('PollingClient test', () => {

    beforeEach(() => {
        jest.clearAllTimers();
        jest.restoreAllMocks();
    })

    it('given timeout 1 second with 6 seconds when call polling method then 6 times call', async () => {

        jest.useFakeTimers();
        const spyAxios = jest.spyOn(axios, 'get').mockResolvedValue({data: true});
        const callback = jest.fn();

        PollingClient.polling(callback, '/second-auth', {}, 1000);

        // 1 time run
        jest.advanceTimersByTime(6000);
        console.log(1);

        await waitFor(() => {
            console.log(2);
            expect(callback).toHaveBeenCalledTimes(6);
            console.log(3);
        });
        expect(callback).toHaveBeenLastCalledWith({data: true});
        expect(spyAxios).toHaveBeenCalledTimes(6);
        expect(spyAxios).toHaveBeenLastCalledWith('http://localhost:8080/second-auth', {});
    });

    it('PoC Message queues, PromiseJobs and Mock Timers', async () => {

        jest.useFakeTimers();
        const callback = jest.fn();

        PollingClient.pocPolling(callback, '/second-auth', {}, 1000);

        // 6 times run
        for (let i = 0; i < 6; i++) {
            jest.advanceTimersByTime(1000); // message queue is resolved
            console.log(1)
            await Promise.resolve(); // `await` is resolved here
            console.log(2)
        }

        expect(callback).toHaveBeenCalledTimes(6);
        expect(callback).toHaveBeenLastCalledWith({data: true});
    });

    it('guess something two wierd promises are made by axios.get method mocking', async () => {

        jest.useFakeTimers();
        const spyAxios = jest.spyOn(axios, 'get').mockResolvedValue({data: true});
        const callback = jest.fn();

        PollingClient.polling(callback, '/second-auth', {}, 1000);

        // 6 times run
        for (let i = 0; i < 6; i++) {
            jest.advanceTimersByTime(1000); // message queue is resolved
            console.log(1)
            await Promise.resolve(); // something wierd promise
            console.log(2)
            await Promise.resolve(); // something wierd promise
            console.log(3)
            await Promise.resolve(); // `await` is resolved here
            console.log(4)
        }

        expect(callback).toHaveBeenCalledTimes(6);
        expect(callback).toHaveBeenLastCalledWith({data: true});
        expect(spyAxios).toHaveBeenCalledTimes(6);
        expect(spyAxios).toHaveBeenLastCalledWith('http://localhost:8080/second-auth', {});
    });

});
import {intervalPolling, longPolling, sleepPolling, timeoutPolling} from "./PollingClient";

describe('PollingClient test', () => {

    beforeEach(() => {
        jest.clearAllTimers();
        jest.useRealTimers();
        jest.restoreAllMocks();
    })

    it('given 6 seconds, timeout 1 second when call polling method then 6 times call', async () => {

        // setup
        jest.useFakeTimers();
        const spyFunc = jest.fn();

        // act
        timeoutPolling(spyFunc, 1000);
        for (let i = 0; i < 6; i++) {
            jest.advanceTimersByTime(1000);
            await Promise.resolve();
        }

        // assert
        expect(spyFunc).toHaveBeenCalledTimes(6)
    });

    it('given 6 seconds, interval 1 second, maximum attempts 5 times when call polling method then 5 times call', () => {

        // setup
        jest.useFakeTimers();
        const spyFunc = jest.fn();

        // act
        intervalPolling(spyFunc, 1000, 5);
        for (let i = 0; i < 6; i++) {
            jest.advanceTimersByTime(1000);
        }

        // assert
        expect(spyFunc).toHaveBeenCalledTimes(5)
    });

    it('sleep 100 ms, getting data what you want at 2nd trial when call polling method then 2 times call', async () => {

        const mockCallback = jest
            .fn()
            .mockResolvedValueOnce({
                data: 'Welcome',
            })
            .mockResolvedValueOnce({
                data: 'Junhyunny',
            })
            .mockResolvedValueOnce({
                data: 'Dev',
            })
            .mockResolvedValueOnce({
                data: 'Log',
            });

        const validateFn = (result) => 'Junhyunny' === result.data;

        const data = await sleepPolling(mockCallback, validateFn, 100);

        expect(mockCallback).toHaveBeenCalledTimes(2);
        expect(data).toEqual({
            data: 'Junhyunny',
        });
    });

    it('sleep 100 ms, getting data what you want at 3rd trial when call polling method then 3 times call', async () => {

        const mockCallback = jest
            .fn()
            .mockResolvedValueOnce({
                status: 500
            })
            .mockResolvedValueOnce({
                status: 502
            })
            .mockResolvedValueOnce({
                status: 200,
                data: 'Junhyunny'
            })
            .mockResolvedValueOnce({
                status: 200,
                data: 'Log',
            });

        const validateFn = (response) => 'Junhyunny' === response.data;

        const data = await longPolling(mockCallback, validateFn, 100);

        expect(mockCallback).toHaveBeenCalledTimes(3);
        expect(data).toEqual({
            status: 200,
            data: 'Junhyunny'
        });
    });
});
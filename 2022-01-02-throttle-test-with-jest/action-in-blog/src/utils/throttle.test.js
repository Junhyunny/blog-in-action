import throttle from "./throttle";

describe('throttle test', () => {

    it('call 5 times when throttle time sleep 100ms during 500ms', () => {

        // setup
        jest.useFakeTimers();
        const funcSpy = jest.fn();
        const throttledFunc = throttle(funcSpy, 100);

        // act
        for (let index = 0; index < 100; index++) {
            throttledFunc();
            jest.advanceTimersByTime(5);
        }

        // assert
        expect(funcSpy).toHaveBeenCalledTimes(5);
    });
});
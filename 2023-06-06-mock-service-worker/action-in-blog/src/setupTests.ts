// jest-dom adds custom jest matchers for asserting on DOM nodes.
// allows you to do things like:
// expect(element).toHaveTextContent(/react/i)
// learn more: https://github.com/testing-library/jest-dom
import "@testing-library/jest-dom";
import {server} from "./mocks/server";

// 테스트 시작 전에 서버를 실행한다.
beforeAll(() => server.listen());

// 테스트가 끝날 때마다 리퀘스트 핸들러를 초기화하여 서로 영향을 미치지 않도록 한다.
afterEach(() => server.resetHandlers());

// 테스트가 완료되면 서버를 종료한다.
afterAll(() => server.close());

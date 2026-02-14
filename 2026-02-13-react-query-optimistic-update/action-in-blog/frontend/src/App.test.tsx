import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { render, screen, waitFor, within } from "@testing-library/react";
import { userEvent } from "@testing-library/user-event/dist/cjs/setup/index.js";
import type { DefaultBodyType } from "msw";
import type { ReactElement } from "react";
import { describe, expect, test } from "vitest";
import { server } from "../vitest-setup.ts";
import AppViaCache from "./AppViaCache.tsx";
import AppViaUI from "./AppViaUI.tsx";
import {
  getGetTodosMockHandler,
  getPostTodosMockHandler,
} from "./apis/todoAPI.msw.ts";
import type { Todo, TodoCreate } from "./model";

const withQueryProvider = (ui: ReactElement) => {
  return (
    <QueryClientProvider client={new QueryClient()}>{ui}</QueryClientProvider>
  );
};

describe("App", () => {
  test("화면을 보면 이전에 작성된 해야할 일(todo) 항목들이 보인다.", async () => {
    server.use(
      getGetTodosMockHandler([
        { id: 1, title: "테스트 작성하기", completed: true },
        { id: 2, title: "구현 코드 작성하기", completed: false },
      ]),
    );

    render(withQueryProvider(<AppViaUI />));

    const firstTodo = await screen.findByText("테스트 작성하기");
    expect(firstTodo).toBeInTheDocument();
    expect(
      // biome-ignore lint/style/noNonNullAssertion: test code
      within(firstTodo.parentElement!).getByRole("checkbox"),
    ).toBeChecked();
    const secondTodo = await screen.findByText("구현 코드 작성하기");
    expect(secondTodo).toBeInTheDocument();
    expect(
      // biome-ignore lint/style/noNonNullAssertion: test code
      within(secondTodo.parentElement!).getByRole("checkbox"),
    ).not.toBeChecked();
  });

  test("화면에 새로운 해야할 일(todo)를 등록할 수 있다.", async () => {
    const expectedRequest: DefaultBodyType[] = [];
    server.use(
      getPostTodosMockHandler(async (info) => {
        const body = (await info.request.json()) as TodoCreate;
        expectedRequest.push(body);
        return { ...body, id: 1, createdAt: "2022-01-01T00:00:00.000Z" };
      }),
    );
    server.use(
      getGetTodosMockHandler(async () => {
        return expectedRequest.map((item, index) => {
          return {
            ...(item as Todo),
            id: index + 1,
            createdAt: "2022-01-01T00:00:00.000Z",
          };
        });
      }),
    );
    render(withQueryProvider(<AppViaUI />));

    await userEvent.type(
      screen.getByPlaceholderText("해야할 일"),
      "블로그 쓰기",
    );
    await userEvent.click(screen.getByRole("button", { name: "등록" }));

    const firstTodo = await screen.findByText("블로그 쓰기");
    expect(firstTodo).toBeInTheDocument();
    expect(
      // biome-ignore lint/style/noNonNullAssertion: test code
      within(firstTodo.parentElement!).getByRole("checkbox"),
    ).not.toBeChecked();
    expect(screen.getByPlaceholderText("해야할 일")).toHaveValue("");
    expect(expectedRequest).toEqual([
      { title: "블로그 쓰기", completed: false },
    ]);
  });
});

describe("optimistic update via UI", () => {
  test("업데이트 요청이 완료되지 않은 상태인 경우에도, 화면에 새로운 해야할 일(todo)가 등록된 것처럼 보인다.", async () => {
    // [1]
    let requestBody: TodoCreate | null = null;
    let resolvePromise: (todo: Todo) => void = () => {};
    server.use(
      getGetTodosMockHandler(() =>
        requestBody ? [{ ...requestBody, id: 1 }] : [],
      ),
    );
    server.use(
      getPostTodosMockHandler(async (info) => {
        requestBody = (await info.request.json()) as TodoCreate;
        return new Promise((resolve) => {
          resolvePromise = resolve;
        });
      }),
    );
    render(withQueryProvider(<AppViaUI />));

    // [2]
    await userEvent.type(
      screen.getByPlaceholderText("해야할 일"),
      "블로그 쓰기",
    );
    await userEvent.click(screen.getByRole("button", { name: "등록" }));

    // [3]
    const optimisticTodo = await screen.findByTestId("optimistic-todo-item");
    expect(optimisticTodo).toBeInTheDocument();
    expect(within(optimisticTodo).getByText("블로그 쓰기")).toBeInTheDocument();
    expect(
      // biome-ignore lint/style/noNonNullAssertion: test code
      within(optimisticTodo.parentElement!).getByRole("checkbox"),
    ).not.toBeChecked();

    // [4]
    resolvePromise({} as unknown as Todo);

    // [5]
    await waitFor(() => {
      expect(
        screen.queryByTestId("optimistic-todo-item"),
      ).not.toBeInTheDocument();
      const firstTodo = screen.getByText("블로그 쓰기");
      expect(firstTodo).toBeInTheDocument();
      expect(
        // biome-ignore lint/style/noNonNullAssertion: test code
        within(firstTodo.parentElement!).getByRole("checkbox"),
      ).not.toBeChecked();
    });
  });
});

describe("optimistic update via cache", () => {
  test("업데이트 요청이 완료되지 않은 상태인 경우에도, 화면에 새로운 해야할 일(todo)가 등록된 것처럼 보인다.", async () => {
    // [1]
    let requestBody: TodoCreate | null = null;
    let resolvePromise: (todo: Todo) => void = () => {};
    server.use(
      getGetTodosMockHandler(() =>
        requestBody ? [{ ...requestBody, id: 1 }] : [],
      ),
    );
    server.use(
      getPostTodosMockHandler(async (info) => {
        requestBody = (await info.request.json()) as TodoCreate;
        return new Promise((resolve) => {
          resolvePromise = resolve;
        });
      }),
    );
    render(withQueryProvider(<AppViaCache />));

    // [2]
    await userEvent.type(
      screen.getByPlaceholderText("해야할 일"),
      "블로그 쓰기",
    );
    await userEvent.click(screen.getByRole("button", { name: "등록" }));

    // [3]
    const optimisticTodo = await screen.findByText("블로그 쓰기");
    expect(optimisticTodo).toBeInTheDocument();
    expect(
      // biome-ignore lint/style/noNonNullAssertion: test code
      within(optimisticTodo.parentElement!).getByRole("checkbox"),
    ).not.toBeChecked();

    // [4]
    resolvePromise({} as unknown as Todo);

    // [5]
    const firstTodo = await screen.findByText("블로그 쓰기");
    expect(firstTodo).toBeInTheDocument();
    expect(
      // biome-ignore lint/style/noNonNullAssertion: test code
      within(firstTodo.parentElement!).getByRole("checkbox"),
    ).not.toBeChecked();
  });

  test("업데이트 요청이 실패한 경우 화면이 이전 상태로 돌아간다.", async () => {
    // [1]
    let rejectPromise: (error: Error) => void = () => {};
    server.use(getGetTodosMockHandler([]));
    server.use(
      getPostTodosMockHandler(async () => {
        return new Promise((_resolve, reject) => {
          rejectPromise = reject;
        });
      }),
    );
    render(withQueryProvider(<AppViaCache />));

    // [2]
    await userEvent.type(
      screen.getByPlaceholderText("해야할 일"),
      "블로그 쓰기",
    );
    await userEvent.click(screen.getByRole("button", { name: "등록" }));

    // [3]
    const optimisticTodo = await screen.findByText("블로그 쓰기");
    expect(optimisticTodo).toBeInTheDocument();
    expect(
      // biome-ignore lint/style/noNonNullAssertion: test code
      within(optimisticTodo.parentElement!).getByRole("checkbox"),
    ).not.toBeChecked();

    // [4]
    rejectPromise(new Error("internal server error"));

    // [5]
    await waitFor(() => {
      expect(screen.queryByText("블로그 쓰기")).not.toBeInTheDocument();
    });
  });
});

import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { render, screen, within } from "@testing-library/react";
import { userEvent } from "@testing-library/user-event/dist/cjs/setup/index.js";
import type { DefaultBodyType } from "msw";
import type { ReactElement } from "react";
import { describe, expect, test } from "vitest";
import { server } from "../vitest-setup.ts";
import App from "./App.tsx";
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

    render(withQueryProvider(<App />));

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
    render(withQueryProvider(<App />));

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

import { rest } from "msw";
import { Todo } from "../types/Todo";

export const handlers = [
  rest.get("/todos", (req, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json([
        { id: 1, content: "Frontend Study" },
        { id: 2, content: "Backend Study" },
      ])
    );
  }),
  rest.post("/todos", async (req, res, ctx) => {
    const { content } = (await req.json()) as Todo;
    return res(
      ctx.status(200),
      ctx.json({
        id: Math.floor(Math.random() * 1000000 + 1),
        content: content,
      })
    );
  }),
];

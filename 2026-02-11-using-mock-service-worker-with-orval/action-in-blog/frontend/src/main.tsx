import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { setupWorker } from "msw/browser";
import App from "./App.tsx";
import { getTodoAPIMock } from "./apis/todoAPI.msw.ts";

if (process.env.NODE_ENV === "development") {
  const worker = setupWorker(...getTodoAPIMock());
  await worker.start();
}

const queryClient = new QueryClient();

// biome-ignore lint/style/noNonNullAssertion: react
createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <App />
    </QueryClientProvider>
  </StrictMode>,
);

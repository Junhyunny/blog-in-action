import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import axios from "axios";
import AppViaCache from "./AppViaCache.tsx";

const queryClient = new QueryClient();

axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL ?? "";

// biome-ignore lint/style/noNonNullAssertion: react
createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <AppViaCache />
    </QueryClientProvider>
  </StrictMode>,
);

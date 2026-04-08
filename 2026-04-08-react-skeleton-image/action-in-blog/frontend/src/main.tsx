import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.tsx";

const root = document.getElementById("root");

createRoot(root ?? document.body).render(
  <StrictMode>
    <App />
  </StrictMode>,
);

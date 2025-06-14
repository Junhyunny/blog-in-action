import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App";
import CustomAuthProvider from "./auth/CustomAuthProvider.tsx";

const cognitoAuthConfig = {
  authority: import.meta.env.VITE_APP_AUTHORITY,
  client_id: import.meta.env.VITE_APP_CLIENT_ID,
  redirect_uri: "http://localhost:5173",
  response_type: "code",
  scope: "phone openid email",
};

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <CustomAuthProvider {...cognitoAuthConfig}>
      <App />
    </CustomAuthProvider>
  </StrictMode>,
);

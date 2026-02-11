import { defineConfig } from "orval";

export default defineConfig({
  api: {
    input: "../api/openapi-specification.yaml",
    output: {
      mode: "split",
      target: "src/apis",
      schemas: "src/model",
      client: "react-query",
      httpClient: "axios",
      mock: true,
    },
  },
});

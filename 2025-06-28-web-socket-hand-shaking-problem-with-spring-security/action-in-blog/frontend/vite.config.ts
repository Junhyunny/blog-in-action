import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

// https://vitejs.dev/config/
export default defineConfig({
	plugins: [react()],
	server: {
		proxy: {
			"/oauth2": {
				target: "http://localhost:8080",
				changeOrigin: true,
			},
			"/ws": {
				target: "ws://localhost:8080",
			},
		},
	},
});

import { useEffect, useState } from "react";
import viteLogo from "/vite.svg";
import reactLogo from "./assets/react.svg";
import "./App.css";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client/dist/sockjs";

function App() {
	const [isConnected, setIsConnected] = useState(false);

	const handleLogin = () => {
		window.location.href = "http://localhost:8080/oauth2/authorization/azure";
	};

	useEffect(() => {
		const stompClient = new Client({
			webSocketFactory: () => new SockJS("/ws"),
			debug           : (str) => {
				console.log(str);
			}
		});

		stompClient.onConnect = (frame) => {
			console.log("Connected: " + frame);
			setIsConnected(true);
		};

		stompClient.onStompError = (frame) => {
			console.error("Broker reported error: " + frame.headers["message"]);
			console.error("Additional details: " + frame.body);
			setIsConnected(false);
		};

		stompClient.onDisconnect = () => {
			console.log("Disconnected");
			setIsConnected(false);
		};

		stompClient.activate();

		return () => {
			stompClient.deactivate();
		};
	}, []);

	return (
		<>
			<div>
				<a href="https://vitejs.dev" target="_blank">
					<img src={viteLogo} className="logo" alt="Vite logo" />
				</a>
				<a href="https://react.dev" target="_blank">
					<img src={reactLogo} className="logo react" alt="React logo" />
				</a>
			</div>
			<h1>Vite + React</h1>
			<div className="card">
				<button onClick={handleLogin}>Login with Microsoft AAD</button>
				<p>
					Backend WebSocket Status: {isConnected ? "Connected" : "Disconnected"}
				</p>
			</div>
			<p className="read-the-docs">
				Click on the Vite and React logos to learn more
			</p>
		</>
	);
}

export default App;

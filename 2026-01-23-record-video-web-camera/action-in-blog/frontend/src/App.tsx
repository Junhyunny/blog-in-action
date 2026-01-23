import { useState } from "react";
import "./App.css";
import PhotoCapture from "./PhotoCapture.tsx";
import VideoRecorder from "./VideoRecorder.tsx";

function App() {
  const [mode, setMode] = useState("video");

  return (
    <>
      <div>
        <input
          id="video"
          name="mode"
          type="radio"
          checked={mode === "video"}
          onChange={() => setMode("video")}
        />
        <label htmlFor="video">비디오</label>
        <input
          id="photo"
          name="mode"
          type="radio"
          checked={mode === "photo"}
          onChange={() => setMode("photo")}
        />
        <label htmlFor="photo">사진</label>
      </div>
      {mode === "video" ? <VideoRecorder /> : <PhotoCapture />}
    </>
  );
}

export default App;

import { useEffect, useRef, useState } from "react";

const loadStream = async () => {
  return await navigator.mediaDevices.getUserMedia({
    video: {
      width: 1024,
      height: 576,
    },
    audio: false,
  });
};

const stopStreamTracks = (stream: MediaStream) => {
  for (const track of stream.getTracks()) {
    track.stop();
  }
};

const createRecorder = (mediaStream: MediaStream) => {
  const chunks: Blob[] = [];
  const recorder = new MediaRecorder(mediaStream, {
    mimeType: "video/webm",
  });
  recorder.ondataavailable = (e) => chunks.push(e.data);
  recorder.onstop = () => {
    const blob = new Blob(chunks, { type: "video/webm" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "video.webm";
    a.click();
    URL.revokeObjectURL(url);
  };
  return recorder;
};

const VideoRecorder = () => {
  const [isRecording, setIsRecording] = useState(false);
  const videoRef = useRef<HTMLVideoElement>(null);
  const recorderRef = useRef<MediaRecorder>(null);

  const startRecording = () => {
    setIsRecording(true);
    const videoElement = videoRef.current;
    if (!videoElement) {
      return;
    }
    const mediaStream = videoElement.srcObject as MediaStream;
    const recorder = createRecorder(mediaStream);
    recorder.start();
    recorderRef.current = recorder;
  };

  const stopRecording = () => {
    setIsRecording(false);
    const recorder = recorderRef.current;
    if (recorder) {
      recorder.stop();
      recorderRef.current = null;
    }
  };

  useEffect(() => {
    let mounted = true;
    let localStream: MediaStream | undefined;
    loadStream()
      .then(async (stream) => {
        if (mounted && videoRef.current) {
          localStream = stream;
          videoRef.current.srcObject = stream;
          await videoRef.current.play();
        } else {
          stopStreamTracks(stream);
        }
      })
      .catch(console.error);
    return () => {
      mounted = false;
      if (localStream) {
        stopStreamTracks(localStream);
      }
    };
  }, []);

  return (
    <div>
      <video
        ref={videoRef}
        muted
        style={{ width: "1024px", backgroundColor: "#333", height: "576px" }}
      />
      <div>
        {!isRecording ? (
          <button type="button" onClick={startRecording}>
            녹화 시작
          </button>
        ) : (
          <button type="button" onClick={stopRecording}>
            녹화 종료
          </button>
        )}
      </div>
    </div>
  );
};

export default VideoRecorder;

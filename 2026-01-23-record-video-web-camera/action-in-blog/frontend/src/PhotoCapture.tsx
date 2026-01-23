import { useEffect, useRef } from "react";

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

const PhotoCapture = () => {
  const videoRef = useRef<HTMLVideoElement>(null);

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

  const capture = () => {
    const video = videoRef.current;
    const canvas = document.createElement("canvas");
    const context = canvas.getContext("2d");
    if (video && context) {
      canvas.width = video.videoWidth;
      canvas.height = video.videoHeight;
      context.drawImage(video, 0, 0, canvas.width, canvas.height);
      const url = canvas.toDataURL("image/png");
      const a = document.createElement("a");
      a.href = url;
      a.download = "photo.png";
      a.click();
    }
  };

  return (
    <div>
      <video
        ref={videoRef}
        muted
        style={{ width: "1024px", backgroundColor: "#333", height: "576px" }}
      />
      <div>
        <button type="button" onClick={capture}>
          촬영
        </button>
      </div>
    </div>
  );
};

export default PhotoCapture;

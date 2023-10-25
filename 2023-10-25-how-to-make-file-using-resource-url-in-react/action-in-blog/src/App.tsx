import React, { useRef, useState } from "react";
import "./App.css";
import { loadImage } from "./utils/image";

function App() {
  const [file, setFile] = useState<File>();
  const imageRef = useRef<HTMLImageElement>(null);

  const readImage = (image: File) => {
    const fileReader = new FileReader();
    fileReader.onloadend = function () {
      if (imageRef.current && fileReader.result) {
        imageRef.current.src = fileReader.result.toString();
      }
    };
    fileReader.readAsDataURL(image);
  };

  const load = () => {
    loadImage(
      "/images/how-to-make-file-using-resource-url-in-react-1.JPG",
    ).then((blob) => {
      const image = new File([blob], "image", { type: blob.type });
      setFile(image);
      readImage(image);
    });
  };

  return (
    <div className="App">
      <img
        src="/images/empty.png"
        alt="base-img"
        ref={imageRef}
        onClick={load}
      />
      <button disabled={file === undefined}>next</button>
    </div>
  );
}

export default App;

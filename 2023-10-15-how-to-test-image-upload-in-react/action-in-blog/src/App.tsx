import React, { ChangeEvent, useRef } from "react";
import defaultImage from "./assets/default-image.jpg";
import "./App.css";

function App() {
  const imageRef = useRef<HTMLImageElement>(null);

  const onFileSelect = (event: ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    const file = files?.item(0);
    if (!file) {
      return;
    }
    const fileReader = new FileReader();
    fileReader.onloadend = function () {
      if (imageRef.current && fileReader.result) {
        imageRef.current.src = fileReader.result.toString();
      }
    };
    fileReader.readAsDataURL(file);
  };

  return (
    <div>
      <div className="image">
        <img ref={imageRef} src={defaultImage} alt="profile-image" />
      </div>
      <input
        aria-label="file-selector"
        type="file"
        accept="image/*"
        onChange={onFileSelect}
      />
    </div>
  );
}

export default App;

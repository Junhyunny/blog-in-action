import { type CSSProperties, useEffect, useState } from "react";
import { loadImageWithRety } from "../utils/utils";
import "./SkeletonImage.css";

type Props = {
  src: string;
};

const imageStyle = {
  padding: "15px",
  borderRadius: "10px",
  width: "150px",
  height: "100px",
  backgroundPosition: "center",
  backgroundSize: "cover",
} as CSSProperties;

export const SkeletonImage = ({ src }: Props) => {
  const [isLoaded, setIsLoaded] = useState(false);
  const [isError, setError] = useState(false);

  useEffect(() => {
    loadImageWithRety(src)
      .catch(() => setError(true))
      .finally(() => setIsLoaded(true));
  }, [src]);

  if (!isLoaded) {
    return (
      <div
        role="status"
        aria-label="skeleton"
        className="skeleton"
        style={imageStyle}
      />
    );
  }

  return (
    <>
      {isError ? (
        <div
          role="img"
          aria-label="alternate-image"
          style={{ ...imageStyle, backgroundColor: "darkblue" }}
        />
      ) : (
        <img src={src} alt="thumbnail-image" style={imageStyle} />
      )}
    </>
  );
};

import { useEffect } from "react";

const BigDom = () => {
  useEffect(() => {
    const bigDom = document.querySelector(".big-dom");
    for (let index = 0; index < 10000; index++) {
      const div = document.createElement("div");
      div.append(
        "x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x ",
      );
      bigDom!.appendChild(div);
    }
  }, []);

  return <div className="big-dom"></div>;
};

export default BigDom;

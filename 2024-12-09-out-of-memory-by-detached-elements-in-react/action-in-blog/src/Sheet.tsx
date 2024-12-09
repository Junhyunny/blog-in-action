import { ReactNode, useState } from "react";
import BigDom from "./BigDom.tsx";

const Category = ({ name }: { name: string }) => {
  const [state, setState] = useState<boolean>(false);
  return (
    <div className="Category">
      <div className="NameAndState">
        {name}: {`${state}`}
      </div>
      <button
        className="ToggleButton"
        onClick={() => setState((prevState) => !prevState)}
      >
        Toggle
      </button>
      <div className="BigString">
        <BigDom />
      </div>
    </div>
  );
};

const Sheets = () => {
  const [currentTab, setCurrentTab] = useState<"A" | "B" | "C">("A");

  // const { AComponent, BComponent, CComponent } = {
  //   AComponent: () => <Category name={"AComponent"} />,
  //   BComponent: () => <Category name={"BComponent"} />,
  //   CComponent: () => <Category name={"CComponent"} />,
  // };
  // const mapTap: { [key in "A" | "B" | "C"]: ReactNode } = {
  //   A: <AComponent />,
  //   B: <BComponent />,
  //   C: <CComponent />,
  // };

  const mapTap: { [key in "A" | "B" | "C"]: ReactNode } = {
    A: <Category name={"A Component"} />,
    B: <Category name={"B Component"} />,
    C: <Category name={"C Component"} />,
  };

  return (
    <div className="Sheets">
      <div className="CategoryTabs">
        {/* 3 */}
        <div onClick={() => setCurrentTab("A")}>A Tab</div>
        <div onClick={() => setCurrentTab("B")}>B Tab</div>
        <div onClick={() => setCurrentTab("C")}>C Tab</div>
      </div>
      {/* 4 */}
      <div className="Tab">{mapTap[currentTab]}</div>
    </div>
  );
};

export default Sheets;

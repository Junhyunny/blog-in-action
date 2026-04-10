import { SkeletonImage } from "./components/SkeletonImage";

function App() {
  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        marginTop: "50px",
      }}
    >
      {Array.from({ length: 1 }, (_, i) => i).map((i) => (
        <div key={`index-${i}`} style={{ marginBottom: "15px" }}>
          <SkeletonImage src={`http://localhost:8000/images/${i + 1}`} />
        </div>
      ))}
    </div>
  );
}

export default App;

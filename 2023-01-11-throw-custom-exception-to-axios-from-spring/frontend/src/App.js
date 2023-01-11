import "./App.css";
import { Fragment, useState } from "react";
import ReactDOM from "react-dom";
import axios from "axios";

axios.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    const { data } = error.response;
    return Promise.reject(data);
  }
);

const Modal = (props) => {
  return (
    props.open && (
      <Fragment>
        {ReactDOM.createPortal(
          <div className="dim" />,
          document.getElementById("dim")
        )}
        {ReactDOM.createPortal(
          <div className="modal">{props.children}</div>,
          document.getElementById("modal")
        )}
      </Fragment>
    )
  );
};

function App() {
  const [openModal, setOpenModal] = useState(false);
  const [message, setMessage] = useState("");

  const requestHandler = () => {
    axios.get("/some-request").catch((error) => {
      setOpenModal(true);
      setMessage(`[${error.code}] ${error.message}`);
    });
  };

  const closeModal = () => {
    setOpenModal(false);
  };

  return (
    <div className="App">
      <Modal open={openModal}>
        <h2>{message}</h2>
        <button onClick={closeModal}>OK</button>
      </Modal>
      <h2>Welcome To Sample Page</h2>
      <button onClick={requestHandler}>Submit</button>
    </div>
  );
}

export default App;

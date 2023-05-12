import React, { useState } from "react";
import axios from "axios";

function App() {
  const [message, setMessage] = useState<string>("");

  const successHandler = ({ data }: { data: any }) => {
    setMessage(data);
    return data;
  };

  const errorHandler = ({ message }: { message: string }) => {
    setMessage(message);
  };

  const withCredentialsHandler = () => {
    axios
      .get("http://localhost:8080/foo", { withCredentials: true })
      .then(successHandler)
      .catch(errorHandler);
  };

  const withoutCredentialsHandler = () => {
    axios
      .get("http://localhost:8080/foo")
      .then(successHandler)
      .catch(errorHandler);
  };

  return (
    <div>
      <div>
        <button onClick={withCredentialsHandler}>withCredentials</button>
        <button onClick={withoutCredentialsHandler}>withoutCredentials</button>
      </div>
      <p>{message}</p>
    </div>
  );
}

export default App;

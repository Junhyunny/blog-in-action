import React from "react";

function App() {
  return (
    <form action="/login" method="post" aria-label="form">
      <label>
        username
        <input type="text" name="username" placeholder="username" />
      </label>
      <label>
        password
        <input type="password" name="password" placeholder="password" />
      </label>
      <button type="submit">login</button>
    </form>
  );
}

export default App;

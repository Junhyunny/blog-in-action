import { Fragment } from 'react';
import Header from './components/Header';
import Auth from './components/Auth';
import Counter from './components/Counter';

function App() {
  return (
    <Fragment>
      <Header />
      <Auth />
      <Counter />
    </Fragment>
  );
}

export default App;

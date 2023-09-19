import Home from './components/Home/Home';
import User from './components/User/User';
import Navbar from  './components/Navbar/Navbar';
import './App.css';
import {BrowserRouter, Switch, Route} from "react-router-dom";
function App() {
  return (
    <div className="App">
      <BrowserRouter>
      <Navbar></Navbar>
        <Switch>
            <Route exact path="/" Component={Home}></Route>
            <Route exact path="/users/:userId" Component={User}></Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;

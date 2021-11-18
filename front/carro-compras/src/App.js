import React from 'react'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Admin from './components/Admin'
import Login from './components/Login'
import Navbar from './components/Navbar'
import Products from './components/Products'
import Cart from './components/Cart'


const App = () => {
  return (
    <div>
      <Router>
        <Navbar></Navbar>
        <Switch>
          <Route exact path='/admin' component={Admin}></Route>
          <Route exact path='/' component={Login}></Route>
          <Route exact path='/products' component={Products}></Route>
          <Route exact path='/cart' component={Cart}></Route>
        </Switch>
      </Router>
    </div>
  )
}

export default App


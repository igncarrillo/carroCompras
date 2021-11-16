import React from 'react'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Admin from './components/Admin'
import Login from './components/Login'
import Menu from './components/Menu'
import Productos from './components/Productos'
import Cart from './components/Cart'


const App = () => {
  return (
    <div>
      <Router>
        <Menu></Menu>
        <Switch>
          <Route exact path='/admin' component={Admin}></Route>
          <Route exact path='/login' component={Login}></Route>
          <Route exact path='/Productos' component={Productos}></Route>
          <Route exact path='/carro' component={Cart}></Route>
        </Switch>
      </Router>
    </div>
  )
}

export default App


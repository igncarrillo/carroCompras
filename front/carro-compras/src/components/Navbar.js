import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <h2 className="text-center" style={{color: "white", position: "absolute", left: "40vw", right: "40vw"}}>Tunuyork Market</h2>
        <ul className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link className="nav-link" to="/login">
              Login
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/admin">
              Admin
            </Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/productos">
              Home
            </Link>
          </li>
        </ul>
        <ul className="navbar-nav">
          <li className="nav-item">
            <Link
              className="bi bi-cart2"
              style={{ fontSize: "2rem", color: "white", marginRight: "6rem" }}
              to="/carro"
            ></Link>
          </li>
        </ul>
      </nav>
      {/* <nav className="navbar navbar-dark bg-dark">
                
            <div className="container">
                    <Link to="/home" className="navbar-brand" style={{fontSize: "3rem", color: "white"}}>Tunuyork Market </Link>
                    
                    <Link to="/" class="float-xl-left" style={{fontSize: "2rem", color: "white"}}>Login</Link>
                    <Link to="/admin" className=" float-xl-left" style={{fontSize: "2rem", color: "white"}}>Admin</Link>
                    
                    <ul className="right">
                        <li><Link to="/cart"><i className="bi bi-cart2" style={{fontSize: "4rem", color: "white"}}></i></Link></li>
                    </ul>
                </div>
            </nav> */}
    </div>
  );
};

export default Navbar;

import React from "react";
import { useEffect, useState } from "react";
//import ProductoItem from "./ProductoItem";
import "bootstrap/dist/css/bootstrap.css";

const Products = () => {
  const [contenido, setcontenido] = useState([]);
  const productos = async () => {
    const token = window.sessionStorage.getItem("token_product");
    const response = await fetch(
      "http://localhost:8080/api/products?enabled=true",
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        credentials: "include",
      }
    );
    const content = await response.json();
    console.log(content);
    setcontenido(content);
  };

  useEffect(() => {
    productos();
  }, []);

  let products = []
  const handleClick = () => {

    console.log(products)
  }

  return (
    <div>
      <h2 className="text-center">Productos</h2>
      <hr />
      {/* <div className="row"> */}
      {/* <div className="col"> */}
      <h2>Lista de productos</h2>
      {/* <ul className="list-group"> */}

          <div className="row" style={{marginInline: "auto"}}>
      {contenido.map(
        (item) => (
          <div className="col ">
             <div
        className="card border-dark bg-secondary"
        style={{  height: "15rem",marginBottom: "1rem" }}
        key={item.id}
      >
        <div className="card-body text-center">
          {/* <img src={""} /> */}
          <span className="card-title">{item.name}</span>
          <div className="card-text">
            <p>{item.description}</p>
            <p>
              <b>Precio: ${item.price}</b>
            </p>
          </div>
          <span
            to="/"
            className="btn-floating halfway-fab waves-effect waves-light red"
            onClick={() => {
              products.push(item)
              handleClick ()
              
            }}
          >
            <a href="#" className="btn btn-primary">
              Añadir al carro
            </a>
          
          </span>
        </div>
      </div>
              </div>
              ))}
            
          </div>
    </div>
  );
};

export default Products;

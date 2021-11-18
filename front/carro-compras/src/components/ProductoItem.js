import React from "react";
import "bootstrap/dist/css/bootstrap.css";

const ProductoItem = ({ data, addToCart }) => {
  let { id, name, price, description } = data;

  return (
      <div
        className="card border-dark bg-secondary"
        style={{  height: "15rem",marginBottom: "1rem" }}
        key={id}
      >
        <div className="card-body text-center">
          {/* <img src={""} /> */}
          <span className="card-title">{name}</span>
          <div className="card-text">
            <p>{description}</p>
            <p>
              <b>Precio: ${price}</b>
            </p>
          </div>
          <span
            to="/"
            className="btn-floating halfway-fab waves-effect waves-light red"
            onClick={() => {
              this.handleClick(id);
            }}
          >
            <a href="#" className="btn btn-primary">
              Añadir al carro
            </a>
          </span>
        </div>
      </div>
  );
};

export default ProductoItem;

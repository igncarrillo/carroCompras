import React from "react";
import "bootstrap/dist/css/bootstrap.css";


// const [products, setproducts] = useState("");

const handleClick = ({id, name, price, description})=>{
  console.log(id, name, price, description)
}

const products = []
const ProductoItem = ({ data}) => {
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
              handleClick(data);
              products.push(data)
              
            }}
          >
            <a href="#" className="btn btn-primary">
              Añadir al carro
            </a>
          
          </span>
          <Inicializer items={products}/>
        </div>
      </div>
  );
};

export default ProductoItem;

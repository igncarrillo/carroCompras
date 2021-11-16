import React from 'react'
import { useReducer } from "react";
import { TYPES } from "../actions/carroActions";
import {
    shoppingInitialState,
    carroReducer,
} from "../reducers/carroReducer";
import CarroItems from './CarroItems';
import ProductoItem from './ProductoItem';
import { useState } from 'react';
import { useEffect } from 'react';

const Cart = () => {

  const [contenido, setcontenido] = useState([])
  const productos = async () =>{
      const token = window.sessionStorage.getItem("token_product")
      const response = await fetch('http://localhost:8080/api/products?enabled=true', {
              method: 'GET',
              headers: {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`},
              credentials: 'include',
              });
      const content = await response.json();
      console.log(content)
      setcontenido(content)

  }

  useEffect(() => {
    productos()
}, [])

  const [state, dispatch] = useReducer(carroReducer, shoppingInitialState);

  state.products = contenido
  const { products, cart } = state;

  const addToCart = (id) => {
    dispatch({ type: TYPES.ADD_TO_CART, payload: id });
  };

  const delFromCart = (id, all = false) => {
    if (all) {
      dispatch({ type: TYPES.REMOVE_ALL_FROM_CART, payload: id });
    } else {
      dispatch({ type: TYPES.REMOVE_ONE_FROM_CART, payload: id });
    }
  };

  const clearCart = () => {
    dispatch({ type: TYPES.CLEAR_CART });
  };

  return (
    <div>
      <h2>Carrito de Compras</h2>
      <h3>Productos</h3>
      <article className="box grid-responsive">
        {products.map((product) => (
          <ProductoItem key={product.id} data={product} addToCart={addToCart} />
        ))}
      </article>
      <h3>Carrito</h3>
      <article className="box">
        <button onClick={clearCart}>Limpiar Carrito</button>
        {cart.map((item, index) => (
          <CarroItems key={index} data={item} delFromCart={delFromCart} />
        ))}
      </article>
    </div>
  );
};

export default Cart
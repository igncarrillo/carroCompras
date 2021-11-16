import React from 'react'
import { useReducer } from "react";
import { TYPES } from "../actions/carroActions";
import {
    shoppingInitialState,
    carroReducer,
} from "../reducers/carroReducer";
import CarroItems from './CarroItems';
import ProductoItem from './ProductoItem';

const Cart = () => {
    const [state, dispatch] = useReducer(carroReducer, shoppingInitialState);

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
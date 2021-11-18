import { ADD_TO_CART,REMOVE_ITEM, REMOVE_ALL_FROM_CART} from './action-types/cart-actions'

export const addToCart= (id)=>{
    return{
        type: ADD_TO_CART,
        id
    }
}

export const removeItem=(id)=>{
    return{
        type: REMOVE_ITEM,
        id
    }
}

export const removeAllItems=(id)=>{
    return{
        type: REMOVE_ALL_FROM_CART,
        id
    }
}

export const subtractQuantity=(id)=>{
    return{
        type: SUB_QUANTITY,
        id
    }
}

export const addQuantity=(id)=>{
    return{
        type: ADD_QUANTITY,
        id
    }
}    
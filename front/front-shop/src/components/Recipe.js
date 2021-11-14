import React, { Component } from 'react'
import { connect } from 'react-redux'
//import { addShipping } from './actions/cartActions'
class Recipe extends Component{
    
    render(){
  
        return(
            <div className="container">
                <div className="collection">
                    <li className="collection-item"><h3>Total de su compra: $ {this.props.total} </h3></li>
                </div>
                    <div className="checkout">
                        <button type="button" className="btn btn-success">Realizar Compra</button>
                    </div>
            </div>
        )
    }
}

const mapStateToProps = (state)=>{
    return{
        addedItems: state.addedItems,
        total: state.total
    }
}

const mapDispatchToProps = (dispatch)=>{
    return{
        addShipping: ()=>{dispatch({type: 'ADD_SHIPPING'})},
        substractShipping: ()=>{dispatch({type: 'SUB_SHIPPING'})}
    }
}

export default connect(mapStateToProps,mapDispatchToProps)(Recipe)

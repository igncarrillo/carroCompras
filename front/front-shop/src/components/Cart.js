import React, { Component } from 'react';
import { connect } from 'react-redux'
import { Link } from 'react-router-dom'
import { removeItem,addQuantity,subtractQuantity} from './actions/cartActions'
import Recipe from './Recipe'

class Cart extends Component{
    constructor(props){
        super(props);
        
        this.state = {
            productos : []


        };    
    }

    componentDidMount() {
        fetch('http://localhost:8080/api/products?enabled=true', {
          headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYzOTI4NDk1NX0.da9XQfEP0-Iv6-zKmnkBDdXMPlQ1PHjz5QONe2E8hsfr0K0WIIWzn43T4WBJsMNEw9wCLrAUt2dhUcaB9fyq4Q"
         }
        })
          .then(response => response.json()) 
          .then(data => {
    
            this.setState({productos: data})
            console.log(data)
    
          })

    }

    //to remove the item completely
    handleRemove = (id)=>{
        this.props.removeItem(id);
    }
    //to add the quantity
    handleAddQuantity = (id)=>{
        this.props.addQuantity(id);
    }
    //to substruct from the quantity
    handleSubtractQuantity = (id)=>{
        this.props.subtractQuantity(id);
    }
    render(){
              
        let addedItems = this.props.items.length ?
            (  
                this.props.items.map(item=>{
                    return(
                       
                        <li className="collection-item avatar" key={item.id}>
                                    <div className="item-img"> 
                                        <img src={item.img} alt={item.img} className=""/>
                                    </div>
                                
                                    <div className="item-desc">
                                        <span className="title">{item.name}</span>
                                        <p>{item.description}</p>
                                        <p><b>Precio: ${item.price}</b></p> 
                                        <p>
                                            <b>Cantidad: {item.quantity}</b> 
                                        </p>
                                        <div className="add-remove">
                                            <Link to="/cart"><i className="material-icons" onClick={()=>{this.handleAddQuantity(item.id)}}>arrow_drop_up</i></Link>
                                            <Link to="/cart"><i className="material-icons" onClick={()=>{this.handleSubtractQuantity(item.id)}}>arrow_drop_down</i></Link>
                                        </div>
                                        <button type="button" className="btn btn-dark" onClick={()=>{this.handleRemove(item.id)}}>Quitar</button>
                                    </div>
                                    
                                </li>
                         
                    )
                })
            ):

             (
                <p>Su carrito se encuentra vacio.</p>
             )
       return(
            <div className="container">
                <div className="cart">
                    <h4>Articulos de su compra:</h4>
                    <ul className="collection">
                        {addedItems}
                    </ul>
                </div> 
                <Recipe />          
            </div>
       )
    }
}


const mapStateToProps = (state)=>{
    return{
        items: state.addedItems,
        //addedItems: state.addedItems
    }
}
const mapDispatchToProps = (dispatch)=>{
    return{
        removeItem: (id)=>{dispatch(removeItem(id))},
        addQuantity: (id)=>{dispatch(addQuantity(id))},
        subtractQuantity: (id)=>{dispatch(subtractQuantity(id))}
    }
}
export default connect(mapStateToProps,mapDispatchToProps)(Cart)
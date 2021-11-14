import React, { Component } from 'react';
import { connect } from 'react-redux'
import { addToCart } from './actions/cartActions'


 class Home extends Component{

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
    
    handleClick = (id)=>{
        this.props.addToCart(id); 
    }

    render(){
        let itemList = this.state.productos.map(item=>{
            return(
                <div className="card" key={item.id}>
                        <div className="card-image">
                            <img src={""}/>
                            <span className="card-title">{item.name}</span>
                            <span to="/" className="btn-floating halfway-fab waves-effect waves-light red" onClick={()=>{this.handleClick(item.id)}}><i className="material-icons">add</i></span>
                        </div>

                        <div className="card-content">
                            <p>{item.description}</p>
                            <p><b>Precio: ${item.price}</b></p>
                        </div>
                        
                 </div>
                 

            )
        })

        return(
            <div className="container">
                <h3 className="center">Productos</h3>
                <div className="box">
                    {itemList}
                </div>
            </div>
        )
    }
}
const mapStateToProps = (state)=>{
    return {
      items: state.items
    }
  }
const mapDispatchToProps= (dispatch)=>{
    
    return{
        addToCart: (id)=>{dispatch(addToCart(id))}
    }
}

export default connect(mapStateToProps,mapDispatchToProps)(Home)
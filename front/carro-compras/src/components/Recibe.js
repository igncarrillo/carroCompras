import { connect } from 'react-redux'

const Recibe () 

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


export default connect(mapStateToProps)(Recipe)
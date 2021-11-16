import React from 'react'
import { useEffect, useState } from 'react'

const Productos = () => {
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
    
    return (
        <div>
            <h2 className="text-center">Productos</h2>
            <hr/>
            <div className='row'>
                <div className='col'>
                    <h2>Listado de productos</h2>
                    <ul className='list-group'>
                        {
                            contenido.map(item =>
                                <li key={item.id} className='list-group-item'>
                                    {item.name}{item.description}
                                </li>
                                )
                        }
                    </ul>
                </div>
            </div>
            
        </div>
    )
}

export default Productos

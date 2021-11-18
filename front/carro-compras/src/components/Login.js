import React from 'react'
import { useState, requiere } from 'react'
import { Redirect } from 'react-router'
import "bootstrap/dist/css/bootstrap.css";


const Login = () => {
    const [username, setusername] = useState('')
    const [password, setpassword] = useState('')
    const [redirect, setredirect] = useState(false)
    
    const RegistrarUsuario = async (e)=>{
        e.preventDefault();
        try{
            setredirect(false)
            const usuario = {username, password}
            const response = await fetch('http://localhost:8080/api/authenticate', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include',
                body:JSON.stringify(usuario)
                });
            const content = await response.json();
            window.sessionStorage.setItem("token_product", content.id_token);
            
            /*const respuesta = await fetch('http://localhost:8081/api/authenticate', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include',
                body:JSON.stringify(usuario)
                });
            const contenido = await respuesta.json();
            window.sessionStorage.setItem("token_carro", contenido.id_token);*/
            setredirect(true)
        }catch (e){
            console.log(e)
        }
    }
    
    
    return (

        <div className='row mt-5'>
            <div className='col'></div>
            <div className='col'>

            <img src="./logo.png" class="img-fluid" alt="Logo"></img>

                <form onSubmit={RegistrarUsuario} className='form-group'>
                    <label>
                    Usuario:
                    <input
                        name=""            
                        type="text"
                        checked={username}
                        onChange={(e) => {setusername(e.target.value)}} />
                    </label>
                    <br />
                    <label>
                    Contraseña:
                    <input
                        name="password"            
                        type="password"
                        value={password}
                        onChange={(e) => {setpassword(e.target.value)}} />

                    <input type="submit" name="Login" value="Login"/>
                    </label>
                </form>
                {
                    redirect !== false ?
                    (
                        <Redirect to='/products'/>
                    ):
                    (
                        <span></span>
                    )
                }
            </div>
            <div className='col'></div>
        </div>
    )
}

export default Login

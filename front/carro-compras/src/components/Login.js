import React from 'react'
import { useState } from 'react'
import { Redirect } from 'react-router'

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
                <form onSubmit={RegistrarUsuario} className='form-group'>
                    <input
                        value={username}
                        onChange={(e) => {setusername(e.target.value)}}
                        className='form-control'
                        placeholder='Introduce el usuario'
                        type='text'/>
                    <input
                        value={password}
                        onChange={(e) => {setpassword(e.target.value)}}
                        className='form-control mt-4'
                        placeholder='Introduce la contraseña'
                        type='password'/>
                    <input
                        className='btn btn-block btn-dark mt-4'
                        placeholder='Registrar Usuario'
                        type='submit'/>
                </form>
                {
                    redirect !== false ?
                    (
                        <Redirect to='/productos'/>
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

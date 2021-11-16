import React, { useState } from 'react'
import { Redirect } from 'react-router-dom'

const Admin = () => {

    const [valor, setvalor] = useState({bol : false})

    // function delay(n){
    //     return new Promise(function(resolve){
    //         setTimeout(resolve,n*1000);
    //     });
    // }

    const redirect = async (e) =>{
        e.preventDefault()
        setvalor({bol : true})
        // await delay(4)
        console.log(valor)
    }

    return (
        <div className='row mt-5'>
            <div className='col'></div>
            <div className='col'>
                <form onSubmit={redirect} className='form-group'>
                    <input
                        className='btn btn-block btn btn-dark'
                        placeholder='Index'
                        type='submit'
                        />
                </form>
                {
                    valor === {bol:true} ?(
                        <Redirect to='/'></Redirect>
                    ):(
                        <span></span>
                    )
                }
                
            </div>
            <div className='col'></div>
        </div>
    )
}

export default Admin

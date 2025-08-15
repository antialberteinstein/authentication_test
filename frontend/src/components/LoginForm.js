import { useNavigate } from 'react-router-dom';
import './LoginForm.css'
import { useCallback, useState } from 'react'

export default function LoginForm() {
    const [form, setForm] = useState({email: "", password: ""});
    const [error, setError] = useState({email: "", password: ""});
    const navigate = useNavigate();

    const handleChange = useCallback((e) => {
        const {name, value} = e.target;

        setForm(prevForm => ({
            ...prevForm,
            [name]: value
        }))

        setError(prevError => {
            const newError = {...prevError};

            if (name === 'email') {
                if (value.length <= 0) {
                    newError.email = 'Email must not be blank!!';
                } else if (!value.includes('@')) {
                    newError.email = 'Email is not valid!!';
                } else {
                    newError.email = '';
                }
            } else {
                if (value.length < 6) {
                    newError.password = 'Password must have greater than or equal 6 characteres!!';
                } else {
                    newError.password = '';
                }
            }

            return newError;
            
        })

    }, [])

    const handleLogin = useCallback(async (email, password) => {
        const body = {
            email: email,
            password: password,
        }

        const response = await fetch('http://nguyen:8080/api/login', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(body)
        });

        if (response.ok) {
            const data = await response.json(); // chờ parse JSON
            const token = data.token;           // lấy token từ JSON
            localStorage.setItem('token', token);
            navigate('/');
        } else {
            alert("Please check your email or password!!");
            navigate('/login');
        }
    }, [])

    const onSubmitForm = useCallback(async (e) => {
        e.preventDefault();
        setError({});
        
        const {email, password} = e.target;
        handleLogin(email.value, password.value);
    }, [])

    const isFormValid =
    !error.email &&
    !error.password &&
    form.email.trim() !== "" &&
    form.password.trim() !== "";

    return (
        <div className="container">
        <form name="loginForm" className="form" onSubmit={onSubmitForm}>
            <div className="row">
                <label htmlFor='email'>Email</label>
                <input name='email' type='text' className='input' onChange={handleChange} value={form.email}></input>
                {error.email && <p className='error'>{error.email}</p>}
            </div>
            <div className="row">
                <label htmlFor='password'>Password</label>
                <input name='password' type='password' className='input' onChange={handleChange} value={form.password}></input>
                {error.password && <p className='error'>{error.password}</p>}
            </div>
            <div className="btn-row-container">
                <input type='reset' value='Reset' className="btn"></input>
                <input type='submit' value='Login' className="btn primary" disabled={!isFormValid}></input>
            </div>
        </form>
        </div>
    )
}
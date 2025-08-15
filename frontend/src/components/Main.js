import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom";

export default function Main() {
    const [data, setData] = useState("");
    const navigate = useNavigate();
    const token = localStorage.getItem('token');

    useEffect(() => {
        setData(fetch('http://nguyen:8080/api/get-data', {
            method: 'GET',
            headers: {
                ContentType: 'application/json',
                Authorization: `Bearer ${token}`
            }
        })
        .then(res => {
            if (!res.ok) {
                navigate("/login");
            }
            return res.text();
        })
        .then(text => setData(text))
        .catch(err => {
            console.error('Fetch error: ', err);
        })
        )
    }, [navigate, token]);


    return (
        <>
            <p>{data}</p>
        </>
    )
}
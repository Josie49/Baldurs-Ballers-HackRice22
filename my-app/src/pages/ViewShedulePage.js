import React, { useState } from 'react';
import { Button, InputNumber } from "antd";
import axios from 'axios';
import './ViewSchedulePage.css'

export default function ViewSchedulePage() {
    const [id, setId] = useState(0);

    const sendData = () => {

        axios.get(`http://localhost:8080/api/employee/schedule/${id}`).then(res => {
            console.log(res);
        })
    }

    return (
        <div className="ViewSchedulePage">
            <InputNumber controls={false} onChange={(e) => setId(e)} />
            <Button type={"primary"} onClick={sendData}> Submit </Button>
        </div>
    );
}
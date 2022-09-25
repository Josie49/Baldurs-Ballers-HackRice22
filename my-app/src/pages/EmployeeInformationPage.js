import React, { useState } from 'react';
import { InputNumber, Checkbox, Button, Input } from 'antd';
import axios from 'axios';

const options = [
    { label: 'Carpentry', value: 'CARPENTRY' },
    { label: 'Backend', value: 'BACKEND'},
    { label: 'UI', value: 'UI'},
    { label: 'Hardware', value: 'HARDWARE'},
    { label: 'Emotional', value: 'EMOTIONAL'},
    { label: 'Wiring', value: 'WIRING'},
    { label: 'Consultation', value: 'CONSULTATION'},
    { label: 'Notary', value: 'NOTARY'},
];

export default function EmployeeInformationPage() {
    const [phoneNumber, setPhoneNumber] = useState(0);
    const [hours, setHours] = useState(0);
    const [skills, setSkills] = useState();
    const [location, setLocation] = useState("");

    const sendData = () => {
        console.log("Hours: ", hours);
        console.log("Skills: ", skills);
        console.log("Phone Number: ", phoneNumber);
        console.log("Location: ", location);
    }

    return (
        <div>

            <div>
                Hours Available
                <InputNumber controls={false} onChange={(e) => setHours(e)} />
            </div>
            <div>
                Worker Phone Number
                <InputNumber controls={false} onChange={(e) => setPhoneNumber(e)} />
            </div>
            <div>
                <h2>Relevant Skills</h2>
                <Checkbox.Group options={options} onChange={(checkedValues) => setSkills(checkedValues)} />
            </div>
            <div>
                <h2>Worker Location</h2>
                <Input onChange={(e) => setLocation(e.target.value)} placeholder="Enter Job Address"/>
            </div>

            <Button type={"primary"} onClick={sendData}> Submit </Button>
        </div>
    )
}
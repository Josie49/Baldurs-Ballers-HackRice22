import React, { useState } from 'react';
import { Button, Checkbox, Input, InputNumber } from 'antd';
import axios from 'axios';
import './WorkOrderPage.css';

const { TextArea } = Input;
const options = [
    { label: 'Carpentry', value: '0' },
    { label: 'Backend', value: '1'},
    { label: 'UI', value: '2'},
    { label: 'Hardware', value: '3'},
    { label: 'Emotional', value: '4'},
    { label: 'Wiring', value: '5'},
    { label: 'Consultation', value: '6'},
    { label: 'Notary', value: '7'},
]

export default function WorkOrderPage() {
    const [details, setDetails] = useState("");
    const [hours, setHours] = useState(0);
    const [skills, setSkills] = useState();
    const [latitude, setLatitude] = useState(0);
    const [longitude, setLongitude] = useState(0);

    const sendData = () => {
        console.log("Hours: ", hours);
        console.log("Skills: ", skills);
        console.log("Details: ", details);
        console.log("Latitude: ", latitude);
        console.log("Longitude: ", longitude);
    }

    return (
        <div className="WorkOrderPage">
            <div>
                <h2> Job Details </h2>
                <TextArea rows={6} placeholder="Job Details" onChange={(e) => setDetails(e.target.value)} />
            </div>
            <div>
                    Hours Needed For The Job
                    <InputNumber controls={false} onChange={(e) => setHours(e)} />
            </div>

            <div>
                <h2>Relevant Skills</h2>
                <Checkbox.Group options={options} onChange={(checkedValues) => setSkills(checkedValues)} />
            </div>

            <div>
                <div>
                    Latitude
                    <InputNumber controls={false} onChange={(e) => setLatitude(e)} />
                </div>
                <div>
                    Longitude
                    <InputNumber controls={false} onChange={(e) => setLongitude(e)} />
                </div>
            </div>

            <Button type={"primary"} onClick={sendData}> Submit </Button>
        </div>
    );
}
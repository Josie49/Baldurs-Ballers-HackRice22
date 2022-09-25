import React, { useState } from 'react';
import { Button, Checkbox, Input, InputNumber } from 'antd';

const { TextArea } = Input;
const options = [
    { label: 'Carpentry', value: 'CARPENTRY' },
    { label: 'Backend', value: 'BACKEND'},
    { label: 'UI', value: 'UI'},
    { label: 'Hardware', value: 'HARDWARE'},
    { label: 'Emotional', value: 'EMOTIONAL'},
    { label: 'Wiring', value: 'WIRING'},
    { label: 'Consultation', value: 'CONSULTATION'},
    { label: 'Notary', value: 'NOTARy'},
]

export default function WorkOrderPage() {
    const [details, setDetails] = useState("");
    const [hours, setHours] = useState(0);
    const [skills, setSkills] = useState();
    const [location, setLocation] = useState("");

    const skillsChange = (checkedValues) => {
        setSkills(checkedValues);
    }

    const sendData = () => {
        console.log("Hours: ", hours);
        console.log("Skills: ", skills);
        console.log("Details: ", details);
        console.log("Location: ", location);
    }

    return (
        <div>
            <div>
                <h2> Job Details </h2>
                <TextArea rows={6} placeholder="Your mom gay lol" onChange={(e) => setDetails(e.target.value)} />
            </div>
            <div>
                    Hours Needed For The Job
                    <InputNumber controls={false} onChange={(e) => setHours(e)} />
            </div>

            <div>
                <h2>Relevant Skills</h2>
                <Checkbox.Group options={options} onChange={skillsChange} />
            </div>

            <div>
                <h2>Job Location</h2>
                <Input onChange={(e) => setLocation(e.target.value)} placeholder="Enter Job Address"/>
            </div>

            <Button type={"primary"} onClick={sendData}> Submit </Button>
        </div>
    );
}
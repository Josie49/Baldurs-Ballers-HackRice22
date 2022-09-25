import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route, NavLink } from 'react-router-dom';
import WorkOrderPage from './pages/WorkOrderPage';
import EmployeeInformationPage from "./pages/EmployeeInformationPage";
import ViewSchedulePage from "./pages/ViewShedulePage";
import "./App.css"

function Home() {
    return (
        <div className="Home">
            <h1>Welcome to Baldur's Ballers v2</h1>
            <div className="Pages">
                <p><NavLink to="/SubmitWorkOrder">Submit A Work Order</NavLink></p>
                <p><NavLink to="/SubmitEmployeeInformation">Submit Employee Information</NavLink></p>
                <p><NavLink to="/EmployeeSchedule">View Employee Schedule</NavLink></p>
            </div>
        </div>
    );
}

function App() {
  return (
    <Router>
        <div className="App">
            <NavLink to="/">Home</NavLink>
        </div>

        <Routes>
            <Route exact path="/SubmitWorkOrder" element={<WorkOrderPage />} />
            <Route exact path="/SubmitEmployeeInformation" element={<EmployeeInformationPage />} />
            <Route exact path="/EmployeeSchedule" element={<ViewSchedulePage />} />
            <Route exact path="/" element={<Home />}/>
        </Routes>
    </Router>
  );
}

export default App;
import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route, NavLink } from 'react-router-dom';
import WorkOrderPage from './pages/WorkOrderPage';
import EmployeeInformationPage from "./pages/EmployeeInformationPage";
import ViewSchedulePage from "./pages/ViewShedulePage";

function Home() {
    return (
        <div>
            <h1>Welcome to Baldur's Ballers v2</h1>
        </div>
    );
}

function App() {
  return (
    <Router>
        <div>
            <NavLink to="/">Home</NavLink>
            <NavLink to="/SubmitWorkOrder">Submit A Work Order</NavLink>
            <NavLink to="/SubmitEmployeeInformation">Submit Employee Information</NavLink>
            <NavLink to="/EmployeeSchedule">View Employee Schedule</NavLink>
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
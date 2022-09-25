import './App.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route, NavLink } from 'react-router-dom';
import WorkOrderPage from './pages/WorkOrderPage';

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
            <NavLink to="/WorkOrderPage">Submit A Work Order</NavLink>
        </div>

        <Routes>
            <Route exact path="/WorkOrderPage" element={<WorkOrderPage />} />

            <Route exact path="/" element={<Home />}/>
        </Routes>
    </Router>
  );
}

export default App;
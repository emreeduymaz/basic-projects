import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Register from './pages/Register';
import Login from './pages/Login';
import Auctions from './pages/Auctions';
import AuctionItem from './pages/AuctionItem';
import Payment from './pages/Payment';

const App = () => {
    return (
        <Router>
            <Navbar />
            <Routes>
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login />} />
                <Route path="/auctions/:id" element={<AuctionItem />} />
                <Route path="/auctions" element={<Auctions />} />
                <Route path="/payment" element={<Payment />} />
                <Route path="/" element={<h1>Home</h1>} />
            </Routes>
        </Router>
    );
};

export default App;

import React, { useState } from 'react';
import { api } from '../services/api';

const Payment = () => {
    const [amount, setAmount] = useState('');
    const [currency, setCurrency] = useState('usd');
    const [source, setSource] = useState('');
    const [description, setDescription] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/payments', { amount, currency, source, description });
            console.log('Payment successful:', response.data);
        } catch (error) {
            console.error('Error processing payment:', error);
        }
    };

    return (
        <div>
            <h1>Payment</h1>
            <form onSubmit={handleSubmit}>
                <input type="text" value={amount} onChange={(e) => setAmount(e.target.value)} placeholder="Amount" required />
                <input type="text" value={currency} onChange={(e) => setCurrency(e.target.value)} placeholder="Currency" required />
                <input type="text" value={source} onChange={(e) => setSource(e.target.value)} placeholder="Source" required />
                <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} placeholder="Description" required />
                <button type="submit">Pay</button>
            </form>
        </div>
    );
};

export default Payment;

import React, { useEffect, useState } from 'react';
import { api } from '../services/api';
import { Link } from 'react-router-dom';

const Auctions = () => {
    const [auctions, setAuctions] = useState([]);

    useEffect(() => {
        const fetchAuctions = async () => {
            try {
                const response = await api.get('/auctions');
                setAuctions(response.data);
            } catch (error) {
                console.error('Error fetching auctions:', error);
                console.error('Error details:', error.response ? error.response.data : error.message);
            }
        };
        fetchAuctions();
    }, []);

    return (
        <div>
            <h1>Auctions</h1>
            <ul>
                {auctions.map(auction => (
                    <li key={auction._id}>
                        <Link to={`/auctions/${auction._id}`}>
                            <h2>{auction.name}</h2>
                            <p>{auction.description}</p>
                            <p>Starting Bid: {auction.startingBid}</p>
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Auctions;

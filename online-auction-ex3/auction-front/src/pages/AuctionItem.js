import React, { useEffect, useState } from 'react';
import { api } from '../services/api';
import { useParams } from 'react-router-dom';

const AuctionItem = () => {
    const { id } = useParams();
    const [auction, setAuction] = useState(null);

    useEffect(() => {
        const fetchAuction = async () => {
            const response = await api.get(`/auctions/${id}`);
            setAuction(response.data);
        };
        fetchAuction();
    }, [id]);

    return (
        <div>
            {auction ? (
                <>
                    <h1>{auction.name}</h1>
                    <p>{auction.description}</p>
                    <p>Starting Bid: {auction.startingBid}</p>
                    <p>Current Bid: {auction.currentBid}</p>
                    {/* Add bidding functionality here */}
                </>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
};

export default AuctionItem;

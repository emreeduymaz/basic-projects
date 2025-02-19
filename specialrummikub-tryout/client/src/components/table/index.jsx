import React, { useState, useEffect } from 'react';

export default function Table(){
    const [gameData, setGameData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [draggingIndex, setDraggingIndex] = useState(null);


    useEffect(() => {

            const fetchGameData = async () => {
                try {
                    const response = await fetch('/start_game');
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    const data = await response.json();
                    setGameData(data);
                    setLoading(false);
                } catch (error) {
                    console.error('Error fetching game data:', error);
                    setError(error.message); // Hata mesajını state'e ayarla
                    setLoading(false);
                }
            };
            if (!gameData) {
                fetchGameData();
            }

    }, [gameData]);


    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error fetching data: {error}</div>; // Hata mesajını göster
    }

    if (!gameData) {
        return <div>No game data available.</div>;
    }


    function players_queue(index){
        let starting_player_index = gameData.starting_player
        if (starting_player_index === 0) starting_player_index = 1
        index = index + starting_player_index
        if (index === 4) index = 0
        if (index === 5) index = 1
        return index
    }
    const playerStyles = [
        "top-10 left-1/2 absolute", // İlk oyuncu: sola hizalı
        "top-1/2 right-10 absolute", // İkinci oyuncu: yukarı hizalı,
        "top-1/2 left-10 absolute" // Dördüncü oyuncu: aşağı hizalı
    ];
    return (
        <div className="flex w-[1400px] h-[1000px] bg-blue-500 mx-auto my-5 relative">

                <div className="w-[1100px] h-[200px] bg-orange-600 flex bottom-10 absolute flex-col items-center">
                <div className="">

                <h2>Starting Player: {gameData.starting_player === 0 ? 1 : gameData.starting_player}</h2>
                { /*<h3>Starting Player's Tiles:</h3>*/}
                    <ul className="flex-1 grid grid-cols-16 pt-[20px]">
                        {gameData.starting_tiles.map((tile, index) => (
                            <li
                                key={index}
                                className="p-4 rounded-md border border-gray-300 bg-white text-black h-[100px] w-[75px]" // Arka plan beyaz, metin siyah
                                style={{color: tile.color}} // Sayı rengini tile.color'a göre ayarla
                            >

                                <span style={{color: tile.color}}>{tile.number}</span>
                            </li>
                        ))}
                    </ul>
                </div>
                </div>

                    { /*<h3>Distributed Tiles:</h3>*/}

                    {gameData.distributed_tiles.map((playerTiles, index) => (
                        <div key={index} className={playerStyles[index]}>
                            <h4>Player {players_queue(index) + 1}'s Tiles:</h4>

                            { /*<ul className="flex-1 grid grid-cols-16 ">
                                {playerTiles.map((tile, index) => (
                                        <li
                                            key={index}
                                            className="p-4 rounded-md border border-gray-300 bg-white text-black"
                                            style={{color: tile.color}}
                                        >

                                            <span style={{color: tile.color}}>{tile.number}</span>
                                        </li>

                                    ))}
                                </ul>*/}

                        </div>
                    ))}



                {/* <h3>Ground Tiles:</h3>
                <ul>
                    {gameData.ground_tiles.map((tile, index) => (
                        <li key={index}>Color: {tile.color}, Number: {tile.number}</li>
                    ))}
                </ul>*/}

                <h3>Okey Tile: </h3>

            <p
                className="h-[100px] w-[75px] p-4 rounded-md border border-gray-300 bg-white text-black flex"
                style={{color: gameData.okey_tile.color}}><span style={{color: gameData.okey_tile.color}}>{gameData.okey_tile.number}</span></p>

        </div>
    )
}
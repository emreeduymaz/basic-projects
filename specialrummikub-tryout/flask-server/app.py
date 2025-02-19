from flask import Flask, render_template, jsonify
import random

app = Flask(__name__)

def generate_combinations():
    colors = ['red', 'blue', 'green', 'black']
    numbers = list(range(1, 14))
    tiles = [{'color': color, 'number': number} for color in colors for number in numbers]
    alltiles = []
    for tile in tiles:
        alltiles.append(tile)
        alltiles.append(tile)

    return alltiles

def distribute_tiles(tiles, num_players):
    # Mix Tiles
    random.shuffle(tiles)

    # Mixed Tiles List
    distributed_tiles = []

    # Giving 15 tiles to chosen player
    starting_player = random.randint(0, num_players - 1)
    starting_tiles = tiles[:15]
    distributed_tiles.append(starting_tiles)
    tiles = tiles[15:]

    # Distribute tiles to players except chosen player
    current_player = starting_player
    for _ in range(1, num_players):
        player_tiles = tiles[:14]
        distributed_tiles.append(player_tiles)
        tiles = tiles[14:]

        # After one distribution, distribute tiles to next player
        current_player = (current_player + 1) % num_players

    chosen_tile = random.choice(tiles)  # Random tile in tiles for choosing okey
    tiles.remove(chosen_tile)  # Removing chosen tile from tiles
    chosen_color = chosen_tile['color']
    chosen_number = chosen_tile['number']  # Defined chosen tile's color and number
    chosen_number = chosen_number + 1   # Increment one to find our okey tile
    okey_tile = {'color': chosen_color, 'number': chosen_number}  # Assign the okey to okey_tile
    ground_tiles = tiles
    distributed_tiles.remove(starting_tiles)
    return starting_player, starting_tiles, distributed_tiles, ground_tiles, okey_tile

@app.route('/')
def home():
    return render_template('index.html')

@app.route('/start_game', methods=['GET'])
def start_game():
    num_players = 4  # Default number of players
    tiles = list(generate_combinations())
    jokers = [{'color': 'joker', 'number': None} for _ in range(2)]
    tiles.extend(jokers)
    starting_player, starting_tiles, distributed_tiles, ground_tiles, okey_tile= distribute_tiles(tiles, num_players)
    return jsonify({
        'starting_player': starting_player,
        'starting_tiles': starting_tiles,
        'distributed_tiles': distributed_tiles,
        'ground_tiles': ground_tiles,
        'okey_tile': okey_tile

    })

if __name__ == '__main__':
    app.run(debug=True)

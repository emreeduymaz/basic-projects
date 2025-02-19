import random
import ask_player_choice
import bot_player
import per_finder

class Game:
    def __init__(self):
        pass

    def start_game(self, players_tiles, ground_tiles):
        num_players = len(players_tiles)
        starting_player = 0  # The player who received the initial 15 tiles starts the game
        print(f"Starting Player: {starting_player}")

        current_player = starting_player
        last_tile = None
        while True:
            player_tiles = players_tiles[current_player]
            print(f"\nPlayer {current_player}'s Turn")
            if current_player == 0:  # If it's your turn
                if last_tile is not None:
                    take_last_tile = input(
                        f"Do you want to take the last tile played by the previous player? ({last_tile}) [yes/no]: ")
                    if take_last_tile.lower() == 'yes':
                        player_tiles.append(last_tile)
                        print(f"You took the last tile: {last_tile}")
                    elif take_last_tile.lower() == 'no':
                        pull_tile = random.choice(ground_tiles)
                        ground_tiles.remove(pull_tile)
                        player_tiles.append(pull_tile)
                        print(f"You took the tile on the ground: {last_tile}")
                        for tile in ground_tiles:
                            print(tile)
                while True:
                    choice_handler = ask_player_choice.PlayerChoiceHandler()
                    choice_idx = choice_handler.ask_player_choice(player_tiles)
                    total_per_value = None
                    if int(choice_idx) == 1 and len(player_tiles) == 15:
                        per_handler = per_finder.PerFinder()
                        ordered_tiles, total_per_value = per_handler.per_finding(player_tiles)
                        player_tiles = ordered_tiles
                        # for tile in ordered_tiles:
                            # print("23w")
                            # print(tile)
                    ordered_player_tiles = player_tiles
                    total_per_value = total_per_value # DIŞARIDA NONE OLARAK BAŞTA TANIT İFTEN ÇIKINCA DA KULLANABİL.
                    print("Value of your pers(sets): ", total_per_value)
                    if choice_idx == 2:
                        pass
                    if choice_idx == 3:
                        pass
                    if choice_idx == 4:
                        pass
                        # self.pair_finding(player_tiles)
                    if choice_idx == 5:
                        choice = input(
                            "Which tile do you want to play? Enter the number of the tile:")
                        while not choice.isdigit() or int(choice) < 1 or int(choice) > len(ordered_player_tiles):
                            choice = input(
                                "Invalid input. Please enter the number of the tile you want to play: ")
                        if int(choice) is not None:
                            dropped_tile = ordered_player_tiles.pop(int(choice) - 1)
                            print(f"You played: {dropped_tile}")
                            last_tile = dropped_tile
                            break

            else:  # If it's a bot's turn
                if last_tile is not None:
                    print(f"Last tile played by Player {current_player - 1}: {last_tile}")
                drop_tile = bot_player.BotPlayer()
                drop_tile = drop_tile.bot_play(player_tiles, ground_tiles)
                last_tile = drop_tile

            # Logic for next player to decide whether to pick from the remaining tiles or not
            print(f"Remaining tiles for Player {current_player}: {player_tiles}")

            # Move to the next player
            current_player = (current_player + 1) % num_players

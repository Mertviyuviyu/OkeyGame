import java.util.Random;

public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 13; i++) {
            for (int j = 0; j < 2; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already sorted
     */
    public void distributeTilesToPlayers() {
        int tilecounter = 0;
        for (int i = 0; i < 15; i++) {
            players[0].addTile( tiles[tilecounter]); //players[0].playerTiles[i] = tiles[tilecounter];
            tiles[tilecounter] = null;
            //players[0].numberOfTiles++;
            tilecounter++;
        }
        for (int i = 1; i < 4; i++) {
            for (int k = 0; k < 14; k++) {
                players[i].addTile( tiles[tilecounter]);//players[i].playerTiles[k] = tiles[tilecounter];
                tiles[tilecounter] = null;
                //players[i].numberOfTiles++;
                tilecounter++;
            }
        }
    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        int currPlayer = getCurrentPlayerIndex();
        // Player.java daki getAndRemove methodu kullanılabilir(?)
        for (int i = 0; i < players[currPlayer].playerTiles.length; i++) {
            
            
             if (players[currPlayer].playerTiles[i] == null) {
                players[currPlayer].playerTiles[i] = lastDiscardedTile;
                return lastDiscardedTile.toString();
            } 
        }
        return "Error";
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        int currPlayer = getCurrentPlayerIndex();
        // Player.javadaki addTile methodu kullanılabilir (?) 
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != null) {
                for (int k = 0; k < players[currPlayer].playerTiles.length; k++) {
                    if (players[currPlayer].playerTiles[k] == null) {
                        players[currPlayer].playerTiles[k] = tiles[i];
                        tiles[i] = null;
                        return players[currPlayer].playerTiles[k].toString();
                    }
                    
                    }
                    
                    
                }

            }
        
        return "Error";
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        Random rand = new Random();
        for (int i = tiles.length - 1; i > 0; i--) {
            int randomIndex = rand.nextInt(i + 1);
            Tile temp = tiles[i];
            tiles[i] = tiles[randomIndex];
            tiles[randomIndex] = temp;
        }
    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game. Use calculateLongestChainPerTile method to get the
     * longest chains per tile.
     * To win, you need one of the following cases to be true:
     * - 8 tiles have length >= 4 and remaining six tiles have length >= 3 the last one can be of any length
     * - 5 tiles have length >= 5 and remaining nine tiles have length >= 3 the last one can be of any length
     * These are assuming we check for the win condition before discarding a tile
     * The given cases do not cover all the winning hands based on the original
     * game and for some rare cases it may be erroneous but it will be enough
     * for this simplified version
     */
    public boolean didGameFinish() {
        int currPlayer = getCurrentPlayerIndex();
        int[] temp =  players[currPlayer].calculateLongestChainPerTile();
        int num5 = 0;
        int num4 = 0;
        int num3 = 0;
        for ( int i = 0; i < players[currPlayer].numberOfTiles; i ++) {
            if ( temp[i] == 5 ) 
            num5++;
            
            else if ( temp[i] == 4 )
            num4++;
            
            else if ( temp[i] == 3 ) 
            num3++;
            
        }
        if ( (num5 == 5 && num3 == 9) || (num4 == 8 & num3 == 6))  {
        return true;
        }
        else 
        return false;
        
    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You may choose randomly or consider if the discarded tile is useful for
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {
        Random rr = new Random();
        int choice = rr.nextInt(2);
        if(choice == 0){
            getTopTile();
            System.out.println("Got from Top Tile");
        }
        else{
            getLastDiscardedTile();
            System.out.println("Got Last Discarded Tile");
        }



    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * For this use the findLongestChainOf method in Player class to calculate
     * the longest chain length per tile of this player,
     * then choose the tile with the lowest chain length and discard it
     * this method should print what tile is discarded since it should be
     * known by other players
     */
    public void discardTileForComputer() {
        int curr = getCurrentPlayerIndex();
        int temp = 100;
        for(int i = 0; i < players[curr].playerTiles.length; i++){
            if(players[curr].findLongestChainOf(players[curr].playerTiles[i]) < temp){
                temp = players[curr].findLongestChainOf(players[curr].playerTiles[i]);

            }
        }
        discardTile(temp);
        System.out.println(lastDiscardedTile);
    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        int curr = getCurrentPlayerIndex();
        lastDiscardedTile = players[curr].playerTiles[tileIndex];
        players[curr].getAndRemoveTile(tileIndex);
        // Player.javadaki getAndRemove methodu kullanılabilir (?)
    }

    public void currentPlayerSortTilesColorFirst() {
        players[currentPlayerIndex].sortTilesColorFirst();
    }

    public void currentPlayerSortTilesValueFirst() {
        players[currentPlayerIndex].sortTilesValueFirst();
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}

package something;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ChunkSaver implements Runnable {
    String file;
    int[][] tileArray;
    TerrainGenerator terrain;
    Chunk chunk;
    Thread t = null;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            saveTiles();
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public ChunkSaver(TerrainGenerator terrain, String file, int[][] tileArray, Chunk chunk) {
        this.tileArray = tileArray;
        this.terrain = terrain;
        this.chunk = chunk;
        this.file = file;
    }

    private void saveTiles() throws IOException, InterruptedException {
        FileWriter filestream = new FileWriter(file, false);
        BufferedWriter fw = new BufferedWriter(filestream);
        for (int i = 0; i < terrain.returnChunkSize(); i++) {
            for (int y = 0; y < terrain.returnMaxY(); y++) {
                //	fw.println(relativechunkTiles[i][y]);
                fw.write(tileArray[i][y] + "\n");
                Thread.yield();

            }
        }
        System.out.println(file + " Has been saved");
        fw.flush();
        fw.close();
    }

}

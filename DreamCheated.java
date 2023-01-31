//import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

public class DreamCheated {
    // wrote this as a refresher on Java.

    public static void main(String[] args) {

        final double PEARLTHRESHOLD = 20.0, PEARLRANGE = 423.0;
        final int GOLDTRADES = 262, DREAMPEARLS = 42, DREAMRODS = 211;

        int max_rods = 0, max_pearls = 0;
        long attempts = 0, max_attempts;
        double total_exec_time = 0, speed;
        String when_found = new String();

        long batch_size = 100_000;


        // lets not bother loading and saving to file yet

        Random rand = new Random();

        while (true) {
            long startTime = System.nanoTime();
            
            if (attempts >= 500_000_000) {
                batch_size = 500_000_000;
            } else if (attempts >= 100_000_000) {
                batch_size = 100_000_000;
            } else if (attempts >= 1_000_000) {
                batch_size = 1_000_000;
            }

            for (int i = 0; i < batch_size; i++) {
                // Pearls
                long i1 = rand.nextLong();
                long i2 = rand.nextLong();
                long i3 = rand.nextLong();
                long i4 = rand.nextLong();
                long i5 = rand.nextLong() & 0x0001_FFFF_FFFF_FFFFL;
                
                // I know this is stupid but ¯\_(ツ)_/¯
                int rods = Long.bitCount(i1) + Long.bitCount(i2) + Long.bitCount(i3) + Long.bitCount(i4) + Long.bitCount(i5);
           


                if (rods >= max_rods || rods >= DREAMRODS) {
                    // Pearls
                    int pearls = 0;

                    for (int j = 0; j < GOLDTRADES; j++) {
                        pearls += (rand.nextDouble() <= (PEARLTHRESHOLD / PEARLRANGE)) ? 1 : 0;
                    }

                    Boolean newMax = (rods >= max_rods && pearls >= max_pearls) ||
                                     (rods >= DREAMRODS && pearls >= max_pearls) ||
                                     (rods >= max_rods && pearls >= DREAMPEARLS);


                    if (newMax) {
                        max_attempts = attempts;
                        max_rods = rods;
                        max_pearls = pearls;

                        System.out.print("Attempts: ");
                        System.out.print(attempts);
                        System.out.print(", Rods: ");
                        System.out.print(rods);
                        System.out.print(", Pearls: ");
                        System.out.print(pearls);
                        System.out.println();
                    }


                }
                attempts += 1;
            }   
    

            long endTime = System.nanoTime();

            double duration = (endTime - startTime) / 1_000_000_000.0;

            speed = batch_size / duration;

            total_exec_time += duration;
            
            System.out.print("Attempts: ");
            System.out.print(attempts);
            System.out.print(", Speed (atmpt/sec): ");
            System.out.println((int) speed);
        }
        
    }
}

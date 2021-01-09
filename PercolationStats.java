import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENDCE_95 = 1.96;
    private final int gridSize, trials;
    private double[] openedSitesThreshold;   

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {        
        gridSize = n;
        this.trials = trials;        
        openedSitesThreshold = new double[trials];
        
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
    }

    // sample mean of percolation threshold
    public double mean() {
        Percolation system;
        int row, col;
        int totalSites = gridSize*gridSize;
        for (int i = 0; i < trials; i++) {
            system = new Percolation(gridSize);
            while (!system.percolates()) {
                row = StdRandom.uniform(1, gridSize+1);
                col = StdRandom.uniform(1, gridSize+1);
                system.open(row, col);
            }
            int openedSites = system.numberOfOpenSites();

            openedSitesThreshold[i] = (double) openedSites/totalSites;
        }
        return StdStats.mean(openedSitesThreshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (trials == 1)
            return Double.NaN;
        /* for (int i = 0; i < openedSitesT.length; i++) {
            double res = openedSitesT[i] - StdStats.mean(openedSitesT);
            sumT[i] = res;
        } */
        return StdStats.stddev(openedSitesThreshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double res;
        double standardDev = stddev();
        if (!Double.isNaN(standardDev))
            res = mean() - ((CONFIDENDCE_95 * standardDev) / Math.sqrt(trials));
        else 
            res = Double.NaN;
        return res;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double res;
        double standardDev = stddev();
        if (!Double.isNaN(standardDev)) 
            res = mean() + (CONFIDENDCE_95 * standardDev)/Math.sqrt(trials);
        else 
            res = Double.NaN;
        return res;
    }

   // test client (see below)
   public static void main(String[] args) {
       PercolationStats pStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
       System.out.printf("mean() = %f\n", pStats.mean());
       System.out.printf("stddev() = %f\n", pStats.stddev());
       System.out.printf("95%% confidence interval = [%f, %f]\n", pStats.confidenceLo(), pStats.confidenceHi());
   }

}

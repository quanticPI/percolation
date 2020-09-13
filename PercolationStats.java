import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int gridSize, trials;
    private double[] openedSitesThreshold;
    private static final double CONFIDENDCE_95 = 1.96;

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
        int row, col;
        int totalSites = gridSize*gridSize;
        for (int i = 0; i < trials; i++) {
            Percolation system = new Percolation(gridSize);
            while (!system.percolates()) {
                row = StdRandom.uniform(1, gridSize+1);
                col = StdRandom.uniform(1, gridSize+1);
                system.open(row, col);
            }
            int openedSites = system.numberOfOpenSites();

            openedSitesThreshold[i] = (double) openedSites/(totalSites);
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
       System.out.println("mean() = \t" + pStats.mean());
       System.out.println("stddev() = \t" + pStats.stddev());
       System.out.println("95% confidence interval = [" + pStats.confidenceLo() + "], [" + pStats.confidenceHi()+"]");
   }

}

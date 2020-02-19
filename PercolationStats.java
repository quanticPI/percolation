import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        Percolation system;
        int row, col;
        if(n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        system = new Percolation(n);        
        while(!system.percolates()){
            row = StdRandom.uniform(1, n+1);
            col = StdRandom.uniform(1, n+1);
            system.open(row, col);
        }
    }

    // sample mean of percolation threshold
    public double mean()

    // sample standard deviation of percolation threshold
    public double stddev()

    // low endpoint of 95% confidence interval
    public double confidenceLo()

    // high endpoint of 95% confidence interval
    public double confidenceHi()

   // test client (see below)
   public static void main(String[] args) {
       
   }

}

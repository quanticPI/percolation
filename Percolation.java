import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    int NN, N;
    protected Site[] sites;
    int openSitesNum;
    private WeightedQuickUnionUF quickUnionUF;
    private boolean _percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n <= 0)
            throw new IllegalArgumentException();
            
        NN = n*n;
        N=n;
        this.quickUnionUF = new WeightedQuickUnionUF(NN);
        sites = new Site[NN];
        for (int i=0; i<NN; i++) {
            sites[i] = new Site(i);
        }
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        Site site = null;
        if(!valid(row, col))
            throw new IllegalArgumentException();
        
        site = sites[getSiteIndex(row, col)];
        if(!site.isOpen()) {            
            site.open();
            openSitesNum++;
            //connect the site with opened sites
            List<Site> neighbours = getSiteNeighboursOf(row, col);
            for (Site neighbour : neighbours) {
                if (neighbour.isOpen())
                    quickUnionUF.union(site.getID(), neighbour.getID());
            }            
        }
        if(row == N && isFull(row, col))
            _percolates = true;
    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        return sites[getSiteIndex(row, col)].isOpen();
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        return quickUnionUF.find(getSiteIndex(row, col)) == quickUnionUF.find(getSiteIndex(1, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSitesNum;
    }

    // does the system percolate?
    public boolean percolates(){
        return _percolates;
    }

    private int getSiteIndex(int row, int col){
        return (N * col + row) - N-1;
    }

    private boolean valid(int row, int col) {
        if(row > 0 && col > 0 && row <= N && col <= N)
            return true;
        return false;
    }   

    private List<Site> getSiteNeighboursOf(int row, int col){
        List<Site> res = new ArrayList<Site>();
        /**
         * neighbours:
         * col+1: right
         * col-N: left
         * row + 1: lower
         * row - 1: upper
         */
        if(valid(row+1, col))
            res.add(sites[getSiteIndex(row+1, col)]);
        if(valid(row, col+1))
            res.add(sites[getSiteIndex(row, col+1)]);
        if(valid(row-1, col))
            res.add(sites[getSiteIndex(row-1, col)]);
        if(valid(row, col-1))
            res.add(sites[getSiteIndex(row, col-1)]);

        return res;
    }

    // test client (optional)
    public static void main(String[] args){
                
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(1, 2);
        p.open(2, 2);
        p.open(3, 2);
        if(p.percolates())
            System.out.println("TEST OK");
        else
            System.out.println("TEST FAILED");
    }
}

class Site {

    private int ID;
    private boolean isOpen;
    
    public Site(int id) {
        this.ID = id;
        isOpen = false;
    }

    public int getID() { return ID; }

    public void open() {
        this.isOpen = true;
    }

    public boolean isOpen() { return isOpen; }
       
}
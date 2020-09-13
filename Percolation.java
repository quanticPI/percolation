
import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private final int numColumns;
    private final Site[] sites;
    private int openSitesNum;
    private final WeightedQuickUnionUF quickUnionUF;
    private boolean percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
            
        int numSites = n*n;
        numColumns = n;
        openSitesNum = 0;
        this.quickUnionUF = new WeightedQuickUnionUF(numSites);
        sites = new Site[numSites];
        for (int i = 0; i < numSites; i++) {
            sites[i] = new Site(i);
        }
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!valid(row, col))
            throw new IllegalArgumentException();
    
        int currentSiteIndex = getSiteIndex(row, col);
        int leftNeighbourIndex = -1;
        int rightNeighbourIndex = -1;
        int upperNeighbourIndex = -1;
        int lowerNeighbourIndex = -1;
        if (!sites[currentSiteIndex].isOpen()) {
            sites[currentSiteIndex].open();
            openSitesNum++;
        
            // connect the site with opened neighbours
            if (valid(row, col+1)) {
                rightNeighbourIndex = getSiteIndex(row, col+1);
                if (sites[rightNeighbourIndex].isOpen())
                    quickUnionUF.union(rightNeighbourIndex, currentSiteIndex);
            }
            if (valid(row, col-1)) {
                leftNeighbourIndex = getSiteIndex(row, col-1);
                if (sites[leftNeighbourIndex].isOpen())
                    quickUnionUF.union(leftNeighbourIndex, currentSiteIndex);
            }
            if (valid(row-1, col)) {
                upperNeighbourIndex = getSiteIndex(row-1, col);
                if (sites[upperNeighbourIndex].isOpen())
                    quickUnionUF.union(upperNeighbourIndex, currentSiteIndex);
            }
            if (valid(row+1, col)) {
                lowerNeighbourIndex = getSiteIndex(row+1, col);
                if(sites[lowerNeighbourIndex].isOpen())
                    quickUnionUF.union(lowerNeighbourIndex, currentSiteIndex);                
            }
            if (row == numColumns && isFull(row, col))
                percolates = true;
        }
    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!valid(row, col))
            throw new IllegalArgumentException();
        return sites[getSiteIndex(row, col)].isOpen();
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!valid(row, col))
            throw new IllegalArgumentException();

        if (sites[getSiteIndex(row, col)].isOpen())
            return quickUnionUF.find(getSiteIndex(row, col)) == quickUnionUF.find(getSiteIndex(1, 1));

        return false;        
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    private int getSiteIndex(int row, int col) {
        return (numColumns * col + row) - numColumns-1;
    }

    private boolean valid(int row, int col) {
        if (row > 0 && col > 0 && row <= numColumns && col <= numColumns)
            return true;
        return false;
    }   
    
    // test client (optional)
    public static void main(String[] args) {
        
        Percolation p = new Percolation(1);
        p.open(1, 1);
        //p.open(1, 2);
        //p.open(2, 2);
       // p.open(3, 2);
        
        if (p.isFull(1, 1))
            System.out.println("TEST OK");
        else
            System.out.println("TEST FAILED");
    }

    private void readFile(String path) {
        Path file = FileSystems.getDefault().getPath("test_inputs", path);
        FileReader reader = new FileReader(file.toString());
        try{
            
        }
    }
}

class Site {

    private final int iD;
    private boolean isOpen;
    
    public Site(int id) {
        this.iD = id;
        isOpen = false;
    }

    public int getID() { return iD; }

    public void open() {
        this.isOpen = true;
    }

    public boolean isOpen() { return isOpen; }
       
}
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/*
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.LinkedList;
import java.io.*;
*/

public class Percolation {
    
    private final WeightedQuickUnionUF quickUnionUF;
    private final int numColumns;
    private final boolean[] sites;
    private int openSitesNum;
    private final int virtualTopIndex;
    private final int virtualBottomIndex;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
            
        int numSites = n*n + 2; // +2 - virtual top and bottom site
        this.numColumns = n;
        this.openSitesNum = 0;
        this.quickUnionUF = new WeightedQuickUnionUF(numSites);
        this.sites = new boolean[numSites];
        this.virtualTopIndex = sites.length - 2;
        this.virtualBottomIndex = sites.length - 1;
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!valid(row, col))
            throw new IllegalArgumentException();
    
        int currentSiteIndex = getSiteIndex(row, col);
        int leftNeighbourIndex = getSiteIndex(row, col-1);
        int rightNeighbourIndex = getSiteIndex(row, col+1);
        int upperNeighbourIndex = getSiteIndex(row-1, col);
        int lowerNeighbourIndex = getSiteIndex(row+1, col);

        if (!sites[currentSiteIndex]) {
            sites[currentSiteIndex] = true;
            openSitesNum++;
            // connect to virtual top site if row = 1
            if (row == 1)
                quickUnionUF.union(virtualTopIndex, currentSiteIndex);
            
            // connect to virtual bottom site if row = numColumns
            if (row == numColumns)
                quickUnionUF.union(virtualBottomIndex, currentSiteIndex);
            
            if (valid(row, col+1) && isOpen(row, col+1))
                    quickUnionUF.union(rightNeighbourIndex, currentSiteIndex);
            if (valid(row, col-1) && isOpen(row, col-1))
                    quickUnionUF.union(leftNeighbourIndex, currentSiteIndex);
            if (valid(row-1, col) && isOpen(row-1, col))
                    quickUnionUF.union(upperNeighbourIndex, currentSiteIndex);
            if (valid(row+1, col) && isOpen(row+1, col))
                    quickUnionUF.union(lowerNeighbourIndex, currentSiteIndex);
        }
    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!valid(row, col))
            throw new IllegalArgumentException();
        return sites[getSiteIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!valid(row, col))
            throw new IllegalArgumentException();

        // check if connected to virtual top site
        if (quickUnionUF.find(getSiteIndex(row, col)) == quickUnionUF.find(virtualTopIndex))
            return true;

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.find(virtualBottomIndex) == quickUnionUF.find(virtualTopIndex);
    }

    private int getSiteIndex(int row, int col) {
        if (!valid(row, col))
            return -1;
        return (numColumns * col + row) - numColumns-1;
    }

    private boolean valid(int row, int col) {
        if (row > 0 && col > 0 && row <= numColumns && col <= numColumns)
            return true;
        return false;
    }   
    
    // test client (optional)
    public static void main(String[] args) {
        /*
        Percolation p = new Percolation(2);
        
        p.open(1, 1);
        // p.open(1, 2);
        p.open(2, 2);
        // p.open(2, 2);
        // p.open(3, 2);               
        if (p.percolates())
            System.out.println("TEST OK");
        else
            System.out.println("TEST FAILED");
        
        LinkedList<String[]> inputList = getTestInputFromFile(args[0]);        
        if (inputList.size() > 0){
            Percolation p = new Percolation(Integer.parseInt(inputList.getFirst()[0]));
            inputList.removeFirst();
            for (String[] site : inputList) {
                p.open(Integer.parseInt(site[0]), Integer.parseInt(site[1]));
            }
            System.out.println("Percolates: "+p.percolates());
        } 
        */
    }
/*
    private static LinkedList<String[]> getTestInputFromFile(String path) {
        Path inputFile = Paths.get(path);
        String line;
        LinkedList<String[]> res = new LinkedList<String[]>();
        try(BufferedReader reader = Files.newBufferedReader(inputFile, Charset.defaultCharset())) {
            res.add(new String[]{reader.readLine(), ""});            
            while(!isNullOrEmpty(line = reader.readLine())) {                
                String[] coordsFromFile = line.trim().split("\\s");
                String[] trimmedCoords = new String[2];
                int i=0;
                for (String coord : coordsFromFile) {
                    if (!coord.equals(""))
                        trimmedCoords[i++] = coord;                    
                }
                res.add(trimmedCoords);
            }
        }
        catch (IOException ioex) {
            System.out.println("Error reading file "+path+": "+ioex.getMessage());
        }
        catch (Exception ex) {
            System.out.println("Error reading file" + ex.getMessage());
        }
        return res;
    }

    private static boolean isNullOrEmpty(String elstring) {
        if (elstring != null && !elstring.isEmpty())
            return false;
        return true;
    }
*/
}
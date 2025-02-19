package eski;

/* TwoScans_TODO.java
   ==================
This draft source file does not compile (12 error messages) because
the definition of class Arr2D is missing. But it contains standard
testing protocol I am going to use when assessing your submissions. 
For illustration purposes double scanning RBCS_LUSH is shown in the 
final comment. After correctly defining Arr2D class with all required 
methods you can run the program,compare output with expected printout 
for individual scan codes, and if satisfied - submit.  

!! Modify this source file and name it "TwoScans_yourSurname.java";
!! name also the class accordingly. I will only accept correctly
!! Final comment contains sample of expected program behavior.    
*/
import static java.lang.System.*; // Instead out.... we can write out....
import java.util.Random;
import java.util.Scanner;

public class TwoScans_eski { // RBCS_LUSH_demo

  public static void main(String[] args) {

    // TODO1: identify yourself in the start message of the program:
    out.println("Program by: Ahmet Hakan Kabacili, ID: 47466, labGroup: L12");
    out.println("Scan codes from SID: <RUSZ>_<LBCH>\n");
    // Here give a short message about what the program does

    Scanner scn = new Scanner(in);
    while (true) { // Conversation
      int m, n, r;// m: rows, n: columns, r: range
      out.print("Enter: m n r = ");
      m = scn.nextInt();
      n = scn.nextInt();
      r = scn.nextInt();
      if (m <= 0 || n <= 0 || r <= 0)
        break;

      Arr2D a0 = new Arr2D(m, n);
      Arr2D a1 = new Arr2D(m, n);
      Arr2D a2 = new Arr2D(m, n);
      Arr2D a3;

      a0.generate(r);
      out.println("a0: random array");
      a0.indexedPrint();

      a1.scanRUSZ(); // Replace RBCS with your first scan code <xyft1>
      out.println("a1: scanRUSZ()");
      a1.indexedPrint();

      a2.scanLBCH(); // Replace LUSH with your cecond scan code <xyft2>
      out.println("a2: scanLBCH()");
      a2.indexedPrint();

      a3 = a1.add(a2);
      out.println("a3 = a1.add(a2)");
      a3.indexedPrint();

      out.println("a3.transpose()");
      a3.transpose().indexedPrint();
    }
    scn.close();
    out.println("\nEnd of program");
  } // main()
} // TwoScans_TODO (example scans: RBCS_LUSH)

class Arr2D {
  // TODO2: define Arr2D class conformant to given specification
  private int[][] array;
  private int rows, cols;

  public Arr2D(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.array = new int[rows][cols];
  }

  public void generate(int r) {
    Random rand = new Random();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        array[i][j] = rand.nextInt(2 * r) - r;
      }
    }
  }

  public void scanRUSZ() {
    int count = 1;

    // First pass: Fill starting from the top-right corner going downwards
    for (int toplam = 0; toplam < rows + cols - 1; toplam++) {
        for (int sutun = cols - 1; sutun >= 0; sutun--) {
            int satir = toplam - (cols - 1 - sutun);
            if (satir >= 0 && satir < rows) {
                array[satir][sutun] = count++;
            }
        }
    }

    // Second pass: Fill remaining elements in row-major order
    for (int satir = 0; satir < rows; satir++) {
        for (int sutun = 0; sutun < cols; sutun++) {
            if (array[satir][sutun] == 0) {
                array[satir][sutun] = count++;
            }
        }
    }
}

  public void scanLBCH() {
    int value = 1;

    // Matrisi doldur (sol alttan başlayarak yukarı doğru)
    for (int j = 0; j < rows; j++) {
        for (int i = cols-1; i >= 0; i--) {
            array[i][j] = value;
            value++;
        }
    }
  }

  public void indexedPrint() {
    out.print("  ");
    for (int j = 0; j < cols; j++) {
      out.printf(" %3d", j);
    }
    out.println();
    out.print(" +");
    for (int j = 0; j < cols; j++) {
      out.print("----");
    }
    out.println();
    for (int i = 0; i < rows; i++) {
      out.printf("%2d|", i);
      for (int j = 0; j < cols; j++) {
        out.printf(" %3d", array[i][j]);
      }
      out.println();
    }
  }

  public Arr2D add(Arr2D arr) {
    if (this.rows != arr.rows || this.cols != arr.cols) {
      throw new IllegalArgumentException("Array dimensions must match for addition.");
    }
    Arr2D result = new Arr2D(this.rows, this.cols);
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        result.array[i][j] = this.array[i][j] + arr.array[i][j];
      }
    }
    return result;
  }

  public Arr2D transpose() {
    Arr2D result = new Arr2D(this.cols, this.rows);
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        result.array[j][i] = this.array[i][j];
      }
    }
    return result;
  }
}

/*
 * Program by: yourFullName, ID: yourSID, labGroup: L?
 * Scan codes from SID: <xyft1>_<xyft2>
 * ...[initial message about program]...
 * 
 * Enter: m n r = 4 5 99
 * a0: random array
 * 0 1 2 3 4
 * +------------------
 * 0|-23 -18 11 -77 46
 * 1|-50 -90 -6 1 -9
 * 2| 32 45 17 83 -39
 * 3| 92 -48 56 17 -44
 * 
 * a1: scanRBCS()
 * 0 1 2 3 4
 * +-------------
 * 0| 8 7 6 5 4
 * 1| 9 18 17 16 3
 * 2|10 19 20 15 2
 * 3|11 12 13 14 1
 * 
 * a2: scanLUSH()
 * 0 1 2 3 4
 * +--------------
 * 0| 1 2 4 7 11
 * 1| 3 5 8 12 15
 * 2| 6 9 13 16 18
 * 3|10 14 17 19 20
 * 
 * a3 = a1.add(a2)
 * 0 1 2 3 4
 * +--------------
 * 0| 9 9 10 12 15
 * 1|12 23 25 28 18
 * 2|16 28 33 31 20
 * 3|21 26 30 33 21
 * 
 * a3.transpose()
 * 0 1 2 3
 * +-----------
 * 0| 9 12 16 21
 * 1| 9 23 28 26
 * 2|10 25 33 30
 * 3|12 28 31 33
 * 4|15 18 20 21
 * 
 * Enter: m n r = 3 3 999
 * a0: random array
 * 0 1 2
 * +-------------
 * 0|584 677 -212
 * 1|434 -547 -358
 * 2|776 -589 836
 * 
 * a1: scanRBCS()
 * 0 1 2
 * +-----
 * 0|5 4 3
 * 1|6 9 2
 * 2|7 8 1
 * 
 * a2: scanLUSH()
 * 0 1 2
 * +-----
 * 0|1 2 4
 * 1|3 5 7
 * 2|6 8 9
 * 
 * a3 = a1.add(a2)
 * 0 1 2
 * +--------
 * 0| 6 6 7
 * 1| 9 14 9
 * 2|13 16 10
 * 
 * a3.transpose()
 * 0 1 2
 * +-------
 * 0|6 9 13
 * 1|6 14 16
 * 2|7 9 10
 * 
 * Enter: m n r = 0 0 0
 * 
 * End of program
 */
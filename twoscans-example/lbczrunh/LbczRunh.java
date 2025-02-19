package lbczrunh;

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

public class LbczRunh { 

  public static void main(String[] args) {

    // TODO1: identify yourself in the start message of the program:
    out.println("Program by: Ahmet Hakan Kabacili, ID: 47466, labGroup: L12");
    out.println("Scan codes from SID: <LBCZ>_<RUNH>\n");
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

      a1.scanLBCZ(); // Replace RBCS with your first scan code <xyft1>
      out.println("a1: scanLBCZ()");
      a1.indexedPrint();

      a2.scanRUNH(); // Replace LUSH with your cecond scan code <xyft2>
      out.println("a2: scanRUNH()");
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

  public void scanLBCZ() {
    int value = 1;
    for (int j = 0; j < cols; j++) {
      if (j % 2 == 0) {
        for (int i = rows - 1; i >= 0; i--) {
          array[i][j] = value++;
        }
      } else {
        for (int i = 0; i < rows; i++) {
          array[i][j] = value++;
        }
      }
    }
  }

  public void scanRUNH() {
    int count = 1;
    for (int toplam = 0; toplam < rows + cols - 1; toplam++) {
      for (int sutun = cols - 1; sutun >= 0; sutun--) {
        int satir = toplam - (cols - 1 - sutun);
        if (satir >= 0 && satir < rows) {
          array[satir][sutun] = count++;
        }
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
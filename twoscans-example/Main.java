public class Main {
    public static void main(String[] args) {
        // İlk matris
        int[][] matrix = {
            {9, 4, 3, 1},
            {10, 8, 5, 2},
            {12, 11, 7, 6}
        };

        // Yeniden düzenlenmiş matris
        int[][] newMatrix = new int[3][4];

        int rowCount = 3;
        int colCount = 4;
        int num = 1;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                newMatrix[i][j] = num++;
            }
        }

        int i = 0, j = 0;
        num = 1;

        while (num <= 12) {
            if (i < rowCount && j < colCount && newMatrix[i][j] == num) {
                newMatrix[i][j] = matrix[i][j];
                num++;
                if (j < colCount - 1) {
                    j++;
                } else {
                    i++;
                    j = 0;
                }
            } else {
                j++;
                if (j >= colCount) {
                    j = 0;
                    i++;
                }
            }
        }

        // Yeniden düzenlenmiş matrisi yazdır
        for (i = 0; i < rowCount; i++) {
            for (j = 0; j < colCount; j++) {
                System.out.print(newMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}

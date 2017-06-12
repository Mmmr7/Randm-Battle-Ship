/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship3;

/**
 *
 * @author Asus
 */
public class BattleShip3 implements IBattleShip{
    // размер поля 
    static final int F_SIZE = 10;
    // -1 - нет игры
    //  0 - игра запущена 
    //  1 - игрок выиграл 
    //  2 - выиграл компьютер 
    int gameStatus = -1;
    // логическая переменная - кто ходит в данный момент
    boolean computerStep;
    // поле игрока     (данные игрока)
    int[][] p = new int[F_SIZE][F_SIZE];
    // поле компьютера (данные компьютера)
    int[][] c = new int[F_SIZE][F_SIZE];
    
    public void printArr(int[][] arr) {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.print(arr[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }

    }
    
    @Override
    public void start() {
        //
        System.out.println("START GAME!!!");
        gameStatus = 0;
        computerStep = false;
        //очисттить массивы masPlay и masComp
        //присвоить всем элементам массива значение 0 
        for (int i = 0; i < F_SIZE; i++) {
            for (int j = 0; j < F_SIZE; j++) {
                p[i][j] = 0;
                c[i][j] = 0;
            }
        }
        //
        makePlayerShips();
        makeComputerShips();
    }

    @Override
    public void shot(int i, int j) {
         //
        System.out.println("SHOT_i=" + i + "_j=" + j);
        c[i][j] += 7;
        testUbit(c, i, j);
        testEndGame();
        if (c[i][j] < 8) {
            computerStep = true;
            while(computerStep) {
                  computerStep = compHodit();
            }
        }
    }
    
    private void testEndGame() {
        int testNumber = 330;
        int kolComp = 0;
        int kolPlay = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (p[i][j] >= 15) {
                    kolPlay += p[i][j];
                }
                if (c[i][j] >= 15) {
                    kolComp += c[i][j];
                }
            }
        }
        if (kolPlay == testNumber) {
            gameStatus = 2;
        } else if (kolComp == testNumber) {
            gameStatus = 1;
        }
    }

    @Override
    public int[][] player() {
        return p; 
    }

    @Override
    public int[][] computer() {
        return c;
    }

    @Override
    public boolean gameStarted() {
        return gameStatus == 0;
    }

    @Override
    public boolean computerStep() {
        return computerStep;
    }

    @Override
    public boolean playerWin() {
        return gameStatus == 1;
    }

    @Override
    public boolean computerWin() {
        return gameStatus == 2;
    }
    
    // methods 
    void makePlayerShips() {
        makeShips(p);
    }
    //
    void makeComputerShips() {
        makeShips(c);
    }
    
    static void makeShips(int[][] arr) {
        // создаем 1 4-х палубный корабль
        makeShip(arr, 4);
        // создаем 2 3-х палубных корабль
        for (int i = 0; i < 2; i++) {
            makeShip(arr, 3);
        }
        // создаем 3 2-х палубных корабль
        for (int i = 0; i < 3; i++) {
            makeShip(arr, 2);
        }
        // создаем 4 1-х палубных корабль
        for (int i = 0; i < 4; i++) {
            makeShip(arr, 1);
        }
    }
    
    static void makeShip(int[][] arr, int numPalub) {
        //
        if (numPalub == 1) {
            //for (int k = 1; k <= 4; k++) {
            int i;
            int j;
            do {
                i = (int) (Math.random() * 10.0D);
                j = (int) (Math.random() * 10.0D);
            } while (arr[i][j] != 0);
            arr[i][j] = 1;

            okrBegin(arr, i, j, -1);
            //}
        } else {
            boolean flag;
            int i;
            int j;
            int napr;
            do {
                flag = false;
                i = (int) (Math.random() * 10.0D);
                j = (int) (Math.random() * 10.0D);
                napr = (int) (Math.random() * 4.0D);
                if (testNewPaluba(arr, i, j)) {
                    if (napr == 0) {
                        if (testNewPaluba(arr, i - (numPalub - 1), j)) {
                            flag = true;
                        }
                    } else if (napr == 1) {
                        if (testNewPaluba(arr, i, j + (numPalub - 1))) {
                            flag = true;
                        }
                    } else if (napr == 2) {
                        if (testNewPaluba(arr, i + (numPalub - 1), j)) {
                            flag = true;
                        }
                    } else if (napr == 3) {
                        if (testNewPaluba(arr, i, j - (numPalub - 1))) {
                            flag = true;
                        }
                    }
                }
            } while (!flag);
            arr[i][j] = numPalub;
            okrBegin(arr, i, j, -2);
            if (napr == 0) {
                for (int k = numPalub - 1; k >= 1; k--) {
                    arr[(i - k)][j] = numPalub;

                    okrBegin(arr, i - k, j, -2);
                }
            } else if (napr == 1) {
                for (int k = numPalub - 1; k >= 1; k--) {
                    arr[i][(j + k)] = numPalub;

                    okrBegin(arr, i, j + k, -2);
                }
            } else if (napr == 2) {
                for (int k = numPalub - 1; k >= 1; k--) {
                    arr[(i + k)][j] = numPalub;

                    okrBegin(arr, i + k, j, -2);
                }
            } else if (napr == 3) {
                for (int k = numPalub - 1; k >= 1; k--) {
                    arr[i][(j - k)] = numPalub;

                    okrBegin(arr, i, j - k, -2);
                }
            }
            okrEnd(arr);
        }
        
    }
    
     private boolean compHodit() {
        boolean rez = false;

        boolean flag = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((p[i][j] >= 9) && (p[i][j] <= 11)) {
                    flag = true;
                    if ((testMasPoz(i - 1, j)) && (p[(i - 1)][j] <= 4) && (p[(i - 1)][j] != -2)) {
                        p[(i - 1)][j] += 7;

                        testUbit(p, i - 1, j);
                        if (p[(i - 1)][j] < 8) {
                            break;
                        }
                        rez = true;

                        break;
                    }
                    if ((testMasPoz(i + 1, j)) && (p[(i + 1)][j] <= 4) && (p[(i + 1)][j] != -2)) {
                        p[(i + 1)][j] += 7;

                        testUbit(p, i + 1, j);
                        if (p[(i + 1)][j] < 8) {
                            break;
                        }
                        rez = true;

                        break;
                    }
                    if ((testMasPoz(i, j - 1)) && (p[i][(j - 1)] <= 4) && (p[i][(j - 1)] != -2)) {
                        p[i][(j - 1)] += 7;

                        testUbit(p, i, j - 1);
                        if (p[i][(j - 1)] < 8) {
                            break;
                        }
                        rez = true;

                        break;
                    }
                    if ((testMasPoz(i, j + 1)) && (p[i][(j + 1)] <= 4) && (p[i][(j + 1)] != -2)) {
                        p[i][(j + 1)] += 7;

                        testUbit(p, i, j + 1);
                        if (p[i][(j + 1)] < 8) {
                            break;
                        }
                        rez = true;

                        break;
                    }
                }
            }
        }
        if (!flag) {
            for (int l = 1; l <= 100; l++) {
                int i = (int) (Math.random() * 10.0D);
                int j = (int) (Math.random() * 10.0D);
                if ((p[i][j] <= 4) && (p[i][j] != -2)) {
                    p[i][j] += 7;

                    testUbit(p, i, j);
                    if (p[i][j] >= 8) {
                        rez = true;
                    }
                    flag = true;

                    break;
                }
            }
            if (!flag) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if ((p[i][j] <= 4) && (p[i][j] != -2)) {
                            p[i][j] += 7;

                            testUbit(p, i, j);
                            if (p[i][j] < 8) {
                                break;
                            }
                            rez = true;

                            break;
                        }
                    }
                }
            }
        }
        testEndGame();

        return rez;
    }
    
    
    private static void testUbit(int[][] mas, int i, int j) {
        if (mas[i][j] == 8) {
            mas[i][j] += 7;
            okrPodbit(mas, i, j);
        } else if (mas[i][j] == 9) {
            analizUbit(mas, i, j, 2);
        } else if (mas[i][j] == 10) {
            analizUbit(mas, i, j, 3);
        } else if (mas[i][j] == 11) {
            analizUbit(mas, i, j, 4);
        }
    }

    private static void okrPodbit(int[][] mas, int i, int j) {
        setOkrPodbit(mas, i - 1, j - 1);
        setOkrPodbit(mas, i - 1, j);
        setOkrPodbit(mas, i - 1, j + 1);
        setOkrPodbit(mas, i, j + 1);
        setOkrPodbit(mas, i + 1, j + 1);
        setOkrPodbit(mas, i + 1, j);
        setOkrPodbit(mas, i + 1, j - 1);
        setOkrPodbit(mas, i, j - 1);
    }

    private static void setOkrPodbit(int[][] mas, int i, int j) {
        if (testMasPoz(i, j)) {
            if ((mas[i][j] == -1) || (mas[i][j] == 6)) {
                mas[i][j] -= 1;
            }
        }
    }

    private static void analizUbit(int[][] mas, int i, int j, int kolPalub) {
        int kolRanen = 0;
        for (int k = i - (kolPalub - 1); k <= i + (kolPalub - 1); k++) {
            for (int g = j - (kolPalub - 1); g <= j + (kolPalub - 1); g++) {
                if ((testMasPoz(k, g)) && (mas[k][g] == kolPalub + 7)) {
                    kolRanen++;
                }
            }
        }
        if (kolRanen == kolPalub) {
            for (int k = i - (kolPalub - 1); k <= i + (kolPalub - 1); k++) {
                for (int g = j - (kolPalub - 1); g <= j + (kolPalub - 1); g++) {
                    if ((testMasPoz(k, g)) && (mas[k][g] == kolPalub + 7)) {
                        mas[k][g] += 7;

                        okrPodbit(mas, k, g);
                    }
                }
            }
        }
    }
    
    
    static boolean testNewPaluba(int[][] mas, int i, int j) {
        if (!testMasPoz(i, j)) {
            return false;
        }
        return (mas[i][j] == 0) || (mas[i][j] == -2);
    }

    static void okrEnd(int[][] mas) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mas[i][j] == -2) {
                    mas[i][j] = -1;
                }
            }
        }
    }
    //
    static void okrBegin(int[][] mas, int i, int j, int val) {
        setOkr(mas, i - 1, j - 1, val);
        setOkr(mas, i - 1, j, val);
        setOkr(mas, i - 1, j + 1, val);
        setOkr(mas, i, j + 1, val);
        setOkr(mas, i + 1, j + 1, val);
        setOkr(mas, i + 1, j, val);
        setOkr(mas, i + 1, j - 1, val);
        setOkr(mas, i, j - 1, val);
    }

    private static void setOkr(int[][] mas, int i, int j, int val) {
        if ((testMasPoz(i, j)) && (mas[i][j] == 0)) {
            setMasValue(mas, i, j, val);
        }
    }

    private static boolean testMasPoz(int i, int j) {
        return (i >= 0) && (i <= 9) && (j >= 0) && (j <= 9);
    }

    private static void setMasValue(int[][] mas, int i, int j, int val) {
        if (testMasPoz(i, j)) {
            mas[i][j] = val;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Okno okno = new Okno();
    }
}

package mis.mygamejavafx;

import java.util.*;

public class GameSave {

    private int[][] save = new int[10][10];
    private int mid_X = 4;
    private int Win_need_scores;

    public GameSave() {

    }

    public Map GameSaveMaker(int X, int Long) {

        Map<Integer, int[]> Re_Dict = new HashMap<>();
        int count = 0;
        int GoWhere = mid_X - X + Long;
        for (int i = mid_X - X; i <= GoWhere; i++) {
            for (int j = mid_X - X; j <= GoWhere; j++) {
                this.save[i][j] = 1;
                Re_Dict.put(count, new int[]{i, j});
                count++;
            }
        }
        this.Win_need_scores = count * 2;
        System.out.printf("新遊戲已建立，本局獲勝需 %d 分%n", count * 2);
        return Re_Dict;
    }

    public void GameSave_printer() {
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                System.out.print(this.save[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public Map Changer(int X, int Y) {
        Map<Integer, int[]> Re_Dict = new HashMap<>();
        int count = 0;
        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;
        int self = 0;
        //下面是破解邊界問題的最簡單方法~~~
        try {
            up = this.save[X][Y - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            down = this.save[X][Y + 1];
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            left = this.save[X - 1][Y];
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            right = this.save[X + 1][Y];
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            self = this.save[X][Y];
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        if (self != 0) {
            if (self == 2) {
                this.save[X][Y] = 1;
                Re_Dict.put(count, new int[]{X, Y, 1});
                count++;
            } else {
                this.save[X][Y] = 2;
                Re_Dict.put(count, new int[]{X, Y, 2});
                count++;
            }
        }
        if (up != 0) {
            if (up == 2) {
                this.save[X][Y - 1] = 1;
                Re_Dict.put(count, new int[]{X, Y - 1, 1});
                count++;
            } else {
                this.save[X][Y - 1] = 2;
                Re_Dict.put(count, new int[]{X, Y - 1, 2});
                count++;
            }
        }
        if (down != 0) {
            if (down == 2) {
                this.save[X][Y + 1] = 1;
                Re_Dict.put(count, new int[]{X, Y + 1, 1});
                count++;
            } else {
                this.save[X][Y + 1] = 2;
                Re_Dict.put(count, new int[]{X, Y + 1, 2});
                count++;
            }
        }
        if (left != 0) {
            if (left == 2) {
                this.save[X - 1][Y] = 1;
                Re_Dict.put(count, new int[]{X - 1, Y, 1});
                count++;
            } else {
                this.save[X - 1][Y] = 2;
                Re_Dict.put(count, new int[]{X - 1, Y, 2});
                count++;
            }
        }
        if (right != 0) {
            if (right == 2) {
                this.save[X + 1][Y] = 1;
                Re_Dict.put(count, new int[]{X + 1, Y, 1});
                count++;
            } else {
                this.save[X + 1][Y] = 2;
                Re_Dict.put(count, new int[]{X + 1, Y, 2});
                count++;
            }
        }

        return Re_Dict;
    }

    public boolean checkWin() {
        int counter = 0;
        for (int[] i : this.save) {
            for (int j : i) {
                counter += j;
            }
        }
        if (counter == this.Win_need_scores) {
            return true;
        } else {
            return false;
        }

    }

    public void Save_reset() {
        this.save = new int[10][10];
        System.out.printf("存檔已重製%n");
    }

    public static void main(String[] args) {
        int size = 5;

        GameSave test = new GameSave();
        switch (size) {
            case 3:
                test.GameSaveMaker(1, 2);
                break;
            case 4:
                test.GameSaveMaker(1, 3);
                break;
            case 5:
                test.GameSaveMaker(2, 4);
                break;
            case 6:
                test.GameSaveMaker(2, 5);
                break;
            case 7:
                test.GameSaveMaker(3, 6);
                break;
            case 8:
                test.GameSaveMaker(3, 7);
                break;
            case 9:
                test.GameSaveMaker(4, 8);
                break;
            case 10:
                test.GameSaveMaker(4, 9);
                break;
        }
        test.GameSave_printer();
        System.out.println();
        test.Changer(2, 2);
        test.GameSave_printer();
        System.out.println();
        test.Changer(2, 3);
        test.GameSave_printer();

    }
}

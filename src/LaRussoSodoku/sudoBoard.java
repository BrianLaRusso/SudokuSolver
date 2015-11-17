/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LaRussoSodoku;

import java.util.Random;
import java.util.TreeSet;

/**
 *
 * @author Nikon
 */
public class sudoBoard
{

    int[][] board = new int[9][9];
    int[][] solvedBoard = new int[9][9];

    //given a string this constructor makes a sodoku obj
    public sudoBoard(String Board)
    {
        int h = 0;
        String temp;

        //for testing
        for (int i = 0; i < 9; i++)
        {

            for (int j = 0; j < 9; j++)
            {
                temp = "" + Board.charAt(h++);
                board[i][j] = Integer.parseInt(temp);
            }

        }

    }

    public sudoBoard(int[][] temp)
    {
        board = (int[][]) temp.clone();
    }

    public boolean isSolved()
    {
        Integer temp;

        TreeSet<Integer> tempTree = new TreeSet();

        //check each row
        for (int i = 0; i < 9; i++)
        {

            if (this.rowSolve(i) == false)
            {
                return false;
            }

        }
        //*/

        //check every col
        for (int i = 0; i < 9; i++)
        {
            if (this.colSolve(i) == false)
            {
                return false;
            }
        }
        //*/

        //check every 3 x 3 box
        for (int i = 0; i < 9; i += 3)
        {

            for (int j = 0; j < 9; j += 3)
            {

                if (this.boxSolve(i, j) == false)
                {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean colSolve(int colNum)
    {
        Integer temp;
        TreeSet<Integer> tempTree = new TreeSet();

        for (int j = 0; j < 9; j++)
        {

            temp = board[j][colNum];

            tempTree.add(temp);

        }

        for (int k = 1; k < 10; k++)
        {
            Integer intK = k;

            try
            {
                if (tempTree.contains(intK) != true)
                {
                    //System.out.println(" col not valid");
                    return false;
                }
            } catch (NullPointerException e)
            {
                System.out.println("something is messed up");
            }

        }

        //check each row
        return true;
    }

    private boolean rowSolve(int rowNum)
    {
        Integer temp;
        TreeSet<Integer> tempTree = new TreeSet();

        for (int j = 0; j < 9; j++)
        {

            temp = board[rowNum][j];

            tempTree.add(temp);

        }

        //check tree the represents row
        for (int k = 1; k < 10; k++)
        {
            Integer intK = k;

            try
            {
                if (tempTree.contains(intK) != true)
                {
                    // System.out.println("row not correct");
                    return false;
                }
            } catch (NullPointerException e)
            {
                System.out.println("something is messed up");
            }

        }

        return true;
    }

    private boolean boxSolve(int boxCornerCol, int boxCornerRow)
    {
        Integer temp;
        TreeSet<Integer> tempTree = new TreeSet();

        //System.out.println("box " + boxCornerRow + " " + boxCornerCol);
        for (int i = boxCornerCol; i < boxCornerCol + 3; i++)
        {
            for (int j = boxCornerRow; j < boxCornerRow + 3; j++)
            {

                temp = board[j][i];

                tempTree.add(temp);

            }

        }

        //check tree the represents box
        for (int k = 1; k < 10; k++)
        {
            Integer intK = k;

            try
            {
                if (tempTree.contains(intK) != true)
                {
                    //System.out.println("box not correct" + "missing" + intK);
                    return false;
                }
            } catch (NullPointerException e)
            {
                System.out.println("something is messed up");
            }

        }

        return true;
    }

    public void bruteSolve()
    {
        Random ran = new Random();
        int[][] tempBoard = this.copyOfBoard();
        sudoBoard temp;

        temp = new sudoBoard(tempBoard);

        while (temp.isSolved() == false)
        {
            ran = new Random();
            tempBoard = this.copyOfBoard();

            for (int i = 0; i < 9; i++)
            {

                for (int j = 0; j < 9; j++)
                {
                    if (tempBoard[i][j] == 0)
                    {
                        while (temp.colSolve(j) && temp.rowSolve(i))
                        {
                            tempBoard[i][j] = ran.nextInt(10);
                            temp = new sudoBoard(tempBoard);
                        }
                    }
                }
            }

            temp = new sudoBoard(tempBoard);
        }

        if (temp.isSolved())
        {
            System.out.printf(temp.toString());
        }

    }

    public void smartSolve()
    {
        solvedBoard = this.copyOfBoard();

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (board[i][j] == 0)
                {
                    if (this.attemptNumber(i, j))
                    {

                    }
                    else
                    {
                        System.out.println("you alg sucks");
                    }
                }
            }
        }

        board = solvedBoard;
        
        if (this.isSolved())
        {
          System.out.println(this);
        }
        else{
            System.out.println("did not solve");
        }

    }

    private boolean attemptNumber(int row, int col)
    {

        for (int i = 1; i < 10; i++)
        {

            if (!this.rowContains(row, i) && !this.colContains(col, i)
                    && !this.boxContains(this.whichBox(col, row), i))
            {
                solvedBoard[row][col] = i;
                return true;
            }

        }
        return false;

    }

    public boolean rowContains(int rowNumber, int valueToCheck)
    {

        TreeSet<Integer> temp = new TreeSet();

        for (int i = 0; i < 9; i++)
        {
            temp.add(board[rowNumber][i]);

        }

        return temp.contains(valueToCheck);

    }

    public boolean colContains(int colNumber, int valueToCheck)
    {

        TreeSet<Integer> temp = new TreeSet();

        for (int i = 0; i < 9; i++)
        {
            temp.add(board[i][colNumber]);

        }

        return temp.contains(valueToCheck);

    }

    public boolean boxContains(int boxNumber, int valueToCheck)
    {
        int colLow = 100;
        int colHigh = 100;
        int rowLow = 100;
        int rowHigh = 100;

        if (boxNumber == 0 || boxNumber == 1 || boxNumber == 2)
        {
            rowLow = 0;
            rowHigh = 2;
        }

        if (boxNumber == 3 || boxNumber == 4 || boxNumber == 5)
        {
            rowLow = 3;
            rowHigh = 5;
        }

        if (boxNumber == 6 || boxNumber == 7 || boxNumber == 8)
        {
            rowLow = 6;
            rowHigh = 8;
        }

        if (boxNumber == 0 || boxNumber == 3 || boxNumber == 6)
        {
            colLow = 0;
            colHigh = 2;
        }

        if (boxNumber == 1 || boxNumber == 4 || boxNumber == 7)
        {
            colLow = 3;
            colHigh = 5;
        }

        if (boxNumber == 2 || boxNumber == 5 || boxNumber == 8)
        {
            colLow = 6;
            colHigh = 8;
        }

        TreeSet<Integer> temp = new TreeSet();

        for (int i = colLow; i <= colHigh; i++)
        {
            for (int j = rowLow; j <= rowHigh; j++)
            {
                temp.add(board[j][i]);
            }

        }

        return temp.contains(valueToCheck);
    }

    private int whichBox(int row, int col)
    {

        if (col < 3 && row < 3)
        {
            return 0;
        }

        else if (2 < col && col < 6 && -1 < row && row < 3)
        {
            return 1;
        }

        else if (5 < col && col < 9 && -1 < row && row < 3)
        {
            return 2;
        }

        else if (-1 < col && col < 3 && 2 < row && row < 6)
        {
            return 3;
        }

        else if (2 < col && col < 6 && 2 < row && row < 6)
        {
            return 4;
        }

        else if (5 < col && col < 9 && 2 < row && row < 6)
        {
            return 5;
        }

        else if (-1 < col && col < 3 && 5 < row && row < 9)
        {
            return 6;
        }

        else if (2 < col && col < 6 && 5 < row && row < 9)
        {
            return 7;
        }

        else if (col > 5 && row > 5)
        {
            return 8;
        }

        else
        {
            return -1;
        }

    }

    private int[][] copyOfBoard()
    {
        /*
         int[][] tempBoard = new int[9][9];

         for (int i = 0; i < 9; i++)
         {

         for (int j = 0; j < 9; j++)
         {
         tempBoard[i][j] = board[i][j];
         }

         }
         //*/
        //return tempBoard;

        int[][] temp = (int[][]) this.board.clone();

        return temp;
    }

    public String toString()
    {
        String output = "";

        for (int i = 0; i < 9; i++)
        {
            if (i % 3 == 0)
            {
                output = output + "%n";
            }

            output = output + "%n";

            for (int j = 0; j < 9; j++)
            {
                if ((j) % 3 == 0)
                {
                    output = output + "   " + board[i][j];
                }
                else
                {
                    output = output + " " + board[i][j];
                }
            }

        }
        output = output + "%n";
        return output;
    }
}

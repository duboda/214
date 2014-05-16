/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgtry;

import java.awt.Color;
import java.util.Random;
import java.lang.Math;
import java.awt.Graphics;

public class Shape {
    
    // Tetromino type reference
    enum Tetrominoes { NoShape, ZShape, SShape, LineShape, 
               TShape, SquareShape, LShape, MirroredLShape };
    // Tetromino shape coordinates reference table
    private static final int[][][] coordsTable = new int[][][] {
            { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
            { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
            { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
            { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
            { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
            { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
            { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
            { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
        };
    
    // Tetromino type
    private Tetrominoes pieceShape;
    // Tetromino shape coordinates
    private int coords[][];
    

    /**
     * Class constructor
     */
    public Shape() {

        coords = new int[4][2];
        setShape(Tetrominoes.NoShape);

    }

    /**
     * Sets this Tetromino to specified shape by looking up reference table
     * @param shape Tetromino shape to set to
     */
    public void setShape(Tetrominoes shape) {

        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < 2; ++j) {
                coords[i][j] = coordsTable[shape.ordinal()][i][j];
            }
        }
        pieceShape = shape;

    }

    // simple getter/setters
    private void setX(int index, int x) { coords[index][0] = x; }
    private void setY(int index, int y) { coords[index][1] = y; }
    public int x(int index) { return coords[index][0]; }
    public int y(int index) { return coords[index][1]; }
    public Tetrominoes getShape()  { return pieceShape; }
    
    /**
     * Randomly sets the shape of a piece.
     */
    public void setRandomShape()
    {
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;
        Tetrominoes[] values = Tetrominoes.values(); 
        setShape(values[x]);
    }
    
    /**
     * Returns the minimum x-coordinate within the shape
     * @return minimum x-coordinate within the shape
     */
    public int minX()
    {
      int m = coords[0][0];
      for (int i=0; i < 4; i++) {
          m = Math.min(m, this.x(i));
      }
      return m;
    }

    /**
     * Returns the minimum y-coordinate within the shape
     * @return minimum y-coordinate within the shape
     */
    public int minY() 
    {
      int m = coords[0][1];
      for (int i=0; i < 4; i++) {
          m = Math.min(m, this.y(i));
      }
      return m;
    }
    
    /**
     * Sets the coordinates in the piece to simulate left rotation.
     * @return left "rotated" piece
     */
    public Shape rotateLeft() 
    {
        if (pieceShape == Tetrominoes.SquareShape)
            return this;

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, y(i));
            result.setY(i, -x(i));
        }
        return result;
    }

    /**
     * Sets the coordinates in the piece to simulate right rotation.
     * @return right "rotated" piece
     */
    public Shape rotateRight()
    {
        if (pieceShape == Tetrominoes.SquareShape)
            return this;

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, -y(i));
            result.setY(i, x(i));
        }
        return result;
    }
    
    public void draw(Graphics g, int startX, int startY){
        int squareWidth = 10;
        int squareHeight = 10;
        for (int i = 0; i < 4; ++i) {
            int x = startX + this.x(i);
            int y = startY - this.y(i);
            drawSquare(g, 0 + x * squareWidth,
                       startY + y * squareHeight,
                       this.getShape());
        }
    }
    
    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape)
    {
        Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), 
            new Color(102, 204, 102), new Color(102, 102, 204), 
            new Color(204, 204, 102), new Color(204, 102, 204), 
            new Color(102, 204, 204), new Color(218, 170, 0)
        };
        int squareWidth = 10;
        int squareHeight = 10;

        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth-1, squareHeight-1);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkgtry;


public class createpiece {
    Shape nextpiece[] = new Shape[21];
    
    public void createpiece(){
        for(int i=0; i<21;i++){
            nextpiece[i] = new Shape();
            nextpiece[i].setRandomShape();
        }
    }
    
}
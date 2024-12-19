/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.chess.engine.pieces;

import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

/**
 *
 * @author yassi
 */
public enum Alliance {
   WHITE {
       @Override
       public int getDirection() {
           return -1;
       }
       
       @Override
       public int getOppositeDirection() {
           return 1;
       }

       @Override
       public boolean isWhite() {
           return true;
//throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

       @Override
       public boolean isBlack() {
           return false;
           //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

       @Override
       public Player choosePlayer(
               final WhitePlayer whitePlayer,
               final BlackPlayer blackPlayer) {
            return whitePlayer;
           
           //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }
   },
   BLACK {
       @Override
       public int getDirection() {
           return 1;
       }
       
       @Override
       public int getOppositeDirection() {
           return 1;
       }

       @Override
       public boolean isWhite() {
           return false;
           //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

       @Override
       public boolean isBlack() {
           return true;
           //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

       @Override
       public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return blackPlayer;
        
//throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

       
   };
   
  public abstract int getDirection();
  public abstract int getOppositeDirection();
  public abstract boolean isWhite();
  public abstract boolean isBlack();

    public abstract Player choosePlayer(
            final WhitePlayer whitePlayer,
            final BlackPlayer blackPlayer);
}

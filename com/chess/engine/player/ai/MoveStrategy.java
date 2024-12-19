/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

/**
 *
 * @author yassi
 */
public interface MoveStrategy {
    Move execute(Board board);
    
}

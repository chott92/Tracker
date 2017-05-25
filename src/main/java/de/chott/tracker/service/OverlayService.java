/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.service;

import de.chott.tracker.model.Game;
import javax.ejb.Singleton;

/**
 *
 * @author cot
 */
@Singleton
public class OverlayService {
    
    private Game currentGame;
    private Game nextUpGame;

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public Game getNextUpGame() {
        return nextUpGame;
    }

    public void setNextUpGame(Game nextUpGame) {
        this.nextUpGame = nextUpGame;
    }
    
    
    
}

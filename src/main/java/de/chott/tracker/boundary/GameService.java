/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.tracker.boundary;

import de.chott.tracker.model.Event;
import de.chott.tracker.model.Game;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cot
 */
@Stateless
public class GameService {
    
    @PersistenceContext
    private EntityManager em;
    
    public Game load (long id){
        return em.find(Game.class, id);
    }
    
    public Game save (Game game){
        return em.merge(game);
    }
    
    public List<Game> loadEventSchedule(Event event){
        return em.createNamedQuery("Game.loadEventSchedule", Game.class)
                .setParameter("paramEvent", event)
                .getResultList();
    }
    
    public void saveEventSchedule(List<Game> schedule){
        for (Game game : schedule){
            save(game);
        }
    }

    public String getGameLabel(Game g) {
        if (g!=null)
        return g.getName() + " - " + g.getCategory() + " (" + g.getRunner() + ")";
        else return "";
    }
    
}

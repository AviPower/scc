/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.progress.scc.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import py.com.progress.scc.model.TipoProducto;

/**
*
* @author Carmen Alarcón (cadialgo1@gmail.com)
*/
@Stateless
public class TipoProductoFacade extends AbstractFacade<TipoProducto> {
    @PersistenceContext(unitName = "py.com.winner_scc_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoProductoFacade() {
        super(TipoProducto.class);
    }
    
}

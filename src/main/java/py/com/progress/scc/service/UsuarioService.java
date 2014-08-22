package py.com.progress.scc.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import py.com.progress.scc.model.Queries;
import py.com.progress.scc.model.Usuario;

@Stateless
public class UsuarioService extends BaseService<Usuario> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioService() {
        super(Usuario.class);
    }

    public Usuario find(String username, String password) {
        TypedQuery<Usuario> query = em.createNamedQuery(Queries.USUARIO__OBTENER_POR_PASSWORD,
                Usuario.class);
        query.setParameter(1, username);
        query.setParameter(2, password);
        List<Usuario> usuarios = query.getResultList();
        if (!usuarios.isEmpty()) {
            return usuarios.get(0);
        }
        return null;
    }

    public Usuario find(String username) {
        TypedQuery<Usuario> query = em.createNamedQuery(Queries.USUARIO__OBTENER_POR_USERNAME,
                Usuario.class);
        query.setParameter(1, username);
        List<Usuario> usuarios = query.getResultList();
        if (!usuarios.isEmpty()) {
            return usuarios.get(0);
        }
        return null;
    }

}

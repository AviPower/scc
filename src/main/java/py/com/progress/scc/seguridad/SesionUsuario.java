package py.com.progress.scc.seguridad;

import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import py.com.progress.scc.model.Usuario;

@Named
@SessionScoped
public class SesionUsuario implements Serializable {

    @Inject
    private UsuariosActivos usuariosActivos;

    private Usuario usuario;

    public Usuario getUsuario() {
	return usuario;
    }

    public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
    }

    /**
     * CDI calls this method before the bean is destroyed. Since this class is
     * session-scoped, it will get called if the user session expires, allowing
     * us to remove the user from the active user list.
     */
    @PreDestroy
    public void release() {
	usuariosActivos.remove(usuario);
    }

}

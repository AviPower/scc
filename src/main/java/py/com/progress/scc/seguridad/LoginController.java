package py.com.progress.scc.seguridad;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import py.com.progress.scc.controller.BaseController;
import py.com.progress.scc.model.Usuario;
import py.com.progress.scc.service.UsuarioService;

@Named
@RequestScoped
public class LoginController extends BaseController implements Serializable {

    @Inject
    private Credenciales credenciales;

    @Inject
    private UsuariosActivos usuariosActivos;

    @Inject
    private UsuarioService usuarioService;

    public String login() {
	String username = credenciales.getUsername();
	String password = credenciales.getPassword();
	Usuario usuario = usuarioService.find(username, password);
	if (usuario == null) {
	    Messages.addGlobalError("Usuario o password invalidos");
	    sesionUsuario.setUsuario(null);
	    return null;
	} else {
	    Messages.addGlobalInfo("Bienvenido {0}!!!", username);
	    sesionUsuario.setUsuario(usuario);
	    if (!usuariosActivos.contains(usuario)) {
		usuariosActivos.add(usuario);
	    }
	    return "protected";
	}
    }

    public String logout() {
	usuariosActivos.remove(getLoggedInUser());
	Faces.getExternalContext().invalidateSession();
	return "login";
    }

    public boolean isLoggedIn() {
	return getLoggedInUser() != null;
    }

}

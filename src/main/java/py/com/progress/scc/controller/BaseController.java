package py.com.progress.scc.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import py.com.progress.scc.model.Usuario;

import py.com.progress.scc.seguridad.SesionUsuario;

public abstract class BaseController {

    @Inject
    protected SesionUsuario sesionUsuario;

    protected Usuario getLoggedInUser() {
        Usuario usuario = null;
        if (sesionUsuario != null) {
            usuario = sesionUsuario.getUsuario();
        }
        return usuario;
    }

    protected void showMessage(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(msg));
    }

}

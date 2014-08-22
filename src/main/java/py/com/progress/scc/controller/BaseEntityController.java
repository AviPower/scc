package py.com.progress.scc.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;
import py.com.progress.scc.service.BaseService;

public abstract class BaseEntityController<T> extends BaseController {

    private BaseService<T> service;
    private Class<T> itemClass;
    private T selected;
    private List<T> items;

    private enum PersistAction {
        CREATE, DELETE, UPDATE;
    }

    public BaseEntityController() {
    }

    public BaseEntityController(Class<T> itemClass) {
        this.itemClass = itemClass;
    }

    protected BaseService<T> getService() {
        return service;
    }

    protected void setService(BaseService<T> ejbFacade) {
        this.service = ejbFacade;
    }

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }

    public List<T> getItems() {
        if (items == null) {
            items = this.service.findAll();
        }
        return items;
    }

    public T prepareCreate(ActionEvent event) {
        T newItem;
        try {
            newItem = itemClass.newInstance();
            this.selected = newItem;
            return newItem;
        } catch (InstantiationException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void save(ActionEvent event) {
        persist(PersistAction.UPDATE, "Actualizado exitosamente");
    }

    public void saveNew(ActionEvent event) {
        persist(PersistAction.CREATE, "Creado exitosamente");
        selected = null;
        items = null;
    }

    public void delete(ActionEvent event) {
        persist(PersistAction.DELETE, "Eliminado exitosamente");
        selected = null;
        items = null;
    }

    private void persist(PersistAction persistAction, String mensaje) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    this.service.edit(selected);
                } else {
                    this.service.remove(selected);
                }
                Messages.addGlobalInfo(mensaje);
            } catch (EJBException ex) {
                String error = "";
                Throwable cause = getRootCause(ex.getCause());
                if (cause != null) {
                    error = cause.getLocalizedMessage();
                }
                if (error.length() > 0) {
                    Messages.addGlobalError(error);
                } else {
                    Messages.addGlobalError("Ocurrió un error");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Throwable getRootCause(Throwable cause) {
        if (cause != null) {
            Throwable source = cause.getCause();
            if (source != null) {
                return getRootCause(source);
            } else {
                return cause;
            }
        }
        return null;
    }

}
package py.com.progress.scc.seguridad;

import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.omnifaces.util.Faces;

/**
 * PhaseListener class that handles authorization for protected views.
 * 
 * @author Carmen Alarcon (cadialgo1@gmail.com)
 * 
 */
public class SecurityPhaseListener implements PhaseListener {

    private static final long serialVersionUID = -1013239137374622294L;

    @Override
    public void afterPhase(PhaseEvent event) {
	// eagerly create session due to JAVASERVERFACES-2215
	Faces.getSession(true);
	// check if view is protected
	UIViewRoot view = Faces.getViewRoot();
	if (view != null) {
	    String viewId = view.getViewId();
	    boolean authenticationRequired = viewId.contains("/protected/");
	    boolean loggedIn = isLoggedIn();
	    if (authenticationRequired && !loggedIn) {
		// send user to login page if not logged in
		Faces.navigate("login");
	    }
	}
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
	return PhaseId.RESTORE_VIEW;
    }

    private boolean isLoggedIn() {
	LoginController controller = (LoginController) Faces
		.evaluateExpressionGet("#{loginController}");
	return controller.isLoggedIn();
    }

}

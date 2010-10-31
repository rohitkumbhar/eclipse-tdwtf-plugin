package com.tdwtf.plugin.submitwtf.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

import com.tdwtf.plugin.submitwtf.Activator;
import com.tdwtf.plugin.submitwtf.wizard.SubmissionWizard;

public class SubmitWTFAction implements IObjectActionDelegate {

	private Shell shell;

	/**
	 * Constructor for Action1.
	 */
	public SubmitWTFAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

		IWorkbenchPage page = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();

		ISelection selection = page.getSelection();
		String selectedText = null;
		if (selection != null && !selection.isEmpty() && selection instanceof TextSelection) {

			TextSelection textSelection = (TextSelection) selection;
			selectedText = textSelection.getText();

		} else if (selection != null && !selection.isEmpty() && selection instanceof TreeSelection) {
			selectedText = "Not implemented for full file contents";
		}

		launchWizard(selectedText);

	}

	private void launchWizard(String selectedText) {
		SubmissionWizard wizard = new SubmissionWizard(selectedText);
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();

	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}

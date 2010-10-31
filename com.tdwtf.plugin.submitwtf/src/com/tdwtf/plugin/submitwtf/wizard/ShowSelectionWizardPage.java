package com.tdwtf.plugin.submitwtf.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class ShowSelectionWizardPage extends WizardPage {

	public static final String SELECTION = "Selection";
	private String selectedText;
	private Text selectedTextControl;

	protected ShowSelectionWizardPage(String selectedText) {
		super(SELECTION);
		this.selectedText = selectedText;
		setTitle("Code Snippet");
		setDescription("Modify/Anonymize your code snippet if necessary.");
	}

	@Override
	public void createControl(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout();
		composite.setLayout(gl);
		gl.numColumns = 1;
		selectedTextControl = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP);
		selectedTextControl.setText(this.selectedText);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		selectedTextControl.setLayoutData(gridData);
		setControl(composite);
		setPageComplete(true);
	}

	public Text getSelectedTextControl() {
		return selectedTextControl;
	}

	public void setSelectedTextControl(Text selectedTextControl) {
		this.selectedTextControl = selectedTextControl;
	}
	
}

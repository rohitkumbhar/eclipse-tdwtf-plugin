package com.tdwtf.plugin.submitwtf.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class GetInformationWizardPage extends WizardPage {

	private Text commentsControl;
	private Text nameControl;
	private Text emailAddressControl;
	private Text subjectControl;
	private Button doNotPublishControl;

	public GetInformationWizardPage() {
		super("SubmitterInformation");
		setTitle("Submitter Information");
		setDescription("Info and comments");
	}

	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout();
		composite.setLayout(gl);
		gl.numColumns = 2;
		
		new Label(composite, SWT.NULL).setText("Name:");
		nameControl = new Text(composite, SWT.SINGLE | SWT.BORDER);
		nameControl.setText("");
		nameControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		new Label(composite, SWT.NULL).setText("Email Address:");
		emailAddressControl = new Text(composite, SWT.SINGLE | SWT.BORDER);
		emailAddressControl.setText("");
		emailAddressControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		new Label(composite, SWT.NULL).setText("Subject:");
		subjectControl = new Text(composite, SWT.SINGLE | SWT.BORDER);
		subjectControl.setText("");
		subjectControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		doNotPublishControl = new Button(composite, SWT.CHECK);
		doNotPublishControl.setText("Do Not Publish");
		new Label(composite, SWT.NULL).setText(""); // dummy

		new Label(composite, SWT.NULL).setText("Comments:");
		new Label(composite, SWT.NULL).setText(""); // dummy

		GridData commentsData = new GridData(GridData.FILL_BOTH);
		commentsData.horizontalSpan = 2;
		commentsData.heightHint = 200;
		commentsControl = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP);
		commentsControl.setText("");
		commentsControl.setLayoutData(commentsData);
		setControl(composite);
		setPageComplete(true);
	}

	public Text getCommentsControl() {
		return commentsControl;
	}

	public void setCommentsControl(Text commentsControl) {
		this.commentsControl = commentsControl;
	}

	public Text getNameControl() {
		return nameControl;
	}

	public void setNameControl(Text nameControl) {
		this.nameControl = nameControl;
	}

	public Text getEmailAddressControl() {
		return emailAddressControl;
	}

	public void setEmailAddressControl(Text emailAddressControl) {
		this.emailAddressControl = emailAddressControl;
	}

	public Button getDoNotPublishControl() {
		return doNotPublishControl;
	}

	public void setDoNotPublishControl(Button doNotPublishControl) {
		this.doNotPublishControl = doNotPublishControl;
	}

	public Text getSubjectControl() {
		return subjectControl;
	}

	public void setSubjectControl(Text subjectControl) {
		this.subjectControl = subjectControl;
	}
	
	

}

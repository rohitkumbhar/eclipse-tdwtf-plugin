package com.tdwtf.plugin.submitwtf.wizard;

import java.lang.reflect.InvocationTargetException;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.w3c.dom.DOMException;

public class SubmissionWizard extends Wizard {

	private String selectedText;
	private ShowSelectionWizardPage selectionPage;
	private GetInformationWizardPage informationPage;

	static final QName serviceName = new QName("http://thedailywtf.com/", "SubmitWTF");
	static final QName portName = new QName("http://thedailywtf.com/", "SubmitWTFSoap12");
	static final String endpointAddress = "http://thedailywtf.com/SubmitWTF.asmx";

	public SubmissionWizard(String selectedText) {
		super();
		this.selectedText = selectedText;
		setHelpAvailable(false);
		setNeedsProgressMonitor(true);

	}

	@Override
	public void addPages() {
		super.addPages();
		selectionPage = new ShowSelectionWizardPage(this.selectedText);
		informationPage = new GetInformationWizardPage();

		addPage(selectionPage);
		addPage(informationPage);

	}

	@Override
	public boolean performFinish() {
		
		final SubmissionData submissionData = getSubmissionData();
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doSubmission(monitor,submissionData);
				} catch (Exception e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * @param monitor 
	 * @param submissionData 
	 * @throws SOAPException
	 * @throws DOMException
	 */
	private void doSubmission(IProgressMonitor monitor, SubmissionData submissionData) throws SOAPException, DOMException {
		
		monitor.beginTask("Initiating submission process...", 4);
		
		monitor.worked(1);
		monitor.setTaskName("Creating service...");
		Service service = Service.create(serviceName);
		service.addPort(portName, SOAPBinding.SOAP12HTTP_BINDING, endpointAddress);
		Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);
		BindingProvider bp = (BindingProvider) dispatch;
		MessageFactory factory = ((SOAPBinding) bp.getBinding()).getMessageFactory();
	
		monitor.worked(1);
		monitor.setTaskName("Creating payload...");
		SOAPMessage request = getPayload(factory, submissionData);
		
		monitor.worked(1);
		monitor.setTaskName("Submitting...");
		dispatch.invoke(request);
		monitor.worked(1);
	}

	/**
	 * @return
	 */
	private SubmissionData getSubmissionData() {
		SubmissionData submissionData = new SubmissionData();
		submissionData.name = informationPage.getNameControl().getText();
		submissionData.email = informationPage.getEmailAddressControl().getText();
		submissionData.comments = informationPage.getCommentsControl().getText();
		submissionData.doNotPublish = informationPage.getDoNotPublishControl().getSelection();
		submissionData.subject = informationPage.getSubjectControl().getText();
		submissionData.snippet = selectionPage.getSelectedTextControl().getText();

		return submissionData;
	}

	/**
	 * @param factory
	 * @return
	 * @throws SOAPException
	 * @throws DOMException
	 */
	private SOAPMessage getPayload(MessageFactory factory, SubmissionData data) throws SOAPException, DOMException {

		SOAPMessage request = factory.createMessage();
		SOAPBody body = request.getSOAPBody();
		QName payloadName = new QName("http://thedailywtf.com/", "Submit", "thed");
		SOAPBodyElement payload = body.addBodyElement(payloadName);

		// Name
		SOAPElement nameElement = payload.addChildElement("name", "thed");
		nameElement.setTextContent(data.name);

		// Email Address
		SOAPElement emailElement = payload.addChildElement("emailAddress", "thed");
		emailElement.setTextContent(data.email);

		// Subject
		SOAPElement subjectElement = payload.addChildElement("subject", "thed");
		subjectElement.setTextContent(data.subject);

		// Subject
		SOAPElement commentsElement = payload.addChildElement("comments", "thed");
		commentsElement.setTextContent(data.comments);

		// Snippet
		SOAPElement snippetElement = payload.addChildElement("codeSubmission", "thed");
		snippetElement.setTextContent(data.snippet);

		// Do Not Publish
		SOAPElement doNotPublishElement = payload.addChildElement("doNotPublish", "thed");
		doNotPublishElement.setTextContent(Boolean.toString(data.doNotPublish));

		return request;
	}

	@Override
	public boolean canFinish() {
		return (getNextPage(getContainer().getCurrentPage()) == null);
	}

	class SubmissionData {

		String name;
		String email;
		String subject;
		String comments;
		String snippet;
		boolean doNotPublish;
	}
}

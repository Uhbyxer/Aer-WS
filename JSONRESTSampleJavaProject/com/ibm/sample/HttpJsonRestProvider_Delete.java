/*
Sample program for use with IBM Integration Bus
© Copyright International Business Machines Corporation 2010
Licensed Materials - Property of IBM
*/
package com.ibm.sample;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.*;

public class HttpJsonRestProvider_Delete extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		MbMessage inMessage = inAssembly.getMessage();

		// create new message
		MbMessage outMessage = new MbMessage();
		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly,
				outMessage);
		MbElement outRoot = outMessage.getRootElement();

		try {
			// Copy message headers from input to output
			copyMessageHeaders(inMessage, outMessage);

			// Create the XML message for the MqXmlApp.msgflow
			// This is the Delete document containing just the target name
			MbElement outParser = outRoot.createElementAsLastChild(MbXMLNSC.PARSER_NAME);
			MbElement outUpdate = outParser.createElementAsLastChild(MbElement.TYPE_NAME, "Delete", null);
			MbElement outData   = outUpdate.createElementAsLastChild(MbElement.TYPE_NAME, "CD", null);
			outData.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Title",
			  inAssembly.getGlobalEnvironment().getRootElement().getFirstElementByPath("SaveState/target").getValueAsString());
			// Propagate the new output message
			out.propagate(outAssembly);
		} finally {

			// clear the outMessage even if there's an exception
			outMessage.clearMessage();
		}
	}

	public void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage)
			throws MbException {
		MbElement outRoot = outMessage.getRootElement();

		// iterate though the headers starting with the first child of the root
		// element
		MbElement header = inMessage.getRootElement().getFirstChild();
		while (header != null && header.getNextSibling() != null) 
		// stop before the last child (body)
		{
			// copy the header and add it to the out message
			outRoot.addAsLastChild(header.copy());
			// move along to next header
			header = header.getNextSibling();
		}
	}

}

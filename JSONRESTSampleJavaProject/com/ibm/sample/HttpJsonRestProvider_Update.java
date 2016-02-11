/*
Sample program for use with IBM Integration Bus
© Copyright International Business Machines Corporation 2010
Licensed Materials - Property of IBM
*/
package com.ibm.sample;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.*;

public class HttpJsonRestProvider_Update extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		MbMessage inMessage = inAssembly.getMessage();

		// create new output message
		MbMessage outMessage = new MbMessage();

		try {
			MbElement outRoot = outMessage.getRootElement();
			// Copy the Properties folder from the input to the output message
			outRoot.addAsLastChild(inMessage.getRootElement().getFirstChild().copy());
			
			// Pick up the JSON data object that provides the CD data to be updated
			MbElement inData = inMessage.getRootElement().getLastChild().getFirstElementByPath("/JSON/Data");
			if ( inData != null ) {
				// In this sample the JSON data and XML document structure are very similar
				// The JSON input
				//   {"Title":"..",,,"Members":["..",,]}
				// Which produces the JSON Domain tree as
				// JSON
				//    Title ..    
				//    ,,
				//    Members <-Array
				//       Item ..
				// Needs to be converted to XML for the XmlMqApp message flow
				// in this sample the structure is a very direct mapping, we just need
				// to insert the XML document element and rename the JSON array "Item" elements
				// XMLNSC
				//     Update
				//         CD
				//            Title ..    
				//            ,,
				//            Members
				//                Member ..
				//
				MbElement outParser = outRoot.createElementAsLastChild(MbXMLNSC.PARSER_NAME);
				MbElement outUpdate = outParser.createElementAsLastChild(MbElement.TYPE_NAME, "Update", null);
				outUpdate.addAsFirstChild(inData.copy());
				MbElement outData = outUpdate.getFirstChild();
				outData.setName("CD");
				MbElement memberEl = outData.getFirstElementByPath("/Members/Item");
				while ( memberEl != null ) {
					memberEl.setName("Member");
					memberEl = memberEl.getNextSibling();
				}
			}
			
			MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly,outMessage);
			out.propagate(outAssembly);

		} finally {
			// clear the outMessage
			outMessage.clearMessage();
		}
	}
}

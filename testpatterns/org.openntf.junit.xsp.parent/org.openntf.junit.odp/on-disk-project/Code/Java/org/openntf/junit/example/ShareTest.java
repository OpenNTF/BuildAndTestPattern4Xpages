package org.openntf.junit.example;

import lotus.domino.Document;

import org.junit.Test;
import org.openntf.junit.example.bo.Share;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class ShareTest {

	@Test
	public void testShareWithDocumentMock() {
		Document docMock = createNiceMock(Document.class);
		try {
			expect(docMock.getItemValueString("ShareName")).andReturn("WebGate");
			expect(docMock.getItemValueInteger("Count")).andReturn(5);
			expect(docMock.getItemValueInteger("PricePerShare")).andReturn(2870);
			replay(docMock);
			Share shareWebGate = Share.initFromDocument(docMock);
			assertEquals("WebGate",shareWebGate.getShareName());
			assertEquals( 5 * 2870, shareWebGate.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(true);
		}
	}
}

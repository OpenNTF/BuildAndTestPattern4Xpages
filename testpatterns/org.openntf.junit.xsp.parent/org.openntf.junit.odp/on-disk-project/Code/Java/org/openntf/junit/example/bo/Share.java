package org.openntf.junit.example.bo;

import lotus.domino.Document;

public class Share {

	private String m_ShareName;
	private int m_Count;
	private int m_PricePerShare;
	
	public static Share initFromDocument( Document doc) {
		Share share = new Share();
		try {
			share.m_ShareName = doc.getItemValueString("ShareName");
			share.m_Count = doc.getItemValueInteger("Count");
			share.m_PricePerShare = doc.getItemValueInteger("PricePerShare");
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return share;
	}
	
	public void setShareName(String shareName) {
		m_ShareName = shareName;
	}
	public String getShareName() {
		return m_ShareName;
	}
	public void setCount(int count) {
		m_Count = count;
	}
	public int getCount() {
		return m_Count;
	}
	public void setPricePerShare(int pricePerShare) {
		m_PricePerShare = pricePerShare;
	}
	public int getPricePerShare() {
		return m_PricePerShare;
	}

	public Object getValue() {
		return m_Count * m_PricePerShare;
	}
	
	
}

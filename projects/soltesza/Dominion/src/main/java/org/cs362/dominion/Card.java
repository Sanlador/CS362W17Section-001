package org.cs362.dominion;

public abstract class Card implements Cloneable {
	protected String name;
	protected int cost;
	private boolean trashed = false;
	
	@Override
	public Card clone() {
		try {
		return (Card)super.clone();
		} 
		catch(CloneNotSupportedException e) {
			throw new Error("Error cloning object!");
		}
	}
	
	public String GetName() { return name; }
	
	public int GetValue() { return cost; }
	
	public boolean GetTrashed() { return trashed; }
	
	public void SetTrashed(boolean val) { trashed = val; }
}
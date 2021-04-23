package me.zombie_striker.jretrowave3d.data;

public class ObjectChain<T> {

	private ObjectChain nextChain;
	private ObjectChain previousChain;
	private T object;
	public T getObject(){
		return object;
	}

	public ObjectChain(T object, ObjectChain<T> previousChain){
		this.object = object;
		this.setPreviousChain(previousChain);
		if(previousChain!=null)
		previousChain.setNextChain(this);
	}
	public boolean hasNext(){
		return nextChain!=null;
	}
	public ObjectChain getNextChain(){
		return nextChain;
	}
	public ObjectChain getPreviousChain(){
		return previousChain;
	}
	public void removeEntry(){
		previousChain.setNextChain(nextChain);
				nextChain.setPreviousChain(previousChain);
	}
	public void addChain(ObjectChain chain, boolean before){
		if(before){
			previousChain.setNextChain(chain);
			chain.setPreviousChain(previousChain);
			chain.setNextChain(this);
			this.setPreviousChain(chain);
		}else{
			nextChain.setPreviousChain(chain);
			chain.setNextChain(previousChain);
			chain.setPreviousChain(this);
			this.setNextChain(chain);
		}
	}
	public void setObject(T object){
		this.object = object;
	}

	private void setNextChain(ObjectChain nextChain) {
		this.nextChain = nextChain;
	}

	private void setPreviousChain(ObjectChain previousChain) {
		this.previousChain = previousChain;
	}
}

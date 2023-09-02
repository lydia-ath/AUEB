
public class WordFreq {
	// key is the word
	private String key;	
	// number is the number of the word's appearances
	private int frequency;

	public WordFreq(String key, int frequency) {
		this.key = key;
		this.frequency = frequency;
	}
	public WordFreq() {
		this.key = null;
		this.frequency = 0;
	}

	public String key() {
		return key;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	@Override
	public String toString() {
		return "key = " + key + ", frequency = " + frequency;
	}
	
}

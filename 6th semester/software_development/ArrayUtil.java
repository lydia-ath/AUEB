
public class ArrayUtil {

	public static boolean reverse(int[] array, int start) {

		if (array == null) {
			return false;
		}

		if (start >= array.length) {
			return false;
		}
		
		int i = 0;
		if (start > 0) {
			i = start;
		}
		
		int j = array.length - 1;
		int tmp;
		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
		return true;
	}
}

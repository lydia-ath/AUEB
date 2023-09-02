/** Lydia Athanasiou p3170003*/

import org.junit.Assert;
import org.junit.Test;

public class ArrayUtilTest3170003 {

    /** Test if the array is empty array === null */
    @Test
        public void testCaseEmptyArray(){
        ArrayUtil array = new ArrayUtil();
        Assert.assertFalse("false", ArrayUtil.reverse(null, 0));
        }

    /** Test if start > array.length */
    @Test
        public void testStartBiggerThanArrayLength(){
        ArrayUtil array1 = new ArrayUtil();
        int [] array = new int[] {1,2,3,4};
        int start = 8;
        Assert.assertFalse("False", ArrayUtil.reverse(array, start) );
    }

    /** Test if start == 0 */
    @Test
        public void testStartIsEqualsToArrayLength3(){
        ArrayUtil array2 = new ArrayUtil();
        int [] array1 = new int[] {1,2,3,4};
        int start = array1.length;
        Assert.assertFalse("False", ArrayUtil.reverse(array1, start));
    }

    /** Test if start > 0*/
    @Test
        public void testCaseStartGraterThanZero(){
        int start = 2;
        int [] array2 = new int[] {1,2,3,4};
        Assert.assertTrue("True", ArrayUtil.reverse(array2, start));
    }

    /** Test if the input array1 traversed correctly in array2 the reversed array1*/
    @Test
        public void testCaseArrayEqualsToReverseArray(){
        ArrayUtil array = new ArrayUtil();
        int [] array1 = new int[] {1,2,3,4};
        int [] array2 = new int[] {4,3,2,1};
        ArrayUtil.reverse(array1,0);
        Assert.assertArrayEquals(array1, array2);
    }


}

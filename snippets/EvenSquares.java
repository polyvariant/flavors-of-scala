import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final public class EvenSquares {
    public static void main(String[] args) {
        // #region list
        // Create our initial list
        List numbers = new ArrayList();
        numbers.add(new Integer(1));
        numbers.add(new Integer(2));
        numbers.add(new Integer(3));
        numbers.add(new Integer(4));
        numbers.add(new Integer(5));
        numbers.add(new Integer(6));
        // #endregion

        // #region map        
        // Filter even numbers and calculate squares
        List result = new ArrayList();
        Iterator iterator = numbers.iterator();
        while (iterator.hasNext()) {
            Integer number = (Integer) iterator.next();
            int value = number.intValue();
            if (value % 2 == 0) {
                result.add(new Integer(value * value));
            }
        }
        // #endregion

        // #region foreach
        // Print results
        System.out.println("Even squares: ");
        Iterator resultIterator = result.iterator();
        while (resultIterator.hasNext()) {
            Integer square = (Integer) resultIterator.next();
            System.out.print(square + " ");
        }
        // #endregion
    }
}
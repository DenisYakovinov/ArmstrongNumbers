import java.util.*;

public class ArmstrongNumbers {

    final static long[][] MATRIX_OF_POWER;

    /*
    initializing a matrix that has numbers 1 to 9 raised to a power equal to the
    number of numbers in the input number to speed up calculations
     */
    static {
        MATRIX_OF_POWER = new long[10][20];
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                long target = i;
                for (int h = 1; h < j; h++) {
                    target *= i;
                }
                MATRIX_OF_POWER[i][j] = target;
            }
        }
    }

    //getting an array of digits from a number
    public static int[] getArrayFromNumbers(long number) {
        List<Integer> tempList = new ArrayList<>();
        while (true) {
            if (number < 1) break;
            int lastDigit = (int) (number % 10);
            tempList.add(lastDigit);
            number /= 10;
        }
        Collections.reverse(tempList);
        int[] result = new int[tempList.size()];
        for (int c = 0; c < tempList.size(); c++) {
            result[c] = tempList.get(c);
        }
        return result;
    }

    /*
    Sorting through getting unique combinations and checking them for Armstrong numbers and adding
    them to the treeSet
     */
    public static TreeSet<Long> getArmstrongNumbersUsingRecursion(int pow, int index,
                                List<Integer> listUse, int powForMatrix, TreeSet<Long> resultSet, Long MaxValue) {

        for (int i = index; i <= 9; i++) {
            ArrayList<Integer> useFull = new ArrayList<>();
            useFull.addAll(listUse);
            useFull.add(i);
            for (int c = useFull.size(); c <= powForMatrix; c++) {
                long resultForCheck = 0;
                for (int digit : useFull) {
                    resultForCheck += MATRIX_OF_POWER[digit][c];
                }
                long resultForCheck2 = 0;
                int[] arrayFromResultForCheck = getArrayFromNumbers(resultForCheck);
                int powForChecking = arrayFromResultForCheck.length;
                for (int k = 0; k < powForChecking; k++) {
                    resultForCheck2 += MATRIX_OF_POWER[arrayFromResultForCheck[k]][powForChecking];
                }
                if (resultForCheck == resultForCheck2 && resultForCheck2 < MaxValue) {
                    resultSet.add(resultForCheck2);
                }

            }
            if (pow == 1) continue;
            getArmstrongNumbersUsingRecursion(pow - 1, i, useFull, powForMatrix, resultSet, MaxValue);
        }
        return resultSet;
    }

    //gets the input number to which you need to find the Armstrong numbers
    public static long[] getArmstrongNumbersInArray(long N) {
        if (N < 0) return new long[0];

        int maxPow = getArrayFromNumbers(N).length;
        TreeSet<Long> tempResult = getArmstrongNumbersUsingRecursion(maxPow, 1, new ArrayList<>(), maxPow, new TreeSet<>(), N);
        int c = 0;
        long[] result = new long[tempResult.size()];
        for (Long x : tempResult) {
            result[c] = x;
            c++;
        }

        return result;
    }

    public static void main(String[] args) {

        long a = System.currentTimeMillis();
        System.out.println(Arrays.toString(getArmstrongNumbersInArray(Long.MAX_VALUE)));
        long b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (b - a) / 1000);

        a = System.currentTimeMillis();
        System.out.println(Arrays.toString(getArmstrongNumbersInArray(1000000)));
        b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (b - a) / 1000);
    }
}

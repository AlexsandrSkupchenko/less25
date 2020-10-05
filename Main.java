package lesson2;

public class Main {

    private static final int size = 10000000;
    private static final int h = size / 2;
    private static Thread thread1, thread2;

    public static void main(String[] args) {

        System.out.println("Время первого метода " + arrayMetod1());
        System.out.println("Время второго метода " + arrayMetod2());
    }

    private static long arrayMetod1() {
        float[] array = new float[size];
        arrayOne(array);
        long a = System.currentTimeMillis();
        arrCalc(array);
        return System.currentTimeMillis() - a;
    }


    private static void arrCalc(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    private static void arrayOne(float[] array) {
        long aa = System.currentTimeMillis();
        for (int j = 0; j < array.length; j++) {
            array[j] = 1;
        }
        System.out.println("время заполнения " + (System.currentTimeMillis() - aa));
    }

    private static long arrayMetod2() {
        float[] array = new float[size];
        long a = System.currentTimeMillis();
        float[] firstfArray = new float[h];
        float[] secondfArray = new float[h];
        System.arraycopy(array, 0, firstfArray, 0, h);
        System.arraycopy(array, h, secondfArray, 0, h);
        thread1 = new Thread(() -> {
            arrCalc(firstfArray);
        });
        thread2 = new Thread(() -> {
            arrCalc(secondfArray);
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        System.arraycopy(firstfArray, 0, array, 0, h);
        System.arraycopy(secondfArray, 0, array, h, h);

        return System.currentTimeMillis() - (a);
    }
}
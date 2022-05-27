package AISD_SemesterWorks.Algorithms;


import java.util.Random;

class BucketSort {
    public static void main(String[] args) {
        int[] elements = new int[10000000];
        int n = elements.length;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            elements[i] = random.nextInt(99999);
        }
        long start = System.nanoTime();
        bucketSort(elements, n);
        long finish = System.nanoTime();
        long difference = finish - start;
        System.out.println("Lead time: " + difference / 1000);

    }

    static void bucketSort(int arr[], int n) {
        int i = n;
        int j = n;
        int k= n;
        int SIZE = n;

        int elements[] = new int[SIZE];

        for (i = 0; i < SIZE; ++i)
            elements[i] = 0;

        for (i = 0; i < n; ++i)
            ++elements[arr[i]];

        for (i = 0, j = 0; j < SIZE; ++j)
            for (k = elements[j]; k > 0; --k)
                arr[i++] = j;
    }


}
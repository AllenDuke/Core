package example.java;

public class Main {

    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
    }

    private static class WorkerThread extends Thread{
        @Override
        public void run() {
            int i=0;
            i++;
        }
    }

}

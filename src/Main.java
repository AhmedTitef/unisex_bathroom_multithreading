import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main extends Thread {
    static Queue<Integer> queueForMen = new LinkedList<>();
    static Queue<Integer> queueForWomen = new LinkedList<>();
    static final Queue<Integer> queueForInsideRestroom = new LinkedList<>();
    static boolean isGood = true;


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        int i = 0;
        final OnePerson onePerson = new OnePerson();
        Thread t1 = new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    int randomGender = random.nextInt( 2 );
                    onePerson.Arrive( 1, randomGender );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        );


        Thread t2 = new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    int randomGender = random.nextInt( 2 );
                    onePerson.Depart( 1, randomGender );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } );

        t1.start();
        t2.start();

        t1.join();
        t2.join();


    }

    static class OnePerson {


        private void Arrive(int id, int gender) throws InterruptedException {


            while (true) {
                synchronized (this) {

                    while (queueForInsideRestroom.size() == 3 && queueForInsideRestroom.peek() == gender)
                        wait();
                    System.out.println( "A thread is added" );
                    queueForInsideRestroom.add( gender );
                    System.out.println( queueForInsideRestroom );
                    notify();
                    Thread.sleep( 1000 );
                }
            }

        }

        private synchronized void UseFacilities(int id, int gender, int time) throws InterruptedException {


            Thread.sleep( 2000 );
            System.out.println( "Using restroom" );


        }

        private void Depart(int id, int gender) throws InterruptedException {
            while (true) {
                synchronized (this) {
                    System.out.println( "departed function" );
                    while (queueForInsideRestroom.size() == 0) {
                        wait();


                    }
                    int val = queueForInsideRestroom.remove();
                    System.out.println( "departed id is " + val );
                    notify();
                    Thread.sleep( 1000 );
                }
            }
        }

    }
}
//
//    System.out.println(currentThread().getName() + " trying to enter");
//            if (queueForInsideRestroom.size() ==3 ){
//synchronized (queueForInsideRestroom){
//        System.out.println("Quuee for restroom is full " + Thread.currentThread().getName() +"is waiting ");
//        queueForInsideRestroom.wait();
//        }
//        }
//
//
//        if (queueForInsideRestroom.size() == 0) {
//
//        isGood = true;
//        queueForInsideRestroom.add( gender );
//        if (gender == 0) {
//        System.out.println( currentThread().getName()+"A man entered restroom" );
//        } else {
//        System.out.println(currentThread().getName()+ "a woman entered restroom" );
//        }
//        } else {
//        if (queueForInsideRestroom.peek() == gender) {
//        if (queueForInsideRestroom.size() < 3) {
//        queueForInsideRestroom.add( gender );
//        if (gender == 0) {
//        System.out.println( currentThread().getName()+"A man entered restroom" );
//        } else {
//        System.out.println( currentThread().getName()+"a woman entered restroom" );
//        }
//        isGood = true;
//        } else if (gender == 1) {
//        queueForWomen.add( id );
//        isGood = false;
//        wait();
//        System.out.println( currentThread().getName()+"A woman been added to the queue to wait because restroom is full" );
//        //put thread into wait until men leaves from restroom
//        } else if (gender == 0) {
//        queueForMen.add( id );
//        isGood = false;
//        System.out.println(currentThread().getName()+ "A man been added to the queue to wait because restroom is full" );
//        wait();
//        }
//
//        } else if (gender == 1) {
//        queueForWomen.add( id );
//        isGood = false;
//
//        System.out.println( currentThread().getName()+"A woman been added to the queue to wait" );
//        wait();
//        //put thread into wait until men leaves from restroom
//        } else if (gender == 0) {
//
//        queueForMen.add( id );
//        isGood = false;
//        System.out.println(currentThread().getName()+ "A man been added to the queue to wait" );
//        wait();
//        }
//
//
//        }
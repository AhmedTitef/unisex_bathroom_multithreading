import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Program_2_Assignment implements Runnable {
    static private Lock lock = new ReentrantLock();
    static private Condition womenWaitingQueue = lock.newCondition();
    static private Condition menWaitingQueue = lock.newCondition();
    static private int womenWaitingForRestroom = 0;
    static private int menWaitingForRestroom = 0;
    static private int womenUsingRestroom = 0;
    static private int menUsingRestroom = 0;
    static private int maxNoOFUsers;
    static private int occupiedCount;


    public static void main(String[] args) throws InterruptedException {
        occupiedCount = 0;
        maxNoOFUsers = 3;

        Thread thread = new Thread( new Program_2_Assignment() );
        thread.start();
        Thread thread1 = new Thread( new Program_2_Assignment() );
        thread1.start();
        Thread thread2 = new Thread( new Program_2_Assignment() );
        thread2.start();
        Thread thread3 = new Thread( new Program_2_Assignment() );
        thread3.start();
        Thread thread4 = new Thread( new Program_2_Assignment() );
        thread4.start();
        Thread thread5 = new Thread( new Program_2_Assignment() );
        thread5.start();


        System.out.println("Another 5 threads are coming");
        TimeUnit.SECONDS.sleep( 10 );
        Thread thread6 = new Thread( new Program_2_Assignment() );
        thread6.start();
        Thread thread7 = new Thread( new Program_2_Assignment() );
        thread7.start();
        Thread thread8 = new Thread( new Program_2_Assignment() );
        thread8.start();
        Thread thread9 = new Thread( new Program_2_Assignment() );
        thread9.start();
        Thread thread10 = new Thread( new Program_2_Assignment() );
        thread10.start();
        Thread thread11 = new Thread( new Program_2_Assignment() );
        thread11.start();



        System.out.println("Another 5 threads are coming");
        TimeUnit.SECONDS.sleep( 10 );
        Thread thread12 = new Thread( new Program_2_Assignment() );
        thread12.start();
        Thread thread13 = new Thread( new Program_2_Assignment() );
        thread13.start();
        Thread thread14 = new Thread( new Program_2_Assignment() );
        thread14.start();
        Thread thread15 = new Thread( new Program_2_Assignment() );
        thread15.start();
        Thread thread16 = new Thread( new Program_2_Assignment() );
        thread16.start();
        Thread thread17 = new Thread( new Program_2_Assignment() );
        thread17.start();

    }

    int id = 0;

    @Override
    public void run() {
        System.out.println( "Thread created with id: " + Thread.currentThread().getId() );


        Random random = new Random();

        Random random1 = new Random();
        int randomId = random1.nextInt( 10 );


        try {

            id = (int) Thread.currentThread().getId();

            int randomGender = random.nextInt( 2 );
            OnePerson person = new OnePerson( id, randomGender, 5 );


            Thread.sleep( 1 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    static class OnePerson {


        OnePerson(int id, int gender, int time) throws InterruptedException {
            Arrive( id, gender );
            UseFacilities( id, gender, time );
            Depart( id, gender );
        }


        private void Depart(int id, int gender) {

            //man leaves
            if (gender == 0) {
                lock.lock();
                try {

                    menUsingRestroom--;
                    occupiedCount--;
                    System.out.println( "Man " + id + " exits bathroom with Thread number " + Thread.currentThread().getName() );

                    if (womenWaitingForRestroom > 0 && menUsingRestroom == 0) {
                        womenWaitingQueue.signal();
                        womenUsingRestroom++;
                        occupiedCount++;
                        womenWaitingForRestroom--;
                    } else if (menWaitingForRestroom > 0) {
                        menWaitingQueue.signal();
                        menWaitingForRestroom--;
                        menUsingRestroom++;
                        occupiedCount++;
                    }
                } finally {
                    lock.unlock();
                }
            }

            //woman leaves
            if (gender == 1) {


                lock.lock();
                try {

                    womenUsingRestroom--;
                    occupiedCount--;
                    System.out.println( "Woman " + id + " exits bathroom  " + Thread.currentThread().getName() );

                    if (womenWaitingForRestroom > 0) {
                        womenWaitingQueue.signal();
                        womenUsingRestroom++;
                        occupiedCount++;
                        womenWaitingForRestroom--;
                    } else if (menWaitingForRestroom > 0 && womenUsingRestroom == 0) {
                        menWaitingQueue.signal();
                        menUsingRestroom++;
                        occupiedCount++;
                        menWaitingForRestroom--;
                    }

                } finally {
                    lock.unlock();
                }
            }
        }

        private void UseFacilities(int id, int gender, int time) throws InterruptedException {

            if (gender == 0) {
                System.out.println( "Man " + id + " is using the facility NOW 000000000000 " + Thread.currentThread().getId() );
                Thread.sleep( time * 1000 );
            } else if (gender == 1) {
                System.out.println( "Woman " + id + " is using the facility NOW 0000000000000" + Thread.currentThread().getId() );
                Thread.sleep( time * 1000 );
            }
        }

        private void Arrive(int id, int gender) {

            //man arriving
            if (gender == 0) {
                lock.lock();
                try {

                    if (occupiedCount < maxNoOFUsers) {

                        if (womenUsingRestroom == 0) {

                            if (womenWaitingForRestroom > 0) {
                                womenWaitingQueue.signal();
                                womenUsingRestroom++;
                                occupiedCount++;
                                womenWaitingForRestroom--;
                            } else {
                                menUsingRestroom++;
                                occupiedCount++;
                                System.out.println( "Man " + id + " enters bathroom " + Thread.currentThread().getId() );
                                menWaitingQueue.signal();
                            }
                        } else {
                            while (womenUsingRestroom != 0) {
                                System.out.println( "Man " + id + " in waiting-----------" + Thread.currentThread().getId() );
                                menWaitingForRestroom++;
                                menWaitingQueue.await();
                            }
                        }


                    } else {
                        while (occupiedCount == maxNoOFUsers) {
                            System.out.println( "Man " + id + " in waiting-----------" + Thread.currentThread().getId() );
                            menWaitingForRestroom++;
                            menWaitingQueue.await();
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            // woman arriving

            if (gender == 1) {
                lock.lock();
                try {

                    if (occupiedCount < maxNoOFUsers) {

                        if (menUsingRestroom == 0) {

                            if (womenWaitingForRestroom == 0) {
                                System.out.println( "Woman " + id + " enters bathroom " + Thread.currentThread().getId() );
                                womenUsingRestroom++;
                                occupiedCount++;
                            } else {
                                while (womenWaitingForRestroom != 0) {
                                    System.out.println( "Woman " + id + " in waiting----------" + Thread.currentThread().getId() );
                                    womenWaitingForRestroom++;
                                    womenWaitingQueue.await();
                                }
                            }
                        } else {
                            while (menUsingRestroom != 0) {
                                System.out.println( "Woman " + id + " in waiting------------" + Thread.currentThread().getId() );
                                womenWaitingForRestroom++;
                                womenWaitingQueue.await();
                            }
                        }

                    } else {
                        while (occupiedCount == maxNoOFUsers) {
                            System.out.println( "Woman " + id + " in waiting------------" + Thread.currentThread().getId() );
                            womenWaitingForRestroom++;
                            womenWaitingQueue.await();
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}

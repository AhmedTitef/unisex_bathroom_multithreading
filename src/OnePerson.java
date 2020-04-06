import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class OnePerson extends Thread {
    static Queue<OnePerson> priorityQueueMale = new LinkedList<>();
    static Queue<OnePerson> priorityQueueFemale = new LinkedList<>();
    static Queue<OnePerson> priorityQueueRestroom = new LinkedList<>();

    private static Lock lock = new ReentrantLock();
    private Thread t;
    private static boolean isWaiting;
    private int threadName;
    private int id;
    private int gender;
    private int time;

   static OnePerson person;
    static OnePerson person1;


    OnePerson(int id, int gender, int time) throws InterruptedException {
        this.id = id;
        this.gender = gender;
        this.time = time;

        threadName = id;




    }

//    public void start () {
//        System.out.println("Starting " +  threadName );
//        if (t == null) {
//            t = new Thread (this, String.valueOf( threadName ) );
//            t.start ();
//        }
//    }
//
//    public void run() {
//        System.out.println("Running " +  threadName );
//        try {
//            for(int i = 4; i > 0; i--) {
//                System.out.println("Thread: " + threadName + ", " + i);
//                // Let the thread sleep for a while.
//                Thread.sleep(5000);
//            }
//        } catch (InterruptedException e) {
//            System.out.println("Thread " +  threadName + " interrupted.");
//        }
//        System.out.println("Thread " +  threadName + " exiting.");
//    }

    private static void Depart(OnePerson person) {


//        if (priorityQueueRestroom.size() == 3) {
//
//            //priorityQueueRestroom.remove( person );
        System.out.println( "id number: " + person.id + " left restroom" );

//            //person.notify();
//        }
        // Depart is called to indicate that the person is ready to exit


    }

    private static void UseFacilities(OnePerson person) throws InterruptedException {

        System.out.println( "Id number: " + person.id + " is using the facility" );
        Thread.sleep( 2000 );
        isWaiting = false;

        //TimeUnit.SECONDS.sleep( person.time );


    }

    private static void Arrive(OnePerson person) {



        //The Arrive procedure must not return until
        // it is okay for the person to enter the restroom

        if (priorityQueueRestroom.size() == 0) {

            System.out.println( "Id number: " + person.id + " entered restroom " + "and gender is " + person.gender );
            priorityQueueRestroom.add( person );

        }

        //if restroom is not empty, let them wait in a line outside
        else {

            if (priorityQueueRestroom.peek().gender == person.gender) {

                priorityQueueRestroom.add( person );
                System.out.println( "Id number: " + person.id + " entered restroom " + "and gender is " + person.gender );


            } else {
                System.out.println( "Cant enter restroom now" );
                if (person.gender == 1) { //if female
                    priorityQueueFemale.add( person );

                    System.out.println( "Id number: " + person.id + " entered Women Queue" );
                    isWaiting = true;

                }

                if (person.gender == 0) { //if male
                    priorityQueueMale.add( person );
                    System.out.println( "Id number: " + person.id + " entered Men Queue" );
                    isWaiting = true;
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {


        for (int i = 1; i <= 5; i++) {

            Random random = new Random();
            int randomGender = random.nextInt( 2 );

            person = new OnePerson( 1, randomGender, 5 );

            int randomGender1 = random.nextInt( 2 );
             person1 = new OnePerson( 2, randomGender1, 5 );



//            Arrive( person );
//            if (isWaiting) {
//                UseFacilities( person );
//            }
//            Depart( person );



//
//            System.out.println( "Restroom Queue:::" + priorityQueueRestroom );
//            System.out.println( "Women Queue:::" + priorityQueueFemale );
//            System.out.println( "Men Queue:::" + priorityQueueMale );


        }

        person.start();
        person1.start();
        if (!priorityQueueMale.isEmpty()){
            for(int i = 0; i < priorityQueueMale.size() ; i++)
            {
                System.out.println("male is not empty");
            }
        }
        if (!priorityQueueFemale.isEmpty()){
            for(int i = 0; i < priorityQueueFemale.size() ; i++)
            {
                System.out.println("femal is not empty");
            }
        }


    }

    @Override
    public void run() {
        Arrive( person );
        Arrive( person1 );

    }

}
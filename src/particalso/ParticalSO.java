package particalso;

/**
 * @author Drayton Williams The purpose of this class is to perform a vanilla
 * PSO
 */
import java.util.*;

public class ParticalSO {

    // For purpose of assignment, the PSO runs for 1000 iterations
    private static final int ITERATIONS = 1000;

    private final int dimension;    // dimesnion of problem
    private int gBest;              // global best
    private final int nParts;       // number of particles
    private final double w;         // inertia weight
    private final double c1;        // cognitive acceleration coefficient
    private final double c2;        // social acceleration coefficient
    private final Particle[] swarm; // collection (swarm) of particles

    public ParticalSO() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter number of particles [20-40]: ");
        nParts = scanner.nextInt();
        System.out.print("Please enter dimension: ");
        dimension = scanner.nextInt();
        System.out.print("Please enter inertia weight: ");
        w = scanner.nextDouble();
        System.out.print("Please enter cognitive coefficient: ");
        c1 = scanner.nextDouble();
        System.out.print("Please enter social coefficient: ");
        c2 = scanner.nextDouble();

        /* For Quick Testing
        nParts = 30;
        w = 0.729844;
        c1 = 1.496180;
        c2 = 1.496180; */
        swarm = new Particle[nParts];

        initialize();
        System.out.println("Global Best Fitness Result: " +
                swarm[gBest].getFitness());
        //System.out.println("Global Best Result Position" + 
        // Arrays.toString(swarm[gBest].position));
    }

    /**
     * Initializes the PSO with a population (with the dimension size) and
     * updates the global best fitness. Implemented as a synchronous PSO,
     * meaning particle movements are concurrent.
     */
    public void initialize() {
        int tempBest = 0;

        // random initial population of particles (swarm)
        for (int i = 0; i < swarm.length; i++) {
            swarm[i] = new Particle(dimension);
        }

        for (int i = 0; i < ITERATIONS; i++) {
            calcFitness();
            tempBest = bestNeighbour();

            calcVelocity(tempBest);
            updatePos();

            calcFitness();
            tempBest = bestNeighbour();

            System.out.println("Round " + (i + 1) + " - " +
                    swarm[tempBest].getFitness());
        }
        gBest = tempBest;
    }

    /**
     * Calculates the fitness of particles within a swarm using the Rastrigin
     * function.
     */
    private void calcFitness() {
        double curFittest = 0;

        for (Particle swarm1 : swarm) {
            if (swarm1.getFitness() != 0) {
                curFittest = swarm1.getFitness();
            }
            swarm1.setFitness(0); // resets fitness

            // Summation (Rastrigin Function)
            for (int j = 0; j < dimension; j++) {
                swarm1.setFitness(swarm1.getFitness() +
                        (Math.pow(swarm1.getPosition(j), 2) -
                                (10 * Math.cos(2 * Math.PI *
                                        swarm1.getPosition(j)))));
            }
            swarm1.setFitness(swarm1.getFitness() + 10 * dimension);

            if (swarm1.getFitness() != 0) {
                if (swarm1.getFitness() < curFittest) {
                    swarm1.pBest = swarm1.position.clone();
                }
            }
        }
    }

    /**
     * Calculates the velocity for a particle within a swarm
     *
     * @param val the value of best particle in swarm when used in a vanilla
     * velocity update formula
     */
    private void calcVelocity(int val) {
        int tempBest = val;

        // Uniform random vectors with component values between 0 and 1.
        // Prevents premature convergence
        Random random = new Random();
        double[] r1 = new double[dimension];
        double[] r2 = new double[dimension];

        for (Particle swarm1 : swarm) {
            for (int j = 0; j < dimension; j++) {
                r1[j] = random.nextDouble();
                r2[j] = random.nextDouble();
            }
            for (int h = 0; h < dimension; h++) {
                swarm1.setVelocity(h, w * swarm1.getVelocity(h) + 
                        (c1 * r1[h] * (swarm1.getPB(h) - swarm1.getPosition(h)))
                        + (c2 * r2[h] * (swarm[tempBest].getPB(h) -
                                swarm1.getPosition(h))));
            }
        }
    }

    /**
     * Updates the position of a particle in the swarm using a vanilla position
     * update formula
     */
    private void updatePos() {
        for (Particle swarm1 : swarm) {
            for (int j = 0; j < dimension; j++) {
                swarm1.setPosition(j, swarm1.getVelocity(j) + 
                        swarm1.getPosition(j));
            }
        }
    }

    /**
     * Finds the best neighbourhood fitness from the entire swarm and returns
     * value.
     *
     * @return int the best neighbourhood fitness
     */
    private int bestNeighbour() {
        int tempBest = 0;
        double minVal = Double.MAX_VALUE;

        for (int i = 0; i < swarm.length; i++) {
            if (minVal > swarm[i].getFitness()) {
                minVal = swarm[i].getFitness();
                tempBest = i;
            }
        }
        return tempBest;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ParticalSO p = new ParticalSO();
    }
}

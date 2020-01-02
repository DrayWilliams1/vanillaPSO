package randomopt;

/**
 * @author Drayton Williams The purpose of this class is to perform a random
 * optimization (for comparison purposes)
 */
import java.util.*;

public class RandomOpt {

    // For purpose of assignment, the PSO runs for 1000 iterations
    private static final int ITERATIONS = 1000;
    
    private static final int MINVELBOUND = -1;
    private static final int MAXVELBOUND = 1;

    private final int dimension;    // dimesnion of problem
    private int gBest;              // global best
    private int nParts;             // number of particles

    private Particle[] swarmR1;     // main random swarm
    private Particle[] swarmR2;     // comparison random swarm

    public RandomOpt() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter number of particles [20-40]: ");
        nParts = scanner.nextInt();
        System.out.print("Please enter dimension: ");
        dimension = scanner.nextInt();

        // For Quick Testing
        nParts = 30;

        initialize();

        System.out.println("Final Global Best Fitness: " +
                swarmR1[gBest].getFitness());
        
        // System.out.println("Final Best Position: " + 
        // Arrays.toString(swarm[gBest].position));
    }

    /**
     * Initializes the Random Optimization
     */
    public void initialize() {
        int tempBest = 0;
        swarmR1 = new Particle[nParts];
        swarmR2 = new Particle[nParts];

        // random initial population of particles (swarm)
        for (int i = 0; i < swarmR1.length; i++) {
            swarmR1[i] = new Particle(dimension);
            swarmR2[i] = new Particle(dimension);
        }

        for (int i = 0; i < ITERATIONS; i++) {
            calcFitness(swarmR1);
            calcVelocity();
            updatePosR2();
            calcFitness(swarmR2);
            updatePosR1();
            tempBest = bestNeighbour();

            System.out.println("Round " + (i + 1) + " - " +
                    swarmR1[tempBest].getFitness());

            // For CSV creation
            //System.out.println(swarmR1[tempBest].getFitness() + ",");
        }
        gBest = tempBest;
    }

    /**
     * Calculates the fitness of particles within a swarm using the Rastrigin
     * function.
     *
     * @param swarm the swarm to have its fitness calculated
     */
    private void calcFitness(Particle[] swarm) {
        for (Particle swarm1 : swarm) {
            swarm1.setFitness(0);
            // Summation (Rastrigin Function)
            for (int j = 0; j < dimension; j++) {
                swarm1.setFitness(swarm1.getFitness() +
                        (Math.pow(swarm1.getPosition(j), 2) -
                                (10 * Math.cos(2 * Math.PI *
                                        swarm1.getPosition(j)))));
            }
            swarm1.setFitness(10 * dimension + swarm1.getFitness()); // 10nx
        }
    }

    /**
     * Calculates the velocity for a particle within a swarm
     */
    private void calcVelocity() {
        Random random = new Random();

        for (Particle swarmR21 : swarmR2) {
            for (int h = 0; h < dimension; h++) {
                swarmR21.setVelocity(h, MINVELBOUND + random.nextDouble() *
                        (MAXVELBOUND - (MINVELBOUND)));
            }
        }
    }

    /**
     * Updates the position of a particle in the main swarm
     */
    private void updatePosR1() {
        for (int i = 0; i < swarmR1.length; i++) {
            if (swarmR1[i].getFitness() > swarmR2[i].getFitness()) {
                swarmR1[i].position = swarmR2[i].position.clone();
            }
        }
    }

    /**
     * Updates the position of a particle in the comparison swarm
     */
    private void updatePosR2() {
        for (int i = 0; i < swarmR2.length; i++) {
            for (int j = 0; j < dimension; j++) {
                swarmR2[i].position[j] = swarmR2[i].getVelocity(j) +
                        swarmR1[i].position[j];
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

        for (int i = 0; i < swarmR1.length; i++) {
            if (minVal > swarmR1[i].getFitness()) {
                minVal = swarmR1[i].getFitness();
                tempBest = i;
            }
        }
        return tempBest;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomOpt r = new RandomOpt();
    }
}
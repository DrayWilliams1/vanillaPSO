package particalso;
/**
 * @author Drayton Williams 
 * The purpose of this class is to create particles for the swarm in the PSO.
 */
import java.util.*;

public class Particle {
    private Random random;
    private static final double MIN = -5.12; // lower range of x values
    private static final double MAX = 5.12;  // upper range of x values
    private double   x;             // x value within function
    private double   fitness;       // the fitness of the particle
    private int      dimension;     // the dimension constraints
    private double[] velocity;      // velocity
    public double[]  position;      // current position
    public double[]  pBest;         // personal best solution

    public Particle(int d) {
        random = new Random();
        setDimension(d);    // initializes particle to dimension of function
        setFitness(0);      // initalizes fitness at 0
        velocity = new double[dimension];
        position = new double[dimension];
        pBest = new double[dimension];
        
        initParticle();
    }
    
    private void initParticle() {
        for(int i = 0; i < dimension; i++) {
            x = MIN + random.nextDouble() * ((MAX - (MIN)));
            position[i] = x;
            pBest[i] = x;
        }
    }
    
    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public double getVelocity(int index) {
        return velocity[index];
    }
    
    public void setVelocity(int index, double val) {
        this.velocity[index] = val;
    }
    
    public double getPosition(int index) {
        return position[index];
    }
    
    public void setPosition(int index, double val) {
        this.position[index] = val;
    }
    
    public double getPB(int index) {
        return pBest[index];
    }
    
    public void setPB(int index, double val) {
        this.pBest[index] = val;
    }
}
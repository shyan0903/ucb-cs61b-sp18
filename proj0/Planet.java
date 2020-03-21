import static java.lang.Math.*;

public class Planet{
    /* instance variables */
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    static final double g = 6.67 * pow(10, -11);

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        /* A constructor */
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p){
        /* A constructor to make a copy of b */
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p){
        /* This instance/nonstatic method calculates the distance between two Bodys. */
        return sqrt(pow(abs(this.xxPos - p.xxPos), 2) + pow(abs(this.yyPos - p.yyPos), 2));
    }

    public double calcForceExertedBy(Planet p){
        /* This instance method calculates the force exerted by p. */
        return (g * this.mass * p.mass / pow(this.calcDistance(p), 2));
    }

    public double calcForceExertedByX(Planet p){
        /* This instance method calculates the force exerted by p on the x-axis. */
        return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
    }

    public double calcForceExertedByY(Planet p){
        /* This instance method calculates the force exerted by p on the y-axis. */
        return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] ps){
        /* Calculates the net force exerted on the x-axis. */
        double net = 0;
        for (Planet p : ps) {
            if (!(this.equals(p))) {
                net += this.calcForceExertedByX(p);
            }
        }
        return net;
    }

    public double calcNetForceExertedByY(Planet[] ps){
        /* Calculates the net force exerted on the y-axis. */
        double net = 0;
        for (Planet p : ps) {
            if (!(this.equals(p))) {
                net += this.calcForceExertedByY(p);
            }
        }
        return net;
    }

    public void update(double t, double xForce, double yForce){
        /* Updates position given force exerted on x, y direction in t time. */

        /* Calculates acceleration */
        double ax = xForce / this.mass;
        double ay = yForce / this.mass;

        this.xxVel += t * ax;
        this.yyVel += t * ay;

        this.xxPos += t * this.xxVel;
        this.yyPos += t * this.yyVel;
    }

    public void draw(){
        /* A class method that draws each Planet at its location. */
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}
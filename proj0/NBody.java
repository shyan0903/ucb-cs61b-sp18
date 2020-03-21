public class NBody {
    public static double readRadius(String file) {
        /* Reads the radius of the universe from the file. */
        In input = new In(file);
        input.readDouble();
        return input.readDouble();
    }

    public static Planet[] readPlanets(String file) {
        /* Reads the array of Planets from the file. */
        In input = new In(file);
        int numPlanets = input.readInt();
        Planet[] planets = new Planet[numPlanets];

        input.readDouble();

        int i = 0;
        while (i < planets.length) {
            double x = input.readDouble();
            double y = input.readDouble();
            double xvel = input.readDouble();
            double yvel = input.readDouble();
            double mass = input.readDouble();
            String image = input.readString();
            planets[i] = new Planet(x, y, xvel, yvel, mass, image);
            i ++;
        }
        return planets;
    }

    public static void main(String[] args) {
        /* Reads in from command line arguments */
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        double radius = readRadius(filename);

        /* Draw the background. */
        StdDraw.setScale( - radius, radius);
        StdDraw.clear(); // set background to default/white
        StdDraw.picture(0, 0, "images/starfield.jpg");

        /* Draw the planets onto the background. */
        for (Planet p : planets) {
            p.draw();
        }

        /* Animation set up. */
        StdDraw.enableDoubleBuffering(); // for smooth animation

        double timer; // counts the time elapsed
        for (timer = 0; timer <= T; timer += dt) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            for (int i = 0; i < planets.length; i++) {
                // Calculate the net forces for every planet.
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i++){
                // Update positions and velocities of each planet
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            /* Draw the background */
            StdDraw.picture(0, 0, "images/starfield.jpg");
            /* Draw all planets */
            for (Planet p : planets) {
                p.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
        }

        /* Printing the final status of the universe (provided). */
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
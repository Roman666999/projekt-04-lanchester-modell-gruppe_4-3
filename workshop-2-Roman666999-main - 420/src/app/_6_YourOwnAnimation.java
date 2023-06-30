package app;

import java.awt.*;
import java.util.ArrayList;
import java.lang.Math;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.ApplicationTime;

import java.lang.Math;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


import javax.swing.JPanel;


public class _6_YourOwnAnimation extends Animation {

	@Override
	protected ArrayList<JFrame> createFrames(ApplicationTime applicationTimeThread) {
		// a list of all frames (windows) that will be shown
		ArrayList<JFrame> frames = new ArrayList<>();

		// Create main frame (window)
		JFrame frame = new JFrame("Mathematik und Simulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new _6_YourOwnAnimationPanel(applicationTimeThread);
		frame.add(panel);
		frame.pack(); // adjusts size of the JFrame to fit the size of it's components
		frame.setVisible(true);

		frames.add(frame);
		return frames;
	}
}

class _6_YourOwnAnimationPanel extends JPanel {

	// panel has a single time tracking thread associated with it
	private final ApplicationTime t;

	private double time;

	public _6_YourOwnAnimationPanel(ApplicationTime thread) {
		this.t = thread;
	}

	// set this panel's preferred size for auto-sizing the container JFrame
	public Dimension getPreferredSize() {
		return new Dimension(_0_Constants.WINDOW_WIDTH, _0_Constants.WINDOW_HEIGHT);
	}

	int width = _0_Constants.WINDOW_WIDTH;
	int height = _0_Constants.WINDOW_HEIGHT;

	int recHeight = 50;								//Höhe Rechteck

	int recWidth1;									//Länge Rechteck China
	int recWidth2;									//Länge Rechteck Taiwan
	double currentX1 = 326;     					// China X
	double currentY1 = 420;    						// China Y
	double currentX2 = 562;       					// Taiwan X
	double currentY2 = 590;       					// Taiwan Y


	double population1 = 2000000;                    // "g0" China Population
	double eff_s = 2;                                // "s" China Effektivitätsparameter


	double population2 = 170000;                    // "h0" Taiwan Population
	double eff_r = 110;                               // "r" Taiwan Effektivitätsparameter


	double baseLength;								//Länge zur Berechnung Verhältnis
	double maxLength = 200;							//maximale Länge Balken
	double scalePopulation;							//Verhältnis China zu Taiwan
	double basePopulation1 = population1;			//Startpopulation China
	double basePopulation2 = population2;			//Startpopulation Taiwan
	double populationChina[];
	double populationTaiwan[];
	int counter;


	Image map = Toolkit.getDefaultToolkit().getImage("map.jpg");


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);


		time = t.getTimeInSeconds()*0.0005;

		if (population1 > 0 && population2 > 0) {


			counter = counter + 1;

			//Rechnung Population China nach Zeit
			population1 = population1 * Math.cosh(Math.sqrt(eff_s * eff_r) * time) - Math.sqrt(eff_r / eff_s) * population2 * Math.sinh(Math.sqrt(eff_s * eff_r) * time);
			//populationChina[counter] = population1;		//speichert die Population in den Array für den Graphen


			//Rechnung Population Taiwan nach Zeit
			population2 = population2 * Math.cosh(Math.sqrt(eff_s * eff_r) * time) - Math.sqrt(eff_s / eff_r) * population1 * Math.sinh(Math.sqrt(eff_s * eff_r) * time);
			//populationTaiwan[counter] = population2;		//speichert die Population in den Array für den Graphen


			if (population1 >= population2) {
				// Rechtecklänge China
				baseLength = maxLength * (population1 / basePopulation1);
				recWidth1 = (int) Math.round(baseLength);

				// Rechtecklänge Taiwan
				scalePopulation = baseLength * (population2 / population1);
				recWidth2 = (int) Math.round(scalePopulation);
			}
			else {
				// Rechtecklänge Taiwan
				baseLength = maxLength * (population2 / basePopulation2);
				recWidth2 = (int) Math.round(baseLength);

				// Rechtecklänge China
				scalePopulation = baseLength * (population1 / population2);
				recWidth1 = (int) Math.round(scalePopulation);
			}


			g.drawImage(map, 0, 0, width, height, this);

			//China Rechteck
			if (population1 >= 0) {
				g.setColor(new Color(255, 0, 0));
				g.fillRect((int) currentX1, (int) currentY1, recWidth1, recHeight);
			}

			//Taiwan Rechteck
			if (population2 >= 0) {
				g.setColor(new Color(0, 137, 255));
				g.fillRect((int) currentX2, (int) currentY2, recWidth2, recHeight);
			}

			//Anzeige Population
			g.setColor(Color.white);

									//Anzeige Population China
			int popCalk1 = (int) Math.round(population1);
			String pop1 = popCalk1 + "";
			if (population1 < 0) {
				pop1 = "0";
			}
			g.drawString("Population China:  " + pop1, 326, 415);


									// Anzeige Population Taiwan
			int popCalk2 = (int) Math.round(population2);
			String pop2 = popCalk2 + "";
			if (population2 < 0) {
				pop2 = "0";
			}
				g.drawString("Population Taiwan:  " + pop2, 562, 585);

		}
			else {

			//Anzeige des Restbalkens des Siegers

			g.drawImage(map, 0, 0, width, height, this);


			//China Rechteck

			if (population1 >= 0) {
				g.setColor(new Color(255, 0, 0));
				g.fillRect((int) currentX1, (int) currentY1, recWidth1, recHeight);
			}

			//Taiwan Rechteck
			if (population2 >= 0) {
				g.setColor(new Color(0, 137, 255));
				g.fillRect((int) currentX2, (int) currentY2, recWidth2, recHeight);
			}

			//Anzeige Population
			g.setColor(Color.white);

			int popCalk1 = (int) Math.round(population1);
			String pop1 = popCalk1 + "";
			if (population1 < 0) {
				pop1 = "0";
			}
			g.drawString("Population China:  " + pop1, 326, 415);

			int popCalk2 = (int) Math.round(population2);
			String pop2 = popCalk2 + "";
			if (population2 < 0) {
				pop2 = "0";
			}
			g.drawString("Population Taiwan:  " + pop2, 562, 585);



		}

	}

/*
	@Override
	public Dimension getPreferredSize() {
		return this.getMaximumSize();
	}

	@Override
	public Dimension getMinimumSize() {
		return this.getMaximumSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(1000, 300);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// Koordinationsystem anzeigen
		g.drawLine(10, 260, 960, 260);
		g.drawLine(40, 10, 40, 290);


		// farbe ändern
		g.setColor(Color.RED);


		//Graph 1

		double x1 = 40;
		double y1 = 100;
		double x2;
		double y2;
		int a2;
		int b2;


		for (int j = 0; j < 960; j++) {

			recWidth1 = (int) Math.round(baseLength);

			a2 = (int) Math.round(x1+j);
			b2 = (int) Math.round(populationChina[1]);

			g.fillOval(a2, b2, 5, 5);



			//Graph 2



		}


	}

*/

}
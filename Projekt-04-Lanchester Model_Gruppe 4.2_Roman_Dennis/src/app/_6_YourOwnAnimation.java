package app;

import java.awt.*;
import java.util.ArrayList;
import java.lang.Math;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.ApplicationTime;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;



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

	int recHeight = 50;                                //Höhe Rechteck

	int recWidth1;                                    //Länge Rechteck China
	int recWidth2;                                    //Länge Rechteck Taiwan
	double currentX1 = 326;                        // China X
	double currentY1 = 420;                            // China Y
	double currentX2 = 562;                        // Taiwan X
	double currentY2 = 590;                        // Taiwan Y


	double basePopulation1 = 300000;                    // "g0" China Population
	double eff_s = 3;                                // "s" China Effektivitätsparameter


	double basePopulation2 = 300000;                    // "h0" Taiwan Population
	double eff_r = 4;                               // "r" Taiwan Effektivitätsparameter


	double baseLength;                                //Länge zur Berechnung Verhältnis
	double maxLength = 300;                            //maximale Länge Balken
	double scalePopulation;                            //Verhältnis China zu Taiwan
	public double population1 = basePopulation1;            //Startpopulation China
	public double population2 = basePopulation2;            //Startpopulation Taiwan


	//Berechnung für L
	double l = eff_r * Math.pow(basePopulation1, 2) - eff_s * Math.pow(basePopulation2, 2);


	//Berechnung Simulationszeit
	double simTime = (Math.atan((basePopulation2 / basePopulation1) * (Math.sqrt(eff_r / eff_s)))) / Math.sqrt(eff_r * eff_s);

	double timeScale = 0.04;


	Image map = Toolkit.getDefaultToolkit().getImage("map.jpg");


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		time = Math.pow(2, t.getTimeInSeconds()) * simTime * timeScale;

		if (population1 > 0 && population2 > 0 && population1-population1 == 0 && population2 - population2 == 0 ) {


			//Rechnung Population China nach Zeit
			population1 = basePopulation1 * Math.cosh(Math.sqrt(eff_s * eff_r) * time) - Math.sqrt(eff_r / eff_s) * basePopulation2 * Math.sinh(Math.sqrt(eff_s * eff_r) * time);

			//Rechnung Population Taiwan nach Zeit
			population2 = basePopulation2 * Math.cosh(Math.sqrt(eff_s * eff_r) * time) - Math.sqrt(eff_s / eff_r) * basePopulation1 * Math.sinh(Math.sqrt(eff_s * eff_r) * time);


				// Rechtecklänge China
				baseLength = maxLength * (population1 / basePopulation1);
				recWidth1 = (int) Math.round(baseLength);

				// Rechtecklänge Taiwan
				scalePopulation = baseLength * (population2 / population1);
				recWidth2 = (int) Math.round(scalePopulation);


			g.drawImage(map, 0, 0, width, height, this);

			//China Rechteck
			if (population1 > 0) {
				g.setColor(new Color(255, 0, 0));
				g.fillRect((int) currentX1, (int) currentY1, recWidth1, recHeight);
			}

			//Taiwan Rechteck
			if (population2 > 0) {
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

		} else {

			//Anzeige des Restbalkens des Siegers

			g.drawImage(map, 0, 0, width, height, this);

			//China Rechteck

			if (population1 > 0) {
				g.setColor(new Color(255, 0, 0));
				g.fillRect((int) currentX1, (int) currentY1, recWidth1, recHeight);
			}

			//Taiwan Rechteck
			if (population2 > 0) {
				g.setColor(new Color(0, 137, 255));
				g.fillRect((int) currentX2, (int) currentY2, recWidth2, recHeight);
			}

			//............................................................................

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

			//Ausgabe Gewinner

			g.setFont(new Font("TimesRoman", Font.PLAIN, 30));

			if (recWidth1 > recWidth2) {
				g.drawString("Winner: China", 700, 780);
			} else if (recWidth1 < recWidth2) {
				g.drawString("Winner: Taiwan", 700, 780);
			} else if (l == 0 && recWidth1 > recWidth2) {
				g.drawString("Tie (Pyrrhussieg China)", 700, 780);
			} else if (l == 0 && recWidth1 < recWidth2) {
				g.drawString("Tie (Pyrrhussieg Taiwan)", 700, 780);
			} else {
				g.drawString("It's a tie", 850, 750);
			}
		}

		//.....................................................................................

		//Rechteck Simulation Graph
		g.setColor(new Color(0, 0, 0));
		g.fillRect((int) 0, (int) 0, width, 300);

		// Koordinationsystem anzeigen
		g.setColor(new Color(255, 255, 255));
		g.drawLine(10, 260, 960, 260);
		g.drawLine(40, 10, 40, 290);

/*
		// Graph China in rot

		// Farbe ändern
		g.setColor(Color.RED);

		int baseX = 40;
		int baseY = 260; //(int) Math.round(basePopulation1);

		int x1=baseX;
		int y1=baseY;

		int x2;
		int y2;

		double tempY1;

		for (int j = 0; j < 920; j++) {

			x2 = j*100 + 40;
			tempY1 = 260 - Math.cosh(j) - Math.sinh(j); //basePopulation1 * Math.cosh(Math.sqrt(eff_s * eff_r) * j) - Math.sqrt(eff_r / eff_s) * basePopulation2 * Math.sinh(Math.sqrt(eff_s * eff_r) * j));
			y2 = (int) Math.round(tempY1);

			g.fillOval(x2, y2, 4, 4);

			if (x2 == baseX) { g.drawLine(baseX, baseY, x2, y2);
			}
			else {g.drawLine(x1, y1, x2, y2);
			}

			x1=x2;
			y1=y2;

		}
/
		//GraphTaiwan in blau

		// farbe ändern
		g.setColor(Color.blue);

		int baseA = 40;
		int baseB = 260; //(int) Math.round(basePopulation2);

		int a1 = baseA;
		int b1 = baseB;

		int a2;
		int b2;

		double tempb1;

		for (int i = -3; i < 920; i++) {

			a2 = i*100 + 40;
			tempb1 = 260 - Math.cosh(i) - Math.sinh(i);; //(basePopulation2 * Math.cosh(Math.sqrt(eff_s * eff_r) * i) - Math.sqrt(eff_s / eff_r) * basePopulation1 * Math.sinh(Math.sqrt(eff_s * eff_r) * i));
			b2 = (int) Math.round(tempb1);


			g.fillOval(a2, b2, 4, 4);

			if (a1 == baseA){g.drawLine(baseA, baseB, a2, b2);
			}
			else {g.drawLine(a1, b1, a2, b2);
			}

			a1=a2;
			b1=b2;

		}

 */

	}
}

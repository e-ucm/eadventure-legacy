package es.eucm.eadventure.tracking.prv.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.tracking.prv.form.ConfigPanel.ChoiceOption;
import es.eucm.eadventure.tracking.prv.form.ConfigPanel.ConfigListener;
import es.eucm.eadventure.tracking.prv.form.ConfigPanel.DoubleOption;
import es.eucm.eadventure.tracking.prv.form.ConfigPanel.InnerOption;
import es.eucm.eadventure.tracking.prv.form.ConfigPanel.IntegerOption;
import es.eucm.eadventure.tracking.prv.form.ConfigPanel.StrategyOption;


/**
 * Demo para el panel de configuracion
 * 
 * @author mfreire
 */

public class Demo extends JFrame {

	private static final long serialVersionUID = 5393378737313833016L;
	
	public Demo() {
		super("Demo de panel de configuracion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel panelCentral = new JPanel(new GridLayout(3, 2, 4, 4));
		add(panelCentral, BorderLayout.EAST);

		// crea dos figuras
		final Figura f1 = new Figura("Primera");
		final Figura f2 = new Figura("Segunda");
		
		// crea un panel central y lo asocia con la primera figura
		final ConfigPanel<Figura> cp = creaPanelConfiguracion();
		// asocia el panel con la figura
		cp.setTarget(f1);
		// carga los valores de la figura en el panel
		cp.initialize();		
		add(cp, BorderLayout.WEST);
		
		// crea una etiqueta que dice si todo es valido
		final String textoTodoValido = "Todos los campos OK";
		final String textoHayErrores = "Hay errores en algunos campos";
		final JLabel valido = new JLabel(textoHayErrores);
		// este evento se lanza cada vez que la validez cambia
		cp.addConfigListener(new ConfigListener() {
			public void configChanged(boolean isConfigValid) {
				valido.setText(isConfigValid ? textoTodoValido: textoHayErrores);				
			}
		});
		add(valido, BorderLayout.SOUTH);
		
		// crea una etiqueta que indica la figura que se esta editando
		final JLabel panelEnEdicion = new JLabel("Editando figura 1");
		add(panelEnEdicion, BorderLayout.NORTH);
		
		// usado por todos los botones
		JButton boton;

		// crea botones para mostrar el estado de las figuras por consola
		boton = new JButton("muestra fig. 1");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.err.println(f1.toString());
			}
		});
		panelCentral.add(boton);
		boton = new JButton("muestra fig. 2");
		boton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.err.println(f2.toString());
			}
		});
		panelCentral.add(boton);

		// crea botones para sobreescribir el panel con las figuras
		boton = new JButton("fig. 1 a panel");
		boton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				cp.setTarget(f1);
				cp.initialize();
				panelEnEdicion.setText("Editando figura 1");
			}
		});
		panelCentral.add(boton);
		boton = new JButton("fig. 2 a panel");
		boton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				cp.setTarget(f2);
				cp.initialize();
				panelEnEdicion.setText("Editando figura 2");
			}
		});
		panelCentral.add(boton);

		// crea botones para sobreescribir las figuras con el panel
		boton = new JButton("panel a fig. 1");
		boton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				cp.setTarget(f1);
				cp.copyUpdate();
				panelEnEdicion.setText("Editando figura 1");
			}
		});
		panelCentral.add(boton);
		boton = new JButton("panel a fig. 2");
		boton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				cp.setTarget(f2);
				cp.copyUpdate();
				panelEnEdicion.setText("Editando figura 2");
			}
		});
		panelCentral.add(boton);
	}
	
	public ConfigPanel<Figura> creaPanelConfiguracion() {
		Color[] colores = new Color[] { Color.red, Color.blue, Color.green };
		Forma[] formas = new Forma[] { new Circulo(), new Rectangulo() };
		
		ConfigPanel<Figura> config = new ConfigPanel<Figura>();
		
		// se pueden a�adir las opciones de forma independiente, o "de seguido"; el resultado es el mismo.
		config.addOption(new IntegerOption<Figura>(  // -- entero
				"grosor (px)", 					     // texto a usar como etiqueta del campo
				"pixeles de grosor del borde",       // texto a usar como 'tooltip' cuando pasas el puntero
				"grosor",  						     // campo (espera que haya un getGrosor y un setGrosor)
				1, 10) {
		            @Override
                    protected boolean isValid (Object v){
		                return super.isValid( v );
		            }
		})							     // min y max (usa Integer.MIN_VALUE /MAX_VALUE para infinitos)
			  .addOption(new ChoiceOption<Figura>(	 // -- eleccion de objeto no-configurable
			    "color",							 // etiqueta 
			    "color del borde", 					 // tooltip
			    "color",   							 // campo (debe haber un getColor y un setColor)
			    colores))                            // elecciones posibles
			  .addOption(new DoubleOption<Figura>(   // -- doble, parecido a entero
			    "% transparencia", 					 // etiqueta
			    "transparencia del borde",           // tooltip
			    "transparencia",                     // campo
			    0, 100,							     // min y max, aplicando factor, si hay; vale usar Double.*_INFINITY) 
			    100))								 // opcional: factor de multiplicacion != 1.0, para mostrar porcentajes
			  .addOption(new StrategyOption<Figura>( // -- eleccion de objeto configurable
				"forma",							 // etiqueta
				"forma de la figura",                // tooltip
				"forma",                             // campo
				formas))                             // elecciones (deben implementar Cloneable)
				
			  // para cada clase de objeto interno, hay que definir sus opciones entre un beginInner y un endInner 
			  .beginInner(new InnerOption<Figura,Forma>(  
			  	"circulo",							 // titulo del sub-panel
			  	"opciones del circulo",				 // tooltip asociado
			  	"forma",							 // campo
			  	Circulo.class))						 // tipo que debe de tener ese campo para que se active el sub-panel
		  		  .addInner(new DoubleOption<Forma>(
		  		     "radio", "radio del circulo", "radio", 0, Integer.MAX_VALUE))
		  		  .endInner()						 // cierra este sub-panel
		  	  // igual, pero opciones para el caso 'forma de tipo rectangulo'  
              .beginInner(new InnerOption<Figura,Forma>( 
			  	"rectangulo", "opciones del rectangulo", "forma", Rectangulo.class))
		  		  .addInner(new DoubleOption<Forma>(
		  		     "ancho", "ancho del rectangulo", "ancho", 0, Double.POSITIVE_INFINITY))
		  		  .addInner(new DoubleOption<Forma>(
		  		     "alto", "alto del rectangulo", "alto", 0, Double.POSITIVE_INFINITY))
		  		  .endInner()

			  // y por ultimo, el punto (siempre estara visible)
			  .beginInner(new InnerOption<Figura,Punto>(
			  	"coordenadas", "coordenadas de la figura", "coordenadas", Punto.class))
		  		  .addInner(new DoubleOption<Forma>(
		  		     "x", "coordenada x", "x", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY))
		  		  .addInner(new DoubleOption<Forma>(
		  		     "y", "coordenada y", "y", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY))
		  		  .endInner()
		  		  
			  // y ahora ya cerramos el formulario
		  	  .endOptions();
		
		return config;
	}
	
	
	// construye y muestra la interfaz
	public static void main(String[] args) {
		
		Demo p = new Demo();
		p.setSize(600, 600);
		p.setVisible(true);	
	}
	
	// --- clases de ejemplo

	/** una figura */
	public static class Figura {
		private String nombre;
		
		private int grosor = 1; 		  			// grosor de la linea
		private double transparencia = 0; 			// porcentaje de transparencia
		private Color color; 			  			// color de pintado
		private Forma forma;			  			// forma (rectangulo o circulo)
		private Punto coordenadas = new Punto();	// coordenadas iniciales
		
		public Figura(String nombre) {
			this.nombre = nombre;
		}
		
		// getters y setters (compactados para reducir lineas)
		public Punto getCoordenadas() {	return coordenadas;	}
		public void setCoordenadas(Punto coordenadas) {	this.coordenadas = coordenadas;	}
		public double getTransparencia() { return transparencia; }
		public void setTransparencia(double transparencia) { this.transparencia = transparencia; }
		public int getGrosor() { return grosor;	}
		public void setGrosor(int grosor) { this.grosor = grosor; }
		public Color getColor() { return color; }
		public void setColor(Color color) {	this.color = color;	}
		public Forma getForma() { return forma;	}
		public void setForma(Forma forma) {	this.forma = forma;	}
		
		// toString, para hacer pruebas
		
        @Override
        public String toString() {
			return nombre  
				+ ":: punto: " + coordenadas 
				+ ", trans: " + transparencia
				+ ", grosor: " + grosor
				+ ", color: " + color
				+ ", forma: " + forma;
		}
	}	
	
	/** un punto */
	public static class Punto {
		private double x, y;	// coordenadas

		public double getX() { return x; }
		public void setX(double x) { this.x = x; }
		public double getY() { return y; }
		public void setY(double y) { this.y = y; }

		
        @Override
        public String toString() {
			return "(" + x + ", " + y + ")"; 
		}
	}
	
	/** una forma; implementa cloneable */
	public static abstract class Forma implements Cloneable {			
		public abstract void dibuja();
		
		// implementacion de 'clone' por defecto, suficiente para objetos sencillos
		
        @Override
        public Forma clone() { 
			try {
				return (Forma)super.clone();
			} catch (CloneNotSupportedException e) {
				throw new IllegalArgumentException(e);
			} 
		}
	}
	
	/** un circulo (una forma, y por tanto 'cloneable') */
	public static class Circulo extends Forma {
		private double radio = 1;

		public double getRadio() { return radio; }
		public void setRadio(double radio) { this.radio = radio; }
		
		
        @Override
        public void dibuja() { /* ... */ };
		
		
        @Override
        public String toString() {
			return "circulo con r=" + radio; 
		}
	}	
	
	/** un rectangulo (una forma, y por tanto 'cloneable') */
	public static class Rectangulo extends Forma {
		private double ancho = 1, alto = 1;
	
		public double getAncho() { return ancho; }
		public void setAncho(double ancho) { this.ancho = ancho; }
		public double getAlto() { return alto; }
		public void setAlto(double alto) { this.alto = alto; }

		
        @Override
        public void dibuja() { /* ... */ };

		
        @Override
        public String toString() {
			return "rectangulo de " + ancho  + "x" + alto; 
		}
	}	
}

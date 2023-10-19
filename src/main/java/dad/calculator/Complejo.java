package dad.calculator;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Complejo {
	private DoubleProperty real = new SimpleDoubleProperty();
    private DoubleProperty imaginario = new SimpleDoubleProperty();


    public Complejo() {
        this.real = new SimpleDoubleProperty(0.0); 
        this.imaginario = new SimpleDoubleProperty(0.0); 
    }

    public DoubleProperty realProperty() {
        return real;
    }

    public DoubleProperty imaginarioProperty() {
        return imaginario;
    }

    public double getReal() {
        return real.get();
    }

    public void setReal(double real) {
        this.real.set(real);
    }

    public double getImaginario() {
        return imaginario.get();
    }

    public void setImaginario(double imaginario) {
        this.imaginario.set(imaginario);
    }
    

}

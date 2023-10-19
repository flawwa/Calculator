package dad.calculator;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;


public class ComplexCalculator extends Application {

    private Label separadorLabel = new Label("+");
    private Label separadorLabel2 = new Label("+");
    private Label separadorLabel3 = new Label("+");

    @Override
    public void start(Stage primaryStage) throws Exception {
        ComboBox<String> operadorCombo = new ComboBox<>();
        operadorCombo.getItems().addAll("+", "-", "*", "/");
        operadorCombo.setValue("+");

        TextField op1Text = new TextField();
        TextField op2Text = new TextField();
        TextField op3Text = new TextField(); 
        TextField op4Text = new TextField(); 
        TextField resultText1 = new TextField();
        TextField resultText2 = new TextField();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        HBox hbox4 = new HBox();
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        VBox vbox3 = new VBox();
        
        Button button = new Button("=");
        
        //Lateral izquierdo
        hbox1.getChildren().addAll(vbox1, vbox2, vbox3);
        
        vbox1.getChildren().addAll(operadorCombo);
        hbox1.setSpacing(10);
        vbox1.setAlignment(Pos.CENTER);
        hbox1.setAlignment(Pos.CENTER);
        
        //Centro
        vbox2.getChildren().addAll(hbox2, hbox3, hbox4);
        hbox2.setSpacing(10);
        vbox2.setAlignment(Pos.CENTER);
        
        hbox2.getChildren().addAll(op1Text, separadorLabel, op2Text, new Label("i"));
        hbox3.getChildren().addAll(op3Text, separadorLabel2, op4Text, new Label("i"));
        hbox4.getChildren().addAll(resultText1, separadorLabel3, resultText2, new Label("i"));
        hbox4.setSpacing(10);
        
        //Lateral derecho
        vbox3.getChildren().addAll(button); 
        hbox3.setSpacing(10);
        vbox3.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(hbox1, 600, 400); // Crear una escena con el contenido deseado
        primaryStage.setScene(scene); // Configurar la escena en la ventana principal
        primaryStage.setTitle("Calculadora compleja JavaFX");
        primaryStage.show(); // Mostrar la ventana

        
        //BINDINGS
        Complejo result = new Complejo();
	    Complejo op01 = new Complejo();
	    Complejo op02 = new Complejo();
	    
		
		result.realProperty().addListener((o, ov, nv) -> System.out.println("result = " + nv));
		result.imaginarioProperty().addListener((o, ov, nv) -> System.out.println("result2 = " + nv));
		
		op1Text.textProperty().bindBidirectional(op01.realProperty(), new NumberStringConverter());
		op2Text.textProperty().bindBidirectional(op01.imaginarioProperty(), new NumberStringConverter());
		op3Text.textProperty().bindBidirectional(op02.realProperty(), new NumberStringConverter());
		op4Text.textProperty().bindBidirectional(op02.imaginarioProperty(), new NumberStringConverter());
		
		resultText1.textProperty().bind(result.realProperty().asString());
		resultText2.textProperty().bind(result.imaginarioProperty().asString());
        
        /*button.setOnAction((o, ov, nv) -> System.out.println("result=" + nv));
        result1.textProperty().bind(result1.asString());
        result2.textProperty().bind(result2.asString());*/ 


	    
		// Listener
		resultText1.textProperty().bind(Bindings.createStringBinding(() -> {
			
			String nv = operadorCombo.getValue();
			System.out.println("Ha seleccionado " + nv);

		    switch (nv) {
		        case "+":
		        	result.realProperty().bind(op01.realProperty().add(op02.realProperty()));
		        	result.imaginarioProperty().bind(op01.imaginarioProperty().add(op02.imaginarioProperty()));
		            break;
		            
		        case "-":
		        	result.realProperty().bind(op01.realProperty().subtract(op02.realProperty()));
		        	result.imaginarioProperty().bind(op01.imaginarioProperty().subtract(op02.imaginarioProperty()));
		            break;
		        case "*":
		        	result.realProperty().bind(op01.realProperty().multiply(op02.realProperty()).subtract(op01.imaginarioProperty().multiply(op02.imaginarioProperty())));
		            result.imaginarioProperty().bind(op01.realProperty().multiply(op02.imaginarioProperty()).add(op01.imaginarioProperty().multiply(op02.realProperty())));
		            break;
		        case "/":
		            double divisorReal = op02.realProperty().get();
		            double divisorImaginario = op02.imaginarioProperty().get();
		            result.realProperty().bind(
		                (op01.realProperty().multiply(divisorReal).add(op01.imaginarioProperty().multiply(divisorImaginario)))
		                    .divide(divisorReal * divisorReal + divisorImaginario * divisorImaginario)
		            );
		            result.imaginarioProperty().bind(
		                (op01.imaginarioProperty().multiply(divisorReal).subtract(op01.realProperty().multiply(divisorImaginario)))
		                    .divide(divisorReal * divisorReal + divisorImaginario * divisorImaginario)
		            );
		            break;
		    }
		    return String.valueOf(result.realProperty().get());
		    
		}, operadorCombo.valueProperty(), op01.realProperty(), op01.imaginarioProperty(), op02.realProperty(), op02.imaginarioProperty()));
        
	operadorCombo.getSelectionModel().selectFirst();
	resultText2.textProperty().bind(result.imaginarioProperty().asString());	

    }
}

package org.vaadin.example;



import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "idplayer",layout = MainLayout.class)
public class Hardi extends VerticalLayout{
	
	Grid<Player> grid =new Grid<>(Player.class);
	TextField filtertext = new TextField();
	public Hardi() {
		
		add(new H1("Microservice"));
	}

}

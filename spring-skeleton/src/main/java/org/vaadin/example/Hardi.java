package org.vaadin.example;



import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "hardi",layout = MainLayout.class)
public class Hardi extends VerticalLayout{
	
	public Hardi() {
		add(new H1("Microservice"));
	}

}

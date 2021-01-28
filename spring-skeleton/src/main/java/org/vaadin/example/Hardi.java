package org.vaadin.example;



import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "idplayer",layout = MainLayout.class)
public class Hardi extends VerticalLayout{
	
	Grid<Player> grid =new Grid<>(Player.class);
	
	PlayerService resultService;
	public Hardi(@Autowired PlayerService re) {
		resultService = re;
	
		
		addClassName("list-view");
		setSizeFull();
		configureGrid();
		
		Div content = new Div(grid);
		content.addClassName("content");
		content.setSizeFull();
		
		add(new H1("Microservice-Player"),content);
		updateList();
	}
	
	private void updateList() {
		grid.setItems(resultService.findAll());
		
	}
	
	private void configureGrid() {
		grid.addClassName("result-grid");
		grid.setSizeFull();
		//les noms doivent eetre gleich que ceux de la classe
		grid.setColumns("id");
		grid.getColumns().forEach(e->{
			e.setAutoWidth(true);
		});
		
		
	}

}

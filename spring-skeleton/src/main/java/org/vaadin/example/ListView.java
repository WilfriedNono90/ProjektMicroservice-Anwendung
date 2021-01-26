package org.vaadin.example;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;


@Route(value = "",layout = MainLayout.class)
@CssImport("./styles/shared-styles.css")
public class ListView extends VerticalLayout {
	
	Grid<Result> grid =new Grid<>(Result.class);
	TextField filtertext = new TextField();
	Button addButton = new Button();
	
	
	ResultService resultService;
	private MainViewForm form;
	
	public ListView(@Autowired ResultService resultRepository) {
		this.resultService = resultRepository;
		addClassName("list-view");
		setSizeFull();
		configureGrid();
		configureFilter();
		
		addButton.setIcon(VaadinIcon.PLUS.create());
		addButton.addClickListener(evt -> editResultat());
		
		form = new MainViewForm();
		
		form.addListener(MainViewForm.SaveEvent.class, this::saveResult);
		form.addListener(MainViewForm.DeleteEvent.class, this::deleteResult);
		form.addListener(MainViewForm.CloseEvent.class, evt-> closeEditor());
		
		Div content = new Div(grid,form);
		content.addClassName("content");
		content.setSizeFull();
		
		add(filtertext,addButton,content);
		updateList();
		closeEditor();
		
	}
	
	private void editResultat() {
		editResult(new Result(0L,0L,0L,0L,0L,new Date()));
	}

	private void saveResult(MainViewForm.SaveEvent evt) {
		resultService.save(evt.getResult());
		updateList();
		closeEditor();
		
	}
	
	private void deleteResult(MainViewForm.DeleteEvent evt) {
		resultService.delete(evt.getResult().getIdplayer());
		updateList();
		closeEditor();
		
	}
	
	
	

	private void closeEditor() {
		form.setResult(null);
		form.setVisible(false);
		removeClassName("editing");
		
	}

	private void configureFilter() {
		filtertext.setPlaceholder("Nach Idplayer Suchen");
		filtertext.setClearButtonVisible(true);
		//quand doit on agir, eager : a chaque frappe du clavier, easy : une fois quil arrete de saisir
		filtertext.setValueChangeMode(ValueChangeMode.LAZY);
		filtertext.addValueChangeListener(e->{
			updateList();
		});
		
		
	}

	private void updateList() {
		grid.setItems(resultService.findAll(filtertext.getValue()));
		
	}

	private void configureGrid() {
		grid.addClassName("result-grid");
		grid.setSizeFull();
		//les noms doivent eetre gleich que ceux de la classe
		grid.setColumns("id","idplayer","roundid","holeid","score","date");
		//ajouter une colonne
		/*grid.addColumn(result -> {
			Long result1 = result.getScore();
			return result == null ?"-":result1;
			
		}).setHeader("Company");*/ 
		//chaque element de la grille a la meme taille
		grid.getColumns().forEach(e->{
			e.setAutoWidth(true);
		});
		
		//active la selection monoligne
		grid.asSingleSelect().addValueChangeListener(evt -> editResult(evt.getValue()));
		
	}

	private void editResult(Result result) {
		if(result == null) {
			closeEditor();
		}else {
			form.setResult(result);
			form.setVisible(true);
			addClassName("editing");
		}
	}

}

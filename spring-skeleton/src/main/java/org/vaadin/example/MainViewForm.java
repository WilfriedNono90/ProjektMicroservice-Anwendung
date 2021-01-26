package org.vaadin.example;



import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.shared.Registration;

public class MainViewForm extends FormLayout {
	
	TextField id = new TextField("id");
	TextField idplayer = new TextField("idplayer");
	TextField roundid = new TextField("roundid");
	TextField holeid = new TextField("holeid");
	TextField score = new TextField("score");
	DatePicker datePicker  = new DatePicker("Date");
	
	
	Button saveButton = new Button("Save"); 
	Button deleteButton = new Button("Delete"); 
	Button cancelButton = new Button("Cancel"); 
	
	//binden linterface a des elements du code
	Binder<Result> binder = new BeanValidationBinder<>(Result.class);
	
	public MainViewForm() {
		addClassName("result-form");
		
		//va binden les feldder et lobjet
		//binder.bindInstanceFields(this);
		
		//on binden les valeurs saisies aux felder de ce formulaire en les convertissant
		binder.forField(idplayer).withConverter(
				new StringToLongConverter("muss Long value sein")
					).bind(Result::getId,Result::setId );
		
		binder.forField(idplayer).withConverter(
			new StringToLongConverter("muss Long value sein")
				).bind(Result::getIdplayer,Result::setIdplayer );
		
		binder.forField(roundid).withConverter(
				new StringToLongConverter("muss Long value sein")
					).bind(Result::getRoundid,Result::setRoundid );
		
		binder.forField(holeid).withConverter(
				new StringToLongConverter("muss Long value sein")
					).bind(Result::getHoleid,Result::setHoleid );
		binder.forField(score).withConverter(
				new StringToLongConverter("muss Long value sein")
					).bind(Result::getScore,Result::setScore );
		binder.forField(datePicker).withConverter(new LocalDateToDateConverter()).bind(Result::getDate,Result::setDate );
		
		add(
			idplayer,
			roundid,
			holeid,
			score,
			datePicker,
			createButtonLayout());
	}
	
	public void setResult(Result result) {
		binder.setBean(result);
	}

	private Component createButtonLayout() {
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
		cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		saveButton.addClickShortcut(Key.ENTER);
		cancelButton.addClickShortcut(Key.ESCAPE); 
		
		saveButton.addClickListener(click -> validationAndSave());
		deleteButton.addClickListener(click -> new DeleteEvent(this, binder.getBean()));
		cancelButton.addClickListener(click -> new CloseEvent(this));
		
		binder.addStatusChangeListener(evt -> saveButton.setEnabled(binder.isValid()));
		
		
		return new HorizontalLayout(saveButton,deleteButton,cancelButton);
	}
	
	private void validationAndSave() {
		if(binder.isValid()) {
			fireEvent(new SaveEvent(this, binder.getBean()));
		}
	}

	public static abstract class MainViewFormEvent extends ComponentEvent<MainViewForm> {
		  private Result Result;

		  protected MainViewFormEvent(MainViewForm source, Result Result) { 
		    super(source, false);
		    this.Result = Result;
		  }

		  public Result getResult() {
		    return Result;
		  }
		}

		public static class SaveEvent extends MainViewFormEvent {
		  SaveEvent(MainViewForm source, Result Result) {
		    super(source, Result);
		  }
		}

		public static class DeleteEvent extends MainViewFormEvent {
		  DeleteEvent(MainViewForm source, Result Result) {
		    super(source, Result);
		  }

		}

		public static class CloseEvent extends MainViewFormEvent {
		  CloseEvent(MainViewForm source) {
		    super(source, null);
		  }
		}

		public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
		    ComponentEventListener<T> listener) { 
		  return getEventBus().addListener(eventType, listener);
		}

}

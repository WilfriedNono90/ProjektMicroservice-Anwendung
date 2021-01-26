package org.vaadin.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api")
public class RestService {

	@Autowired
    private ResultService resultService;
	
	@GetMapping(path = "/result" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Result> getResult() {
		return resultService.findAll();
		
	}
	
	@GetMapping(path = "/result/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Result getOneResult(@PathVariable(value = "id") Long id) {
		return resultService.findById(id).get();
	}
	
	
	@PostMapping(path = "/result", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Result save(@RequestBody Result Result) {
		return resultService.save(Result);
	}
	
	@PutMapping(path = "/result/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Result save(@RequestBody Result Result, @PathVariable(value = "id") Long id) {
		return resultService.update(Result,id);
	}
	
	@DeleteMapping(path = "/result/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public boolean delete(@PathVariable Long id) {
		
		return resultService.delete(id);
		
	}

}
